package resolution;

import java.io.IOException;

import creation.Ville;
import principal.AutresMethodes;
import principal.Menu;


/**
 * Classe qui contient les m�thodes permettant de r�soudre manuellement la communaut� d'agglom�ration 
 * @authors Jatheesh Soundaranayagam, Liticia Bellil, Sabri Meftah
 *
 */
public class Manuel {
	
	/**
	 * M�thode qui r�soud la communaut� d'agglom�ration
	 * @throws IOException manipulations du fichier
	 */
	public static void resoudre() throws IOException {
		int choix = 1;
		
		while(choix != 3) {
			choix = menu();
			
			switch(choix) {
			case 1:
				ajout();
				break;
				
			case 2:
				retrait();
				break;
				
			case 3: 
				System.out.println("\nSauvegardez si vous souhaitez conserver les changements ;-)");
				AutresMethodes.retourMenuPrincipal(Menu.file.getPath());
				break;
				
			default: break;
			}	
		}	
	}
	
	
	/**
	 * M�thode qui affiche le menu pour l'ajout et la suppression d'�coles
	 * @return le choix de l'utilisateur
	 */
	public static int menu() {
		System.out.println("\n1 : Ajouter une �cole");
		System.out.println("2 : Retirer une �cole");
		System.out.println("3 : Fin");
		return AutresMethodes.traitementException(1, 3);
	}
	
	
	/**
	 * M�thode qui traite le choix 1
	 */
	public static void ajout() {
		System.out.print("Nom de la ville : ");
		String nomVille = Menu.sc.nextLine();
		Ville ville = Menu.ca.stringToVille(nomVille);
		Menu.ca.ajoutEcole(ville);
		Menu.ca.afficheVilleAvecEcole();
	}
	
	
	/**
	 * M�thode qui traite le choix 2
	 */
	public static void retrait() {
		System.out.print("Nom de la ville : ");
		String nomVille = Menu.sc.nextLine();
		Ville ville = Menu.ca.stringToVille(nomVille);
		
		messageErreurSuppression(ville);
	}
	
	
	/**
	 * M�thode qui affiche un message d'erreur si la ville entr�e en param�tre ne peut pas �tre supprim�e
	 * @param ville : ville que l'utilisateur veut supprimer
	 */
	public static void messageErreurSuppression(Ville ville) {
		String str = "";
		
		if(ville.getIndice()<0) {
			System.out.println(ville.getNom() + " NE FAIT PAS PARTIE DE LA COMMUNAUT� D'AGGLOM�RATION.");
		}
		for(int i=1; i<Menu.ca.suppressionEcole(ville).size()-1; i++) {
			str += ", " + Menu.ca.suppressionEcole(ville).get(i).getNom();
		}
		if(Menu.ca.suppressionEcole(ville).size()>1) {
			System.out.println("L'�COLE DE LA VILLE " + ville.getNom() + " N'A PAS �T� SUPPRIM�E CAR LES VILLES " + 
			Menu.ca.suppressionEcole(ville).get(0).getNom() + str + " et " + Menu.ca.suppressionEcole(ville).get(Menu.ca.suppressionEcole(ville).size()-1).getNom() + 
			" N'AURAIENT PLUS EU ACC�S � UNE �COLE.");
		}
		if(Menu.ca.suppressionEcole(ville).size() == 1) {
			System.out.println("L'�COLE DE LA VILLE " + ville.getNom() + " N'A PAS �T� SUPPRIM�E CAR LA VILLE " + 
			Menu.ca.suppressionEcole(ville).get(0).getNom() + " N'AURAIT PLUS EU ACC�S � UNE �COLE.");		
		}
		
		Menu.ca.afficheVilleAvecEcole();
	}
}

