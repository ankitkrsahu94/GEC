import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.util.SystemOutLogger;

import com.google.gson.Gson;



public class Village {
	String population;

	int state_id =1;
	int loc_type_id =10;
	int verification_status =1;
	int loc_id;
	String loc_name;
	Map<Integer,Double> loc_association;
	double rf_infiltration_rate=1;
	double gw_specific_yield=1;
	double transmissivity=1;
	double storage_coefficient =1;
	String soil_type="";
	String area;
	Map<String, Double> gw_dependency;
	Map<String,Map<String,Map<String,Object>>> crop_info = new HashMap<>();
	waterbodies water_bodies;
	ArrayList<Map<String,Object>> canal_info;
	ArtificialWC aritificial_wc;
	Map<String, Map<String, Map<String, WellsUtilizationData>>> resourceDistribution;
//	IrrigationUtilization irrigation_source;
//	ResidentialUtilization residential_source;
//	IndustrialUtilization industrial_source;
	
	
	public Map<String, Double> getGwDependencyFactor() {
		return gw_dependency;
	}
	public void setGwDependencyFactor(Map<String, Double> gwDependencyFactor) {
		this.gw_dependency = gwDependencyFactor;
	}
	public String getPopulation() {
		return population;
	}
	public Map<String, Map<String, Map<String, WellsUtilizationData>>> getResourceDistribution() {
		return resourceDistribution;
	}
	public void setResourceDistribution(Map<String, Map<String, Map<String, WellsUtilizationData>>> resourceDistribution) {
		this.resourceDistribution = resourceDistribution;
	}
	public void setPopulation(String population) {
		this.population = population;
	}
	public Map<Integer, Double> getBasinAssociation() {
		return loc_association;
	}
	public void setBasinAssociation(Map<Integer, Double> basinAssociation) {
		this.loc_association = basinAssociation;
	}
	public ArrayList<Map<String,Object>> getCanal() {
		return canal_info;
	}
	public void setCanal(ArrayList<Map<String,Object>> canal) {
		this.canal_info = canal;
	}
	public int getVillageId() {
		return loc_id;
	}
	public void setVillageId(int villageId) {
		this.loc_id = villageId;
	}
	public String getVillageName() {
		return loc_name;
	}
	public void setVillageName(String villageName) {
		this.loc_name = villageName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
	     this.area = area;
		
	}
	public Map<String, Map<String, Map<String, Object>>> getCrop() {
		return crop_info;
	}
	public void setCrop(Map<String, Map<String, Map<String, Object>>> crop) {
		this.crop_info = crop;
	}
	public waterbodies getWaterbodies() {
		return water_bodies;
	}
	public void setWaterbodies(waterbodies waterbodies) {
		this.water_bodies = waterbodies;
	}
	public ArtificialWC getArtificialWC() {
		return aritificial_wc;
	}
	public void setArtificialWC(ArtificialWC artificialWC) {
		this.aritificial_wc = artificialWC;
	}
	
	@Override
	public String toString() {
		return "Village [population=" + population + ", state_id=" + state_id
				+ ", loc_type_id=" + loc_type_id + ", verification_status="
				+ verification_status + ", loc_id=" + loc_id + ", loc_name="
				+ loc_name + ", loc_association=" + loc_association
				+ ", rf_infiltration_rate=" + rf_infiltration_rate
				+ ", gw_specific_yield=" + gw_specific_yield
				+ ", transmissivity=" + transmissivity
				+ ", storage_coefficient=" + storage_coefficient
				+ ", soil_type=" + soil_type + ", area=" + area
				+ ", gwDependencyFactor=" + gw_dependency + ", crop_info="
				+ crop_info + ", water_bodies=" + water_bodies
				+ ", canal_info=" + canal_info + ", aritificial_wc="
				+ aritificial_wc + ", resourceDistribution="
				+ resourceDistribution + "]";
	}
}
