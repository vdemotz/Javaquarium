package ch.tomtenv.javaquarium;

/**
 * Fish represents all possible fishes
 */
public class Fish extends LivingBeing {

	private final String name;
	private Gender gender;
	private final Species species;
	private final Diet diet;
	
	/**
	 * New fish
	 * @param name
	 * @param species
	 * @param age
	 */
	public Fish(String name, Species species, int age) {
		//set age, health, species and name
		super(age);
		this.name = name;
		this.species = species;
		
		//decide gender
		if (species.equals(Species.THON) || species.equals(Species.CARPE)) {
			//fish's gender is well-defined
			if (World.r.nextBoolean()){
				this.gender = Gender.MALE;
			} else {
				this.gender = Gender.FEMALE;
			}
		} else if (species.equals(Species.BAR) || species.equals(Species.MEROU)) {
			//fish is hermaphrodite with age
			if (age < 10){
				this.gender = Gender.MALE;
			} else {
				this.gender = Gender.FEMALE;
			}
			
		} else {
			//fish is hermaphrodite with opportunity
			this.gender = Gender.MALE;
		}
		
		//decide diet
		if (species.equals(Species.THON) || species.equals(Species.MEROU) || species.equals(Species.CLOWN)) {
			this.diet = Diet.CARNIVOROUS;
		} else {
			this.diet = Diet.HERBIVOROUS;
		}
	}
	
	/**
	 * The fish gets more hungry
	 */
	public void growHunger() {
		this.setHealth(getHealth()-1);
	}
	
	/**
	 * Get the name of the fish
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the gender of the fish
	 * @return
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Set the gender of the fish, used for hermaphrodites fishes
	 * @param gender
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * Get the species of the fish
	 * @return
	 */
	public Species getSpecies() {
		return species;
	}
	
	/**
	 * True if the fish is carnivorous, false otherwise
	 * @return
	 */
	public boolean isCarnivorous() {
		if (this.diet.equals(Diet.CARNIVOROUS)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "[Fish : "+name+","+gender+","+species.getName()+","+age+" years old, "+health+" PV.]";
	}
	
	/**
	 * Used to save
	 * @return
	 */
	public String toSave() {
		return name+", "+species.getName()+", "+age+" ans";
	}
	
	/**
	 * Represents the gender of a fish
	 */
	public enum Gender {
		MALE,
		FEMALE;
	}
	
	/**
	 * Represents the diet of a fish
	 */
	public enum Diet {
		CARNIVOROUS,
		HERBIVOROUS;
	}
	
	/**
	 * List all possible species
	 */
	public enum Species {
		CARPE("Carpe"),
		THON("Thon"),
		BAR("Bar"),
		MEROU("Merou"),
		SOLE("Sole"),
		CLOWN("Poisson-clown"),
		UNKNOWN("Unknown");
		
		private String name;
		
		private Species(String s) {
			this.name = s;
		}
		
		public String getName() {
			return this.name;
		}
		
		public static Species getSpecies(String s){
			for (Species species : Species.values()){
				if (s.equals(species.getName())) {
					return species;
				}
			}
			return Species.UNKNOWN;
		}
	}
}
