import java.util.ArrayList;
import java.util.List;


public class convetTo {


	
	public static void main(String[] args) {

		
		List<String> distNames = new ArrayList<>();

//		distNames.add("Nellore");
//		distNames.add("Chittor");
//		distNames.add("Visakhapatnam");
//		distNames.add("Anantapur");
		
//		distNames.add("Srikakulam");
//		distNames.add("Krishna");
//		distNames.add("EastG");
//		distNames.add("Karnool");
//		distNames.add("Guntur");
		distNames.add("Prakasham");
		
		
		for (String distName : distNames) {
			
			System.out.println("********************** Computing for district " + distName);
			
				System.out.println("**** started VillData computation");
				VillageMetaData.compute(distName);
				System.out.println("**** VillData computation completed");
				
				
				System.out.println("**** started Basin computation");
				BasinXmltojson.compute(distName);
				System.out.println("**** Basin computation comleted");
			
			System.out.println("********************** Computation completed for district " + distName);
		}
		
		
		System.out.println("Everything completed .............");
	}

}
