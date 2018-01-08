import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class Hierarchy {
	public static void main(String k[]) {
	 String hierarchyfile ="/home/megha/Downloads/final vsp - Copy/location hirearchy MD/hierarchy_insertion/parliament_assembly_hierarchy.csv";
     String record = "";
     HashSet<String> parliamentName = new HashSet<>();
     HashSet<String> assemblyName = new HashSet<>();
     HashMap<String,String> District_Parliament =new HashMap<>();
     HashMap<String,String> Parliament_Assembly = new HashMap<>();
     HashMap<String,String> Assembly_Mandal = new HashMap<>();
	
		
	    try(BufferedReader iem = new BufferedReader(new FileReader(hierarchyfile))) {
	    	record = iem.readLine();
	        while((record = iem.readLine()) != null) {
	            //System.out.println("record"+record);
	
	            String fields[] = record.split(";");
	            /*if(fields.length!=5){
	                System.out.println("fields"+fields.length);
	
	            }*/
	            if(fields.length==5){
	            	String District = format.removeQuotes(fields[format.convert("a")]);
	            	String Parliament = format.removeQuotes(fields[format.convert("b")])+"-PARLIAMENTARY";
	            	String Assembly = format.removeQuotes(fields[format.convert("c")])+"-ASSEMBLY";
	            	String Mandal = format.removeQuotes(fields[format.convert("d")]);
	            	String Village = format.removeQuotes(fields[format.convert("e")]);
	            	
	            	if(!Parliament.equalsIgnoreCase("#N/A-PARLIAMENTARY")){
		            	parliamentName.add(Parliament);
	            	}
	            	if(!Assembly.equalsIgnoreCase("#N/A-ASSEMBLY")){
		            	assemblyName.add(Assembly);          	

	            	}
	            	District_Parliament.put(Parliament, District);
	            	Parliament_Assembly.put(Assembly, Parliament);
	            	Assembly_Mandal.put(Mandal, Assembly);
	            	//System.out.println(Parliament);
	               
	              
	            }
	           
	            //System.out.println("**"+MicroBasin_GWvillage_key);
	            
	           
	        }
	       
	        
	    }catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    System.out.println(parliamentName.size());
	    System.out.println(assemblyName.size());
	    for(String str:parliamentName){
	    	//System.out.println(str);
	    	try (FileWriter file = new FileWriter("/home/megha/Downloads/final vsp - Copy/location hirearchy MD/hierarchy_insertion/insertParliament.sql",true)) {
                String query1 ="insert into platform_data.extension_entity_map(entity_type,insert_ts,deleted,user_session_id) values('location', (SELECT UNIX_TIMESTAMP() * 1000),0,0);";
                String query2 ="insert into platform_data.location(display_name,location_uuid,is_parent,deleted,insert_ts,user_session_id,extension_entity_map_id) values  ('"+
                		str+
                		"',(SELECT UUID()),1 ,0,(SELECT UNIX_TIMESTAMP() * 1000),0,(SELECT LAST_INSERT_ID()));";
                String query3 ="insert into platform_data.location_type_map (location_id,location_type_md_id,deleted,user_session_id,insert_ts) values ((select location_id from platform_data.location where display_name='"+
                			str+
                		"') ,(select location_type_md_id from platform_data.location_type_md where name='"
                		+ str
                		+ "') ,0,0,(SELECT UNIX_TIMESTAMP() * 1000));";
                
                
                /*System.out.println(query1);
                System.out.println(query2);
                System.out.println(query3);*/

            	file.write(query1);
            	file.write(query2);
            	file.write(query3);
                file.flush();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
	    	//System.out.println(str);
	    	
	    }
	    System.out.println("parliament inserted");
	    
	    for(String par:District_Parliament.keySet()){
	    	try (FileWriter file = new FileWriter("/home/megha/Downloads/final vsp - Copy/location hirearchy MD/hierarchy_insertion/insertDistrict_Parliament_Assoc.sql",true)) {
               String index = "select @index := IFNULL(max(`child_loc_index`),0)+1 from"
               		+ " `platform_data`.`location_association` where  `parent_location_id` ="
               		+ " (select `location_id` from `platform_data`.`location` where `display_name` "
               		+ "= '"
               		+ District_Parliament.get(par)
               		+"') and `child_location_id` in (select `location_id` from"
               		+ " `platform_data`.`location_type_map` where `location_type_md_id` = "
               		+ "(select `location_type_md_id` from `platform_data`.`location_type_md` where "
               		+ "`name`='PARLIAMENT'));";
               

	    		String query1 ="insert into platform_data.location_association(parent_location_id, child_location_id, child_loc_index, deleted, user_session_id, insert_ts) values "
	    				+ "((select location_id from location where display_name= '"
	    				+District_Parliament.get(par)
	    				+"'), (select location_id from location where display_name= '"
	    				+ par
	    				+"'), "
	    				+ "@index"
	    				+ ", 0, 0, (SELECT UNIX_TIMESTAMP() * 1000));";
                
                
               // System.out.println(query1);
               
                file.write(index);
            	file.write(query1);
            	
                file.flush();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
	    	//System.out.println(str);
	    	
	    }
	    System.out.println(District_Parliament.size());
	    System.out.println("District-parliament assoc inserted");
	    
	    for(String assem:Parliament_Assembly.keySet()){
	    	try (FileWriter file = new FileWriter("/home/megha/Downloads/final vsp - Copy/location hirearchy MD/hierarchy_insertion/insertParliament_Assembly_Assoc.sql",true)) {
               
	    		 String index = "select @index := IFNULL(max(`child_loc_index`),0)+1 from"
	                		+ " `platform_data`.`location_association` where  `parent_location_id` ="
	                		+ " (select `location_id` from `platform_data`.`location` where `display_name` "
	                		+ "= '"
	                		+ Parliament_Assembly.get(assem)
	                		+"') and `child_location_id` in (select `location_id` from"
	                		+ " `platform_data`.`location_type_map` where `location_type_md_id` = "
	                		+ "(select `location_type_md_id` from `platform_data`.`location_type_md` where "
	                		+ "`name`='ASSEMBLY'));";
	    		 
	    		String query1 ="insert into platform_data.location_association(parent_location_id, child_location_id, child_loc_index, deleted, user_session_id, insert_ts) values "
	    				+ "((select location_id from location where display_name= '"
	    				+Parliament_Assembly.get(assem)
	    				+"'), (select location_id from location where display_name= '"
	    				+ assem
	    				+"'), "
	    				+ "@index"
	    				+ ", 0, 0, (SELECT UNIX_TIMESTAMP() * 1000));";
                
                
              //  System.out.println(query1);
               
	    		file.write(index);
            	file.write(query1);
            	
                file.flush();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
	    	//System.out.println(str);
	    	
	    }
	    System.out.println(Parliament_Assembly.size());
	    System.out.println("Parliament_Assembly assoc inserted");
	    
	    //------------------------------------------
	    
	    for(String mandal:Assembly_Mandal.keySet()){
	    	try (FileWriter file = new FileWriter("/home/megha/Downloads/final vsp - Copy/location hirearchy MD/hierarchy_insertion/insertAssembly_Mandal_Assoc.sql",true)) {
	    		String query2 = "select @index := IFNULL(max(`child_loc_index`),0)+1 from"
                		+ " `platform_data`.`location_association` where  `parent_location_id` ="
                		+ " (select `location_id` from `platform_data`.`location` where `display_name` "
                		+ "= '"
                		+ Assembly_Mandal.get(mandal)
                		+"') and `child_location_id` in (select `location_id` from"
                		+ " `platform_data`.`location_type_map` where `location_type_md_id` = "
                		+ "(select `location_type_md_id` from `platform_data`.`location_type_md` where "
                		+ "`name`='MANDAL'));";
	    		String query1 ="insert into platform_data.location_association(parent_location_id, child_location_id, child_loc_index, deleted, user_session_id, insert_ts) values "
	    				+ "((select location_id from location where display_name= '"
	    				+Assembly_Mandal.get(mandal)
	    				+"'), (select location_id from location where display_name= '"
	    				+ mandal
	    				+"'), "
	    				+ "@index"
	    				+ ", 0, 0, (SELECT UNIX_TIMESTAMP() * 1000));";
                
                
              //  System.out.println(query1);
               
	    		file.write(query2);
            	file.write(query1);
            	
                file.flush();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
	    	//System.out.println(str);
	    	
	    }
	    System.out.println(Assembly_Mandal.size());
	    System.out.println("Assembly_Mandal assoc inserted");
	    
	    for(String str:assemblyName){
	    	try (FileWriter file = new FileWriter("/home/megha/Downloads/final vsp - Copy/location hirearchy MD/hierarchy_insertion/insertAssembly.sql",true)) {
                String query1 ="insert into platform_data.extension_entity_map(entity_type,insert_ts,deleted,user_session_id) values('location', (SELECT UNIX_TIMESTAMP() * 1000),0,0);";
                String query2 ="insert into platform_data.location(display_name,location_uuid,is_parent,deleted,insert_ts,user_session_id,extension_entity_map_id) values  ('"+
                		str+
                		"',(SELECT UUID()),1 ,0,(SELECT UNIX_TIMESTAMP() * 1000),0,(SELECT LAST_INSERT_ID()));";
                String query3 ="insert into platform_data.location_type_map (location_id,location_type_md_id,deleted,user_session_id,insert_ts) values ((select location_id from platform_data.location where display_name='"+
                			str+
                		"') ,(select location_type_md_id from platform_data.location_type_md where name='"
                		+ str
                		+ "') ,0,0,(SELECT UNIX_TIMESTAMP() * 1000));";
                
                
                /*System.out.println(query1);
                System.out.println(query2);
                System.out.println(query3);*/

            	file.write(query1);
            	file.write(query2);
            	file.write(query3);
                file.flush();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
	    	//System.out.println(str);
	    	
	    }
	    System.out.println("Assembly inserted");
    
	}
}
