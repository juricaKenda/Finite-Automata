

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Stack;
import java.util.stream.Collectors;



public class DFA {

	private ArrayList<State> allStates,acceptedStates;
	private State startState;
	private ArrayList<String> allowedInputs;
	private DeltaFunction delta;
	private int nextGroupID = 1;
	private ArrayList<Integer> groups = new ArrayList<>();
	
	public DFA(ArrayList<State> allStates, ArrayList<State> acceptedStates, State startState, ArrayList<String> allowedInputs, DeltaFunction deltaFunction) {
		this.acceptedStates = acceptedStates;
		this.startState = startState;
		this.allowedInputs = allowedInputs;
		this.delta = deltaFunction;
		this.allStates = allStates;
		this.groups.add(Constants.DEFAULT_GROUP);
		nextGroupID = Constants.DEFAULT_GROUP + 1;
	}

	@Override
	public String toString() {
		return "DFA\n"+"allStates=" + allStates + "\nallowedInputs=" + allowedInputs + "\nacceptedStates=" + acceptedStates + "\nstartState=" + startState
				+ "\ndelta=" + delta;
	}

	public void pretty() {
		System.out.println(stateArrayfomat(this.allStates));
		System.out.println(stringArrayFormat(this.allowedInputs));
		System.out.println(stateArrayfomat(this.acceptedStates));
		System.out.println(this.startState.toString().trim());
		printMappings();
		
	}
	
	private void printMappings() {
		for(State eachState : this.allStates) {
			for(String eachInput : this.allowedInputs) {
				ArrayList<State> testList = this.delta.getMapping(eachState.toString()+eachInput);
				if(testList != null) {
					State returnedState = testList.get(0);
					System.out.println(eachState.toString().trim() +","
									  +eachInput.trim() + "->"
									  +returnedState.toString().trim());
				}
			}
		}
	}

	private String stringArrayFormat(ArrayList<String> list) {
		return list.toString()
				   .replace("[", "")
                   .replace("]", "")
                   .replace(" ", "")
                   .trim();
	}

	private String stateArrayfomat(ArrayList<State> list) {
		return list.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .trim();
	}

	

	public void minimise() {
		dropUnreachableStates();
		
		groupByAcceptability();
//		this.seeGroups();
		
		while(groupBySimilarty()) {
			removeEmptyGroups();
			this.seeGroups();
		};
		
		clearSimilars();
	}

	private void clearSimilars() {
		for(int each : this.groups) {
			ArrayList<State> group = this.getCurrentGroup(each);
			group = this.trimAndSort(group);
			if(group.size() == 0) {
				continue;
			}
			State first = group.get(0);
			
			//Remove left hand side mappings
			group.stream().filter(state -> !state.equals(first))
				 .forEach(state -> this.removeFromMappings(state));
			
			//Remove from all states
			group.stream().filter(state -> !state.equals(first))
			 .forEach(state -> this.allStates.remove(state));
			
			//Replace in delta
			group.stream().filter(state -> !state.equals(first))
			 .forEach(state -> this.replaceMappings(state,first));
			
			//Replace in accepted states 
			group.stream().filter(state -> !state.equals(first))
			 .forEach(state -> this.replaceInAccepted(state,first));
		
			//Replace in start state
			group.stream().filter(state -> !state.equals(first))
			 .forEach(state -> this.replaceInStart(state,first));
		}
	}

	private void replaceInStart(State state, State first) {
			if(this.startState.equals(state)) {
				this.startState = first;
			}
	}

	private void replaceInAccepted(State state, State first) {
		if(this.acceptedStates.contains(state)) {
			this.acceptedStates.remove(state);
			this.acceptedStates.add(first);
			this.acceptedStates =  this.trimAndSort(acceptedStates);
		}
	}

	private void replaceMappings(State state, State first) {
		HashMap<String,ArrayList<State>> mappings = this.delta.getAll();
		
		for(String eachInput : this.allowedInputs) {
			for(State each : this.allStates) {
				if(mappings.get(each.toString()+eachInput).contains(state)) {
					ArrayList<State> replacedList = new ArrayList<>();
					replacedList.add(first);
					mappings.replace(each.toString()+eachInput,replacedList);
				}
				
			}
		}
		
	}

	private void removeFromMappings(State state) {
		for(String eachInput : this.allowedInputs) {
			this.delta.removeMapping(state.toString()+eachInput);
		}
		
	}

	private void removeEmptyGroups() {
		ArrayList<Integer> willBeRemoved = new ArrayList<Integer>();
		
		for(int each : this.groups) {
			if(this.getCurrentGroup(each).size() == 0) {
				willBeRemoved.add(each);
			}
		}
		
		for(int eachToRemove : willBeRemoved) {
			this.groups.remove(eachToRemove);
		}
		
	}

