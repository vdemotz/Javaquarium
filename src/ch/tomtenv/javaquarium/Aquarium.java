package ch.tomtenv.javaquarium;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import ch.tomtenv.javaquarium.Fish.Species;

/**
 * Aquarium represents the aquarium... DUH!
 */
public class Aquarium {
	
	//State
	private List<Fish> listFish;
	private List<Seaweed> listSeaweed;
	private List<Fish> listNewFish;
	private List<Seaweed> listNewSeaweed;
	private PrintStream stream;
	private PrintWriter writer;
	private int turn;
	
	//Constants
	private final int thresholdFish = 5;
	private final int thresholdSeaweed = 10;
	private final int ageBirth = 0;
	private final int gainEatFish = 5;
	private final int gainEatSeaweed = 3;
	private final int lostBeEatenFish = 4;
	private final int lostBeEatenSeaweed = 2;
	
	/**
	 * Create a new empty aquarium
	 * @param stream
	 * @param writer
	 */
	public Aquarium(PrintStream stream, PrintWriter writer) {
		this.stream = stream;
		this.writer = writer;
		listFish = new ArrayList<Fish>();
		listSeaweed = new ArrayList<Seaweed>();
		listNewFish = new ArrayList<Fish>();
		listNewSeaweed = new ArrayList<Seaweed>();
		turn = 0;
	}
	
	/**
	 * Create a new customed aquarium
	 * @param stream
	 * @param writer
	 * @param listFish
	 * @param listSeaweed
	 * @param turn
	 */
	public Aquarium(PrintStream stream, PrintWriter writer, List<Fish> listFish, List<Seaweed> listSeaweed, int turn) {
		this.stream = stream;
		this.writer = writer;
		this.listFish = listFish;
		this.listSeaweed = listSeaweed;
		listNewFish = new ArrayList<Fish>();
		listNewSeaweed = new ArrayList<Seaweed>();
		this.turn = turn;
	}
	
	/**
	 * Add a fish to the aquarium
	 * @param fish
	 */
	public void add(Fish fish) {
		listFish.add(fish);
	}
	
	/**
	 * Add a seaweed to the aquarium
	 * @param seaweed
	 */
	public void add(Seaweed seaweed) {
		listSeaweed.add(seaweed);
	}
	
	/**
	 * Play a turn
	 */
	public void makeTurn() {
		//seaweed grow at
		growSeaweed();
		//fishes lost health
		growHunger();
		//fishes have sex or eat
		liveFish();
		//seaweed can separate
		liveSeaweed();
		//birth take place
		addBirth();
		//death take place
		collectDeaths();
		//increment turn
		turn++;
		//notify and save
		printState();
	}
	
	/**
	 * @return the list of fishes in the aquarium
	 */
	public List<Fish> getListFish() {
		return this.listFish;
	}
	
	/**
	 * @return the list of seaweeds in the aquarium
	 */
	public List<Seaweed> getListSeaweed() {
		return this.listSeaweed;
	}
	
	/**
	 * @return the turn of the aquarium
	 */
	public int getTurn() {
		return this.turn;
	}
	
	private void printState() {
		//print the state in the console and in the report.txt file
		stream.println("\nTurn "+turn);
		writer.println("\nTurn "+turn);
		stream.println("There are actually "+listSeaweed.size()+" seaweeds.");
		writer.println("There are actually "+listSeaweed.size()+" seaweeds.");
		for (Fish f : listFish) {
			stream.println(f.toString());
			writer.println(f.toString());
		}
	}
	
	private void liveSeaweed() {
		//seaweeds separate if they are healthy enough
		for (Seaweed s : listSeaweed) {
			s.incrementAge();
			if (s.getHealth() >= this.thresholdSeaweed) {
				separate(s);
			}
		}
	}
	
	private void liveFish() {
		//fishes have sex or eat
		for (Fish f : listFish) {
			f.incrementAge();
			if (f.getHealth() <= this.thresholdFish) {
				LivingBeing prey = getPrey(f);
				meal(f, prey);
			} else {
				sex(getPartner(f), f);
			}
		}
	}
	
	private void growHunger() {
		//each fish lose a bit of health
		for (Fish f : listFish) {
			f.growHunger();
		}
	}
	
	private void growSeaweed() {
		//each seaweed gain a bit of health
		for (Seaweed s : listSeaweed) {
			s.grow();
		}
	}
	
	private void sex(Fish f1, Fish f2) {
		//two fishes try to make baby
		if (f1.getSpecies().equals(f2.getSpecies())) {
			if (f1.getSpecies().equals(Species.SOLE) || f2.getSpecies().equals(Species.CLOWN)) {
				//case hermaphrodite with opportunity, they have a child anyway
				listNewFish.add(new Fish(Names.randomName(), f1.getSpecies(), this.ageBirth));
			} else {
				if (!f1.getGender().equals(f2.getGender())) {
					listNewFish.add(new Fish("newFish", f1.getSpecies(), this.ageBirth));
				}
			}
		}
	}
	
	private void separate(Seaweed s) {
		//a seaweed separate
		int newHealth = s.getHealth() % 2;
		s.setHealth(newHealth);
		listNewSeaweed.add(new Seaweed(this.ageBirth, newHealth));
	}
	
	private void meal(Fish predator, LivingBeing prey) {
		//predator eats prey
		int gain = 0;
		int los = 0;
		if (predator.isCarnivorous()) {
			gain = this.gainEatFish;
			los = this.lostBeEatenFish;
		} else {
			gain = this.gainEatSeaweed;
			los = this.lostBeEatenSeaweed;
		}
		prey.setHealth(prey.getHealth() - los);
		predator.setHealth(predator.getHealth() + gain);
	}
	
	private Fish getPartner(Fish fish) {
		//a fish gets a random partner
		boolean equals = true;
		Fish partner = null;
		while (equals) {
			partner = listFish.get(World.r.nextInt(listFish.size()));
			equals = partner.equals(fish);
		}
		return partner;
	}
	
	private LivingBeing getPrey(Fish fish) {
		//a fish gets a random prey of the right type
		if (fish.isCarnivorous()) {
			if (listFish.size() <= 1)
				return null;
			boolean equals = true;
			Fish prey = null;
			while (equals) {
				prey = listFish.get(World.r.nextInt(listFish.size()));
				equals = prey.equals(fish);
			}
			return prey;
		} else {
			if (listSeaweed.size() < 1)
				return null;
			return(listSeaweed.get(World.r.nextInt(listSeaweed.size())));
		}
	}
	
	private void addBirth() {
		//births take place
		for (Fish f : listNewFish) {
			listFish.add(f);
		}
		for (Seaweed s : listNewSeaweed){
			listSeaweed.add(s);
		}
		listNewFish = new ArrayList<Fish>();
		listNewSeaweed = new ArrayList<Seaweed>();
	}
	
	private void collectDeaths() {
		//deaths take place
		for (int i = 0; i < listFish.size(); i++){
			if (!listFish.get(i).isAlive) {
				listFish.remove(listFish.get(i));
			}
		}
		for (int i = 0; i < listSeaweed.size(); i++){
			if (!listSeaweed.get(i).isAlive) {
				listSeaweed.remove(listSeaweed.get(i));
			}
		}
	}
}
