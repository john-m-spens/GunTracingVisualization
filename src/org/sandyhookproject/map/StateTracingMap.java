package org.sandyhookproject.map;

import java.util.ArrayList;

public class StateTracingMap {
	
	private int id;
	private long population;
	private String name;
	private ArrayList<Long> data;

	public StateTracingMap(int i, String nm, long pop) {
		id = i;
		population = pop;
		name = nm;
		data = new ArrayList<Long>();
	}

	public StateTracingMap(int i, String nm, long pop, long[] dat) {
		id = i;
		population = pop;
		name = nm;
		data = new ArrayList<Long>();
		
		for (i=0;i<56;i++) {
			data.add(new Long(dat[i]));
		}
		
	}
	
	public int getId() {
		return id;
	}
	
	public long getPopulation() {
		return population;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Long> getData() {
		return data;
	}
	
	public void getDataElement(int i) {
		data.get(i);
	}

	public void setData(int i, long val) {
		data.set(i, val);
	}
	
}
