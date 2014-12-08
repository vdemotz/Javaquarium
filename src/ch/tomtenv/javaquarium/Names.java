package ch.tomtenv.javaquarium;

/**
 * Some names
 */
public class Names {	
	public static final String[] names = {"Bernadette", "Charles", "Henry", "Jean", "Poulet", "Francois",
		"Claude", "Yves", "Gregoire", "Basile", "Mathilde", "Jeanne"};
	
	/**
	 * Return a random name
	 */
	public static String randomName() {
		return names[World.r.nextInt(names.length)];
	}
}
