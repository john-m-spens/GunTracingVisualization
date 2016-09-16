package org.sandyhookproject.entity;

public class StatePopulation {

	private String stateAbbreviation;
	private int yearOfRecord;
	private long population;
	
	public StatePopulation() {

		stateAbbreviation = "";
		yearOfRecord = 0;
		population = 0;
	}
	
	public StatePopulation(String abbrev, int popYear, long pop) {
		
		stateAbbreviation = abbrev;
		yearOfRecord = popYear;
		population = pop;
	}
	
	public String getStateAbbreviation() {
		return stateAbbreviation;
	}
		
	public int getYearOfRecord() {
		return yearOfRecord;
	}

	public long getPopulation() {
		return population;
	}
	
	public boolean match(StatePopulation stateRec) {
		return matchState(stateRec.getStateAbbreviation(),stateRec.getYearOfRecord()); 
	}
	
	public boolean match(String stateAbbrev, int yr) {
		return matchState(stateAbbrev, yr); 
	}

	private boolean matchState(String stateAbbrev, int yr) {
		return ((stateAbbreviation.compareToIgnoreCase(stateAbbrev) == 0) && (yr == yearOfRecord));
	}

	public String returnJSONobject() {
		
	  	String retString = "{\"stateAbbrev\":\"";
		retString = retString.concat(stateAbbreviation);
		retString = retString.concat("\",\"populationYear\":");
		retString = retString.concat(Integer.toString(yearOfRecord));
		retString = retString.concat(",\"population\":");
		retString = retString.concat(Long.toString(population));
		retString = retString.concat("}");
		return retString;
	}
}
