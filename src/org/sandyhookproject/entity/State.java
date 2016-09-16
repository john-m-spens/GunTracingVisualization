package org.sandyhookproject.entity;

public class State {
	
	private String abbrev;
	private String name;
	
	public State() {
		abbrev = "";
		name = "";
	}
	
	public State(String ab, String nm) {
		abbrev = ab;
		name = nm;
	}

	public String getAbbreviation() {
		return abbrev;
	}
	
	public String getName() {
		return name;
	}

	public boolean match(State st) {
		
		boolean isMatch = false;
		
		if (st.getAbbreviation().compareToIgnoreCase(abbrev) == 0) {
			isMatch = true;
		}
		return isMatch;
	}
	
}
