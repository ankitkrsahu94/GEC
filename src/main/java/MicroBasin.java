import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class MicroBasin {
	ArrayList<Map<String,Object>> canal_info;

	int parent_id =Constants.PARENT_ID;
	int verification_status =1;
	int loc_id;
	String loc_name;
	int loc_type_id =Constants.LOC_TYPE_MB;
	String area;
	String loc_association;
	String geological_info;
	String infiltration_info;
	Map<String, Map<String, WaterBody>> water_bodies;
	ArtificialWC aritificial_wc;
	String resource_distribution;
	String population;
	public String rf_data;
	public String gw_data;
	public String gw_dependency;
		
	public String getPopulation() {
		return population;
	}
	public void setPopulation(String population) {
		this.population = population;
	}
	public Map<String, Map<String, WaterBody>> getWater_bodies() {
		return water_bodies;
	}
	public void setWater_bodies(Map<String, Map<String, WaterBody>> water_bodies) {
		this.water_bodies = water_bodies;
	}
	public ArrayList<Map<String, Object>> getCanal_info() {
		return canal_info;
	}
	public void setCanal_info(ArrayList<Map<String, Object>> canal_info) {
		this.canal_info = canal_info;
	}
	public ArtificialWC getArtificialWC() {
		return aritificial_wc;
	}
	public void setArtificialWC(ArtificialWC aritificial_wc) {
		this.aritificial_wc = aritificial_wc;
	}

	public int getMicroBasinID() {
		return loc_id;
	}
	public void setMicroBasinID(int microBasinID) {
		this.loc_id = microBasinID;
	}
	public String getMicroBasinName() {
		return loc_name;
	}
	public void setMicroBasinName(String microBasinName) {
		this.loc_name = microBasinName;
	}
	public Map<Integer, Map<String, Double>> getBasinAssociation() {
		Gson gs = new Gson();
		
		Type type = new TypeToken<Map<Integer, Map<String, Double>>>() {}.getType(); 
		Map<Integer, Map<String, Double>> assoc = gs.fromJson(this.loc_association, type);
		//System.out.println(assoc);
		return assoc;
	}
	public void setBasinAssociation(Map<Integer, Map<String, Double>> VillageAssociation) {
		//System.out.println(VillageAssociation);
		Gson gs = new Gson();
		String assoc = gs.toJson(VillageAssociation);
		//System.out.println("String "+assoc);
		Type type = new TypeToken<Map<Integer, Map<String, Double>>>() {}.getType(); 
		Map<Integer, Map<String, Double>> assocmap = gs.fromJson(assoc, type);
		this.loc_association = assoc;
		//System.out.println("map"+getBasinAssociation());
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
	     this.area = area;
	}
	public String getGeologicalInfo() {
		return geological_info;
	}
	public void setGeologicalInfo(Map<String, Map<String, Double>> geologicalInfo) {
		Gson gs = new Gson();
		String geo = gs.toJson(geologicalInfo);
		geological_info = geo;
	}
	public String getInfiltrationInfo() {
		return infiltration_info;
	}
	public void setInfiltrationInfo(Map<String, Map<String, Double>> infiltrationInfo) {
		Gson gs = new Gson();
		String inf = gs.toJson(infiltrationInfo);
		infiltration_info = inf;
	}
	public Map<String, Utilization>  getResource_distribution() {
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, Utilization>>() {	}.getType();
		Map<String, Utilization> ret = gson.fromJson(resource_distribution, type);
		return ret;
	}
	
	public void setResource_distribution(Map<String, Utilization> resource_distribution) {
		Gson gson = new Gson();
		this.resource_distribution = gson.toJson(resource_distribution);
	}

}
