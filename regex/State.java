package com.dfa.regex;

public class State{
	private String name;

	public State(String name) {
		super();
		this.name = name;
	}

	public String getToken() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}