package creation;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe permettant de créer une communauté d'agglomération et d'effectuer des 
 * modifications sur ses villes et ses routes
 * @author Jatheesh Soundaranayagam, Liticia Bellil, Sabri Meftah
 */

public class CommunauteAgglomeration {
	private boolean[][] routes;
	private Ville[] villes;	
	
	/**
	 * Constructeur de la classe CA
	 * @param routes : tableau à deux dimensions de booléens. Si l'élément vaut true,
	 * il existe une route entre les deux villes correspondant à ses indices. Si 
	 * l'élément vaut false, il n'existe pas de route entre les deux villes 
	 * correspondant à ses indices
	 * @param villes : tableau de type Ville contenant les villes de la communauté
	 * d'agglomération
	 */
	public CommunauteAgglomeration(boolean[][] routes, Ville[] villes) {
		this.routes = routes;
		this.villes = villes;
	}
	
	
	/**
	 * Méthode renvoyant le tableau contenant les villes de la communauté d'agglomération
	 * @return un tableau d'objets de type Ville
	 */
	public Ville[] getVilles() {
		return villes;
	}
	
	
	/**
	 * Méthode renvoyant le tableau contenant les routes de la communauté
	 * @return un tableau de booléens à deux dimensions
	 */
	public boolean[][] getRoutes() {
		return routes;
	}
	
	
	/**
	 * Méthode renvoyant un objet Ville correspondant à son nom pris en paramètre
	 * @param nomVille : nom de la ville dont on veut obtenir l'objet
	 * @return un objet Ville dont le nom, l'indice et la présence d'une école
	 * correspond à nomVille si nomVille existe bien dans la CA. Sinon, on retourne
	 * un objet Ville dont seul le nom correspond à nomVille
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
	 * Méthode permettant l'ajout d'une route si les villes passées en paramètres 
	 * font partie de la communauté d'agglomération et ne sont pas identiques. 
	 * @param ville1 : ville correspondant au nom entré par l'utilisateur lorsqu'il
	 * souhaite créer une route passant par cette ville
	 * @param ville2 : ville correspondant au nom entré par l'utilisateur lorsqu'il
	 * souhaite créer une route passant par cette ville
	 */
	public void ajoutRoute(Ville ville1, Ville ville2) {
		if(ville1.getNom().toLowerCase() == ville2.getNom().toLowerCase()) {
			System.out.println("VOUS AVEZ ENTRÉ DEUX FOIS LA MÊME VILLE.");
		}
		
		/* Si la ville ne fait pas parti de la communauté d'agglomération, la méthode
		 * charToVille() appelée dans le main, créé un objet Ville d'indice -1, c'est 
		 * pour cela qu'on vérifie si l'indice de la ville est inférieur à 0
		 */
		else if(ville1.getIndice()<0 && ville2.getIndice()<0) {
			System.out.println(ville1.getNom() + " ET " + ville2.getNom() + " NE FONT PAS PARTIE DE LA COMMUNAUTÉ D'AGGLOMÉRATION.");
		}
		else if(ville1.getIndice()<0) {
			System.out.println(ville1.getNom() + " NE FAIT PAS PARTIE DE LA COMMUNAUTÉ D'AGGLOMÉRATION.");
		}
		else if(ville2.getIndice()<0) {
			System.out.println(ville2.getNom() + " NE FAIT PAS PARTIE DE LA COMMUNAUTÉ D'AGGLOMÉRATION.");
		}
		else if(routes[ville1.getIndice()][ville2.getIndice()]) {
			System.out.println("LA ROUTE " + ville1.getNom() + "-" + ville2.getNom() + " EXISTE DÉJÀ.");
		}
		else {
			routes[ville1.getIndice()][ville2.getIndice()] = true;
			routes[ville2.getIndice()][ville1.getIndice()] = true;
			System.out.println("La route " + ville1.getNom() + "-" + ville2.getNom() + " a été ajoutée.");
		}
	}
	
	
	/**
	 * Méthode permettant d'ajouter une école dans la ville entrée en paramètre
	 * @param ville : ville dans laquelle on veut ajouter une école
	 */
	public void ajoutEcole(Ville ville) {
		if(ville.getIndice()<0) {
			System.out.println(ville.getNom() + " NE FAIT PAS PARTIE DE LA COMMUNAUTÉ D'AGGLOMÉRATION.");
		}
		else if(!ville.isThereEcole()) ville.changeEcole();
	}
	
	
	/**
	 * Méthode permettant de retrouver un objet Ville à partir de son indice
	 * @param i : indice de la ville à retrouver
	 * @return la ville dont l'indice est égal à i
	 */
	public Ville indiceToVille(int i) {
		for(Ville elt : villes) {
			if(elt.getIndice() == i) return elt;
		}
		return new Ville("", -1, false);
	}
	
	
	/**
	 * Méthode qui retourne une liste de villes correpondantes aux villes reliées par
	 * une route à la ville entrée en paramètre
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
	 * Méthode qui supprime l'école de la ville entrée en paramètre, si elle en a une, et si cela n'enfreint
	 * pas la contrainte d'accessibilité
	 * @param ville : ville dans laquelle on veut supprimer l'école
	 */
	public List<Ville> suppressionEcole(Ville ville) {
		List<Ville> villesSansEcole = new ArrayList<Ville>();
		List<Ville> villesSansAcces = new ArrayList<Ville>();
		int compteur=0;
		
		if(ville.isThereEcole()) {
			
			/* On supprime l'école de ville, la ville entrée en paramètre, en attendant de voir si il n'y a
			 * pas quelque chose qui pourrait empêcher cette suppression 
			 */
			ville.changeEcole();
			
			for(Ville elt : villesVoisines(ville)) {
				if(!elt.isThereEcole()) villesSansEcole.add(elt);
			}
			
			/* Si toutes les villes voisines de ville n'ont pas d'école, cela veut dire que si on supprime
			 * l'école de ville, ville n'a plus accès à une école, on l'ajoute donc à villesSansAcces			
			 */
			if(villesSansEcole.size()==villesVoisines(ville).size()) {
				villesSansAcces.add(ville);
			}
			
			/* On vérifie pour chaque ville elt de villesSansEcole si ses villes voisines n'ont pas d'école. 
			 * A chaque fois que c'est le cas, un compteur s'incrémente. Si le compteur est égal à la taille
			 * de la liste des villes voisines de la ville elt, cela veut dire qu'aucune de ces dernières 
			 * n'a d'école. Dans ce cas, elt n'a plus accès à une école, elle est donc ajoutée à 
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
			
			/* S'il existe une ou des villes qui risquent de ne plus avoir accès à une école, on remet une école 
			 * dans ville 
			 */				
			if(!villesSansAcces.isEmpty()) ville.changeEcole();
		}
		return villesSansAcces;
	}
	
	
	/**
	 * Cette méthode indique si la suppression de la ville passée en paramètre respecte la contrainte d'accessibilité 
	 * ou non, mais elle ne la supprime pas 
	 * @param ville : la ville pour laquelle on vérifie s'il est possible de la supprimer 
	 * @return true si la suppression de la ville est possible, false sinon
	 */
	public boolean contrainteRespectee(Ville ville) {
		if(suppressionEcole(ville).isEmpty()) return true;
		else return false;
	}
	
	
	/**
	 * Méthode qui affiche le nom des villes dans lesquelles il y a une école
	 */
	public void afficheVilleAvecEcole() {
		System.out.print("Les villes dans lesquelles il y a une Ã©cole : ");
		for(Ville elt : villes) {
			if(elt.isThereEcole()) {
				System.out.print(elt.getNom() + " ");
			}
		}
		System.out.println();
	}
	
	
	/**
	 * Méthode qui retire les écoles dans toutes les villes
	 */
	public void retirerToutesEcoles() {
		for(Ville v : villes) {
			if(v.isThereEcole()) v.changeEcole();
		}
	}
	
	
	/**
	 * Méthode qui met une école dans chaque ville 
	 */
	public void remettreToutesEcoles() {
		for(Ville v : villes) {
			if(!v.isThereEcole()) v.changeEcole();
		}
	}
	
	
	/**
	 * Méthode qui vérifie si toutes les villes ont une école (si la solution naïve est adoptée)
	 * @return true si la solution naïve est adoptée, false sinon
	 */
	public boolean isSolutionNaive() {
		for(Ville v : villes) {
			if(!v.isThereEcole()) return false;
		}
		return true;
	}
	
	
	/**
	 * Méthode qui retourne une chaine de caractères qui contient toutes les informations sur la communauté d'agglomération :
	 * les villes, les routes et les écoles
	 * @returns les informations de la communauté sous la forme d'une chaine de caractères
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