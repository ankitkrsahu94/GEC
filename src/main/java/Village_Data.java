import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;



public class Village_Data {
	
	public static String assesYear = Constants.GEC_ASSESSMENT_YEAR;
	String loc_assoc;

	int parent_id=Constants.PARENT_ID;
	int loc_type_id =Constants.LOC_TYPE_VILLAGE;
	int verific_status =1;
	String assessment_year = assesYear;
	int loc_id;
	String loc_name;
	double rf_infil_rate=1;
	double gw_specific_yield=1;
	String soil_type="";
	String area;
	String crop_info;
	String population;
	String water_bodies;
	String canal_info;
	String artificial_wc;
	String gw_dependency;
	String resource_dist;
	int processed_status = 3;
	String rf_data;
	String gw_data;
	String canal_discharge;

	
	
	Village_Data(Village vill){
		this.loc_id= vill.loc_id;
		this.loc_name=vill.loc_name;
		if(vill.loc_association!=null && !vill.loc_association.isEmpty()){
			setBasinAssociation(vill.loc_association);
		}
		setArea(vill.area);
		this.population=vill.population;
		setWaterbodies(vill.water_bodies);
		if(vill.canal_info!=null){
			setCanal(vill.canal_info);

		}
		setArtificialWC(vill.aritificial_wc);

		if(vill.crop_info!=null){
			setCrop(vill.crop_info);
		}
		
		if(vill.resourceDistribution != null){
			setResourceDistribution(vill.resourceDistribution);
		}
		
		if(vill.gw_dependency != null){
			setGwDependencyFactor(vill.gw_dependency);
		}
		
		if(vill.rf_data != null) {
			this.rf_data = vill.rf_data;
		}
		
		if(vill.gw_data != null) {
			this.gw_data = vill.gw_data;
		}
	}
	
	public String getGwDependencyFactor() {
		return gw_dependency;
	}


	public void setGwDependencyFactor(Map<String, Map<String, Double>> gwDependencyFactor) {
		Gson gs = new Gson();
		String dependency = gs.toJson(gwDependencyFactor);
		this.gw_dependency = dependency;
	}


	public String getResourceDistribution() {
		return resource_dist;
	}
	public void setResourceDistribution(Map<String, Map<String, Map<String, WellsUtilizationData>>> resourceDistribution) {
		Gson gs = new Gson();
		String distri = gs.toJson(resourceDistribution);
		this.resource_dist = distri;
	}
	private void setCrop(Map<String, Map<String, Map<String, Map<String, CropData>>>> crop_info2) {
		Gson gs = new Gson();
		String crop = gs.toJson(crop_info2);
		this.crop_info=crop;
	}

	public String getPopulation() {
		return population;
	}
	public void setPopulation(String population) {
		this.population = population;
	}
	public String getBasinAssociation() {
		return loc_assoc;
	}
	public void setBasinAssociation(Map<Integer, Double> basinAssociation) {
		Gson gs = new Gson();
		String basinAssoc = gs.toJson(basinAssociation);
		this.loc_assoc = basinAssoc;
	}
	public String getCanal() {
		return canal_info;
	}
	public void setCanal(Map<String, Map<String, CanalData>> canal) {
		 Gson gs = new Gson();
		 String canal_info;
		 canal_info= gs.toJson(canal);
		this.canal_info = canal_info;
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
		 Map<String,Object> obj = new HashMap<>();
		 Gson gs = new Gson();
		 Area areaObj = gs.fromJson(area, Area.class);
		 obj.put(Constants.COMMAND, areaObj.command);
		 obj.put(Constants.NON_COMMAND, areaObj.non_command);
		 obj.put(Constants.POOR_QUALITY, areaObj.poor_quality);
		 obj.put(Constants.HILLY, areaObj.hilly);
		 obj.put(Constants.FOREST, areaObj.forest);
		 obj.put(Constants.TOTAL, areaObj.total);
	     this.area = gs.toJson(obj);
		
	}
	public String getCrop() {
		return crop_info;
	}
	
	public String getWaterbodies() {
		return water_bodies;
	}
	public void setWaterbodies(Map<String, Map<String, WaterBody>> waterbodies) {
		Gson gs = new Gson();
		String wb = gs.toJson(waterbodies);
		this.water_bodies = wb;
	}
	public String getArtificialWC() {
		return artificial_wc;
	}
	public void setArtificialWC(Map<String, ArtificialWC> artificialWC) {
		Gson gs = new Gson();
		String WC = gs.toJson(artificialWC);
		this.artificial_wc = WC;
	}
	

}


