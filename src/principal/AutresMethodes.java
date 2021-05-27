package principal;

import java.io.IOException;
import java.util.InputMismatchException;

/**
 * Classe contenant les méthodes qui peuvent être utiles dans différentes classes
 * @author Jatheesh Soundaranayagam, Liticia Bellil, Sabri Meftah
 *
 */

public class AutresMethodes {
	/**
	 * Méthode qui traite les cas où on attend un entier et l'utilisateur rentre autre chose 
	 * @param borneBasse : l'entier correspondant au chiffre minimum que l'utilisateur peut rentrer
	 * @param borneHaute : l'entier correspondant au chiffre maximum
	 * @return l'entier entré par l'utilisateur
	 */
	public static int traitementException(int borneBasse, int borneHaute) {
		int choix=0;
		
		boolean choixEstFait = false;
		while(!choixEstFait) {
			try {
				System.out.print("Votre choix : ");
				choix = Menu.sc.nextInt();
				if(choix<borneBasse || choix>borneHaute) {
					System.out.println("VEUILLEZ ENTRER UN CHIFFRE CORRESPONDANT À UNE DES OPTIONS PROPOSÉES.");
				}
				else choixEstFait = true;
			}
			catch(InputMismatchException e) {
				System.out.println("VEUILLEZ ENTRER UN CHIFFRE CORRESPONDANT À UNE DES OPTIONS PROPOSÉES.");
			}
			finally {
				Menu.sc.nextLine();
			}
		}
		return choix;		
	}
	
	
	/**
	 * Méthode permettant de revenir au menu principal
	 * @param chemin : chemin du fichier qui sera entré en argument de la fonction main
	 * @throws IOException si le fichier n'existe pas, ou s'il y a une erreur lors de sa création
	 */
	public static void retourMenuPrincipal(String chemin) throws IOException {
		String[] cheminFichier = {chemin};
		Menu.main(cheminFichier);
	}
	
	
	/**
	 * Méthode qui initialise le tableau contenant les routes de la communauté d'agglomération,
	 * toutes les cases du tableau sont égales à false car aucune route n'existe lors de l'initialisation
	 * @param n : le nombre de ville donc le nombre de case dans chacun des tableaux du tableau à deux
	 * dimensions
	 * @return un tableau de booléens à deux dimensions dont toutes les cases sont égales à false
	 */
	public static boolean[][] initRoutes(int n) {
		boolean[][] routes = new boolean[n][n];
		for(int i=0; i<n; i++) {
			for(int j=1; j<n; j++) {
				routes[i][j] = false;
			}
		}
		return routes;
	}
}
