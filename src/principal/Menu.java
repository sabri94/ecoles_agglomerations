package principal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import creation.CommunauteAgglomeration;
import creation.ConfigVillesRoutes;
import fichier.RecupererCA;
import fichier.Sauvegarder;
import resolution.Automatique;
import resolution.Manuel;

/**
 * Classe contenant la méthode principale main
 * @author Jatheesh Soundaranayagam, Liticia Bellil, Sabri Meftah
 *
 */
public class Menu {
	/**
	 * Scanner utilisé dans tout le programme
	 */
	public static Scanner sc = new Scanner(System.in);
	/**
	 * Fichier entré en argument dans le terminal
	 */
	public static File file;
	/**
	 * Communauté d'agglomération qui est modifiée dans différentes parties du programme
	 */
	public static CommunauteAgglomeration ca;
	

	/**
	 * Méthode principale main
	 * @param : tableau de strings contenant les arguments entrés dans le terminal
	 * @throws IOException contient des méthodes suceptibles de lever une exception
	 * suite Ã  une erreur au niveau de la création d'un fichier par exemple
	 */
	public static void main(String[] args) throws IOException {
		file = new File(args[0]);
		verifArgument();
		
		int choix = menuPrincipal();
		switch(choix) {
			case 1:
				obtenirLaCA();
				Manuel.resoudre();
				break;
				
			case 2:
				obtenirLaCA();
				Automatique.resoudre();
				break;
				
			case 3:
				Sauvegarder.save();
				break;
				
			case 4:	
				sc.close();
				System.exit(0);
					
			default: break;	
		}		
	}
	
	
	/**
	 * Méthode qui vérifie si le chemin entré en argument du programme existe, et s'il s'agit bien d'un fichier txt
	 */
	public static void verifArgument() {
		if(file.getPath().length()<4 || !file.getPath().substring(file.getPath().length()-4).equals(".txt")) {
			System.out.println("ERREUR : l'argument n'est pas un fichier texte\n");
			System.exit(1);
		}
		
		/*On essaye de créer le fichier pour vérifier si le chemin entré en argument est correct. Si c'est le cas,
		 * on le supprime immédiatement car il faut que l'utilisateur sauvegarde pour créer un fichier. Sinon, le
		 * programme affiche une erreur, puis s'arrête
		 */
		if(!file.exists()) {
			try {
				if(!file.exists()) {
					file.createNewFile();
					file.delete();
				}
			}
			catch(IOException e) {
				System.out.println("ERREUR : le chemin entré en argument n'existe pas\n");
				System.exit(1);
			}
		}
	}
	
	
	/**
	 * Méthode contenant le menu principal
	 * @return le chiffre correspondant au choix de l'utilisateur
	 */
	public static int menuPrincipal() {
		System.out.println("1 : Résoudre manuellement");
		System.out.println("2 : Résoudre automatiquement");
		System.out.println("3 : Sauvegarder");
		System.out.println("4 : Fin");
		
		return AutresMethodes.traitementException(1, 4);
	}
	
	
	/**
	 * Méthode qui récupère la première ligne du fichier argument s'il existe
	 * @return la première ligne du fichier s'il existe
	 * @throws IOException on vérifie dans cette méthode, si le fichier argument existe
	 */
	public static String premiereLigneFichier() throws IOException {
		String line = "";
		if(file.exists()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			line = br.readLine();
			br.close();
		}
		return line;
	}
	
	
	/**
	 * Méthode qui permet d'obtenir les villes et les routes de la communauté. En les demandant à l'utilisateur
	 * si le fichier argument n'existe pas, en la construisant depuis le fichier s'il existe
	 * @throws IOException la méthode File.exists() est utilisée
	 */
	public static void obtenirLaCA() throws IOException {
		if(!file.exists() || premiereLigneFichier()==null) {
			ConfigVillesRoutes.creerVillesRoutes();
		}
		else RecupererCA.constructionCA();
	}
}
