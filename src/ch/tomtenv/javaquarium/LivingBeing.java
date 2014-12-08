package ch.tomtenv.javaquarium;

/**
 * LivingBeing is an abstract class representing what a living being can look like
 */
public abstract class LivingBeing {
	
	protected int health;
	protected int age;
	protected boolean isAlive;
	
	/**
	 * Create new living being
	 * @param age
	 */
	public LivingBeing(int age) {
		this.health = World.BASIC_HEALTH;
		this.age = age;
		this.isAlive = true;
	}
	
	/**
	 * @return the health of the living being
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @param set the health of the living being
	 */
	public void setHealth(int health) {
		this.health = health;
		if (this.health <= 0) {
			this.setIsAlive(false);
		}
	}
	
	/**
	 * @return the age of the living being
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param set the age of the living being
	 */
	public void setAge(int age) {
		this.age = age;
		if (age > 20){
			setIsAlive(false);
		}
	}
	
	/**
	 * @param increment the age of the living being
	 */
	public void incrementAge() {
		age++;
		if (age > 20){
			setIsAlive(false);
		}
	}
	
	/**
	 * Is the living being really living
	 * @return
	 */
	public boolean isAlive() {
		return isAlive;
	}

	/**
	 * Die potato!
	 */
	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
}
