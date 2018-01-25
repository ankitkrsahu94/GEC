import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class convetTo {


	
	public static void main(String[] args) {

		
		List<String> distNames = new ArrayList<>();
		
//		distNames.add("Chittor");
//		distNames.add("Anantapur");
//		distNames.add("Srikakulam");
//		distNames.add("Guntur");
		distNames.add("Visakhapatnam");
//		distNames.add("WGodavari");
		
		
//		distNames.add("Nellore");
//		distNames.add("Krishna");
//		distNames.add("EastG");
//		distNames.add("Prakasham");
//		distNames.add("Kadapa");
//		distNames.add("Karnool");
//		distNames.add("vizianagaram");
		/**
		 * Ankit : /home/ankit/Documents/GEC/codebase/data_files_used/
		 * Akshay : /home/akshay/proj/GECScriptsGen/GEC/data_files_used/
		 */
		String path = "/home/ankit/Documents/GEC/codebase/data_files_used/";
		
		String villCQLScriptFile = path+"final_scripts/vill_meta_data.cql";
		String basinCQLScriptFile = path+"final_scripts/basin_meta_data.cql";

		try(BufferedWriter bwVill = new BufferedWriter(new FileWriter(villCQLScriptFile));
				BufferedWriter bwBasin = new BufferedWriter(new FileWriter(basinCQLScriptFile))) {
			
			
			for (String distName : distNames) {
				System.out.println("********************** Computing for district " + distName);
				
				System.out.println("**** started VillData computation");
				VillageMetaData.compute(distName,path, bwVill);
				System.out.println("**** VillData computation completed");
				
				
				System.out.println("**** started Basin computation");
				BasinXmltojson.compute(distName,path, bwBasin);
				System.out.println("**** Basin computation comleted");
				
				System.out.println("********************** Computation completed for district " + distName);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("Everything completed .............");
	}

}
