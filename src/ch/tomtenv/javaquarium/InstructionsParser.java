package ch.tomtenv.javaquarium;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ch.tomtenv.javaquarium.Fish.Species;

/**
 * Parse a text file into an abstraction more easy to use
 */
public class InstructionsParser {
	
	//patterns
	private final static String turnPatternStr = "===== Tour \\S* ====";
	private final static String seaweedPatternStr = "\\S* algues \\S* ans";
	private final static String fishPatternStr = "\\S*, \\S*, \\S* ans";
	private final static String numberPatternStr = "-?\\d+";
	private final static String namePatternStr = "-?[\\w-]+";
	
	public static void parse(String txt, Instructions set) {
		//compile patterns
		Pattern patternTurn = Pattern.compile(turnPatternStr);
		Pattern patternSeaweed = Pattern.compile(seaweedPatternStr);
		Pattern patternFish = Pattern.compile(fishPatternStr);
		Pattern patternNumber = Pattern.compile(numberPatternStr);
		Pattern patternName = Pattern.compile(namePatternStr);
		
		String[] lines = txt.split("\n");
		
		int turn = 0;
		List<Fish> listFish = new ArrayList<Fish>();
		List<Seaweed> listSeaweed = new ArrayList<Seaweed>();
		
		//Eeeerk, this is really ugly code!
		for (String s : lines){
			Matcher mTurn = patternTurn.matcher(s);
			Matcher mFish = patternFish.matcher(s);
			Matcher mSeaweed = patternSeaweed.matcher(s);
			if (mTurn.matches()) {
				//match turn
				Matcher mNumber = patternNumber.matcher(s);
				mNumber.find();
				int newTurn = Integer.parseInt(mNumber.group());
				
				set.addInstruction(new Instruction(listFish, listSeaweed, turn));
				listFish = new ArrayList<Fish>();
				listSeaweed = new ArrayList<Seaweed>();
				turn = newTurn;
			} else if (mFish.matches()) {
				//Match age of fish
				Matcher mNumber = patternNumber.matcher(s);
				mNumber.find();
				int age = Integer.parseInt(mNumber.group());
				
				//Match name of fish
				Matcher mName = patternName.matcher(s);
				mName.find();
				String name = mName.group();
				
				//and species
				mName.find();
				String species = mName.group();
				
				listFish.add(new Fish(name, Species.getSpecies(species), age));
			} else if (mSeaweed.matches()) {
				//Match number of seaweed
				Matcher mNumber = patternNumber.matcher(s);
				mNumber.find();
				int number = Integer.parseInt(mNumber.group());
				
				//And age of seaweed
				mNumber.find();
				int age = Integer.parseInt(mNumber.group());
				
				for (int i = 0; i < number; i++) {
					listSeaweed.add(new Seaweed(age));
				}
			} else {
				//silently ignores ^.^
			}
		}
		set.addInstruction(new Instruction(listFish, listSeaweed, turn));
		listFish = new ArrayList<Fish>();
		listSeaweed = new ArrayList<Seaweed>();
	}

}
