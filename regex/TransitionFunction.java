package com.dfa.regex;

import java.util.HashMap;

public class TransitionFunction{
	private HashMap<String,State> mappings ;
	
	public TransitionFunction() {
		this.mappings = new HashMap<>();
	}
	
	public void define(String input,State endState) {
		this.mappings.put(input, endState);
	}
	
	public State getNextState(String input) {
		return this.mappings.get(input);
	}
}
