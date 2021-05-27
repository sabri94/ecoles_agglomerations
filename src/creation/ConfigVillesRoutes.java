package creation;

import java.util.InputMismatchException;

import principal.AutresMethodes;
import principal.Menu;

/**
 * Classe qui contient les méthodes permettant la création des villes et des routes de la
 * communauté d'agglomération
 * @author Jatheesh Soundaranayagam, Liticia Bellil, Sabri Meftah
 *
 */

public class ConfigVillesRoutes {

	/**
	 * Le nombre de villes dans la communauté d'agglomération
	 */
	static int n;
	
	
	/**
	 * Méthode qui contient toutes les méthodes de la classe ConfigVillesRoutes, elle permet
	 * de créer les villes et les routes
	 */
	public static void creerVillesRoutes() {
		n = nbVilles();
		Ville[] villes = creerVilles();
		boolean[][] routes = AutresMethodes.initRoutes(n);
		Menu.ca = new CommunauteAgglomeration(routes, villes);
		if(n>1) creerRoutes();
	}
	
	
	/**
	 * Méthode qui demande le nombre de villes à l'utilisateur
	 * @return le nombre de villes entrées
	 */
	public static int nbVilles() {
		int n=0;
		boolean ok = false;
		
		System.out.println();
		while(!ok) {
			try {
				System.out.print("Combien y a-t-il de villes dans cette communauté d'agglomération ? ");
				n = Menu.sc.nextInt();
				if(n<1) {
					System.out.println("VEUILLEZ ENTRER UN NOMBRE ENTIER POSITIF.");
				}
				else ok = true;
			}
			catch(InputMismatchException e) {
				System.out.println("VEUILLEZ ENTRER UN NOMBRE ENTIER POSITIF.");
			}
			finally {
				Menu.sc.nextLine();
			}
		}
		return n;
	}
	
	
	/**
	 * Méthode qui crée les villes de la communauté d'agglomération
	 * @return le tableau contenant les villes de la communauté
	 */
	public static Ville[] creerVilles() {
		Ville[] villes = new Ville[n];
		
		for(int i=0; i<n; i++) {
			System.out.print("Ville " + (i+1) + " : ");
			String nomVille = Menu.sc.nextLine();
			
			for(int j=0; j<i; j++) {
				while(villes[j].getNom().equals(nomVille)) {
					System.out.print("Ville " + (i+1) + " : ");
					System.out.println("VOUS AVEZ DÉJÀ ENTRÉ CETTE VILLE.");
					nomVille = Menu.sc.nextLine();
				}
			}
			villes[i] = new Ville(nomVille, i, true);
		}
		
		return villes;
	}

		
	/*
	 * Méthode qui permet à l'utilisateur de créer les routes de la communauté d'agglomération
	 */
	public static void creerRoutes() {
		int choix=1;
		
		while(choix == 1) {
			System.out.println("\n1 : Ajouter une route");
			System.out.println("2 : Fin");
			choix = AutresMethodes.traitementException(1, 2); 	
					
			if(choix == 1) {
				System.out.print("Ville 1 : ");
				String nomVille = Menu.sc.nextLine();
				Ville ville1 = Menu.ca.stringToVille(nomVille);
						
				System.out.print("Ville 2 : ");
				nomVille = Menu.sc.nextLine();
				Ville ville2 = Menu.ca.stringToVille(nomVille);
				
				Menu.ca.ajoutRoute(ville1, ville2);
			}
		}
	}
}
