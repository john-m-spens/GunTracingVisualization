package org.sandyhookproject.entity;

import java.util.ArrayList;
import java.util.Iterator;

public class GunTraces {

	private ArrayList<GunTrace> gunTraces;
	
	public GunTraces() {
		gunTraces = new ArrayList<GunTrace>();
	}
	
	public void add(GunTrace trace) {
		gunTraces.add(trace);
	}
	
	public GunTraces getTraces(State state) {
		
		GunTraces traces = new GunTraces();
		GunTrace nextTrace;
		
		Iterator<GunTrace> traceIterator = gunTraces.iterator();
		
		while (traceIterator.hasNext()) {
			nextTrace = traceIterator.next();
			State nextState = nextTrace.getTracedTo();
			
			if (nextState.match(state)) {
				 traces.add(nextTrace);
			 }
		}
		return traces;
	}

	public GunTraces getTraces(State state, int yr) {
		
		GunTraces traces = new GunTraces();
		GunTrace nextTrace;
		
		Iterator<GunTrace> traceIterator = gunTraces.iterator();
		
		while (traceIterator.hasNext()) {
			nextTrace = traceIterator.next();
			State nextState = nextTrace.getTracedTo();
			
			if (nextState == null) {
				return traces;
			}
			
			if (nextState.match(state)) {
				if (nextTrace.getTraceYear() == yr) {
					traces.add(nextTrace);
				}
			 }
		}
		return traces;
	}
	public GunTrace findTrace(State tracedTo, State tracedFrom, int traceYear) {
		
		long gunCounts = 0;
		Iterator<GunTrace> traceIterator = gunTraces.iterator();
		Boolean hasMore = traceIterator.hasNext();
		
		while (hasMore) {
			GunTrace tr = traceIterator.next();
			hasMore = traceIterator.hasNext();
			State trcdTo = tr.getTracedTo();
			State trcdFrom = tr.getStateTracedFrom();
			
			if ((trcdTo != null) && (trcdFrom != null)) {
				int trcdYr = tr.getTraceYear();
				
				if (trcdFrom.match(tracedFrom)) {
				
					if (trcdTo.match(tracedTo)) {
						if (trcdYr == traceYear) {
							gunCounts = tr.getGunsTraced();
							hasMore = false;
						}
					}
				}
			}
		}
		GunTrace trace = new GunTrace(tracedTo, traceYear, tracedFrom, gunCounts);
		return trace;
	}
	
}
