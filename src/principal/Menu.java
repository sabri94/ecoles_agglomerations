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
 * Classe contenant la m�thode principale main
 * @author Jatheesh Soundaranayagam, Liticia Bellil, Sabri Meftah
 *
 */
public class Menu {
	/**
	 * Scanner utilis� dans tout le programme
	 */
	public static Scanner sc = new Scanner(System.in);
	/**
	 * Fichier entr� en argument dans le terminal
	 */
	public static File file;
	/**
	 * Communaut� d'agglom�ration qui est modifi�e dans diff�rentes parties du programme
	 */
	public static CommunauteAgglomeration ca;
	

	/**
	 * M�thode principale main
	 * @param : tableau de strings contenant les arguments entr�s dans le terminal
	 * @throws IOException contient des m�thodes suceptibles de lever une exception
	 * suite à une erreur au niveau de la cr�ation d'un fichier par exemple
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
	 * M�thode qui v�rifie si le chemin entr� en argument du programme existe, et s'il s'agit bien d'un fichier txt
	 */
	public static void verifArgument() {
		if(file.getPath().length()<4 || !file.getPath().substring(file.getPath().length()-4).equals(".txt")) {
			System.out.println("ERREUR : l'argument n'est pas un fichier texte\n");
			System.exit(1);
		}
		
		/*On essaye de cr�er le fichier pour v�rifier si le chemin entr� en argument est correct. Si c'est le cas,
		 * on le supprime imm�diatement car il faut que l'utilisateur sauvegarde pour cr�er un fichier. Sinon, le
		 * programme affiche une erreur, puis s'arr�te
		 */
		if(!file.exists()) {
			try {
				if(!file.exists()) {
					file.createNewFile();
					file.delete();
				}
			}
			catch(IOException e) {
				System.out.println("ERREUR : le chemin entr� en argument n'existe pas\n");
				System.exit(1);
			}
		}
	}
	
	
	/**
	 * M�thode contenant le menu principal
	 * @return le chiffre correspondant au choix de l'utilisateur
	 */
	public static int menuPrincipal() {
		System.out.println("1 : R�soudre manuellement");
		System.out.println("2 : R�soudre automatiquement");
		System.out.println("3 : Sauvegarder");
		System.out.println("4 : Fin");
		
		return AutresMethodes.traitementException(1, 4);
	}
	
	
	/**
	 * M�thode qui r�cup�re la premi�re ligne du fichier argument s'il existe
	 * @return la premi�re ligne du fichier s'il existe
	 * @throws IOException on v�rifie dans cette m�thode, si le fichier argument existe
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
	 * M�thode qui permet d'obtenir les villes et les routes de la communaut�. En les demandant � l'utilisateur
	 * si le fichier argument n'existe pas, en la construisant depuis le fichier s'il existe
	 * @throws IOException la m�thode File.exists() est utilis�e
	 */
	public static void obtenirLaCA() throws IOException {
		if(!file.exists() || premiereLigneFichier()==null) {
			ConfigVillesRoutes.creerVillesRoutes();
		}
		else RecupererCA.constructionCA();
	}
}
