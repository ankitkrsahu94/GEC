
public class IwmData {
	String parent_entity_uuid = "6f86292b-dd9a-4987-bb8f-c3940263b349"; //for Andhra Pradesh
    String location_type;
    int component_type = 26; //LOCATION_INTERSECTION
    String time_period = Constants.GEC_ASSESSMENT_YEAR.split("-")[0];
    int event_value_type = 1;	//RAW_DATA_EVENT
    int sub_time_period = Integer.valueOf(Constants.GEC_ASSESSMENT_YEAR.split("-")[0]);
    String entity_uuid; //set from village class
    String data1; //set from village class
    String data2;
	
    public IwmData(String location_type, String entity_uuid, String data1) {
		super();
		this.location_type = location_type;
		this.entity_uuid = entity_uuid;
		this.data1 = data1;
	}
}
