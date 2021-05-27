package creation;

/**
 * Classe permettant de g�n�rer une ville
 * @author Jatheesh Soundaranayagam, Liticia Bellil, Sabri Meftah
 * 
 */
public class Ville {
	private String nom;
	private int indice;
	private boolean existeEcole; 
	
	/**
	 * Constructeur permettant de cr�er un objet Ville
	 * @param nom : nom de la ville
	 * @param indice : indice de la ville
	 * @param existeEcole : vaut true si il y a une �cole dans la ville, false sinon
	 */
	public Ville(String nom, int indice, boolean existeEcole) {
		this.nom = nom;
		this.indice = indice;
		this.existeEcole = existeEcole;
	}
	
	
	/**
	 * M�thode qui retourne le nom de la ville
	 * @return le nom de la ville
	 */
	public String getNom() {
		return nom;
	}
	

	/**
	 * M�thode qui retourne l'indice de la ville
	 * @return un entier correspondant � l'indice de la ville
	 */
	public int getIndice() {
		return indice;
	}
	
	
	/**
	 * M�thode qui v�rifie s'il y a une �cole dans la ville ou non
	 * @return true s'il y a une �cole dans la ville, false sinon
	 */
	public boolean isThereEcole() {
		return existeEcole;
	}
	
	
	/**
	 * M�thode qui supprime l'�cole de la ville s'il y en a une, ou qui ajoute une �cole s'il 
	 * n'y en a pas
	 */
	public void changeEcole() {
		if(existeEcole) existeEcole=false;
		else existeEcole=true;
	}
}