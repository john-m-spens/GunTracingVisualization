package org.sandyhookproject.tableLoader;

import java.util.Iterator;

import org.sandyhookproject.dataLoader.DataLoader;
import org.sandyhookproject.entity.*;

public class TracingTable {

	private int columns;
	private long rows;
	private GunTraces traces;
	private Iterator<GunTrace> iterator;
	
	public TracingTable() {
		DataLoader dl = new DataLoader();
		traces = dl.loadGunTraces();
		iterator = traces.getIterator();
		columns = traces.getClass().getFields().length;
		rows = traces.count();
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public GunTrace next() {
		
		if (iterator.hasNext()) {
			return iterator.next();
		} else {
			return null;
		}
	}
	
	public int getColumns() {
		return columns;
	}
	
	public Long getRows() {
		return rows;
	}
}
