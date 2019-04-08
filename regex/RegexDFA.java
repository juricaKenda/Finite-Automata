package com.dfa.regex;

import java.util.ArrayList;
import java.util.HashMap;

public class RegexDFA {

	private ArrayList<State> allStates;
	private ArrayList<State> allowedStates;
	private TransitionFunction delta;
	private State currentState,startState;
	private String regex;
	
	public RegexDFA(String regex) {
		constructDFA(regex);
	}
	public void constructDFA(String regex) {
		this.regex = regex;
		allStates = new ArrayList<State>();
		delta = new TransitionFunction();
		//Create and add start state
		startState = new State("START");
		currentState = startState;
		allStates.add(startState);
		HashMap<String,Integer> doubleStates = findDoubleStates(regex);
	
		int connectionNum = 0;
		//Create all states
		for(int each=0; each <regex.length(); each++) {
			char eachCharacter = regex.charAt(each);
			if(doubleStates.containsKey(""+eachCharacter)) {
				allStates.add(new State(each+""+eachCharacter));
			}else {
				allStates.add(new State(""+eachCharacter));
			}
			connectionNum++;
		}
		//Connect start state and first state of the regular expression	
		delta.define(startState.getToken()+regex.charAt(0), allStates.get(1));
		
		//Map next states in the delta function
		for(int eachIndex=1; eachIndex < connectionNum; eachIndex++) {
			State from = allStates.get(eachIndex);
			State to = allStates.get(eachIndex+1);
			char transition = regex.charAt(eachIndex);
			delta.define(from.getToken()+transition, to);
		}
		
		//Map the return to state one when regular expression starts but does not reach the end
		State to = allStates.get(1); //this is the first relevant state
		char transition = regex.charAt(0); // this is the transition cue
		for(int eachIndex=1; eachIndex < connectionNum; eachIndex++) {
			State from = allStates.get(eachIndex);
			delta.define(from.getToken()+transition, to);
		}

		//Only put the final state as the allowed state
		allowedStates = new ArrayList<>();
		allowedStates.add(allStates.get(connectionNum));
		
	}
	
	private HashMap<String,Integer> findDoubleStates(String regex) {
		HashMap<String,Integer> doubleStates = new HashMap<String, Integer>();
		
		//Collect and note the doubles
		for(int each=0; each <regex.length(); each++) {
			char eachCharacter = regex.charAt(each);
			if(!doubleStates.containsKey(""+eachCharacter)) {
				doubleStates.put(""+eachCharacter,1);
			}else {
				doubleStates.put(""+eachCharacter,doubleStates.get(""+eachCharacter)+1);
			}
			
		}
		//Remove the non doubles
		for(int each=0; each <regex.length(); each++) {
			char eachCharacter = regex.charAt(each);
			if(doubleStates.get(""+eachCharacter)==1) {
				doubleStates.remove(""+eachCharacter);
			}
		}
		return doubleStates;
	}
		
	public void run(String input) {
		for(int each=0; each<input.length(); each++) {
			Character currentInput = input.charAt(each);
			//Make the transition
			if(this.delta.getNextState(this.currentState.getToken()+currentInput) == null) {
				this.currentState = this.startState;
			}
			else{
				this.currentState = this.delta.getNextState(this.currentState.getToken()+currentInput);
				if(this.allowedStates.contains(currentState)) {
					System.out.println(this.regex+" found!");
					this.currentState = this.startState;
				}
			}
		}
	}
	
	
	
}
