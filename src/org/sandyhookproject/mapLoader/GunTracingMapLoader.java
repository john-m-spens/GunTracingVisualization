package org.sandyhookproject.mapLoader;

import org.sandyhookproject.dataLoader.DataLoader;
import org.sandyhookproject.entity.*;
import org.sandyhookproject.map.GunTracingMap;
import org.sandyhookproject.map.StateTracingMap;

public class GunTracingMapLoader {

	private String[] stateSequence = {"Alabama","Alaska","<empty>","Arizona","Arkansas","California","<empty>","Colorado","Connecticut","Delaware","District of Columbia","Florida","Georgia","<empty>","Hawaii","Idaho","Illinois","Indiana","Iowa","Kansas","Kentucky","Louisiana","Maine","Maryland","Massachusetts","Michigan","Minnesota","Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico","New York","North Carolina","North Dakota","Ohio","Oklahoma","Oregon","Pennsylvania","<empty>","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Vermont","Virginia","<empty>","Washington","West Virginia","Wisconsin","Wyoming"};
	private long[] emptyTraceData = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	private States states;
	private StatePopulations statePops;
	private GunTraces gunTraces;
	
	public GunTracingMapLoader() {
		
		DataLoader dataLoader = new DataLoader();
		gunTraces = dataLoader.loadGunTraces();
		states = dataLoader.loadStates();
		statePops = dataLoader.loadStatePopulations();
	}
	
	public GunTracingMap loadGunTracingMap(int traceYear) {
		
		GunTracingMap gunTracingMap = new GunTracingMap();
		
		for (int i=0;i < stateSequence.length;i++) {
		
			if (stateSequence[i].compareToIgnoreCase("<empty>") == 0) {
				gunTracingMap.add(new StateTracingMap(i+1,"<empty>", 0, emptyTraceData));
			} else {
				State state = states.getStateByName(stateSequence[i]);
				String stAbbrev = state.getAbbreviation();
				StatePopulation statePop = statePops.find(stAbbrev, traceYear);
				long population = statePop.getPopulation();
				gunTraces.getTraces(state, traceYear);
				long[] traceDataArray = createTraceDataArray(state,traceYear);
				StateTracingMap stateTracingMap = new StateTracingMap(i+1,state.getName(), population, traceDataArray);
				gunTracingMap.add(stateTracingMap);
			}
		}
		return gunTracingMap;
	}
	
	private long[] createTraceDataArray(State tracedTo, int traceYear) {

		long[] traceData = emptyTraceData;
		
		for (int i=0;i < stateSequence.length;i++) {
			
			if (stateSequence[i].compareToIgnoreCase("<empty>") != 0) {
				State tracedFrom = states.getStateByName(stateSequence[i]);
				GunTrace gunTrace = gunTraces.findTrace(tracedTo, tracedFrom, traceYear);
			
				if (gunTrace == null) {
					traceData[i] = -1;
				} else {
					traceData[i] = gunTrace.getGunsTraced();
				}
			}
		}
		return traceData;
	}
}
