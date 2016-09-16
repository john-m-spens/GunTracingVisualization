package org.sandyhookproject.map;

import java.util.ArrayList;

public class GunTracingMap {
	
	private ArrayList<StateTracingMap> gunTracingMap;
	private int gunTracingIdx = 0;
	
	public GunTracingMap() {
		gunTracingMap = new ArrayList<StateTracingMap>();
	}
	
	public void add(StateTracingMap stateTracingMap) {
		gunTracingMap.add(stateTracingMap);
	}
	
	public StateTracingMap get(int i) {
		return gunTracingMap.get(i);
	}

	public void first() {
		gunTracingIdx = 0;		
	}

	public boolean hasNext() {
		return (gunTracingIdx < gunTracingMap.size());
	}

	public StateTracingMap next() {
		return gunTracingMap.get(gunTracingIdx++);
	}
}
