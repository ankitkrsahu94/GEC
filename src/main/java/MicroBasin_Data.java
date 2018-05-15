import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class MicroBasin_Data {

	int parent_id =Constants.PARENT_ID;
	int verific_status =1;
	String assessment_year = Constants.GEC_ASSESSMENT_YEAR;
	int loc_id;
	String loc_name;
	int loc_type_id =Constants.LOC_TYPE_MB;
	String area;
	String loc_assoc;
	String geo_info;
	String infil_info;
	String water_bodies;
	String canal_info;
	String artificial_wc;
	String resource_dist;
	String population;
	String rf_data;
	String gw_data;
	String gw_dependency;
	
	MicroBasin_Data(MicroBasin basin){
		this.loc_id= basin.loc_id;
		this.loc_name=basin.loc_name;
		setArea(basin.area);
		this.loc_assoc=basin.loc_association;
		this.geo_info=basin.geological_info;
		this.infil_info=basin.infiltration_info;
		this.resource_dist=basin.resource_distribution;
		this.population=basin.population;
		
		if(basin.water_bodies!=null){
			setWater_bodies(basin.water_bodies);
		}
		if(basin.canal_info!=null){
			setCanal_info(basin.canal_info);

		}
		if(basin.aritificial_wc!=null){
			setArtificial_wc(basin.aritificial_wc);
		}
		
		if(basin.rf_data != null) {
			this.rf_data = basin.rf_data;
		}
		
		if(basin.gw_data != null) {
			this.gw_data = basin.gw_data;
		}

		if(basin.gw_dependency != null) {
			this.gw_dependency = basin.gw_dependency;
		}
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
//		 Map<String,Object> obj = new HashMap<>();
//		 Gson gs = new Gson();
//		 Area areaObj = gs.fromJson(area, Area.class);
//		 obj.put(Constants.COMMAND, areaObj.command);
//		 obj.put(Constants.NON_COMMAND, areaObj.non_command);
//		 obj.put(Constants.POOR_QUALITY, areaObj.poor_quality);
//		 obj.put(Constants.TOTAL, areaObj.total);
	     this.area = area;//gs.toJson(obj);
		
	}
	public String getWater_bodies() {
		return water_bodies;
	}
	public void setWater_bodies(Map<String, Map<String, WaterBody>> water_bodies2) {
		Gson gs = new Gson();
		String wb = gs.toJson(water_bodies2);
		this.water_bodies = wb;
	}
	public String getCanal_info() {
		return canal_info;
	}
	public void setCanal_info(ArrayList<Map<String, Object>> canal_info2) {
		 Gson gs = new Gson();
		 String canal_info;
		 canal_info= gs.toJson(canal_info2);
		this.canal_info = canal_info;
	}
	public String getArtificial_wc() {
		return artificial_wc;
	}
	public void setArtificial_wc(ArtificialWC aritificial_wc) {
		Gson gs = new Gson();
		String WC = gs.toJson(aritificial_wc);
		this.artificial_wc = WC;
	}
	
}
