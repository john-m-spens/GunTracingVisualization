package org.sandyhookproject.webService;

import javax.ws.rs.GET; 
import javax.ws.rs.Path;  
import javax.ws.rs.PathParam;  
import javax.ws.rs.Produces;  
import javax.ws.rs.core.MediaType;
import org.sandyhookproject.dataLoader.DataLoader;
import org.sandyhookproject.entity.StatePopulation;
import org.sandyhookproject.entity.StatePopulations;

@Path("StatePopulationService")

public class StatePopulationService {

	@GET  
    @Path("/getStatePopulation/{state}/{populationYear}")  
     @Produces(MediaType.APPLICATION_JSON) // TEXT_XML)  
    
	public String getStatePopulation(@PathParam("state") String state, @PathParam("populationYear") int populationYear) {  
		
		DataLoader dataLoader = new DataLoader();
		
		StatePopulations statePopulations = dataLoader.loadStatePopulations();
		StatePopulation statePop = statePopulations.find(state, populationYear);
		return statePop.returnJSONobject();
	}  
}