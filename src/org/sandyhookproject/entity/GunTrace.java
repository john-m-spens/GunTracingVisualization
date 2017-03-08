package org.sandyhookproject.entity;

public class GunTrace {

	private State tracedTo;
	private int traceYear;
	private State tracedFrom;
	private long gunsTraced;
	
	public GunTrace() {
		tracedTo = new State();
		traceYear = 0;
		tracedFrom = new State();
		gunsTraced = -1;
	}
	
	public GunTrace(State st, int yr, State trFrom, long nGuns) {
		
		tracedTo = st;
		traceYear = yr;
		tracedFrom = trFrom;
		gunsTraced = nGuns;
		
	}
	
	public State getTracedTo() {
		
		if (tracedTo == null) {
			tracedTo = new State();
		} 
		return tracedTo;
	}
	
	public int getTraceYear() {
		return traceYear;
	}
	public State getStateTracedFrom() {

		if (tracedFrom == null) {
			tracedFrom = new State();
		}
		return tracedFrom;
	}
	
	public long getGunsTraced() {
		return gunsTraced;
	}

	public boolean match(GunTrace trace) {
		
		boolean isMatch = false;
		
		if (tracedTo.match(trace.getTracedTo())) {
			if (traceYear == trace.getTraceYear()) {
				if (tracedFrom.match(trace.getStateTracedFrom())) {
					isMatch = true;
				}
			}
		}
		return isMatch;
	}
}
