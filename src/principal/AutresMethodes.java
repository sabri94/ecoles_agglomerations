package principal;

import java.io.IOException;
import java.util.InputMismatchException;

/**
 * Classe contenant les m�thodes qui peuvent �tre utiles dans diff�rentes classes
 * @author Jatheesh Soundaranayagam, Liticia Bellil, Sabri Meftah
 *
 */

public class AutresMethodes {
	/**
	 * M�thode qui traite les cas o� on attend un entier et l'utilisateur rentre autre chose 
	 * @param borneBasse : l'entier correspondant au chiffre minimum que l'utilisateur peut rentrer
	 * @param borneHaute : l'entier correspondant au chiffre maximum
	 * @return l'entier entr� par l'utilisateur
	 */
	public static int traitementException(int borneBasse, int borneHaute) {
		int choix=0;
		
		boolean choixEstFait = false;
		while(!choixEstFait) {
			try {
				System.out.print("Votre choix : ");
				choix = Menu.sc.nextInt();
				if(choix<borneBasse || choix>borneHaute) {
					System.out.println("VEUILLEZ ENTRER UN CHIFFRE CORRESPONDANT � UNE DES OPTIONS PROPOS�ES.");
				}
				else choixEstFait = true;
			}
			catch(InputMismatchException e) {
				System.out.println("VEUILLEZ ENTRER UN CHIFFRE CORRESPONDANT � UNE DES OPTIONS PROPOS�ES.");
			}
			finally {
				Menu.sc.nextLine();
			}
		}
		return choix;		
	}
	
	
	/**
	 * M�thode permettant de revenir au menu principal
	 * @param chemin : chemin du fichier qui sera entr� en argument de la fonction main
	 * @throws IOException si le fichier n'existe pas, ou s'il y a une erreur lors de sa cr�ation
	 */
	public static void retourMenuPrincipal(String chemin) throws IOException {
		String[] cheminFichier = {chemin};
		Menu.main(cheminFichier);
	}
	
	
	/**
	 * M�thode qui initialise le tableau contenant les routes de la communaut� d'agglom�ration,
	 * toutes les cases du tableau sont �gales � false car aucune route n'existe lors de l'initialisation
	 * @param n : le nombre de ville donc le nombre de case dans chacun des tableaux du tableau � deux
	 * dimensions
	 * @return un tableau de bool�ens � deux dimensions dont toutes les cases sont �gales � false
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
