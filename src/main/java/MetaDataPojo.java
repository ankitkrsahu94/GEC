import java.util.Map;

import com.google.gson.Gson;

public class MetaDataPojo {
	int parent_id =1;
	String gw_infiltration;
	String canal_infiltration;
	String geological_info;
	String infiltration_info;
	public MetaDataPojo(Map<String, Map<String, Map<String, Double>>> gw_infiltration,
			Map<String, Map<String, Double>> canal_infiltration,
			Map<String, Map<String, Object>> geological_info,
			Map<String, Map<String, Object>> infiltration_info) {
		Gson gs= new Gson();
		String gw = gs.toJson(gw_infiltration);
		String canal = gs.toJson(canal_infiltration);
		String geo = gs.toJson(geological_info);
		String infi = gs.toJson(infiltration_info);
		this.gw_infiltration = gw;
		this.canal_infiltration = canal;
		this.geological_info = geo;
		this.infiltration_info = infi;
	}
	

}
