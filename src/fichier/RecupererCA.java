package fichier;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import creation.CommunauteAgglomeration;
import creation.Ville;
import principal.AutresMethodes;
import principal.Menu;

/**
 * Classe permettant de récuperer une communauté d'agglomération depuis un fichier
 * @author Jatheesh Soundaranayagam, Liticia Bellil, Sabri Meftah
 *
 */
public class RecupererCA {
	
	/**
	 * Permet de lire un fichier
	 */
	static BufferedReader br;
	/*
	 * Ligne actuelle du fichier
	 */
	static String line;
	/*
	 * Permet de contruire le tableau de villes, puis représente le nombre de villes
	 */
	static int n = 0;
	/**
	 * Liste contenant les noms des villes du fichier agrument 
	 */
	static List<String> nomsVilles = new ArrayList<>();
	/*
	 * Tableau contenant les villes du fichier
	 */
	static Ville[] villes;
	
	
	/**
	 * Méthode qui permet de construire la communauté d'agglomération à partir du fichier argument
	 * @throws IOException possible erreur lors de la lecture du fichier
	 */
	public static void constructionCA() throws IOException {
		nomsVilles = nomsVilles();
		
		villes = new Ville[n];
		for(int i=0; i<n; i++) {
			villes[i] = new Ville(nomsVilles.get(i), i, true);
		}
		
		Menu.ca = new CommunauteAgglomeration( creationRoutesCA(AutresMethodes.initRoutes(n)) , villes);
		
		if(line != null) Menu.ca.retirerToutesEcoles();
		recupererEcoles();
		
		n=0;
		br.close();
	}
	
	
	/**
	 * Méthode permettant de récupérer les noms des villes du fichier
	 * @return une liste contenant les noms des villes
	 * @throws IOException lecture du fichier argument
	 */
	public static List<String> nomsVilles() throws IOException {
		List<String> nomsVilles = new ArrayList<>();
		br = new BufferedReader(new InputStreamReader(new FileInputStream(Menu.file), "UTF-8"));
		line = br.readLine();
		
		while(line!=null && line.charAt(0) == 'v') {
			nomsVilles.add(line.substring(6, line.length()-2));
			line = br.readLine();
			n++;
		}
		return nomsVilles;
	}

	
	/**
	 * Méthode qui créer les routes qui sont dans le fichier
	 * @param routes : le tableau des routes à l'état initial (avec toutes les cases égales
	 * à false)
	 * @return le tableau avec les routes du fichier
	 * @throws IOException lecture du fichier
	 */
	public static boolean[][] creationRoutesCA(boolean[][] routes) throws IOException {		
		
		while(line!=null && line.charAt(0) == 'r') {		
			String[] strs = line.split(",");
			String nomVille1 = strs[0].substring(6);
			String nomVille2 = strs[1].substring(0, strs[1].length()-2);
			
			int indiceVille1=-1, indiceVille2=-1;
			for(Ville v : villes) {
				if(v.getNom().equals(nomVille1)) {
					indiceVille1 = v.getIndice();
				}
				if(v.getNom().equals(nomVille2)) {
					indiceVille2 = v.getIndice();
				}
			}
			
			routes[indiceVille1][indiceVille2] = true;
			routes[indiceVille2][indiceVille1] = true;
			line = br.readLine();
		}
		return routes;
	}
	
	
	/**
	 * Méthode qui permet de mettre ou non des écoles dans les différentes villes la communauté
	 * en fonction du fichier
	 * @throws IOException lecture du fichier
	 */
	public static void recupererEcoles() throws IOException {
		while(line!=null && line.charAt(0) == 'e') {
			String nomVille = line.substring(6, line.length()-2);
			for(Ville v : Menu.ca.getVilles()) {
				if(v.getNom().equals(nomVille)) v.changeEcole();
			}
			line = br.readLine();
		}
	}
}
