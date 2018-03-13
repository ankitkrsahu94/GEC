import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class InsertMetaData {
	public static void main(String k[]){
		String geofile = "/home/megha/Downloads/final vsp - Copy/geo_ifo.csv";      // Input File
        String infilfile ="/home/megha/Downloads/final vsp - Copy/infil_info.csv";
		String record = "";
		Gson gs = new Gson();
		Map<String, Map<String,Map<String, Double>>> gw_infiltration = new HashMap<>();
		{Map<String, Double> range = new HashMap<>();
		range.put("0-10", 0.45);
	    range.put("10-25", 0.35);
	    range.put("25-100", 0.20);
	    gw_infiltration.put(Constants.GROUND_WATER_IRRIGATION, new HashMap<String, Map<String,Double>>());
	    gw_infiltration.get(Constants.GROUND_WATER_IRRIGATION).put("paddy",range);}
	    {Map<String, Double> range = new HashMap<>();
		range.put("0-10", 0.25);
	    range.put("10-25", 0.15);
	    range.put("25-100", 0.05);
	    gw_infiltration.get(Constants.GROUND_WATER_IRRIGATION).put("nonPaddy",range);}
	    
	    {Map<String, Double> range = new HashMap<>();
		range.put("0-10", 0.50);
	    range.put("10-25", 0.40);
	    range.put("25-100", 0.25);
	    gw_infiltration.put(Constants.SURFACE_WATER_IRRIGATION, new HashMap<String, Map<String,Double>>());
	    gw_infiltration.get(Constants.SURFACE_WATER_IRRIGATION).put("paddy",range);}
	    {Map<String, Double> range = new HashMap<>();
		range.put("0-10", 0.35);
	    range.put("10-25", 0.25);
	    range.put("25-100", 0.15);
	    gw_infiltration.get(Constants.SURFACE_WATER_IRRIGATION).put("nonPaddy",range);}
	    
	    
	    Map<String,Map<String, Double>>canal_infiltration = new HashMap<>();
	    {
	    	Map<String, Double> soil = new HashMap<>();
	    	soil.put("Normal Soil", 3.5);
	    	soil.put("Sandy Soil", 5.5);
	    	canal_infiltration.put("Lined", soil);
	    }
	    {
	    	Map<String, Double> soil = new HashMap<>();
	    	soil.put("Normal Soil", 17.5);
	    	soil.put("Sandy Soil", 27.5);
	    	canal_infiltration.put("Unlined", soil);
	    }
	    Map<String,Map<String,Object>>geological_info = new HashMap<>();
	    
	    try(BufferedReader iem = new BufferedReader(new FileReader(geofile))) {
	    	//readXLSXFile();
        	int count =0;
            
            while((record = iem.readLine()) != null) {
                //System.out.println("record"+record);

                String fields[] = record.split(";");
               // System.out.println("fields"+fields);
                if(fields.length==4){
                	count++;
                	String name = format.removeQuotes(fields[format.convert("a")]);
                	double recc = Double.parseDouble(fields[format.convert("b")]);
                	double max = Double.parseDouble(fields[format.convert("c")]);
                	double min = Double.parseDouble(fields[format.convert("d")]);
                	{
            			Map<String,Object> yield= new HashMap<>();
            			Map<String, Double> data = new HashMap<>();
            			data.put("Recommended", recc);
            			data.put("Max", max);
            			data.put("Min", min);
            			yield.put("specificYield", data);
            			yield.put("fraction", 1);
            			yield.put("transmissivity", 0);
            			yield.put("storage_coefficient", 0);
            			
            			geological_info.put(name, yield);
            		}
                 // System.out.println(geological_info);
                   
                }
        }
           
            System.out.println("geo count"+count);
            
            
        }catch (IOException e) {
            e.printStackTrace();
        }
	    
	    Map<String,Map<String,Object>>infiltration_info = new HashMap<>();
	    
	    try(BufferedReader iem = new BufferedReader(new FileReader(infilfile))) {
	    	//readXLSXFile();
        	int count =0;
            
            while((record = iem.readLine()) != null) {
                //System.out.println("record"+record);

                String fields[] = record.split(";");
               // System.out.println("fields"+fields);
                if(fields.length==4){
                	count++;
                	String name = format.removeQuotes(fields[format.convert("a")]);
                	double recc = Double.parseDouble(fields[format.convert("b")]);
                	double max = Double.parseDouble(fields[format.convert("c")]);
                	double min = Double.parseDouble(fields[format.convert("d")]);
                	{
            			Map<String,Object> yield= new HashMap<>();
            			Map<String, Double> data = new HashMap<>();
            			data.put("Recommended", recc);
            			data.put("Max", max);
            			data.put("Min", min);
            			yield.put("specificYield", data);
            			yield.put("fraction", 1);
            			yield.put("transmissivity", 0);
            			yield.put("storage_coefficient", 0);
            			infiltration_info.put(name, yield);
            		}
                 // System.out.println(geological_info);
                   
                }
        }
           
            System.out.println("infiltration count"+count);
            
            
        }catch (IOException e) {
            e.printStackTrace();
        }
		
		MetaDataPojo metadata = new MetaDataPojo(gw_infiltration,canal_infiltration,geological_info,infiltration_info);
		String metajson = gs.toJson(metadata);
		try (FileWriter file = new FileWriter("/home/ankit/Desktop/insertMetaData.cql")) {
            String query = "Insert into gec_meta_data JSON '"+metajson+"';";
            System.out.println(query);
            

        	file.write(query);
            file.flush();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		

	}

}
