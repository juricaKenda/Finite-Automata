package com.nfa.epsilon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;
import java.util.stream.Collectors;

public class NFA {

	private ArrayList<State> allStates,acceptedStates;
	private State startState;
	private ArrayList<String> inputStrings;
	private ArrayList<String> allowedInputs;
	private DeltaFunction delta;
	File outputFile;
	
	public NFA(ArrayList<State> allStates, ArrayList<State> acceptedStates, State startState,
			ArrayList<String> inputStrings, ArrayList<String> allowedInputs, DeltaFunction delta) {
		super();
		this.allStates = allStates;
		this.acceptedStates = acceptedStates;
		this.startState = startState;
		this.inputStrings = inputStrings;
		this.allowedInputs = allowedInputs;
		this.delta = delta;
		
	}

	
	
	public String[] runThroughInputs(String fileLoc) {
		this.outputFile = new File(fileLoc);
		//Result set predstavlja skup rezultata za svaki od input nizova
		String[] resultSet = new String[inputStrings.size()];
		int currentResultNumber = 0;
		
		//Pisac za file
		try (FileWriter writer = new FileWriter(outputFile)){
		
		//Moramo provjeriti svaki od nizova stanja
		for(String eachInputSet : this.inputStrings) {
			
			//Svaki niz stanja ima svoj log i svoju listu stanja
			StringBuilder statesLog = new StringBuilder();
			ArrayList<State> currentLevelStates = new ArrayList<>();
			
			//Pocetno stanje uvijek ulazi u listu stanja
			currentLevelStates.add(startState);
			//Hodamo po stanjima za svaki znak niza
			for(String currentInput : eachInputSet.split(",")) {
				
				//Epsilon prijelaz
				currentLevelStates.addAll(epsilonTransition(currentLevelStates));
				statesLog.append(this.formatForOutput(currentLevelStates)+"|");

				//Input prijelaz
				ArrayList<State> nextLevelStates = inputTransition(currentLevelStates,currentInput);
				//nakon ovog prijelaza vazna su nam samo stanja koja idu u sljedeci korak
				currentLevelStates.clear();
				currentLevelStates.addAll(getDistinct(nextLevelStates));
			}
			//Epsilon prijelaz
			currentLevelStates.addAll(epsilonTransition(currentLevelStates));
			statesLog.append(formatForOutput(currentLevelStates));
			
			//Spremanje rezultata loga u resultset i u file
			resultSet[currentResultNumber] = statesLog.toString();
			System.out.println(resultSet[currentResultNumber]);
			writer.append(statesLog.toString()+"\n");
			currentResultNumber++;
			
		}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	private ArrayList<State> epsilonTransition(ArrayList<State> forTransition){
		Stack<State> toCheckStack = new Stack<>();
		
		for(State each : forTransition) {
			toCheckStack.push(each);
		}
		
		ArrayList<State> toReturn = new ArrayList<State>();
		while(!toCheckStack.isEmpty()) {
			State currentState = toCheckStack.pop();
			ArrayList<State> epsilonResults = this.delta.getMapping(currentState.toString()+"$");
			if(epsilonResults != null) {
				for(State each : this.getDistinct(epsilonResults)) {
					if(!toReturn.contains(each)) {
						toCheckStack.push(each);
					}
				
				}
			}
			toReturn.add(currentState);
		}
		
		return this.getDistinct(toReturn);
	}
	private String formatForOutput(ArrayList<State> toFormat) {
		toFormat = this.trimAndSort(toFormat);
		return toFormat.toString().replace("[", "").replace("]", "").replace(" ","");
	}
	private ArrayList<State> getDistinct(ArrayList<State> forDistinct){
		return (ArrayList<State>)forDistinct.stream().distinct().collect(Collectors.toList());
	}
	private ArrayList<State> trimAndSort(ArrayList<State> forSorting){
		forSorting = (ArrayList<State>)forSorting.stream().distinct().collect(Collectors.toList());
		if(forSorting.size() > 1) {
			forSorting.removeIf(state -> (state.toString().trim()).equals("#"));
		}
		Collections.sort(forSorting,Comparator.comparingInt(state-> this.allStates.indexOf(state)));
		return forSorting;
	}
	private ArrayList<State> inputTransition(ArrayList<State> thisLevelStates, String currentInput) {
		ArrayList<State> inputExtraStates = new ArrayList<State>();
		for(State each : thisLevelStates) {
			ArrayList<State> stateExtra = delta.getMapping(each.toString()+currentInput);
			if(stateExtra != null) {
				inputExtraStates.addAll(stateExtra);
			}
		}
		return inputExtraStates;
	}

	@Override
	public String toString() {
		return "NFA\nallStates=" + allStates + ",\nacceptedStates=" + acceptedStates + ",\nstartState=" + startState
				+ ",\ninputStrings=" + inputStrings + ",\nallowedInputs=" + allowedInputs + ",\ndelta->" + delta + "\n";
	}
	
	
}
