package ch.tomtenv.javaquarium;

import java.util.List;

/**
 * Instruction is used to store and manipulate a user voluntee of inserting fishes and seaweeds
 * into the aquarium
 */
public class Instruction {
	private int turn;
	private List<Fish> listFish;
	private List<Seaweed> listSeaweed;
	
	/**
	 * Create new instruction
	 * @param listFish
	 * @param listSeaweed
	 * @param turn
	 */
	public Instruction(List<Fish> listFish, List<Seaweed> listSeaweed, int turn) {
		this.listFish = listFish;
		this.listSeaweed = listSeaweed;
		this.turn = turn;
	}
	
	/**
	 * @return the turn of the instruction
	 */
	public int getTurn() {
		return this.turn;
	}
	
	/**
	 * @return the list of fishes of the instruction
	 */
	public List<Fish> getListFish() {
		return this.listFish;
	}
	
	/**
	 * @return the list of seaweeds of the instruction
	 */
	public List<Seaweed> getListSeaweed() {
		return this.listSeaweed;
	}
}