	private boolean groupBySimilarty() {
		
		Stack<Integer> possibleGroups = new Stack<Integer>();
		
		//Search each group 
		for(int eachGroupNumber : this.groups) {
			
			//Get a group
			ArrayList<State> currentGroup = getCurrentGroup(eachGroupNumber);
			if(currentGroup.size()==0) {
				continue;
			}
			//If exists, get the first state
			State firstState = currentGroup.get(0);
			
			//A place to store
			ArrayList<Integer> newGroups = new ArrayList<Integer>();
			
			
			for(State comparingState : currentGroup) {
				
				//They don't match and need to be separated
				if(!this.gotoSameGroups(firstState, comparingState)) {
					
					//Compare comparing state to all new groups that may have been formed
					if(!this.fitsInAnyGroup(comparingState,newGroups)) {
						comparingState.setGroup(this.giveNewId());
						newGroups.add(this.getGroup(comparingState));
					}
				
				//They do match and we assign them to the same groups
				}else {
					comparingState.setGroup(this.getGroup(firstState));
				}
				
			}
			
			//Store new groups
			for(int eachNew : newGroups) {
				possibleGroups.push(eachNew);
			}
		}
		
		this.groups.addAll(possibleGroups);		
		return possibleGroups.size()>0;
	}

	private boolean fitsInAnyGroup(State comparingState, ArrayList<Integer> newGroups) {
		for(int group : newGroups) {
			ArrayList<State> currentGroup = this.getCurrentGroup(group);
			for(State each : currentGroup) {
				if(this.gotoSameGroups(comparingState, each)) {
					comparingState.setGroup(this.getGroup(each));
					return true;
				}
			}
		}
		return false;
	}

	private ArrayList<State> getCurrentGroup(int eachGroup) {
		return (ArrayList<State>)this.allStates.stream()
							 .filter(state -> ((Integer)state.getGroup()).equals(eachGroup))
							 .collect(Collectors.toList());
	}

	private void groupByAcceptability() {
		int acceptableGroupId = this.giveNewId();
		
		this.groups.add(acceptableGroupId);
		
		int unacceptableGroupId = this.giveNewId();
		this.groups.add(unacceptableGroupId);
		
		for(State eachState : allStates) {
			
			if(this.acceptedStates.contains(eachState)) {
				eachState.setGroup(acceptableGroupId);
			}else {
				eachState.setGroup(unacceptableGroupId);
			}
		}
		
		this.groups.remove(Constants.DEFAULT_GROUP);
		
	}

	private void dropUnreachableStates() {
		ArrayList<State> reachableStates = new ArrayList<State>();
		Stack<State> toCheckStack = new Stack<>();
		toCheckStack.push(startState);
		
		while(!toCheckStack.isEmpty()) {
			State currentlyChecking = toCheckStack.pop();
			
			for(String each : this.allowedInputs) {
				
				ArrayList<State> moreStates = this.delta.getMapping(currentlyChecking.toString()+each);
				if(moreStates != null) {
					for(State eachNewState : getDistinct(moreStates)) {
						if(!reachableStates.contains(eachNewState)) {
							toCheckStack.push(eachNewState);
						}
						
					}
					
				}
				reachableStates.add(currentlyChecking);
			}
		}
		//Remove unreachable states
		ArrayList<State> unreachableStates = new ArrayList<>();
		reachableStates = trimAndSort(reachableStates);
		
		for(State unreachable : this.allStates) {
			if(!reachableStates.contains(unreachable)) {
				unreachableStates.add(unreachable);
			}
		}
				
		this.allStates.clear();
		this.allStates.addAll(reachableStates);
		
		dropUnreachableTransitions(unreachableStates);
		dropUnreachableAccepted(allStates);
	}
	
	
	private boolean gotoSameGroups(State one,State two) {
		
		for(String eachInput : this.allowedInputs) {
			
			State stateFromOne = this.delta.getMapping(one.toString()+eachInput).get(0);
			State stateFromTwo = this.delta.getMapping(two.toString()+eachInput).get(0);
			
			if(stateFromOne.equals(stateFromTwo))	continue;
			
			if(this.getGroup(stateFromOne) != this.getGroup(stateFromTwo)) {
				return false;
			}
		}
		return true;
	}
	
	private void dropUnreachableAccepted(ArrayList<State> reachable) {
		this.acceptedStates= (ArrayList<State>)this.acceptedStates.stream()
												.filter(state -> reachable.contains(state))
												.collect(Collectors.toList());
		
	}

	private void dropUnreachableTransitions(ArrayList<State> unreachableStates) {
		for(State unreachable : unreachableStates) {
			for(String eachInput : this.allowedInputs) {
				this.delta.removeMapping(unreachable.toString()+eachInput);
			}
		}
		
	}
	
	public void seeGroups() {
//		System.out.println();
//		for(int eachGroup : this.groups) {
//			System.out.println("\nGROUP " + eachGroup);
//			this.allStates.stream()
//						  .filter(state-> ((Integer )state.getGroup()).equals(eachGroup))
//						  .forEach(state -> System.out.print(state+","));
//		}
	}

	
	public ArrayList<State> getDistinct(ArrayList<State> forDistinct){
		return (ArrayList<State>)forDistinct.stream().distinct().collect(Collectors.toList());
	}

	public ArrayList<State> trimAndSort(ArrayList<State> forSorting){
		forSorting = getDistinct(forSorting);
		Collections.sort(forSorting,Comparator.comparingInt(state-> this.allStates.indexOf(state)));
		return forSorting;
	}
	
	private int getGroup(State toSearch) {
		for(State each : this.allStates) {
			if(toSearch.equals(each)) {
				return each.getGroup();
			}
		}
		
		return -100;
	}
	
	private int giveNewId() {
		return this.nextGroupID++;
	}
}
