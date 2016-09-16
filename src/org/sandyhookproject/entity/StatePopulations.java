package org.sandyhookproject.entity;

import java.util.ArrayList;
import java.util.Iterator;

public class StatePopulations {
	
	private ArrayList<StatePopulation> statesPop;
	
	public StatePopulations() {
		statesPop = new ArrayList<StatePopulation>();
	}
		
	public void add(StatePopulation statePop) {
		addNewStatePop(statePop);
	}
	
	public void add(String stateAbbrev, int yr, long pop) {
		StatePopulation statePop = new StatePopulation(stateAbbrev, yr,pop);
		addNewStatePop(statePop);
	}
	
	public StatePopulation find(String abbrev, int yr) {
		
		Iterator<StatePopulation> statesList = statesPop.iterator();
	
		while (statesList.hasNext()) {
			StatePopulation nextStatePop = statesList.next();

			if (nextStatePop.match(abbrev, yr)) {
				 return nextStatePop;
			 }
		}
		return null;
	}

	private void addNewStatePop(StatePopulation statePop) {
		statesPop.add(statePop);
	}

}
