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
	 * Fichier dans lequel est sauvegardée la communauté d'agglomération et qui sera passé en argument
	 * lors de la prochaine itération du programme
	 */
	static File fichier;
	
	
	/**
	 * Méthode qui sauvegarde le communauté dans le fichier
	 * @throws IOException écriture dans un fichier
	 */
	public static void save() throws IOException {
		String chemin = verifChemin();
		creationFichier();
		
		System.out.println("La communauté d'agglomération a bien été sauvegardée.\n");
		AutresMethodes.retourMenuPrincipal(chemin);
	}	
	
	
	/**
	 * Méthode qui vérifie la validité du chemin entré par l'utilisateur, et s'il s'agit bien d'un 
	 * fichier txt
	 * @return un chemin correct entré par l'utilisateur
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
				System.out.println("LE CHEMIN QUE VOUS AVEZ ENTRÉ N'EXISTE PAS.");
			}	
		}
		return chemin;
	}
	
	
	/**
	 * Méthode qui crée le fichier avec la communauté d'agglomération dedans
	 * @throws IOException écriture dans le fichier
	 */
	public static void creationFichier() throws IOException {
		
		//Si le chemin correspond au fichier entré en argument, ce dernier est remplacé
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
