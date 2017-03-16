package org.sandyhookproject.dataLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.sandyhookproject.entity.*;

public class DataLoader {


	private Connection con;
	
	public DataLoader() {

		String configurationFile = "/org/sandyhookproject/resources/config.properties";
		
	    try {
			String connectionString = getConfigurationSetting(configurationFile, "connection");
			String userName = getConfigurationSetting(configurationFile, "userName");
			String password = getConfigurationSetting(configurationFile, "password");
		
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(connectionString, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public StatePopulations loadStatePopulations() {
		
		StatePopulations statePops = new StatePopulations();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT state_abbrev, population_year, population FROM state_populations");
			
			while (rs.next()) {
				statePops.add(rs.getString(1),rs.getInt(2), rs.getInt(3));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statePops;
	}
	
	public GunTraces loadGunTraces() {
		
		GunTraces traces = new GunTraces();
		States states = loadStates();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT located_in, trace_year, originated_in, num_guns FROM gun_trace_data");
			
			while (rs.next()) {
				int yr = rs.getInt(2);
				State tracedTo = states.getStateByAbbreviation(rs.getString(3));
				State tracedFrom = states.getStateByAbbreviation(rs.getString(1));
				long nGuns = rs.getLong(4);
				GunTrace trace = new GunTrace(tracedTo,yr,tracedFrom,nGuns);
				traces.add(trace);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return traces;
	}
	public States loadStates() {
		
		States states = new States();
		states.add(new State("AL", "Alabama"));
		states.add(new State("AK", "Alaska"));
		states.add(new State("AZ", "Arizona"));
		states.add(new State("AR", "Arkansas"));
		states.add(new State("CA", "California"));
		states.add(new State("CO", "Colorado"));
		states.add(new State("CT", "Connecticut"));
		states.add(new State("DE", "Delaware"));
		states.add(new State("DC", "District of Columbia"));
		states.add(new State("FL", "Florida"));
		states.add(new State("GA", "Georgia"));
		states.add(new State("HI", "Hawaii"));
		states.add(new State("ID", "Idaho"));
		states.add(new State("IL", "Illinois"));
		states.add(new State("IN", "Indiana"));
		states.add(new State("IA", "Iowa"));
		states.add(new State("KS", "Kansas"));
		states.add(new State("KY", "Kentucky"));
		states.add(new State("LA", "Louisiana"));
		states.add(new State("ME", "Maine"));
		states.add(new State("MD", "Maryland"));
		states.add(new State("MA", "Massachusetts"));
		states.add(new State("MI", "Michigan"));
		states.add(new State("MN", "Minnesota"));
		states.add(new State("MS", "Mississippi"));
		states.add(new State("MO", "Missouri"));
		states.add(new State("MT", "Montana"));
		states.add(new State("NB", "Nebraska"));
		states.add(new State("NV", "Nevada"));
		states.add(new State("NH", "New Hampshire"));
		states.add(new State("NJ", "New Jersey"));
		states.add(new State("NM", "New Mexico"));
		states.add(new State("NY", "New York"));
		states.add(new State("NC", "North Carolina"));
		states.add(new State("ND", "North Dakota"));
		states.add(new State("OH", "Ohio"));
		states.add(new State("OK", "Oklahoma"));
		states.add(new State("OR", "Oregon"));
		states.add(new State("PA", "Pennsylvania"));
		states.add(new State("RI", "Rhode Island"));
		states.add(new State("SC", "South Carolina"));
		states.add(new State("SD", "South Dakota"));
		states.add(new State("TN", "Tennessee"));
		states.add(new State("TX", "Texas"));
		states.add(new State("UT", "Utah"));
		states.add(new State("VT", "Vermont"));
		states.add(new State("VA", "Virginia"));
		states.add(new State("WA", "Washington"));
		states.add(new State("WV", "West Virginia"));
		states.add(new State("WI", "Wisconsin"));
		states.add(new State("WY", "Wyoming"));
		return states;
	}

	private String getConfigurationSetting(String propertiesFileName, String propertyName) {
		
		String propertyValue = "";
		InputStream configFile = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName);
		
		if (configFile != null) {
		    try {
				Properties configProps = new Properties();
				configProps.load(configFile);
				propertyValue = configProps.getProperty(propertyName).trim();
			
		    } catch (FileNotFoundException ex) {
		    	System.out.println("File Not Found!");
			propertyValue = "";
		
		    } catch (IOException ex) {
		    	System.out.println("File Not Found!");
		    }
		}
		return propertyValue;
	}

	/*
	private void createTestFile() {
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			String content = "This is the content to write into file\n";
			File newFile = new File("CreateThisFile.txt");
			System.out.println(newFile.getAbsoluteFile());
			
			fw = new FileWriter("WhereAmI.txt");
			bw = new BufferedWriter(fw);
			bw.write(content);
		  	System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private void getClassPaths() {
	
		ClassLoader cl = this.getClass().getClassLoader();
		URLClassLoader urlClassLoader = (URLClassLoader)cl;
		URL configUrl = urlClassLoader.findResource("/org/sandyhookproject/resources/config.properties");
		
		if (configUrl != null) {
			System.out.println(configUrl.toString());
		}
		else {
			System.out.println("Configuration File Not Found!");
		}
		
		try {
			urlClassLoader.close();
		}
		catch (IOException e) {
			System.out.println("Failed to close urlClassLoader!");
		}
		
	        URL[] urls = ((URLClassLoader)cl).getURLs();

	        for(URL url: urls){
	        	System.out.println(url.getFile());
	        }
		
	}
	*/
}



