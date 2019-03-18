package com.dfa.doubleparity;

import java.util.ArrayList;
import java.util.HashMap;

public class DoubleParityDFA {
	private String inputString;
	private TransitionFunction delta;
	private ArrayList<State> allStates,acceptedStates;
	private State currentState;
	
	public DoubleParityDFA(String input) {
		this.inputString = input;
		this.initializeAndMap();
	}
	
	private void initializeAndMap() {
		this.allStates = new ArrayList<>();
		this.acceptedStates = new ArrayList<>();
		this.delta = new TransitionFunction();
		
		//Declare states 
		State evenEvenState = new State("EE");
		this.allStates.add(evenEvenState);
		this.acceptedStates.add(evenEvenState);
		
		State evenOddState = new State("EO");
		this.allStates.add(evenOddState);
		
		State oddEvenState = new State("OE");
		this.allStates.add(evenOddState);
		
		State oddOddState = new State("OO");
		this.allStates.add(oddOddState);
		
		//Define states in the mapping function
		this.delta.define(evenEvenState.getToken()+0,oddEvenState);
		this.delta.define(evenEvenState.getToken()+1,evenOddState);
		
		this.delta.define(evenOddState.getToken()+0,oddOddState);
		this.delta.define(evenOddState.getToken()+1,evenEvenState);
		
		this.delta.define(oddEvenState.getToken()+0,evenEvenState);
		this.delta.define(oddEvenState.getToken()+1,oddOddState);
		
		this.delta.define(oddOddState.getToken()+0,evenOddState);
		this.delta.define(oddOddState.getToken()+1,oddEvenState);
		
		
		//Set starting state
		this.currentState = evenEvenState;
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
