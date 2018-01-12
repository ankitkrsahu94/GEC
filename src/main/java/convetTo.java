import java.util.ArrayList;
import java.util.List;


public class convetTo {


	
	public static void main(String[] args) {

		
		List<String> distNames = new ArrayList<>();

//		distNames.add("Nellore");
//		distNames.add("Chittor");
//		distNames.add("Anantapur");
//		distNames.add("Srikakulam");
//		distNames.add("Krishna");
//		distNames.add("EastG");
//		distNames.add("Guntur");
//		distNames.add("Prakasham");
//		distNames.add("Kadapa");
		distNames.add("Visakhapatnam");
//		distNames.add("Karnool");
//		distNames.add("WGodavari");
		/**
		 * Ankit : /home/ankit/Documents/GEC/codebase/data_files_used/
		 * Akshay : /home/akshay/proj/GECScriptsGen/GEC/data_files_used/
		 */
		String path = "/home/akshay/proj/GECScriptsGen/GEC/data_files_used/";
		for (String distName : distNames) {
			System.out.println("********************** Computing for district " + distName);
			
				System.out.println("**** started VillData computation");
				VillageMetaData.compute(distName,path);
				System.out.println("**** VillData computation completed");
				
				
				System.out.println("**** started Basin computation");
				BasinXmltojson.compute(distName,path);
				System.out.println("**** Basin computation comleted");
			
			System.out.println("********************** Computation completed for district " + distName);
		}
		
		
		System.out.println("Everything completed .............");
	}

}
