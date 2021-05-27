package resolution;

import java.io.IOException;

import creation.Ville;
import principal.AutresMethodes;
import principal.Menu;


/**
 * Classe qui contient les méthodes permettant de résoudre manuellement la communauté d'agglomération 
 * @authors Jatheesh Soundaranayagam, Liticia Bellil, Sabri Meftah
 *
 */
public class Manuel {
	
	/**
	 * Méthode qui résoud la communauté d'agglomération
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
	 * Méthode qui affiche le menu pour l'ajout et la suppression d'écoles
	 * @return le choix de l'utilisateur
	 */
	public static int menu() {
		System.out.println("\n1 : Ajouter une école");
		System.out.println("2 : Retirer une école");
		System.out.println("3 : Fin");
		return AutresMethodes.traitementException(1, 3);
	}
	
	
	/**
	 * Méthode qui traite le choix 1
	 */
	public static void ajout() {
		System.out.print("Nom de la ville : ");
		String nomVille = Menu.sc.nextLine();
		Ville ville = Menu.ca.stringToVille(nomVille);
		Menu.ca.ajoutEcole(ville);
		Menu.ca.afficheVilleAvecEcole();
	}
	
	
	/**
	 * Méthode qui traite le choix 2
	 */
	public static void retrait() {
		System.out.print("Nom de la ville : ");
		String nomVille = Menu.sc.nextLine();
		Ville ville = Menu.ca.stringToVille(nomVille);
		
		messageErreurSuppression(ville);
	}
	
	
	/**
	 * Méthode qui affiche un message d'erreur si la ville entrée en paramètre ne peut pas être supprimée
	 * @param ville : ville que l'utilisateur veut supprimer
	 */
	public static void messageErreurSuppression(Ville ville) {
		String str = "";
		
		if(ville.getIndice()<0) {
			System.out.println(ville.getNom() + " NE FAIT PAS PARTIE DE LA COMMUNAUTÉ D'AGGLOMÉRATION.");
		}
		for(int i=1; i<Menu.ca.suppressionEcole(ville).size()-1; i++) {
			str += ", " + Menu.ca.suppressionEcole(ville).get(i).getNom();
		}
		if(Menu.ca.suppressionEcole(ville).size()>1) {
			System.out.println("L'ÉCOLE DE LA VILLE " + ville.getNom() + " N'A PAS ÉTÉ SUPPRIMÉE CAR LES VILLES " + 
			Menu.ca.suppressionEcole(ville).get(0).getNom() + str + " et " + Menu.ca.suppressionEcole(ville).get(Menu.ca.suppressionEcole(ville).size()-1).getNom() + 
			" N'AURAIENT PLUS EU ACCÈS À UNE ÉCOLE.");
		}
		if(Menu.ca.suppressionEcole(ville).size() == 1) {
			System.out.println("L'ÉCOLE DE LA VILLE " + ville.getNom() + " N'A PAS ÉTÉ SUPPRIMÉE CAR LA VILLE " + 
			Menu.ca.suppressionEcole(ville).get(0).getNom() + " N'AURAIT PLUS EU ACCÈS À UNE ÉCOLE.");		
		}
		
		Menu.ca.afficheVilleAvecEcole();
	}
}

