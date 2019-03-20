package com.automata.moore;

import java.util.ArrayList;
import java.util.HashMap;


public class MooreStateMachine {

	private ArrayList<State> allStates;
	private String inputString;
	private TransitionFunction delta;
	private OutputFunction gamma;
	private State currentState;
	private String currentlyRead;
	
	public MooreStateMachine(String input) {
		this.inputString = input;
		this.currentlyRead = "";
		this.initializeAndMap();
	}
	
	private void initializeAndMap() {
		this.allStates = new ArrayList<>();
		this.delta = new TransitionFunction();
		this.gamma = new OutputFunction();
		
		//Define states with starting state being q0
		State qZero = new State("q0");
		State qOne = new State("q1");
		State qTwo = new State("q2");
		
		this.currentState = qZero;
		
		//Map states in delta function
		this.delta.define(qZero.getToken()+'0', qZero);
		this.delta.define(qZero.getToken()+'1', qOne);
		
		this.delta.define(qOne.getToken()+'0', qTwo);
		this.delta.define(qOne.getToken()+'1', qZero);
		
		this.delta.define(qTwo.getToken()+'0', qOne);
		this.delta.define(qTwo.getToken()+'1', qTwo);
		
		//Map state outputs in gamma function
		this.gamma.define(qZero, "0");
		this.gamma.define(qOne, "1");
		this.gamma.define(qTwo, "10");
		
		
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
					this.currentlyRead += currentInput;
					System.out.println("Remainder for : " + Integer.parseInt(this.currentlyRead ,2)
										+ " -> "+Integer.parseInt(this.gamma.getOuput(currentState),2));
				}

	}
	
	class OutputFunction{
		private HashMap<State,String> outputMapping;
		
		public OutputFunction() {
			this.outputMapping = new HashMap<>();
		}
		
		public void define(State state,String output) {
			this.outputMapping.put(state, output);
		}
		
		public String getOuput(State currentState) {
			return this.outputMapping.get(currentState);
		}
	}
	
	
	
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
		
		@Override
		public String toString() {
			return this.name;
		}
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
	
	
}
