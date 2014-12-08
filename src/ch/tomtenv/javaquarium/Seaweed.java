package ch.tomtenv.javaquarium;

/**
 * Yeah, Bob the seaweed!!! What, was he really a sponge?
 */
public class Seaweed extends LivingBeing {

	/**
	 * Create new seaweed
	 * @param age
	 */
	public Seaweed(int age) {
		super(age);
	}
	
	/**
	 * Create new seaweed
	 * @param age
	 * @param health
	 */
	public Seaweed(int age, int health) {
		super(age);
		this.health = health;
	}
	
	/**
	 * Seaweed grows
	 */
	public void grow() {
		this.health++;
	}

	@Override
	public String toString() {
		return "[ Seaweed : "+age+" years old, "+health+" PV.]";
	}
	
	/**
	 * Used to save
	 * @return
	 */
	public String toSave() {
		return 1 + " algues "+ age + " ans";
	}
}
