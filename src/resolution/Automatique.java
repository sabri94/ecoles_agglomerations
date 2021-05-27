package resolution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import creation.Ville;
import principal.*;

/**
 * Classe contenant les méthodes pour résoudre automatiquement la communauté d'agglomération
 * @author Jatheesh Soundaranayagam, Liticia Bellil, Sabri Meftah
 *
 */
public class Automatique {
	
	/**
	 * Le nombre de villes de la (des) solution(s) optimale(s)
	 */
	static int min;
	/**
	 * Chiffre correspondant à la solution optimale choisie pour être dans le fichier
	 */
	static int choix=1;
	
	
	/**
	 * Méthode qui permet de résoudre automatiquement la communauté d'agglomération
	 * @throws IOException manipulations du fichier
	 */
	public static void resoudre() throws IOException {
		Menu.ca.remettreToutesEcoles();
		moinsDe3villes();
		
		if(Menu.ca.getVilles().length > 2) {
			List<List<String>> solutions = new ArrayList<>();
	
			toutesLesSolutions(solutions);
			min = nbVillesMin(solutions);
			solutionsOptimales(solutions);
			ordreAlphabetique(solutions);
			supprimerDoublons(solutions);
			choixSolution(solutions);
			majEcolesCA(solutions);
			
			System.out.println("\nSauvegardez si vous souhaitez conserver les changements ;-)");
			AutresMethodes.retourMenuPrincipal(Menu.file.getPath());
		}	
	}
	
	
	/**
	 * Méthode qui traitent les cas où il n'y a qu'une ou deux villes dans la communauté
	 * @throws IOException manipulations du fichier
	 */
	public static void moinsDe3villes() throws IOException {
		if(Menu.ca.getVilles().length == 1) {
			System.out.println("\nLa solution optimale est d'avoir une école dans la ville " + Menu.ca.getVilles()[0].getNom() + ".\n");
			System.out.println("Sauvegardez si vous souhaitez conserver les changements ;-)");
			AutresMethodes.retourMenuPrincipal(Menu.file.getPath());
		}
		
		else if(Menu.ca.getVilles().length == 2) {
			if(Menu.ca.getRoutes() [Menu.ca.getVilles()[0].getIndice()] [Menu.ca.getVilles()[1].getIndice()]) {
				System.out.println("\nLes solutions optimales sont les suivantes :");
				System.out.println("Une seule école dans la communauté, dans la ville " + Menu.ca.getVilles()[0].getNom() + " ou dans la ville " + Menu.ca.getVilles()[1].getNom() + ".\n");
				Menu.ca.getVilles()[1].changeEcole();
			}	
			else {
				System.out.println("\nLa solution optimale est la suivante :");
				System.out.println(Menu.ca.getVilles()[0].getNom() + " " + Menu.ca.getVilles()[1].getNom() + "\n");
			}
			System.out.println("Sauvegardez si vous souhaitez conserver les changements ;-)");
			AutresMethodes.retourMenuPrincipal(Menu.file.getPath());
		}
	}
	
	
	/**
	 * Méthode qui récupère toutes les solutions possibles selon l'algorithme utilisé
	 * @param solutions : liste contenant toutes les solutions 
	 */
	public static void toutesLesSolutions(List<List<String>> solutions) {
		/* Voici ce en quoi consiste cet algorithme :
		 * On prend la première ville du tableau, puis on supprime une par une les villes qui suivent. La première ville et les villes 
		 * qui n'ont pas pu être supprimées constituent une solution. On change l'ordre des villes à partir de l'indice 1 du tableau, 
		 * et on recommence à supprimer les villes une par une. On réitère jusqu'à avoir obtenu tous les ordres possibles. On change à
		 * présent l'ordre à partir de l'indice 0, puis on réitère tous ce qu'on a fait précédemment. On le fait jusqu'à ce que toutes
		 * les villes du tableau soient passées par l'indice 0.
		*/ 
		for(int i=0; i<Menu.ca.getVilles().length; i++) {
			List<String> listeInitiale = listeInitiale();
			
			for(int j=0; j<Menu.ca.getVilles().length-1; j++) {
				ajoutSolution(solutions);
				Menu.ca.remettreToutesEcoles();
				decalage(1);
			}
			
			revenirAlisteInitiale(listeInitiale);
			decalage(0);
		}
	}
	
	
	/**
	 * Méthode qui récupère le nom des villes dans l'ordre où elles ont été entrées par l'utilisateur
	 * @return une liste de strings correspondant aux noms des villes
	 */
	public static List<String> listeInitiale() {
		List<String> listeInitiale = new ArrayList<>();
		for(Ville v : Menu.ca.getVilles()) {
			listeInitiale.add(v.getNom());
		}
		return listeInitiale;
	}
	
	
	/**
	 * Méthode qui ajoute une solution à la liste contenant les solutions
	 * @param solutions : la liste contenant les solutions
	 */
	public static void ajoutSolution(List<List<String>> solutions) {
		List<String> solution = new ArrayList<>();
		
		solution.add(Menu.ca.getVilles()[0].getNom());
		for(int i=1; i<Menu.ca.getVilles().length; i++) {
			if(!Menu.ca.contrainteRespectee(Menu.ca.getVilles()[i])) {
				solution.add(Menu.ca.getVilles()[i].getNom());
			}
		}
		
		solutions.add(solution);
	}
	
	
	/**
	 * Modifie l'ordre des villes dans le tableau
	 * @param borneInf : indice à partir duquel l'ordre des villes est modifié
	 */
	public static void decalage(int borneInf) {
		Ville res = Menu.ca.getVilles()[borneInf];
		Menu.ca.getVilles()[borneInf] = Menu.ca.getVilles()[Menu.ca.getVilles().length-1];
		Ville res2;
		for(int i=borneInf+1; i<Menu.ca.getVilles().length; i++) {
			res2 = Menu.ca.getVilles()[i];
			Menu.ca.getVilles()[i] = res;
			res = res2;
		}
	}
	
	
	/**
	 * Replace les villes dans leur ordre initial
	 * @param listeInitiale : liste dans laquelle sont stockées les villes dans l'ordre initial
	 */
	public static void revenirAlisteInitiale(List<String> listeInitiale) {
		for(int j=0; j<Menu.ca.getVilles().length; j++) {
			Menu.ca.getVilles()[j] = Menu.ca.stringToVille(listeInitiale.get(j));
		}
	}
	
	
	/**
	 * Méthode qui permet d'obtenir le nombre de villes minimum parmis toutes les solutions,
	 * et donc le nombre de villes de la solution optimale
	 * @param solutions : la liste contenant les solutions
	 * @return
	 */
	public static int nbVillesMin(List<List<String>> solutions) {
		int min = solutions.get(0).size();		
		for(int i=1; i<solutions.size(); i++) {
			if(min > solutions.get(i).size()) {
				min = solutions.get(i).size();
			}
		}	
		return min;
	}
	
	
	/**
	 * Méthode qui supprime toutes les solutions dont le nombre de villes n'est pas le plus petit
	 * @param solutions : liste des solutions
	 */
	public static void solutionsOptimales(List<List<String>> solutions) {
		for(int i=0; i<solutions.size(); i++) {
			if(solutions.get(i).size() > min) {
				solutions.remove(solutions.get(i));
				i--;
			}
		}	
	}
	
	
	/**
	 * Méthode qui met les noms des villes dans l'ordre alphabétique dans chaque solution
	 * @param solutions : liste des solutions
	 */
	public static void ordreAlphabetique(List<List<String>> solutions) {
		//On met les solutions dans l'ordre alphabétique pour mieux pouvoir supprimer les doublons par la suite
		for(List<String> list : solutions) {
			for(int i=0; i<list.size()-1; i++) {
		        for(int j=i+1; j<list.size(); j++) {
		            if(list.get(i).compareTo(list.get(j)) > 0) {
		            	String res = list.get(i);
						list.set(i, list.get(j));
						list.set(j, res);
		            }
		        }
		    }
		}
	}
	
	
	/**
	 * Méthode qui supprime les doublons de chaque solutions
	 * @param solutions : liste des solutions
	 */
	public static void supprimerDoublons(List<List<String>> solutions) {
		for(int i=0; i<solutions.size(); i++) {
			for(int j=i+1; j<solutions.size(); j++) {
				boolean identiques = true;
				
				for(int k=0; k<min; k++) {
					if(!solutions.get(i).get(k).equals(solutions.get(j).get(k))) {
						identiques = false;
						break;
					}
				}
				if(identiques) {
					solutions.remove(j);
					j--;
				}
			}
		}
	}
	
	
	/**
	 * Méthode qui propose de choisir parmis les différentes solutions optimales, laquelle sera conserver dans le fichier
	 * @param solutions : liste des solutions
	 */
	public static void choixSolution(List<List<String>> solutions) {		
		if(solutions.size() == 1) {
			System.out.println("\nVoici la solution optimale :");
			for(String s : solutions.get(0)) {
				System.out.println(s + " ");
			}
		}
		
		else {
			System.out.println("\nVoici les solutions optimales, laquelle voulez-vous conserver ?");
			for(int i=0; i<solutions.size(); i++) {
				System.out.print((i+1) + ")");
				for(int j=0; j<solutions.get(i).size(); j++) {
					System.out.print(" " + solutions.get(i).get(j));
				}
				System.out.println();
			}
			choix = AutresMethodes.traitementException(1, solutions.size());
		}
	}
	
	
	/**
	 * Méthode qui modifie la communauté d'agglomération en fonction de la solution choisie
	 * @param solutions : liste des solutions
	 */
	public static void majEcolesCA(List<List<String>> solutions) {
		for(Ville v : Menu.ca.getVilles()) {
			boolean present = false;
			for(String s : solutions.get(choix-1)) {
				if(v.getNom().equals(s)) {
					present = true;
					break;
				}
			}
			if(!present) v.changeEcole();
		}
	}
}	