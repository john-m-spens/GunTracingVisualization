package org.sandyhookproject.webService;

import javax.ws.rs.GET; 
import javax.ws.rs.Path;  
import javax.ws.rs.PathParam;  
import javax.ws.rs.Produces;  
import javax.ws.rs.core.MediaType;

import org.sandyhookproject.Json.*;
import org.sandyhookproject.map.*;
import org.sandyhookproject.mapLoader.*;


@Path("GunTracingService")

public class GunTracingService {

	@GET  
    @Path("/getGunTraces/{tracingYear}")  
     @Produces(MediaType.APPLICATION_JSON)  
    
	public String getGunTraces(@PathParam("tracingYear") int tracingYear) {  
		
		GunTracingMapLoader gunTracingMapLoader = new GunTracingMapLoader();
		GunTracingMap gunTracingMap = gunTracingMapLoader.loadGunTracingMap(tracingYear); 
	 	GunTracingMapJson gunTracingMapJson = new GunTracingMapJson();
		String json = gunTracingMapJson.generateGunTracingMapJson(gunTracingMap);
		return json;
	}  
}
