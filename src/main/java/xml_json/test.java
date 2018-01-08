package xml_json;

import java.util.HashMap;
import java.util.Map;

public class test {

	public static void main(String[] args) {

		Map<String, StringBuffer> mp = new HashMap<String, StringBuffer>();
		
		try {
			
			addParameter(mp,"asd",12);
			addParameter(mp,"asd1","asdas");
			addParameter(mp,"asd2",12.12);
			addParameter(mp,"asd3",new Long(123));
			
			
			String query = "insert into location("+ mp.get("fieldNames") +") values(" + mp.get("fieldValues")+");";

			System.out.println(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void addParameter(Map<String,StringBuffer> query, String fieldName, Object fieldValue) throws Exception{
		
		if(fieldValue == null)
			return;

		if(!query.containsKey("fieldNames")) {
			query.put("fieldNames",new StringBuffer());
			query.put("fieldValues",new StringBuffer());
		} else {
			query.get("fieldValues").append(", ");
			query.get("fieldNames").append(", ");
		}
		
		query.get("fieldNames").append(fieldName);
		
		if(fieldValue instanceof String)
			query.get("fieldValues").append("'" + fieldValue + "'");
		else if(fieldValue instanceof Integer) {
			query.get("fieldValues").append(fieldValue);
		} else if(fieldValue instanceof Double) {
			query.get("fieldValues").append(fieldValue);
		} else if(fieldValue instanceof Long) {
			query.get("fieldValues").append(fieldValue);
		} else {
			throw new Exception("This object class can not be inserted to databse " + fieldValue.getClass());
		}
		
	}
}
