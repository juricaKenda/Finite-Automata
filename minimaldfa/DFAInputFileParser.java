

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;



public class DFAInputFileParser {


	//Elements needed for parsing the file
	private File inputfile;
	private String input;
	//Elements needed to construct NFA
	private ArrayList<String> inputStrings;
	private	ArrayList<State> allStates;
	private	ArrayList<String> allowedInputs;
	private	ArrayList<State> acceptedStates;
	private	State startState;
	private DeltaFunction deltaFunction;
	
	public DFAInputFileParser(String input) {
//		this.inputfile = new File(fileLocation);
		this.input = input;
		this.parse();
	}
	public DFAInputFileParser(File file) {
		this.inputfile = file;
		this.parse();
	}
	
	private boolean parse() {
		Scanner scanner;
		try {
			scanner = new Scanner(this.input);
			
			//Collect first five rows and extract data from them
			StringBuilder firstFiveCollector = new StringBuilder();
			for(int count=0; count <4; count++) {
				firstFiveCollector.append(scanner.nextLine()+"\n");
			}
			this.extractDefinition(firstFiveCollector.toString());
			
			//All following rows are for delta function
			extractMappings(scanner);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void extractMappings(Scanner scanner) {
		this.deltaFunction = new DeltaFunction();
		
		while(scanner.hasNext()) {
			//Contains the entire mapping expression
			String[] currentMappingElements = scanner.nextLine().split("->");
			
			//Contains id of the current state and the input with it
			String[] fromElements = currentMappingElements[0].split(",");
			String from = "";
			for(String each : fromElements) {
				from += each.trim();
			}

			//Contains all go to states for that mapping, so we need State objects
			String to = currentMappingElements[1].trim();
			ArrayList<State> gotoStates = extractStates(to);
			
			
			this.deltaFunction.defineMapping(from, gotoStates);
		}
		
	}

	private boolean extractDefinition(String firstFiveRows) {
		Scanner scanner = new Scanner(firstFiveRows);
		
		
		for(int count=1; count<5; count++) {
			String currentLine = scanner.nextLine();
			switch(count) {
				case 1:
					allStates = extractStates(currentLine);
					break;
				case 2:
					allowedInputs = extractAllowedInputs(currentLine);
					break;
				case 3:
					acceptedStates = extractStates(currentLine);
					break;
				case 4:
					startState = new State(currentLine.trim());
					break;
			}
		}
		scanner.close();
		return true;
	}
	
	public DFA getDFA() {
		return new DFA(this.allStates,this.acceptedStates,this.startState,this.allowedInputs,this.deltaFunction);
	}


	private ArrayList<String> extractAllowedInputs(String currentLine) {
		String[] inputs = currentLine.split(",");
		ArrayList<String> allowedInputs = new ArrayList<String>();
		
		for(String eachInput : inputs) {
			allowedInputs.add(eachInput.trim());
		}
		
		return allowedInputs;
	}

	private ArrayList<State> extractStates(String currentLine) {
		String[] stateNames = currentLine.split(",");
		ArrayList<State> allStates = new ArrayList<>();
		
		for(String eachName : stateNames) {
			allStates.add(new State(eachName.trim()));
		}

		return allStates;
	}

	private ArrayList<String> extractInputStrings(String currentLine) {
		String[] inputStrings = currentLine.split("\\|");
		ArrayList<String> list = new ArrayList<String>();
		for(String each : inputStrings) {
			list.add(each.trim());
		}
		return list;
	}


}
