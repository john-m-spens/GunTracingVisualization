package org.sandyhookproject.entity;

import java.util.ArrayList;
import java.util.Iterator;

public class States {

	private ArrayList<State> states;
	
	public States() {
		states = new ArrayList<State>();
	}

	public void add(State state) {
		states.add(state);
	}
		
	public State getStateByName(String nm) {
		State nextState;
		Iterator<State> statesList = states.iterator();
				
		while (statesList.hasNext()) {
			nextState = statesList.next();
	
			if (nextState.getName().compareToIgnoreCase(nm) == 0) {
				 return nextState;
			}
		}
		return null;
	}
	
	public State getStateByAbbreviation(String ab) {
		State nextState;
		Iterator<State> statesList = states.iterator();
				
		while (statesList.hasNext()) {
			nextState = statesList.next();
	
			if (nextState.getAbbreviation().compareToIgnoreCase(ab) == 0) {
				 return nextState;
			}
		}
		return null;
	}
}
