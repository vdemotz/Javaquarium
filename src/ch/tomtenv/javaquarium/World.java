package ch.tomtenv.javaquarium;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Random;

/**
 * I'm the master of the wooooorld!!!!
 * This class runs a specific world. Whole this project has been made fast just to occupy some
 * hours on the train. Please don't judge on the style!
 */
class World {

	//constant of the world
	public static final int BASIC_HEALTH = 10;
	public static final String CARRIAGE_RETURN = "\n";
	public static final String PREFIX_TURN = "===== Tour ";
	public static final String POSTFIX_TURN = " ====";
	public static final String ACCEPT_CHAR = "y";
	
	//Messages
	public static final String ARGS_ERROR = "ERROR : please check arguments";
	public static final String BYE = "Good bye!";
	public static final String HELLO = "Welcome in Javaquarium simulation!";
	public static final String DONE = "Done!";
	public static final String START = "Start simulation...";
	public static final String NEXT_TIME = "Next time? ... Bye!";
	public static final String LOAD_SIM = "Try to load last simulation...";
	public static final String PROMPT_SAVE = "Do you want to save aquarium? ("+ACCEPT_CHAR+" / *)";
	public static final String PROMPT_LOAD = "Do you want to load last save of the aquarium? ("+ACCEPT_CHAR+" / *)";
	public static final String PROMPT_CONTINUE = "Do you want to continue? ("+ACCEPT_CHAR+" / *)";
	public static final String PROMPT_BEGIN = "Do you want to begin? ("+ACCEPT_CHAR+" / *)";
	
	//Random used to generate some randomize parameters
	public static Random r;
	
	//Files names, encoding type
	private static final String reportFileName = "report.txt";
	private static final String encoding = "UTF-8";
	private static final String saveFileName = "recovery.txt";
	
	public static void main(String[] args) {
		//check arguments length
		if (args == null || args.length != 1){
			System.err.print(World.ARGS_ERROR);
		}
		
		//set random
		r = new Random();
		
		//Instantiate writers
		InputStream in = System.in;
		PrintStream out = System.out;
		PrintWriter writerReport = null;
		try {
			writerReport = new PrintWriter(World.reportFileName, World.encoding);
		} catch (IOException ex) {
			//die silently... Bad!!!
		}
		
		//Say hello!
		out.println(World.HELLO);
		
		//prompt to begin
		boolean run = prompt(out, in, World.PROMPT_BEGIN);
		
		//load instructions
		Instructions instructions = null;
		if (run){
			out.println(World.START);
			instructions = loadFile(args[0]);
			if (instructions == null) {
				System.err.println(World.ARGS_ERROR);
				return;
			}
			out.println(World.DONE);
		} else {
			out.println(World.NEXT_TIME);
		}
		
		//Initialize aquarium
		Aquarium aquarium = null;
		if (prompt(out, in, World.PROMPT_LOAD)) {
			out.println(World.LOAD_SIM);
			aquarium = load(out, writerReport, loadFile(World.saveFileName).second());
			out.println(World.DONE);
		} else {
			aquarium = new Aquarium(out, writerReport);
		}
		
		//Instantiate writer to save
		PrintWriter writerSave = null;
		try {
			writerSave = new PrintWriter(World.saveFileName, World.encoding);
		} catch (IOException ex) {
			//die silently... Baaad!!!
		}
		
		//Make turns in the aquarium as long as the user ask for it
		int turn = aquarium.getTurn();
		while(run) {
			Instruction instruction = instructions.getInstruction(turn);
			if (instruction != null) {
				for (Fish f : instruction.getListFish()){
					aquarium.add(f);
				}
				for (Seaweed s : instruction.getListSeaweed()){
					aquarium.add(s);
				}
			}
			aquarium.makeTurn();
			run = prompt(out, in, World.PROMPT_CONTINUE);
			turn ++;
		}
		
		//save if necessary
		if(prompt(out, in, World.PROMPT_SAVE)){
			save(aquarium, writerSave);
		}
		
		//don't forget to close writers
		writerReport.close();
		writerSave.close();
		
		//And say good bye!
		out.println(World.BYE);
	}
	
	private static Instructions loadFile(String arg) {
		//read a file and create a set of instructions
		Instructions ret = new Instructions();
		//try to read the file
		try {
			BufferedReader reader = new BufferedReader(new FileReader(arg));
		    String line;
		    String instructionsTxt = "";
		    while ((line = reader.readLine()) != null) {
		    	instructionsTxt += line+World.CARRIAGE_RETURN;
		    }
		    InstructionsParser.parse(instructionsTxt, ret);
		    reader.close();
		} catch (FileNotFoundException e) {
			//die silently... Baaaad!
		} catch (IOException e) {
			//die silently... Baaaad!
		}
		return ret;
	}
	
	private static void save(Aquarium aquarium, PrintWriter writer) {
		//save the aquarium into a file
		writer.println(World.PREFIX_TURN+aquarium.getTurn()+World.POSTFIX_TURN);
		for (Fish f : aquarium.getListFish()) {
			writer.println(f.toSave());
		}
		for (Seaweed s : aquarium.getListSeaweed()) {
			writer.println(s.toSave());
		}
	}
	
	private static Aquarium load(PrintStream stream, PrintWriter writer, Instruction instruction) {
		//create a new aquarium with saved state
		return new Aquarium(stream, writer, instruction.getListFish(), instruction.getListSeaweed(), instruction.getTurn());
	}
	
	private static boolean prompt(PrintStream streamOut, InputStream streamIn, String demand) {
		//ask user
		streamOut.println(demand);
		//read response from user
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(streamIn));
	    String response = "";
		try {
			response = bufferRead.readLine();
		} catch (IOException e) {
		}
		if (response.equals("y")) {
			return true;
		} else {
			return false;
		}
	}
}
