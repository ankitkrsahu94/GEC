import java.util.Map;

public class Village {
	String population;

	int state_id =Constants.PARENT_ID;
	int loc_type_id =Constants.LOC_TYPE_VILLAGE;
	int verification_status =1;
	int loc_id;
	String loc_name;
	Map<Integer,Double> loc_association;
	double rf_infiltration_rate=1;
	double gw_specific_yield=1;
	String soil_type="";
	String area;
	Map<String, Map<String, Double>> gw_dependency;
	Map<String, Map<String, Map<String, Map<String, CropData>>>> crop_info;
	//AreaType vs waterBodyType vs IStorageStructureData
	public Map<String, Map<String, WaterBody>> water_bodies;
	Map<String, Map<String, CanalData>> canal_info;
	Map<String, ArtificialWC> aritificial_wc;
	Map<String, Map<String, Map<String, WellsUtilizationData>>> resourceDistribution;
	
	public String rf_data=null;
	public String gw_data=null;
	public String canal_discharge=null;
//	IrrigationUtilization irrigation_source;
//	ResidentialUtilization residential_source;
//	IndustrialUtilization industrial_source;
	
	
	public Map<String, Map<String, Double>> getGwDependencyFactor() {
		return gw_dependency;
	}
	public void setGwDependencyFactor(Map<String, Map<String, Double>> gwDependencyFactor) {
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
	public Map<String, Map<String, CanalData>> getCanal() {
		return canal_info;
	}
	public void setCanal(Map<String, Map<String, CanalData>> canal) {
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
	public Map<String, Map<String, Map<String, Map<String, CropData>>>> getCrop() {
		return crop_info;
	}
	public void setCrop(Map<String, Map<String, Map<String, Map<String, CropData>>>> crop) {
		this.crop_info = crop;
	}
	public Map<String, Map<String, WaterBody>> getWaterbodies() {
		return water_bodies;
	}
	public void setWaterbodies(Map<String, Map<String, WaterBody>> waterbodies) {
		this.water_bodies = waterbodies;
	}
	public Map<String, ArtificialWC> getArtificialWC() {
		return aritificial_wc;
	}
	public void setArtificialWC(Map<String, ArtificialWC> artificialWC) {
		this.aritificial_wc = artificialWC;
	}
	
	@Override
	public String toString() {
		return "Village [population=" + population + ", state_id=" + state_id
				+ ", loc_type_id=" + loc_type_id + ", verification_status="
				+ verification_status + ", loc_id=" + loc_id + ", loc_name="
				+ loc_name + ", loc_association=" + loc_association
				+ ", rf_infiltration_rate=" + rf_infiltration_rate
				+ ", gw_specific_yield=" + gw_specific_yield + ", soil_type="
				+ soil_type + ", area=" + area + ", gw_dependency="
				+ gw_dependency + ", crop_info=" + crop_info
				+ ", water_bodies=" + water_bodies + ", canal_info="
				+ canal_info + ", aritificial_wc=" + aritificial_wc
				+ ", resourceDistribution=" + resourceDistribution
				+ ", rf_data=" + rf_data + ", gw_data=" + gw_data
				+ ", canal_discharge=" + canal_discharge + "]";
	}
	
}
