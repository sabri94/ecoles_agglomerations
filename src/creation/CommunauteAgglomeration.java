package creation;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe permettant de cr�er une communaut� d'agglom�ration et d'effectuer des 
 * modifications sur ses villes et ses routes
 * @author Jatheesh Soundaranayagam, Liticia Bellil, Sabri Meftah
 */

public class CommunauteAgglomeration {
	private boolean[][] routes;
	private Ville[] villes;	
	
	/**
	 * Constructeur de la classe CA
	 * @param routes : tableau � deux dimensions de bool�ens. Si l'�l�ment vaut true,
	 * il existe une route entre les deux villes correspondant � ses indices. Si 
	 * l'�l�ment vaut false, il n'existe pas de route entre les deux villes 
	 * correspondant � ses indices
	 * @param villes : tableau de type Ville contenant les villes de la communaut�
	 * d'agglom�ration
	 */
	public CommunauteAgglomeration(boolean[][] routes, Ville[] villes) {
		this.routes = routes;
		this.villes = villes;
	}
	
	
	/**
	 * M�thode renvoyant le tableau contenant les villes de la communaut� d'agglom�ration
	 * @return un tableau d'objets de type Ville
	 */
	public Ville[] getVilles() {
		return villes;
	}
	
	
	/**
	 * M�thode renvoyant le tableau contenant les routes de la communaut�
	 * @return un tableau de bool�ens � deux dimensions
	 */
	public boolean[][] getRoutes() {
		return routes;
	}
	
	
	/**
	 * M�thode renvoyant un objet Ville correspondant � son nom pris en param�tre
	 * @param nomVille : nom de la ville dont on veut obtenir l'objet
	 * @return un objet Ville dont le nom, l'indice et la pr�sence d'une �cole
	 * correspond � nomVille si nomVille existe bien dans la CA. Sinon, on retourne
	 * un objet Ville dont seul le nom correspond � nomVille
	 * 
	 */
	public Ville stringToVille(String nomVille) {
		for(Ville elt : villes) {
			if(elt.getNom().toLowerCase().equals(nomVille.toLowerCase())) {
				return elt;
			}
		}
		return new Ville(nomVille, -1, false);
	}
	
	
	/**
	 * M�thode permettant l'ajout d'une route si les villes pass�es en param�tres 
	 * font partie de la communaut� d'agglom�ration et ne sont pas identiques. 
	 * @param ville1 : ville correspondant au nom entr� par l'utilisateur lorsqu'il
	 * souhaite cr�er une route passant par cette ville
	 * @param ville2 : ville correspondant au nom entr� par l'utilisateur lorsqu'il
	 * souhaite cr�er une route passant par cette ville
	 */
	public void ajoutRoute(Ville ville1, Ville ville2) {
		if(ville1.getNom().toLowerCase() == ville2.getNom().toLowerCase()) {
			System.out.println("VOUS AVEZ ENTR� DEUX FOIS LA M�ME VILLE.");
		}
		
		/* Si la ville ne fait pas parti de la communaut� d'agglom�ration, la m�thode
		 * charToVille() appel�e dans le main, cr�� un objet Ville d'indice -1, c'est 
		 * pour cela qu'on v�rifie si l'indice de la ville est inf�rieur � 0
		 */
		else if(ville1.getIndice()<0 && ville2.getIndice()<0) {
			System.out.println(ville1.getNom() + " ET " + ville2.getNom() + " NE FONT PAS PARTIE DE LA COMMUNAUT� D'AGGLOM�RATION.");
		}
		else if(ville1.getIndice()<0) {
			System.out.println(ville1.getNom() + " NE FAIT PAS PARTIE DE LA COMMUNAUT� D'AGGLOM�RATION.");
		}
		else if(ville2.getIndice()<0) {
			System.out.println(ville2.getNom() + " NE FAIT PAS PARTIE DE LA COMMUNAUT� D'AGGLOM�RATION.");
		}
		else if(routes[ville1.getIndice()][ville2.getIndice()]) {
			System.out.println("LA ROUTE " + ville1.getNom() + "-" + ville2.getNom() + " EXISTE D�J�.");
		}
		else {
			routes[ville1.getIndice()][ville2.getIndice()] = true;
			routes[ville2.getIndice()][ville1.getIndice()] = true;
			System.out.println("La route " + ville1.getNom() + "-" + ville2.getNom() + " a �t� ajout�e.");
		}
	}
	
	
	/**
	 * M�thode permettant d'ajouter une �cole dans la ville entr�e en param�tre
	 * @param ville : ville dans laquelle on veut ajouter une �cole
	 */
	public void ajoutEcole(Ville ville) {
		if(ville.getIndice()<0) {
			System.out.println(ville.getNom() + " NE FAIT PAS PARTIE DE LA COMMUNAUT� D'AGGLOM�RATION.");
		}
		else if(!ville.isThereEcole()) ville.changeEcole();
	}
	
	
	/**
	 * M�thode permettant de retrouver un objet Ville � partir de son indice
	 * @param i : indice de la ville � retrouver
	 * @return la ville dont l'indice est �gal � i
	 */
	public Ville indiceToVille(int i) {
		for(Ville elt : villes) {
			if(elt.getIndice() == i) return elt;
		}
		return new Ville("", -1, false);
	}
	
	
	/**
	 * M�thode qui retourne une liste de villes correpondantes aux villes reli�es par
	 * une route � la ville entr�e en param�tre
	 * @param v : ville dont on veut connaitre les villes voisines
	 * @return la liste des villes voisines de v
	 */
	public List<Ville> villesVoisines(Ville v) {
		List<Ville> villesVoisines = new ArrayList<Ville>();
		for(int i = 0; i<routes.length; i++) {
			if(routes[v.getIndice()][i]) {
				villesVoisines.add(indiceToVille(i));
			}
		}
		return villesVoisines;
	}
	
	
	/**
	 * M�thode qui supprime l'�cole de la ville entr�e en param�tre, si elle en a une, et si cela n'enfreint
	 * pas la contrainte d'accessibilit�
	 * @param ville : ville dans laquelle on veut supprimer l'�cole
	 */
	public List<Ville> suppressionEcole(Ville ville) {
		List<Ville> villesSansEcole = new ArrayList<Ville>();
		List<Ville> villesSansAcces = new ArrayList<Ville>();
		int compteur=0;
		
		if(ville.isThereEcole()) {
			
			/* On supprime l'�cole de ville, la ville entr�e en param�tre, en attendant de voir si il n'y a
			 * pas quelque chose qui pourrait emp�cher cette suppression 
			 */
			ville.changeEcole();
			
			for(Ville elt : villesVoisines(ville)) {
				if(!elt.isThereEcole()) villesSansEcole.add(elt);
			}
			
			/* Si toutes les villes voisines de ville n'ont pas d'�cole, cela veut dire que si on supprime
			 * l'�cole de ville, ville n'a plus acc�s � une �cole, on l'ajoute donc � villesSansAcces			
			 */
			if(villesSansEcole.size()==villesVoisines(ville).size()) {
				villesSansAcces.add(ville);
			}
			
			/* On v�rifie pour chaque ville elt de villesSansEcole si ses villes voisines n'ont pas d'�cole. 
			 * A chaque fois que c'est le cas, un compteur s'incr�mente. Si le compteur est �gal � la taille
			 * de la liste des villes voisines de la ville elt, cela veut dire qu'aucune de ces derni�res 
			 * n'a d'�cole. Dans ce cas, elt n'a plus acc�s � une �cole, elle est donc ajout�e � 
			 * villesSansAcces
			 */
			for(Ville elt : villesSansEcole) {
				for(Ville v : villesVoisines(elt)) {
					if(v.isThereEcole()) break;
					else compteur++;
				}
				if(compteur==villesVoisines(elt).size()) {
					villesSansAcces.add(elt);
				}
				compteur=0;
			}
			
			/* S'il existe une ou des villes qui risquent de ne plus avoir acc�s � une �cole, on remet une �cole 
			 * dans ville 
			 */				
			if(!villesSansAcces.isEmpty()) ville.changeEcole();
		}
		return villesSansAcces;
	}
	
	
	/**
	 * Cette m�thode indique si la suppression de la ville pass�e en param�tre respecte la contrainte d'accessibilit� 
	 * ou non, mais elle ne la supprime pas 
	 * @param ville : la ville pour laquelle on v�rifie s'il est possible de la supprimer 
	 * @return true si la suppression de la ville est possible, false sinon
	 */
	public boolean contrainteRespectee(Ville ville) {
		if(suppressionEcole(ville).isEmpty()) return true;
		else return false;
	}
	
	
	/**
	 * M�thode qui affiche le nom des villes dans lesquelles il y a une �cole
	 */
	public void afficheVilleAvecEcole() {
		System.out.print("Les villes dans lesquelles il y a une école : ");
		for(Ville elt : villes) {
			if(elt.isThereEcole()) {
				System.out.print(elt.getNom() + " ");
			}
		}
		System.out.println();
	}
	
	
	/**
	 * M�thode qui retire les �coles dans toutes les villes
	 */
	public void retirerToutesEcoles() {
		for(Ville v : villes) {
			if(v.isThereEcole()) v.changeEcole();
		}
	}
	
	
	/**
	 * M�thode qui met une �cole dans chaque ville 
	 */
	public void remettreToutesEcoles() {
		for(Ville v : villes) {
			if(!v.isThereEcole()) v.changeEcole();
		}
	}
	
	
	/**
	 * M�thode qui v�rifie si toutes les villes ont une �cole (si la solution na�ve est adopt�e)
	 * @return true si la solution na�ve est adopt�e, false sinon
	 */
	public boolean isSolutionNaive() {
		for(Ville v : villes) {
			if(!v.isThereEcole()) return false;
		}
		return true;
	}
	
	
	/**
	 * M�thode qui retourne une chaine de caract�res qui contient toutes les informations sur la communaut� d'agglom�ration :
	 * les villes, les routes et les �coles
	 * @returns les informations de la communaut� sous la forme d'une chaine de caract�res
	 */
	@Override
	public String toString() {
		String s="";
		
		for(int i=0; i<villes.length; i++) {
			s += "ville(" + villes[i].getNom() + ").\n";
		}
		
		for(int i=0; i<routes.length; i++) {
			for(int j=0; j<routes.length; j++) {
				if(routes[i][j]) {
					routes[j][i] = false;
					s += "route(" + villes[i].getNom() + "," + villes[j].getNom() + ").\n";
				}
			}
		}
		
		if(!isSolutionNaive()) {
			for(int i=0; i<villes.length; i++) {
				if(villes[i].isThereEcole()) {
					s += "ecole(" + villes[i].getNom() + ").\n";
				}
			}
		}	
		return s;
	}
}		