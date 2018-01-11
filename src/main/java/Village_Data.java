import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.util.SystemOutLogger;

import com.google.gson.Gson;



public class Village_Data {
	String loc_association;

	int parent_id =1;
	int loc_type_id =10;
	int verification_status =1;
	int loc_id;
	String loc_name;
	double rf_infiltration_rate=1;
	double gw_specific_yield=1;
	double transmissivity=1;
	double storage_coefficient =1;
	String soil_type="";
	String area;
	String crop_info;
	String population;
	String water_bodies;
	String canal_info;
	String artificial_wc;
	String gw_dependency;
	String resource_distribution;

	
	
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
	}
	
	public String getGwDependencyFactor() {
		return gw_dependency;
	}


	public void setGwDependencyFactor(Map<String, Double> gwDependencyFactor) {
		Gson gs = new Gson();
		String dependency = gs.toJson(gwDependencyFactor);
		this.gw_dependency = dependency;
	}


	public String getResourceDistribution() {
		return resource_distribution;
	}
	public void setResourceDistribution(Map<String, Map<String, Map<String, WellsUtilizationData>>> resourceDistribution) {
		Gson gs = new Gson();
		String distri = gs.toJson(resourceDistribution);
		this.resource_distribution = distri;
	}
	private void setCrop(Map<String, Map<String, Map<String, Object>>> crop_info2) {
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
		return loc_association;
	}
	public void setBasinAssociation(Map<Integer, Double> basinAssociation) {
		Gson gs = new Gson();
		String basinAssoc = gs.toJson(basinAssociation);
		this.loc_association = basinAssoc;
	}
	public String getCanal() {
		return canal_info;
	}
	public void setCanal(ArrayList<Map<String,Object>> canal) {
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
		 Map<String, Object> areaDistribution = new HashMap<>();
         areaDistribution.put("recharge_worthy",areaObj.rechargeWorthy );
         areaDistribution.put("non_recharge_worthy",areaObj.nonRechargeWorthy );
         obj.put("total", areaObj.total);
	     obj.put("areaDistribution", areaDistribution);
	     this.area = gs.toJson(obj);
		
	}
	public String getCrop() {
		return crop_info;
	}
	
	public String getWaterbodies() {
		return water_bodies;
	}
	public void setWaterbodies(waterbodies waterbodies) {
		Gson gs = new Gson();
		String wb = gs.toJson(waterbodies);
		this.water_bodies = wb;
	}
	public String getArtificialWC() {
		return artificial_wc;
	}
	public void setArtificialWC(ArtificialWC artificialWC) {
		Gson gs = new Gson();
		String WC = gs.toJson(artificialWC);
		this.artificial_wc = WC;
	}
	

}


