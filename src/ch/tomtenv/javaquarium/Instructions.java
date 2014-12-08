package ch.tomtenv.javaquarium;

import java.util.ArrayList;
import java.util.List;

/**
 * Instructions is used to encapsulate all instructions (Instruction class) of the user
 */
public class Instructions {

	private List<Instruction> listInstruction;
	
	/**
	 * Create new empty set of instructions
	 */
	public Instructions() {
		listInstruction = new ArrayList<Instruction>();
	}
	
	/**
	 * Add an instruction to the list. Unsafe, user should check turn.
	 * @param instruction
	 */
	public void addInstruction(Instruction instruction) {
		listInstruction.add(instruction);
	}
	
	/**
	 * Get the instruction for a specific turn
	 * @param turn
	 * @return
	 */
	public Instruction getInstruction(int turn) {
		for (Instruction i : listInstruction){
			if (i.getTurn() == turn) {
				return i;
			}
		}
		return null;
	}
	
	/**
	 * Very unsafe. Rely on the very precise recovery.txt structure
	 * @return
	 */
	public Instruction second() {
		return listInstruction.get(1);
	}
}
