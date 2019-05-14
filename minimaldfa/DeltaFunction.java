

import java.util.ArrayList;
import java.util.HashMap;


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

	public void removeMapping(String key) {
		this.mappings.remove(key);
	}
	
	public HashMap<String,ArrayList<State>> getAll(){
		return this.mappings;
	}
}
