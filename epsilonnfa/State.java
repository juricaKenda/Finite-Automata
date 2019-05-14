package com.nfa.epsilon;

public class State implements Comparable {

	private String idName;
	
	public State(String id) {
		this.idName = id;
	}
	
	@Override
	public String toString() {
		return this.idName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idName == null) ? 0 : idName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (idName == null) {
			if (other.idName != null)
				return false;
		} else if (!idName.equals(other.idName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Object o) {
		State other = (State)o;
		
		return this.idName.compareTo(other.idName);
	}
}
