package org.sandyhookproject.Json;

import java.util.ArrayList;
import java.util.Iterator;

import org.sandyhookproject.map.*;

public class GunTracingMapJson {

	public GunTracingMapJson() {
	}

	public String generateGunTracingMapJson(GunTracingMap gunTracingMap) {

		String json = "{\"states\":[";
		gunTracingMap.first();
		
		while (gunTracingMap.hasNext()) {
			json = json.concat(generateStateTracingMapJson(gunTracingMap.next()));
		}
		json = replaceLastChar(json,"]}");
		return json;
	}
	
	private String generateStateTracingMapJson(StateTracingMap stateTracingMap) {
	
		String json = "{\"type\":\"State\",\"id\":\"";
		json = json.concat(Integer.toString(stateTracingMap.getId()));
		json = json.concat("\",\"name\":\"");
		json = json.concat(stateTracingMap.getName());
		json = json.concat("\",\"population\":");
		json = json.concat(Long.toString(stateTracingMap.getPopulation()));
		json = json.concat(",\"data\":");
		json = json.concat(getStateGunCountsArray(stateTracingMap.getData()));
		json = replaceLastChar(json," },");
		return json;
	}
	
	private String getStateGunCountsArray(ArrayList<Long> data) {

		String json = "[";
		
		Iterator<Long> dataIterator = data.iterator();
		
		while (dataIterator.hasNext()) {
			Long dataElement = dataIterator.next();
			json = json.concat(dataElement.toString());
			json = json.concat(",");
		}
		json = replaceLastChar(json,"] ");
		return json;
	}

	private String replaceLastChar(String str, String repl) {

		int strLen = str.length();
		String strOut = "";
		
		if (strLen > 1) {
			strOut = str.substring(0, strLen-1);
		}
		strOut = strOut.concat(repl);
		return strOut;
	}
}