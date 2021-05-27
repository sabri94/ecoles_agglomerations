package fichier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import principal.AutresMethodes;
import principal.Menu;


public class Sauvegarder {
	/**
	 * Fichier dans lequel est sauvegard�e la communaut� d'agglom�ration et qui sera pass� en argument
	 * lors de la prochaine it�ration du programme
	 */
	static File fichier;
	
	
	/**
	 * M�thode qui sauvegarde le communaut� dans le fichier
	 * @throws IOException �criture dans un fichier
	 */
	public static void save() throws IOException {
		String chemin = verifChemin();
		creationFichier();
		
		System.out.println("La communaut� d'agglom�ration a bien �t� sauvegard�e.\n");
		AutresMethodes.retourMenuPrincipal(chemin);
	}	
	
	
	/**
	 * M�thode qui v�rifie la validit� du chemin entr� par l'utilisateur, et s'il s'agit bien d'un 
	 * fichier txt
	 * @return un chemin correct entr� par l'utilisateur
	 */
	public static String verifChemin() {
		String chemin = "";
		boolean fichierCree = false;
		
		while(!fichierCree) {
			try {
				System.out.print("Veuillez entrer le chemin du fichier : ");
				chemin = Menu.sc.nextLine();
				
				if(chemin.length()<4 || !chemin.substring(chemin.length()-4).equals(".txt")) {
					System.out.println("VEUILLEZ ENTRER UN FICHIER TEXTE.");
				}
				
				else {
					fichier = new File(chemin);
					fichier.createNewFile();
					fichierCree = true;
				}		
			}			
			catch(IOException e) {
				fichierCree = false;
				System.out.println("LE CHEMIN QUE VOUS AVEZ ENTR� N'EXISTE PAS.");
			}	
		}
		return chemin;
	}
	
	
	/**
	 * M�thode qui cr�e le fichier avec la communaut� d'agglom�ration dedans
	 * @throws IOException �criture dans le fichier
	 */
	public static void creationFichier() throws IOException {
		
		//Si le chemin correspond au fichier entr� en argument, ce dernier est remplac�
		if(Menu.file.exists() && Menu.file.getPath()==fichier.getPath()) {
			Files.copy(fichier.toPath(), Menu.file.toPath());
			if(Menu.ca != null) {
				FileWriter fw = new FileWriter(Menu.file);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(Menu.ca.toString());
				bw.close();
				fw.close();
			}	
		}
		
		else {
			if(Menu.ca != null) {
				FileWriter fw = new FileWriter(fichier);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(Menu.ca.toString());
				bw.close();
				fw.close();
			}	
		}
	}
}
