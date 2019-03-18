package com.dfa.parity;

import java.util.ArrayList;
import java.util.HashMap;

public class ParityDFA {
	
	private String inputString;
	private TransitionFunction delta;
	private ArrayList<State> allStates,acceptedStates;
	private State currentState;
	
	public ParityDFA(String input) {
		this.inputString = input;
		this.initializeAndMap();
	}
	
	private void initializeAndMap() {
		this.allStates = new ArrayList<>();
		this.acceptedStates = new ArrayList<>();
		this.delta = new TransitionFunction();
		
		//Declare states 
		State acceptedState = new State("A");
		this.allStates.add(acceptedState);
		this.acceptedStates.add(acceptedState);
		
		State refusedState = new State("B");
		this.allStates.add(refusedState);
		
		//Define states in the mapping function
		this.delta.define(acceptedState.getToken()+0,acceptedState);
		this.delta.define(acceptedState.getToken()+1,refusedState);
		
		this.delta.define(refusedState.getToken()+0,refusedState);
		this.delta.define(refusedState.getToken()+1,acceptedState);
		
		//Set starting state
		this.currentState = acceptedState;
	}
	public void run() {
		
		//Move through the states
		for(int each=0; each<this.inputString.length(); each++) {
			Character currentInput = this.inputString.charAt(each);
			//Test input char
			if(!currentInput.equals('0') && !currentInput.equals('1') ) {
				System.out.println("Invalid input");
				return;
			}
			//Make the transition
			this.currentState = this.delta.calculate(this.currentState.getToken()+currentInput);
		}
		
		//Test the end state
		System.out.print("Input " + this.inputString + " has been : ");
		if(this.acceptedStates.contains(currentState)) {
			System.out.println("Accepted");
			return;
		}
		System.out.println("Declined");
	}
	
	/**
	 * Defines the proper transitions between states
	 * for all inputs
	 *
	 */
	class TransitionFunction{
		private HashMap<String,State> mappingFunction;
		
		public TransitionFunction() {
			this.mappingFunction = new HashMap<>();
		}
		
		public void define(String input,State endState) {
			this.mappingFunction.put(input, endState);
		}
		
		public State calculate(String input) {
			return this.mappingFunction.get(input);
		}
	}
	
	/**
	 *Defines a possible automata state
	 */
	class State{
		private String name;
		
		public State(String name) {
			this.name = name;
		}
		
		/**
		 * 
		 * @return a token that represents this state
		 */
		public String getToken() {
			return this.name;
		}
	}
}
