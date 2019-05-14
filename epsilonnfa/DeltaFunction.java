package com.nfa.epsilon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

public class DeltaFunction {

	private HashMap<String,ArrayList<State>> mappings;
	
	public DeltaFunction() {
		this.mappings = new HashMap<String, ArrayList<State>>();
	}
	
	public void defineMapping(String idAndInput,ArrayList<State> gotoStates) {
		this.mappings.put(idAndInput.trim(), gotoStates);
	}
	
	public ArrayList<State> getMapping(String idAndInput){
		
		if( !idAndInput.contains("$") && this.mappings.get(idAndInput) == null){
			ArrayList<State> listWithNull = new ArrayList<State>();
			listWithNull.add(new State("#"));
			return listWithNull;
		}
		return this.mappings.get(idAndInput);
	}
	
	@Override
	public String toString(){
		return this.mappings.toString();
	}
}
