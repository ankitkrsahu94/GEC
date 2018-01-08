//
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileInputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.json.JSONObject;
//
//import com.google.gson.Gson;
//
//
//public class VillageMetaData {
//	
//    static Map<String,Map<String,Integer>> irrOperativenon = new HashMap<>();
//    static Map<String,Map<String,Integer>> resOperativenon = new HashMap<>();
//    static Map<String,Map<String,Integer>> indOperativenon = new HashMap<>();
//    static Map<String,Map<String,Integer>> irrOperative = new HashMap<>();
//    static Map<String,Map<String,Integer>> resOperative = new HashMap<>();
//    static Map<String,Map<String,Integer>> indOperative = new HashMap<>();
//    
//	static int mBWs=0;
//   	static int mDW =0;
//   	static int mPS=0;
//   	static int mSTW=0;
//   	static int mMTW=0;
//   	static int mDTW=0;
//   	static int mFP=0;
//   	static int nmBWs=0;
//   	static int nmDW =0;
//   	static int nmPS=0;
//   	static int nmSTW=0;
//   	static int nmMTW=0;
//   	static int nmDTW=0;
//   	static int nmFP=0;
//    
//	public static void main(String k[]) {
//
//        String gwlocToIWMloc = "/home/akshay/Downloads/final vsp - Copy/location hirearchy MD/a.Ap admin hirarchy.csv";      // Input File
//        String areafile = "/home/akshay/Downloads/final vsp - Copy/location hirearchy MD/d.Village  Resource Distribution/Area.csv";
//        String cropfile ="/home/akshay/Downloads/final vsp - Copy/location hirearchy MD/d.Village  Resource Distribution/Applied Irrigation-f.csv";
//        String waterbodiesfile ="/home/akshay/Downloads/final vsp - Copy/location hirearchy MD/d.Village  Resource Distribution/MI tanks.csv";
//        String canalfile = "/home/akshay/Downloads/final vsp - Copy/location hirearchy MD/d.Village  Resource Distribution/Canals.csv";
//        String artificialWCfile="/home/akshay/Downloads/final vsp - Copy/location hirearchy MD/d.Village  Resource Distribution/Wc structures.csv";
//        String irrigationUtilizationfile ="/home/akshay/Downloads/final vsp - Copy/location hirearchy MD/d.Village  Resource Distribution/Borewell irrigated.csv";
//        String residentialUtilizationfile="/home/akshay/Downloads/final vsp - Copy/location hirearchy MD/d.Village  Resource Distribution/Borewell residential.csv";
//        String villageIdfile = "/home/akshay/Downloads/iwm_village_name_id_map.csv";
//        String paddykhariffile = "/home/akshay/Downloads/village_area_sown_all_seasons/village_area_sown_paddy_kharif_2016.csv"; 
//        String paddyrabifile = "/home/akshay/Downloads/village_area_sown_all_seasons/village_area_sown_paddy_rabi_2016.csv";
//        String nonpaddykhariffile = "/home/akshay/Downloads/village_area_sown_all_seasons/village_area_sown_non_paddy_kharif_2016.csv";
//        String nonpaddyrabifile ="/home/akshay/Downloads/village_area_sown_all_seasons/village_area_sown_non_paddy_rabi_2016.csv";
//        String cropvillfile = "/home/akshay/Downloads/village_area_sown_all_seasons/ecrop_to_iwm_location_map.csv";
//        String basinIdfile ="/home/akshay/Downloads/iwm_basin_name_id_map.csv";
//        String populationfile="/home/akshay/Downloads/final vsp - Copy/Drafts/b. population lpcd.csv";
//        String operatingfile = "/home/akshay/Downloads/final vsp - Copy/vsp and rain fall unit drift.csv";
//        String record = "";
//        HashMap<String,String> basinmapping = new HashMap<>();
//        HashMap<String,String> locmapping = new HashMap<>();
//        HashMap<String,Integer> Basin_Id = new HashMap<>();
//        HashMap<Integer,String> villageId = new HashMap<>();
//        JSONObject infiltrationRate = new JSONObject(); 
//        infiltrationRate.put("infiltrationRate", new Integer(1));
//        JSONObject specificYield = new JSONObject(); 
//        specificYield.put("specificYield", new Integer(1));
//        JSONObject transmissivity = new JSONObject(); 
//        transmissivity.put("transmissivity", new Integer(1));
//        JSONObject storageCoefficient = new JSONObject(); 
//        storageCoefficient.put("storageCoefficient", new Integer(1));
//        Village village = new Village();
//        HashMap<String,Village> village_details=new HashMap<>();
//        Map<Integer,Map<String,Double>> paddykharif = new HashMap<>();
//        Map<Integer,Map<String,Double>> paddyrabi = new HashMap<>();
//        Map<Integer,Map<String,Double>> nonpaddykharif = new HashMap<>();
//        Map<Integer,Map<String,Double>> nonpaddyrabi = new HashMap<>();
//        Map<String,Double> computeDugwell = new HashMap<>();
//        Map<String,Double> computeTubewell = new HashMap<>();
//        Map<Integer,Integer> cropId = new HashMap<>();
//        Map<String,Map<String,Map<String,Integer>>> irrOperativenonT = new HashMap<>();
//        Map<String,Map<String,Map<String,Integer>>> resOperativenonT = new HashMap<>();
//        Map<String,Map<String,Map<String,Integer>>> indOperativenonT = new HashMap<>();
//        Map<String,Map<String,Map<String,Integer>>> irrOperativeT = new HashMap<>();
//        Map<String,Map<String,Map<String,Integer>>> resOperativeT = new HashMap<>();
//        Map<String,Map<String,Map<String,Integer>>> indOperativeT = new HashMap<>();
//        Map<String,Map<String,Map<String,Map<String,Double>>>> yieldMap = new HashMap<>();
//        
//        //loc mapping
//        try(BufferedReader iem = new BufferedReader(new FileReader(gwlocToIWMloc))) {
//            
//        	//readXLSXFile();
//            record = iem.readLine();
//            while((record = iem.readLine()) != null) {
//                //System.out.println("record"+record);
//
//                String fields[] = record.split(",");
//               // System.out.println("fields"+fields);
//                if(fields.length==4){
//                	String MicroBasinName;
//                	String GWvillageName;
//                	String IWMvillageName;
//                	if(!format.removeQuotes(fields[format.convert("a")]).isEmpty() && !format.removeQuotes(fields[format.convert("c")]).isEmpty() && !format.removeQuotes(fields[format.convert("d")]).isEmpty()&& !format.removeQuotes(fields[format.convert("d")]).equalsIgnoreCase("#N/A")){
//                    	MicroBasinName = format.removeQuotes(fields[format.convert("a")]);
//                    	String IWMMicroBasinName=format.removeQuotes(fields[format.convert("b")]);
//                		GWvillageName = format.removeQuotes(fields[format.convert("c")]);
//                        IWMvillageName = format.removeQuotes(fields[format.convert("d")]);
//                        String MicroBasin_GWvillage_key = MicroBasinName + "##" +GWvillageName;
//                        locmapping.put(MicroBasin_GWvillage_key,IWMvillageName);
//                        basinmapping.put(MicroBasinName,IWMMicroBasinName);
//                	}
//                	
//                }
//               
//                //System.out.println("**"+MicroBasin_GWvillage_key);
//            }
//            int c1=0;
//            for(String key:locmapping.keySet()){
//            	//c1++;
//            	village_details.put(locmapping.get(key),new Village());
//            }
//            System.out.println("b4 naming"+village_details.size());
//            for(String key:village_details.keySet()){
//            	
//            		village_details.get(key).setVillageName(key);
//            	
//            }
//            int c=0;
//            for(String vill:village_details.keySet()){
//            	c++;
//            //	System.out.println(vill);
//            }
//            System.out.println("village name count"+c);
//            
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        
//        
//        //operating days---- command
//        try(BufferedReader iem = new BufferedReader(new FileReader(operatingfile))) {
//            
//            record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//            
//            irrOperativenon = new HashMap<>();
//            resOperativenon = new HashMap<>();
//            indOperativenon = new HashMap<>();
//            irrOperative = new HashMap<>();
//            resOperative = new HashMap<>();
//            indOperative = new HashMap<>();
//            Map<String,Map<String,Map<String,Double>>> yield = new HashMap<>();
//            yieldMap.put("command", yield);
//            Map<String,Map<String,Double>> yieldA = new HashMap<>();
//            Map<String,Map<String,Double>> yieldD = new HashMap<>();
//            Map<String,Map<String,Double>> yieldI = new HashMap<>();
//            yield.put("agriculture",yieldA);
//            yield.put("domestic",yieldD);
//            yield.put("industrial",yieldI);
//            
//            while((record = iem.readLine()) != null) {
//                //System.out.println("record"+record);
//
//                String fields[] = record.split(",");
//                if(fields.length == format.convert("aa")+1){
//                	int irrmonsoon;
//                	int irrnonmonsoon;
//                    int resmonsoon;
//                    int resnonmonsoon;
//                    int indmonsoon;
//                    int indnonmonsoon;
//                    double yieldVA, yieldVD, yieldVI;
//                    
//                	String BasinName = format.removeQuotes(fields[format.convert("b")]);
//                    String wellname =format.removeQuotes(fields[format.convert("c")]);
//                    
//                    Map<String,Map<String, Double>> yieldBasin = yield.get(BasinName);
//                    if(yieldBasin == null) {
//                    	yieldBasin = new HashMap<>();
//                    	yield.put(BasinName, yieldBasin);
//                    }
//                    Map<String, Double>	yieldWells = yieldBasin.get(wellname);
//                    if(yieldWells == null) {
//                    	yieldWells = new HashMap<>();
//                    	yieldBasin.put(wellname, yieldWells);
//                    }
//                    	
//                    yieldWells.put("",fields[format.convert("d")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("d")]));
//                    if(fields[format.convert("e")].isEmpty()){
//                    	irrmonsoon=0;
//                    }
//                    else{
//                    	irrmonsoon = Integer.parseInt(fields[format.convert("e")]);
//                    }
//                    if(fields[format.convert("f")].isEmpty()){
//                    	irrnonmonsoon=0;
//                    }
//                    else{
//                    	irrnonmonsoon = Integer.parseInt(fields[format.convert("f")]);
//                    }
//
//                    yieldVD = fields[format.convert("g")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("g")]);
//                    if(fields[format.convert("h")].isEmpty()){
//                    	resmonsoon=0;
//                    }
//                    else{
//                    	resmonsoon = Integer.parseInt(fields[format.convert("h")]);
//                    }
//                    if(fields[format.convert("i")].isEmpty()){
//                    	resnonmonsoon=0;
//                    }
//                    else{
//                    	resnonmonsoon = Integer.parseInt(fields[format.convert("i")]);
//                    }
//                    
//                    yieldVI = fields[format.convert("j")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("j")]);
//                    if(fields[format.convert("k")].isEmpty()){
//                    	indmonsoon=0;
//                    }
//                    else{
//                    	indmonsoon = Integer.parseInt(fields[format.convert("k")]);
//                    }
//                    if(fields[format.convert("l")].isEmpty()){
//                    	indnonmonsoon=0;
//                    }
//                    else{
//                    	indnonmonsoon = Integer.parseInt(fields[format.convert("l")]);
//                    }
//                   
//                    Map<String,Integer>irrmon=new HashMap<>();
//                    irrmon.put(wellname, irrmonsoon);
//                    if(irrOperative.get(BasinName)==null){
//                    	irrOperative.put(BasinName, irrmon);
//                    }
//                    else{
//                    	irrOperative.get(BasinName).put(wellname, irrmonsoon);
//                    }
//                    Map<String,Integer>resmon=new HashMap<>();
//                    resmon.put(wellname, resmonsoon);
//                    if(resOperative.get(BasinName)==null){
//                    	resOperative.put(BasinName, resmon);
//                    }
//                    else{
//                    	resOperative.get(BasinName).put(wellname, resmonsoon);
//                    }
//                    Map<String,Integer>indmon=new HashMap<>();
//                    indmon.put(wellname, indmonsoon);
//                    if(indOperative.get(BasinName)==null){
//                    	indOperative.put(BasinName, indmon);
//                    }
//                    else{
//                    	indOperative.get(BasinName).put(wellname, indmonsoon);
//                    }
//                    Map<String,Integer>irrnonmon=new HashMap<>();
//                    irrnonmon.put(wellname, irrnonmonsoon);
//                    if(irrOperativenon.get(BasinName)==null){
//                    	irrOperativenon.put(BasinName,irrnonmon);
//                    }
//                    else{
//                    	irrOperativenon.get(BasinName).put(wellname, irrnonmonsoon);
//                    }
//                    Map<String,Integer>resnonmon=new HashMap<>();
//                    resnonmon.put(wellname, resnonmonsoon);
//                    if(resOperativenon.get(BasinName)==null){
//                    	resOperativenon.put(BasinName,resnonmon);
//                    }
//                    else{
//                    	resOperativenon.get(BasinName).put(wellname, resnonmonsoon);
//                    }
//                    Map<String,Integer>indnonmon=new HashMap<>();
//                    indnonmon.put(wellname, indnonmonsoon);
//                    if(indOperativenon.get(BasinName)==null){
//                    	indOperativenon.put(BasinName,indnonmon);
//                    }
//                    else{
//                    	indOperativenon.get(BasinName).put(wellname, indnonmonsoon);
//                    }
//                    
//                }
//               
//                //System.out.println(resOperative);
//            }
//
//            irrOperativenonT.put("command",irrOperativenon);
//            resOperativenonT.put("command",resOperativenon);
//            indOperativenonT.put("command",indOperativenon);
//            irrOperativeT.put("command",irrOperative);
//            resOperativeT.put("command",resOperative);
//            indOperativeT.put("command",indOperative);            
//            
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//             
//
//        //operating days---- non-command
//        try(BufferedReader iem = new BufferedReader(new FileReader(operatingfile))) {
//            
//            record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//            
//            irrOperativenon = new HashMap<>();
//            resOperativenon = new HashMap<>();
//            indOperativenon = new HashMap<>();
//            irrOperative = new HashMap<>();
//            resOperative = new HashMap<>();
//            indOperative = new HashMap<>();
//            
//            while((record = iem.readLine()) != null) {
//                //System.out.println("record"+record);
//
//                String fields[] = record.split(",");
//                if(fields.length == format.convert("aa")+1){
//                	int irrmonsoon;
//                	int irrnonmonsoon;
//                    int resmonsoon;
//                    int resnonmonsoon;
//                    int indmonsoon;
//                    int indnonmonsoon;
//                    
//                	String BasinName = format.removeQuotes(fields[format.convert("b")]);
//                    String wellname =format.removeQuotes(fields[format.convert("c")]);
//                    if(fields[format.convert("n")].isEmpty()){
//                    	irrmonsoon=0;
//                    }
//                    else{
//                    	irrmonsoon = Integer.parseInt(fields[format.convert("n")]);
//                    }
//                    if(fields[format.convert("o")].isEmpty()){
//                    	irrnonmonsoon=0;
//                    }
//                    else{
//                    	irrnonmonsoon = Integer.parseInt(fields[format.convert("o")]);
//                    }
//                    if(fields[format.convert("q")].isEmpty()){
//                    	resmonsoon=0;
//                    }
//                    else{
//                    	resmonsoon = Integer.parseInt(fields[format.convert("q")]);
//                    }
//                    if(fields[format.convert("r")].isEmpty()){
//                    	resnonmonsoon=0;
//                    }
//                    else{
//                    	resnonmonsoon = Integer.parseInt(fields[format.convert("r")]);
//                    }
//                    if(fields[format.convert("t")].isEmpty()){
//                    	indmonsoon=0;
//                    }
//                    else{
//                    	indmonsoon = Integer.parseInt(fields[format.convert("t")]);
//                    }
//                    if(fields[format.convert("u")].isEmpty()){
//                    	indnonmonsoon=0;
//                    }
//                    else{
//                    	indnonmonsoon = Integer.parseInt(fields[format.convert("u")]);
//                    }
//                    
//                    Map<String,Integer>irrmon=new HashMap<>();
//                    irrmon.put(wellname, irrmonsoon);
//                    if(irrOperative.get(BasinName)==null){
//                    	irrOperative.put(BasinName, irrmon);
//                    }
//                    else{
//                    	irrOperative.get(BasinName).put(wellname, irrmonsoon);
//                    }
//                    Map<String,Integer>resmon=new HashMap<>();
//                    resmon.put(wellname, resmonsoon);
//                    if(resOperative.get(BasinName)==null){
//                    	resOperative.put(BasinName, resmon);
//                    }
//                    else{
//                    	resOperative.get(BasinName).put(wellname, resmonsoon);
//                    }
//                    Map<String,Integer>indmon=new HashMap<>();
//                    indmon.put(wellname, indmonsoon);
//                    if(indOperative.get(BasinName)==null){
//                    	indOperative.put(BasinName, indmon);
//                    }
//                    else{
//                    	indOperative.get(BasinName).put(wellname, indmonsoon);
//                    }
//                    Map<String,Integer>irrnonmon=new HashMap<>();
//                    irrnonmon.put(wellname, irrnonmonsoon);
//                    if(irrOperativenon.get(BasinName)==null){
//                    	irrOperativenon.put(BasinName,irrnonmon);
//                    }
//                    else{
//                    	irrOperativenon.get(BasinName).put(wellname, irrnonmonsoon);
//                    }
//                    Map<String,Integer>resnonmon=new HashMap<>();
//                    resnonmon.put(wellname, resnonmonsoon);
//                    if(resOperativenon.get(BasinName)==null){
//                    	resOperativenon.put(BasinName,resnonmon);
//                    }
//                    else{
//                    	resOperativenon.get(BasinName).put(wellname, resnonmonsoon);
//                    }
//                    Map<String,Integer>indnonmon=new HashMap<>();
//                    indnonmon.put(wellname, indnonmonsoon);
//                    if(indOperativenon.get(BasinName)==null){
//                    	indOperativenon.put(BasinName,indnonmon);
//                    }
//                    else{
//                    	indOperativenon.get(BasinName).put(wellname, indnonmonsoon);
//                    }
//                    
//                }
//               
//                //System.out.println(resOperative);
//            }
//
//            irrOperativenonT.put("non_command",irrOperativenon);
//            resOperativenonT.put("non_command",resOperativenon);
//            indOperativenonT.put("non_command",indOperativenon);
//            irrOperativeT.put("non_command",irrOperative);
//            resOperativeT.put("non_command",resOperative);
//            indOperativeT.put("non_command",indOperative);            
//            
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        //operating days---- poorQuality
//        try(BufferedReader iem = new BufferedReader(new FileReader(operatingfile))) {
//            
//            record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//            
//            irrOperativenon = new HashMap<>();
//            resOperativenon = new HashMap<>();
//            indOperativenon = new HashMap<>();
//            irrOperative = new HashMap<>();
//            resOperative = new HashMap<>();
//            indOperative = new HashMap<>();
//            
//            while((record = iem.readLine()) != null) {
//                //System.out.println("record"+record);
//
//                String fields[] = record.split(",");
//               // System.out.println("fields"+fields.length);
//                if(fields.length == format.convert("aa")+1){
//                	int irrmonsoon;
//                	int irrnonmonsoon;
//                    int indmonsoon;
//                    int indnonmonsoon;
//                    
//                	String BasinName = format.removeQuotes(fields[format.convert("b")]);
//                    String wellname =format.removeQuotes(fields[format.convert("c")]);
//                    if(fields[format.convert("w")].isEmpty()){
//                    	irrmonsoon=0;
//                    }
//                    else{
//                    	irrmonsoon = Integer.parseInt(fields[format.convert("w")]);
//                    }
//                    if(fields[format.convert("x")].isEmpty()){
//                    	irrnonmonsoon=0;
//                    }
//                    else{
//                    	irrnonmonsoon = Integer.parseInt(fields[format.convert("x")]);
//                    }
//                    if(fields[format.convert("z")].isEmpty()){
//                    	indmonsoon=0;
//                    }
//                    else{
//                    	indmonsoon = Integer.parseInt(fields[format.convert("z")]);
//                    }
//                    if(fields[format.convert("aa")].isEmpty()){
//                    	indnonmonsoon=0;
//                    }
//                    else{
//                    	indnonmonsoon = Integer.parseInt(fields[format.convert("aa")]);
//                    }
//                    
//                    Map<String,Integer>irrmon=new HashMap<>();
//                    irrmon.put(wellname, irrmonsoon);
//                    if(irrOperative.get(BasinName)==null){
//                    	irrOperative.put(BasinName, irrmon);
//                    }
//                    else{
//                    	irrOperative.get(BasinName).put(wellname, irrmonsoon);
//                    }
//                    Map<String,Integer>indmon=new HashMap<>();
//                    indmon.put(wellname, indmonsoon);
//                    if(indOperative.get(BasinName)==null){
//                    	indOperative.put(BasinName, indmon);
//                    }
//                    else{
//                    	indOperative.get(BasinName).put(wellname, indmonsoon);
//                    }
//                    Map<String,Integer>irrnonmon=new HashMap<>();
//                    irrnonmon.put(wellname, irrnonmonsoon);
//                    if(irrOperativenon.get(BasinName)==null){
//                    	irrOperativenon.put(BasinName,irrnonmon);
//                    }
//                    else{
//                    	irrOperativenon.get(BasinName).put(wellname, irrnonmonsoon);
//                    }
//                    Map<String,Integer>indnonmon=new HashMap<>();
//                    indnonmon.put(wellname, indnonmonsoon);
//                    if(indOperativenon.get(BasinName)==null){
//                    	indOperativenon.put(BasinName,indnonmon);
//                    }
//                    else{
//                    	indOperativenon.get(BasinName).put(wellname, indnonmonsoon);
//                    }
//                    
//                }
//               
//                //System.out.println(resOperative);
//            }
//
//            irrOperativenonT.put("poor_quality",irrOperativenon);
//            indOperativenonT.put("poor_quality",indOperativenon);
//            resOperativenonT.put("poor_quality",resOperativenon);
//            irrOperativeT.put("poor_quality",irrOperative);
//            indOperativeT.put("poor_quality",indOperative);      
//            resOperativeT.put("poor_quality",resOperative);
//            
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        
//        //Basin Id----
//        try(BufferedReader iem = new BufferedReader(new FileReader(basinIdfile))) {
//            
//        
//            record = iem.readLine();
//            while((record = iem.readLine()) != null) {
//                //System.out.println("record"+record);
//
//                String fields[] = record.split(",");
//               // System.out.println("fields"+fields.length);
//                if(fields.length==3){
//                	String BasinName = format.removeQuotes(fields[format.convert("b")]);
//                    int BasinCode =Integer.parseInt(format.removeQuotes(fields[format.convert("c")]));
//                    Basin_Id.put(BasinName, BasinCode);
//                }
//                //System.out.println("**"+MicroBasin_GWvillage_key);
//            }
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
////village ID-----
//        try(BufferedReader iem = new BufferedReader(new FileReader(villageIdfile))) {
//            int c=0;
//
//        	//readXLSXFile();
//            record = iem.readLine();
//            while((record = iem.readLine()) != null) {
//                String fields[] = record.split(",");
//               // System.out.println("fields"+fields);
//                if(fields.length==2){
//                	int VillageId = Integer.parseInt(fields[1]);
//                    String IWMvillageName = format.removeQuotes(fields[0]);
//                    villageId.put(VillageId,IWMvillageName);
//                    if(village_details.get(IWMvillageName)!=null){
//                    	c++;
//                    	village_details.get(IWMvillageName).setVillageId(VillageId);
//                	}
//                }
//
//            }
//            
//        System.out.println("village id count"+c);
//       /* for(String key:village_details.keySet()){
//        	if(village_details.get(key).loc_id==0){
//        		//System.out.println(key);
//        		System.out.println(village_details.get(key).getVillageName());
//        	}
//        }*/
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        //crop village to iwm village
//        try(BufferedReader iem = new BufferedReader(new FileReader(cropvillfile))) {
//            record = iem.readLine();
//
//            while((record = iem.readLine()) != null) {
//                //System.out.println("record"+record);
//
//                String fields[] = record.split(",");
//               // System.out.println("fields"+fields.length);
//                if(fields.length==3){
//                	int cropcode = Integer.parseInt(fields[format.convert("a")]);
//                	int villagecode = Integer.parseInt(fields[format.convert("b")]);
//                	cropId.put(cropcode, villagecode);
//                }
//                //System.out.println("**"+MicroBasin_GWvillage_key);
//            }
//            System.out.println("crop id size "+cropId.size());
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        //paddykharif
//        try(BufferedReader iem = new BufferedReader(new FileReader(paddykhariffile))) {
//            
//            while((record = iem.readLine()) != null) {
//                //System.out.println("record"+record);
//
//                String fields[] = record.split(",");
//               // System.out.println("fields"+fields.length);
//                if(fields.length==3){
//                	int cropcode = Integer.parseInt(fields[format.convert("a")]);
//                    String source =format.removeQuotes(fields[format.convert("b")]);
//                    double area = Double.parseDouble(fields[format.convert("c")]);
//                    if(paddykharif.get(cropcode)==null){
//                    	Map<String,Double> water = new HashMap<>();
//                        water.put(source, area);
//                        paddykharif.put(cropcode, water);
//                    }
//                    else{
//                    	paddykharif.get(cropcode).put(source, area);
//                    	
//                    }
//                }
//                //System.out.println("**"+MicroBasin_GWvillage_key);
//            }
//            System.out.println("paddy kharif size "+paddykharif.size());
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//       
//        //paddyrabi
//        try(BufferedReader iem = new BufferedReader(new FileReader(paddyrabifile))) {
//            
//            while((record = iem.readLine()) != null) {
//                //System.out.println("record"+record);
//
//                String fields[] = record.split(",");
//               // System.out.println("fields"+fields.length);
//                if(fields.length==3){
//                	int cropcode = Integer.parseInt(fields[format.convert("a")]);
//                    String source =format.removeQuotes(fields[format.convert("b")]);
//                    double area = Double.parseDouble(fields[format.convert("c")]);
//                    if(paddyrabi.get(cropcode)==null){
//                    	Map<String,Double> water = new HashMap<>();
//                        water.put(source, area);
//                        paddyrabi.put(cropcode, water);
//                    }
//                    else{
//                    	paddyrabi.get(cropcode).put(source, area);
//                    	
//                    }
//                }
//                //System.out.println("**"+MicroBasin_GWvillage_key);
//            }
//            System.out.println("paddy rabi size "+paddyrabi.size());
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//      //non paddykharif
//        try(BufferedReader iem = new BufferedReader(new FileReader(nonpaddykhariffile))) {
//            
//            while((record = iem.readLine()) != null) {
//                //System.out.println("record"+record);
//
//                String fields[] = record.split(",");
//               // System.out.println("fields"+fields.length);
//                if(fields.length==3){
//                	int cropcode = Integer.parseInt(fields[format.convert("a")]);
//                    String source =format.removeQuotes(fields[format.convert("b")]);
//                    double area = Double.parseDouble(fields[format.convert("c")]);
//                    if(nonpaddykharif.get(cropcode)==null){
//                    	Map<String,Double> water = new HashMap<>();
//                        water.put(source, area);
//                        nonpaddykharif.put(cropcode, water);
//                    }
//                    else{
//                    	nonpaddykharif.get(cropcode).put(source, area);
//                    	
//                    }
//                }
//                //System.out.println("**"+MicroBasin_GWvillage_key);
//            }
//            System.out.println("nonpaddy kharif size "+nonpaddykharif.size());
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//       
//        //non paddyrabi
//        try(BufferedReader iem = new BufferedReader(new FileReader(nonpaddyrabifile))) {
//            
//            while((record = iem.readLine()) != null) {
//                //System.out.println("record"+record);
//
//                String fields[] = record.split(",");
//               // System.out.println("fields"+fields.length);
//                if(fields.length==3){
//                	int cropcode = Integer.parseInt(fields[format.convert("a")]);
//                    String source =format.removeQuotes(fields[format.convert("b")]);
//                    double area = Double.parseDouble(fields[format.convert("c")]);
//                    if(nonpaddyrabi.get(cropcode)==null){
//                    	Map<String,Double> water = new HashMap<>();
//                        water.put(source, area);
//                        nonpaddyrabi.put(cropcode, water);
//                    }
//                    else{
//                    	nonpaddyrabi.get(cropcode).put(source, area);
//                    	
//                    }
//                }
//                //System.out.println("**"+MicroBasin_GWvillage_key);
//            }
//            System.out.println("nonpaddy rabi size "+nonpaddyrabi.size());
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//      //compute dugwell
//        try(BufferedReader iem = new BufferedReader(new FileReader(irrigationUtilizationfile))) {
//        	record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//            while((record = iem.readLine()) != null) {
//                //System.out.println("record"+record);
//
//                String fields[] = record.split(",");
//               // System.out.println("fields"+fields.length);
//                if(fields.length==20||fields.length==28){
//                	String MicroBasinName = format.removeQuotes(fields[format.convert("c")]);
//            		String GWvillageName = format.removeQuotes(fields[format.convert("d")]);
//                    String MicroBasin_GWvillage_key = MicroBasinName + "##" +GWvillageName;
//                	double dugwell = Double.parseDouble(fields[format.convert("l")]);
//                	computeDugwell.put(locmapping.get(MicroBasin_GWvillage_key), dugwell);
//                }
//                //System.out.println("**"+MicroBasin_GWvillage_key);
//            }
//            System.out.println("Dugwell size "+computeDugwell.size());
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//      //compute tubewell
//        try(BufferedReader iem = new BufferedReader(new FileReader(irrigationUtilizationfile))) {
//        	record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//            while((record = iem.readLine()) != null) {
//                //System.out.println("record"+record);
//
//                String fields[] = record.split(",");
//               // System.out.println("fields"+fields.length);
//                if(fields.length==20||fields.length==28){
//                	String MicroBasinName = format.removeQuotes(fields[format.convert("c")]);
//            		String GWvillageName = format.removeQuotes(fields[format.convert("d")]);
//                    String MicroBasin_GWvillage_key = MicroBasinName + "##" +GWvillageName;
//                	double tubewell = Double.parseDouble(fields[format.convert("l")]);
//                	computeTubewell.put(locmapping.get(MicroBasin_GWvillage_key), tubewell);
//                }
//                //System.out.println("**"+MicroBasin_GWvillage_key);
//            }
//            System.out.println("Tubewell size "+computeTubewell.size());
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//      //json for area
//        Gson Area = new Gson();
//        JSONObject jsn = new JSONObject();
//        //inserting into area json Object
//        try(BufferedReader iem = new BufferedReader(new FileReader(areafile))) {
//            
//        	int count =0;
//        	int c2=0;
//            record = iem.readLine();
//            record = iem.readLine();
//            
//            while((record = iem.readLine()) != null) {
//                String fields[] = record.split(",");
//               /* if(fields.length!=25&&fields.length!=23&&fields.length!=24){
//                	System.out.println(fields.length);
//                	for(String f:fields){
//                		System.out.print(f +" ");
//                	}
//                	System.out.println();
//                }*/
//                if(fields.length==25 || fields.length==23||fields.length==24||fields.length==26){
//                	double total = 0;
//                	double command = 0;
//                	double nonCommand = 0;
//                	double poorQuality = 0;
//                	
//	                if(!fields[4].isEmpty()){
//	                	total=Double.parseDouble(fields[4]);
//	                }
//	                if(!fields[5].isEmpty()){
//	                	command = Double.parseDouble(fields[5]);
//	                }
//	                if(!fields[9].isEmpty()){
//	                    nonCommand=Double.parseDouble(fields[9]);
//	                }
//	                if(!fields[13].isEmpty()){
//	                	poorQuality=Double.parseDouble(fields[13]);
//	                }
//                	RechargeWorthy rw = new RechargeWorthy(command,nonCommand,poorQuality);
//                	double hilly=0;
//                	double forest =0;
//                	if(!fields[6].isEmpty()||!fields[10].isEmpty()||!fields[14].isEmpty()){
//	                	hilly= Double.parseDouble(fields[6])+Double.parseDouble(fields[10])+Double.parseDouble(fields[14]);
//	                }
//                	if(!fields[7].isEmpty()||!fields[11].isEmpty()||!fields[15].isEmpty()){
//	                	forest= Double.parseDouble(fields[7])+Double.parseDouble(fields[11])+Double.parseDouble(fields[15]);
//	                }
//                	NonRechargeWorthy nrw = new NonRechargeWorthy(hilly,forest);
//                	
//                	Area area = new Area(total,rw,nrw);
//                	String areajson = Area.toJson(area);
//                	
//                	//JSONObject jsn = new JSONObject(areajson.getBytes());
//                	//System.out.println(areajson);
//                	
//                	if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
//                    	
//                		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getArea()==null){
//                			count++;
//                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setArea(areajson);
//                			
//                		}
//                		else{
//                			Area areaobj=Area.fromJson(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getArea(), Area.class);
//                			total+=areaobj.total;
//                			command+=areaobj.rechargeWorthy.command;
//                   			nonCommand+=areaobj.rechargeWorthy.non_command;
//                			poorQuality+=areaobj.rechargeWorthy.poor_quality;
//                			RechargeWorthy rwObj = new RechargeWorthy(command,nonCommand,poorQuality);
//                			hilly+=areaobj.nonRechargeWorthy.hilly;
//                			forest+=areaobj.nonRechargeWorthy.forest;
//                        	NonRechargeWorthy nrwobj = new NonRechargeWorthy(hilly,forest);
//                        	Area areaObj = new Area(total,rwObj,nrwobj);  
//                        	String areaObjjson = Area.toJson(areaObj);
//                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setArea(areaObjjson);
//                			
//                		}
//                	}
//                	//System.out.println("#############");
//                	//break;
//                }
//
//            }
//        
//             for(String key:village_details.keySet()){
//        	if(village_details.get(key).area==null){
//        		//System.out.println(key);
//        		System.out.println(village_details.get(key).getVillageName());
//        	}
//        }
//            System.out.println("area count= "+count);
//            
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        // json for basinAssociation
//        try(BufferedReader iem = new BufferedReader(new FileReader(areafile))) {
//              
//          	//readXLSXFile();
//              record = iem.readLine();
//              record = iem.readLine();
//
//              while((record = iem.readLine()) != null) {
//                  //System.out.println("record"+record);
//
//                  String fields[] = record.split(",");
//                 // System.out.println("fields"+fields);
//                  if(fields.length==25 || fields.length==23||fields.length==24||fields.length==26){
//                  	int BasinCode;
//                  	double Villagearea = 0;
//                  	String BasinName = format.removeQuotes(fields[format.convert("c")]);
//                  	String gwvillageName = format.removeQuotes(fields[format.convert("d")]);
//                    double fractionArea = Double.parseDouble(fields[format.convert("e")]);
//                    if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
//                      /*	System.out.println(BasinName);
//                      	System.out.println("Basin_Id"+Basin_Id);
//                      	System.out.println("basinmapping"+basinmapping);*/
//                    	if(Basin_Id.containsKey(basinmapping.get(BasinName))) {
//                    		BasinCode = Basin_Id.get(basinmapping.get(BasinName));
//                    		Area areaobj=Area.fromJson(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getArea(), Area.class);
//                  		  	Villagearea = areaobj.total;
//                          	
//                              if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getBasinAssociation()==null){
//                            	  
//                            	  HashMap<Integer,Double> basin_assoc = new HashMap<>();
//                              	village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setBasinAssociation(basin_assoc);
//                              	
//                              }
//                              if(Villagearea!=0){
//                            	 // System.out.println(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).loc_name+" "+(fractionArea/Villagearea));
//                                  village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getBasinAssociation().put(BasinCode,((fractionArea/Villagearea)));
//                              
//                              }
//                             
//                    	}
//                    	}
//                      
//                  }
//        }
//              int c=0;
//              for(String vill:village_details.keySet()){
//              	c++;
//              	//System.out.println(village_details.get(vill));
//              }
//              System.out.println("village assoc count"+c);
//              
//              
//          }catch (IOException e) {
//              e.printStackTrace();
//          }
//        
//        //#TODO
//        
//        //Map for crop
//        /*Map<String,Object> obj=new HashMap<>();
//		Map<String,Map<String,Double>> obj2 = new HashMap<>();
//		Map<String,Double> water=new HashMap<>();
//		Map<String,Map<String,Object>> paddy=new HashMap<>();*/
//        
//        try{
//        	
//        	for(int id:paddykharif.keySet()){
//                for(String source:paddykharif.get(id).keySet()){
//                				//System.out.println(source);
//                	if(source.equalsIgnoreCase("Canal")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("command")==null){
//            						Map<String,Map<String,Object>> paddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("kharif", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						paddy.put("paddy", obj);
//            						village_details.get(IWMName).crop_info.put("command", paddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("command").get("paddy");
//            					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater")==null){
//            						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater");
//            					//System.out.println(irrigatedArea);
//            					//System.out.println(paddykharif.get(id).get(source));
//            					irrigatedArea+=paddykharif.get(id).get(source);
//            					//System.out.println(irrigatedArea);
//            					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", irrigatedArea);
//            					//System.out.println("**in canal "+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=paddykharif.get(id).get(source);
//            					//System.out.println(totalArea);
//            					obj.put("cropArea", totalArea);
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("Lift irrigation")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
//            						Map<String,Map<String,Object>> paddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("kharif", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						paddy.put("paddy", obj);
//            						village_details.get(IWMName).crop_info.put("non_command", paddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
//            					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater")==null){
//            						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater");
//            					//System.out.println(irrigatedArea);
//            					irrigatedArea+=paddykharif.get(id).get(source);
//            					//System.out.println(irrigatedArea);
//            					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", irrigatedArea);
//            					//System.out.println("***in lift irr"+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=paddykharif.get(id).get(source);
//            					obj.put("cropArea", totalArea);
//
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("Tanks")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
//            						Map<String,Map<String,Object>> paddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("kharif", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						paddy.put("paddy", obj);
//            						village_details.get(IWMName).crop_info.put("non_command", paddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
//            					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater")==null){
//            						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater");
//            					//System.out.println(irrigatedArea);
//            					//System.out.println(paddykharif.get(id).get(source));
//            					irrigatedArea+=paddykharif.get(id).get(source);
//            					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", irrigatedArea);
//            					//System.out.println("***in Tanks"+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=paddykharif.get(id).get(source);
//            					obj.put("cropArea", totalArea);
//
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("MI Tanks")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
//            						Map<String,Map<String,Object>> paddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedByMITanks", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("kharif", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						paddy.put("paddy", obj);
//            						village_details.get(IWMName).crop_info.put("non_command", paddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
//            					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByMITanks")==null){
//            						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByMITanks", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByMITanks");
//            					//System.out.println(irrigatedArea);
//            					//System.out.println(paddykharif.get(id).get(source));
//            					irrigatedArea+=paddykharif.get(id).get(source);
//            					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByMITanks", irrigatedArea);
//            					//System.out.println("***in MI Tanks"+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=paddykharif.get(id).get(source);
//            					obj.put("cropArea", totalArea);
//
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("Dug well")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(computeDugwell.containsKey(IWMName)){
//            						if(computeDugwell.get(IWMName)>0){
//            							if(village_details.get(IWMName).crop_info.get("command")==null){
//                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("kharif", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						paddy.put("paddy", obj);
//                    						village_details.get(IWMName).crop_info.put("command", paddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("command").get("paddy");
//                    					//System.out.println(obj);
//                    					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(paddykharif.get(id).get(source));
//                    					irrigatedArea+=paddykharif.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area "+totalArea);
//                    					totalArea+=paddykharif.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            						else{
//            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
//                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("kharif", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						paddy.put("paddy", obj);
//                    						village_details.get(IWMName).crop_info.put("non_command", paddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
//                    					//System.out.println(obj);
//                    					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(paddykharif.get(id).get(source));
//                    					irrigatedArea+=paddykharif.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area "+totalArea);
//                    					totalArea+=paddykharif.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            					}
//            					
//
//            				}
//                						
//            			}
//                	}
//                	
//                	if(source.equalsIgnoreCase("Tube well")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(computeDugwell.containsKey(IWMName)){
//            						if(computeDugwell.get(IWMName)>0){
//            							if(village_details.get(IWMName).crop_info.get("command")==null){
//                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("kharif", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						paddy.put("paddy", obj);
//                    						village_details.get(IWMName).crop_info.put("command", paddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("command").get("paddy");
//                    					//System.out.println(obj);
//                    					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(paddykharif.get(id).get(source));
//                    					irrigatedArea+=paddykharif.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area command "+totalArea);
//                    					totalArea+=paddykharif.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            						else{
//            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
//                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("kharif", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						paddy.put("paddy", obj);
//                    						village_details.get(IWMName).crop_info.put("non_command", paddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
//                    					//System.out.println(obj);
//                    					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(paddykharif.get(id).get(source));
//                    					irrigatedArea+=paddykharif.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area noncommand"+totalArea);
//                    					totalArea+=paddykharif.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            					}
//            					
//
//            				}
//                						
//            			}
//                	}
//                }
//             }
//        	
//        	//paddy rabi-------------------------------
//        	for(int id:paddyrabi.keySet()){
//                for(String source:paddyrabi.get(id).keySet()){
//                				//System.out.println(source);
//                	if(source.equalsIgnoreCase("Canal")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("command")==null){
//            						Map<String,Map<String,Object>> paddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("rabi", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						paddy.put("paddy", obj);
//            						village_details.get(IWMName).crop_info.put("command", paddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("command").get("paddy");
//            					if(obj.get("rabi")==null){
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("rabi", obj2);
//            						
//            					}
//            					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater")==null){
//            						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater");
//            					//System.out.println(irrigatedArea);
//            					//System.out.println(paddykharif.get(id).get(source));
//            					irrigatedArea+=paddyrabi.get(id).get(source);
//            					//System.out.println(irrigatedArea);
//            					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", irrigatedArea);
//            					//System.out.println("**in canal "+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=paddyrabi.get(id).get(source);
//            					//System.out.println(totalArea);
//            					obj.put("cropArea", totalArea);
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("Lift irrigation")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
//            						Map<String,Map<String,Object>> paddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("rabi", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						paddy.put("paddy", obj);
//            						village_details.get(IWMName).crop_info.put("non_command", paddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
//            					if(obj.get("rabi")==null){
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("rabi", obj2);
//            						
//            					}
//            					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater")==null){
//            						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater");
//            					//System.out.println(irrigatedArea);
//            					irrigatedArea+=paddyrabi.get(id).get(source);
//            					//System.out.println(irrigatedArea);
//            					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", irrigatedArea);
//            					//System.out.println("***in lift irr"+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=paddyrabi.get(id).get(source);
//            					obj.put("cropArea", totalArea);
//
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("Tanks")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
//            						Map<String,Map<String,Object>> paddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("rabi", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						paddy.put("paddy", obj);
//            						village_details.get(IWMName).crop_info.put("non_command", paddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
//            					if(obj.get("rabi")==null){
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("rabi", obj2);
//            						
//            					}
//            					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater")==null){
//            						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater");
//            					//System.out.println(irrigatedArea);
//            					//System.out.println(paddyrabi.get(id).get(source));
//            					irrigatedArea+=paddyrabi.get(id).get(source);
//            					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", irrigatedArea);
//            					//System.out.println("***in Tanks"+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=paddyrabi.get(id).get(source);
//            					obj.put("cropArea", totalArea);
//
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("MI Tanks")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
//            						Map<String,Map<String,Object>> paddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedByMITanks", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("rabi", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						paddy.put("paddy", obj);
//            						village_details.get(IWMName).crop_info.put("non_command", paddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
//            					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByMITanks")==null){
//            						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByMITanks", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByMITanks");
//            					//System.out.println(irrigatedArea);
//            					//System.out.println(paddyrabi.get(id).get(source));
//            					irrigatedArea+=paddyrabi.get(id).get(source);
//            					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByMITanks", irrigatedArea);
//            					//System.out.println("***in MI Tanks"+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=paddyrabi.get(id).get(source);
//            					obj.put("cropArea", totalArea);
//
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("Dug well")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(computeDugwell.containsKey(IWMName)){
//            						if(computeDugwell.get(IWMName)>0){
//            							if(village_details.get(IWMName).crop_info.get("command")==null){
//                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						paddy.put("paddy", obj);
//                    						village_details.get(IWMName).crop_info.put("command", paddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("command").get("paddy");
//                    					//System.out.println(obj);
//                    					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(paddyrabi.get(id).get(source));
//                    					irrigatedArea+=paddyrabi.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area "+totalArea);
//                    					totalArea+=paddyrabi.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            						else{
//            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
//                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						paddy.put("paddy", obj);
//                    						village_details.get(IWMName).crop_info.put("non_command", paddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
//                    					//System.out.println(obj);
//                    					if(obj.get("rabi")==null){
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedBySurfaceWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						
//                    					}
//                    					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(paddyrabi.get(id).get(source));
//                    					irrigatedArea+=paddyrabi.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area "+totalArea);
//                    					totalArea+=paddyrabi.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            					}
//            					
//
//            				}
//                						
//            			}
//                	}
//                	
//                	if(source.equalsIgnoreCase("Tube well")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(computeDugwell.containsKey(IWMName)){
//            						if(computeDugwell.get(IWMName)>0){
//            							if(village_details.get(IWMName).crop_info.get("command")==null){
//                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						paddy.put("paddy", obj);
//                    						village_details.get(IWMName).crop_info.put("command", paddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("command").get("paddy");
//                    					//System.out.println(obj);
//                    					if(obj.get("rabi")==null){
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedBySurfaceWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						
//                    					}
//                    					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(paddyrabi.get(id).get(source));
//                    					irrigatedArea+=paddyrabi.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area command "+totalArea);
//                    					totalArea+=paddyrabi.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            						else{
//            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
//                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						paddy.put("paddy", obj);
//                    						village_details.get(IWMName).crop_info.put("non_command", paddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
//                    					//System.out.println(obj);
//                    					if(obj.get("rabi")==null){
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedBySurfaceWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						
//                    					}
//                    					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(paddyrabi.get(id).get(source));
//                    					irrigatedArea+=paddyrabi.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area noncommand"+totalArea);
//                    					totalArea+=paddyrabi.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            					}
//            					
//
//            				}
//                						
//            			}
//                	}
//                }
//             }
//        	
//        	//#TODO
//        	//-------------------------------------non paddy--------------------------------------------
//        	
//
//        	
//        	for(int id:nonpaddykharif.keySet()){
//                for(String source:nonpaddykharif.get(id).keySet()){
//                				//System.out.println(source);
//                	if(source.equalsIgnoreCase("Canal")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("command")==null){
//            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("kharif", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						nonpaddy.put("nonPaddy", obj);
//            						village_details.get(IWMName).crop_info.put("command", nonpaddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
//            					if(obj==null){
//            						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
//            						Map<String,Object>obj1 = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj1.put("kharif", obj2);
//            						obj1.put("cropArea", 0.0);
//            						obj1.put("waterRequired", 0);
//            						obj1.put("waterRequiredUnit", "mm");
//            						village_details.get(IWMName).crop_info.get("command").put("nonPaddy", obj1);
//                					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
//
//            					}
//            					if(obj.get("kharif")==null){
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("kharif", obj2);
//            						
//            					}
//            					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater")==null){
//            						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater");
//            					//System.out.println(irrigatedArea);
//            					//System.out.println(nonpaddykharif.get(id).get(source));
//            					irrigatedArea+=nonpaddykharif.get(id).get(source);
//            					//System.out.println(irrigatedArea);
//            					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", irrigatedArea);
//            					//System.out.println("**in canal "+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=nonpaddykharif.get(id).get(source);
//            					//System.out.println(totalArea);
//            					obj.put("cropArea", totalArea);
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("Lift irrigation")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
//            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("kharif", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						nonpaddy.put("nonPaddy", obj);
//            						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//            					if(obj==null){
//            						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
//            						Map<String,Object>obj1 = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj1.put("kharif", obj2);
//            						obj1.put("cropArea", 0.0);
//            						obj1.put("waterRequired", 0);
//            						obj1.put("waterRequiredUnit", "mm");
//            						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
//                					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//
//            					}
//            					if(obj.get("kharif")==null){
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("kharif", obj2);
//            						
//            					}
//            					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater")==null){
//            						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater");
//            					//System.out.println(irrigatedArea);
//            					irrigatedArea+=nonpaddykharif.get(id).get(source);
//            					//System.out.println(irrigatedArea);
//            					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", irrigatedArea);
//            					//System.out.println("***in lift irr"+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=nonpaddykharif.get(id).get(source);
//            					obj.put("cropArea", totalArea);
//
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("Tanks")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
//            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("kharif", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						nonpaddy.put("nonPaddy", obj);
//            						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//            					if(obj==null){
//            						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
//            						Map<String,Object>obj1 = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj1.put("kharif", obj2);
//            						obj1.put("cropArea", 0.0);
//            						obj1.put("waterRequired", 0);
//            						obj1.put("waterRequiredUnit", "mm");
//            						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
//                					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//
//            					}
//            					if(obj.get("kharif")==null){
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("kharif", obj2);
//            						
//            					}
//            					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater")==null){
//            						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater");
//            					//System.out.println(irrigatedArea);
//            					//System.out.println(nonpaddykharif.get(id).get(source));
//            					irrigatedArea+=nonpaddykharif.get(id).get(source);
//            					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", irrigatedArea);
//            					//System.out.println("***in Tanks"+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=nonpaddykharif.get(id).get(source);
//            					obj.put("cropArea", totalArea);
//
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("MI Tanks")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("command")==null){
//            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedByMITanks", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("kharif", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						nonpaddy.put("nonPaddy", obj);
//            						village_details.get(IWMName).crop_info.put("command", nonpaddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
//            					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByMITanks")==null){
//            						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByMITanks", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByMITanks");
//            					//System.out.println(irrigatedArea);
//            					//System.out.println(nonpaddykharif.get(id).get(source));
//            					irrigatedArea+=nonpaddykharif.get(id).get(source);
//            					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByMITanks", irrigatedArea);
//            					//System.out.println("***in MI Tanks"+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=nonpaddykharif.get(id).get(source);
//            					obj.put("cropArea", totalArea);
//
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("Dug well")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(computeDugwell.containsKey(IWMName)){
//            						if(computeDugwell.get(IWMName)>0){
//            							if(village_details.get(IWMName).crop_info.get("command")==null){
//                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("kharif", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						nonpaddy.put("nonPaddy", obj);
//                    						village_details.get(IWMName).crop_info.put("command", nonpaddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
//                    					//System.out.println(obj);
//                    					if(obj==null){
//                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
//                    						Map<String,Object>obj1 = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj1.put("kharif", obj2);
//                    						obj1.put("cropArea", 0.0);
//                    						obj1.put("waterRequired", 0);
//                    						obj1.put("waterRequiredUnit", "mm");
//                    						village_details.get(IWMName).crop_info.get("command").put("nonPaddy", obj1);
//                        					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
//
//                    					}
//                    					//System.out.println(obj);
//                    					if(obj.get("kharif")==null){
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("kharif", obj2);
//                    						
//                    					}
//                    					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(nonpaddykharif.get(id).get(source));
//                    					irrigatedArea+=nonpaddykharif.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area "+totalArea);
//                    					totalArea+=nonpaddykharif.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            						else{
//            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
//                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("kharif", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						nonpaddy.put("nonPaddy", obj);
//                    						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//                    					//System.out.println(obj);
//                    					if(obj==null){
//                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
//                    						Map<String,Object>obj1 = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj1.put("kharif", obj2);
//                    						obj1.put("cropArea", 0.0);
//                    						obj1.put("waterRequired", 0);
//                    						obj1.put("waterRequiredUnit", "mm");
//                    						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
//                        					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//
//                    					}
//                    					if(obj.get("kharif")==null){
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("kharif", obj2);
//                    						
//                    					}
//                    					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(nonpaddykharif.get(id).get(source));
//                    					irrigatedArea+=nonpaddykharif.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area "+totalArea);
//                    					totalArea+=nonpaddykharif.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            					}
//            					
//
//            				}
//                						
//            			}
//                	}
//                	
//                	if(source.equalsIgnoreCase("Tube well")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(computeDugwell.containsKey(IWMName)){
//            						if(computeDugwell.get(IWMName)>0){
//            							if(village_details.get(IWMName).crop_info.get("command")==null){
//                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("kharif", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						nonpaddy.put("nonPaddy", obj);
//                    						village_details.get(IWMName).crop_info.put("command", nonpaddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
//                    					//System.out.println(obj);
//                    					if(obj==null){
//                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
//                    						Map<String,Object>obj1 = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj1.put("kharif", obj2);
//                    						obj1.put("cropArea", 0.0);
//                    						obj1.put("waterRequired", 0);
//                    						obj1.put("waterRequiredUnit", "mm");
//                    						village_details.get(IWMName).crop_info.get("command").put("nonPaddy", obj1);
//                        					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
//
//                    					}
//                    					//System.out.println(obj);
//                    					if(obj.get("kharif")==null){
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("kharif", obj2);
//                    						
//                    					}
//                    					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(nonpaddykharif.get(id).get(source));
//                    					irrigatedArea+=nonpaddykharif.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area command "+totalArea);
//                    					totalArea+=nonpaddykharif.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            						else{
//            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
//                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("kharif", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						nonpaddy.put("nonPaddy", obj);
//                    						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//                    					//System.out.println(obj);
//                    					if(obj==null){
//                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
//                    						Map<String,Object>obj1 = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj1.put("kharif", obj2);
//                    						obj1.put("cropArea", 0.0);
//                    						obj1.put("waterRequired", 0);
//                    						obj1.put("waterRequiredUnit", "mm");
//                    						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
//                        					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//
//                    					}
//                    					if(obj.get("kharif")==null){
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("kharif", obj2);
//                    						
//                    					}
//                    					if(((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(nonpaddykharif.get(id).get(source));
//                    					irrigatedArea+=nonpaddykharif.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("kharif")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area noncommand"+totalArea);
//                    					totalArea+=nonpaddykharif.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            					}
//            					
//
//            				}
//                						
//            			}
//                	}
//                }
//             }
//        	
//        	//nonpaddy rabi-------------------------------
//        	for(int id:nonpaddyrabi.keySet()){
//                for(String source:nonpaddyrabi.get(id).keySet()){
//                				//System.out.println(source);
//                	if(source.equalsIgnoreCase("Canal")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("command")==null){
//            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("rabi", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						nonpaddy.put("nonPaddy", obj);
//            						village_details.get(IWMName).crop_info.put("command", nonpaddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
//            					if(obj==null){
//            						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
//            						Map<String,Object>obj1 = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj1.put("rabi", obj2);
//            						obj1.put("cropArea", 0.0);
//            						obj1.put("waterRequired", 0);
//            						obj1.put("waterRequiredUnit", "mm");
//            						village_details.get(IWMName).crop_info.get("command").put("nonPaddy", obj1);
//                					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
//
//            					}
//            					if(obj.get("rabi")==null){
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("rabi", obj2);
//            						
//            					}
//            					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater")==null){
//            						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater");
//            					//System.out.println(irrigatedArea);
//            					//System.out.println(nonpaddykharif.get(id).get(source));
//            					irrigatedArea+=nonpaddyrabi.get(id).get(source);
//            					//System.out.println(irrigatedArea);
//            					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", irrigatedArea);
//            					//System.out.println("**in canal "+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=nonpaddyrabi.get(id).get(source);
//            					//System.out.println(totalArea);
//            					obj.put("cropArea", totalArea);
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("Lift irrigation")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
//            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("rabi", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						nonpaddy.put("nonPaddy", obj);
//            						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//            					if(obj==null){
//            						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
//            						Map<String,Object>obj1 = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj1.put("rabi", obj2);
//            						obj1.put("cropArea", 0.0);
//            						obj1.put("waterRequired", 0);
//            						obj1.put("waterRequiredUnit", "mm");
//            						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
//                					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//
//            					}
//            					if(obj.get("rabi")==null){
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("rabi", obj2);
//            						
//            					}
//            					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater")==null){
//            						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater");
//            					//System.out.println(irrigatedArea);
//            					irrigatedArea+=nonpaddyrabi.get(id).get(source);
//            					//System.out.println(irrigatedArea);
//            					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", irrigatedArea);
//            					//System.out.println("***in lift irr"+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=nonpaddyrabi.get(id).get(source);
//            					obj.put("cropArea", totalArea);
//
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("Tanks")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
//            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("rabi", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						nonpaddy.put("nonPaddy", obj);
//            						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//            					if(obj==null){
//            						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
//            						Map<String,Object>obj1 = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj1.put("rabi", obj2);
//            						obj1.put("cropArea", 0.0);
//            						obj1.put("waterRequired", 0);
//            						obj1.put("waterRequiredUnit", "mm");
//            						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
//                					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//
//            					}
//            					if(obj.get("rabi")==null){
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedBySurfaceWater", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("rabi", obj2);
//            						
//            					}
//            					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater")==null){
//            						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedBySurfaceWater");
//            					//System.out.println(irrigatedArea);
//            					//System.out.println(nonpaddyrabi.get(id).get(source));
//            					irrigatedArea+=nonpaddyrabi.get(id).get(source);
//            					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedBySurfaceWater", irrigatedArea);
//            					//System.out.println("***in Tanks"+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=nonpaddyrabi.get(id).get(source);
//            					obj.put("cropArea", totalArea);
//
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("MI Tanks")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
//            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//            						obj = new HashMap<>();
//            						obj2 = new HashMap<>();
//            						water.put("areaIrrigatedByMITanks", 0.0);
//            						obj2.put("irrigationAreaInfo", water);
//            						obj.put("rabi", obj2);
//            						obj.put("cropArea", 0.0);
//            						obj.put("waterRequired", 0);
//            						obj.put("waterRequiredUnit", "mm");
//            						nonpaddy.put("nonPaddy", obj);
//            						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						
//
//            					}
//            					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//            					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByMITanks")==null){
//            						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByMITanks", 0.0);
//            					}
//            					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByMITanks");
//            					//System.out.println(irrigatedArea);
//            					//System.out.println(nonpaddyrabi.get(id).get(source));
//            					irrigatedArea+=nonpaddyrabi.get(id).get(source);
//            					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByMITanks", irrigatedArea);
//            					//System.out.println("***in MI Tanks"+village_details.get(IWMName).crop_info);
//            					double totalArea = (double)(obj.get("cropArea"));
//            					//System.out.println("total area "+totalArea);
//            					totalArea+=nonpaddyrabi.get(id).get(source);
//            					obj.put("cropArea", totalArea);
//
//            				}
//                						
//            			}
//                	}
//                	if(source.equalsIgnoreCase("Dug well")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(computeDugwell.containsKey(IWMName)){
//            						if(computeDugwell.get(IWMName)>0){
//            							if(village_details.get(IWMName).crop_info.get("command")==null){
//                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						nonpaddy.put("nonPaddy", obj);
//                    						village_details.get(IWMName).crop_info.put("command", nonpaddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
//                    					//System.out.println(obj);
//                    					if(obj==null){
//                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
//                    						Map<String,Object>obj1 = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj1.put("rabi", obj2);
//                    						obj1.put("cropArea", 0.0);
//                    						obj1.put("waterRequired", 0);
//                    						obj1.put("waterRequiredUnit", "mm");
//                    						village_details.get(IWMName).crop_info.get("command").put("nonPaddy", obj1);
//                        					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
//
//                    					}
//                    					//System.out.println(obj);
//                    					if(obj.get("rabi")==null){
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						
//                    					}
//                    					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(nonpaddyrabi.get(id).get(source));
//                    					irrigatedArea+=nonpaddyrabi.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area "+totalArea);
//                    					totalArea+=nonpaddyrabi.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            						else{
//            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
//                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						nonpaddy.put("nonPaddy", obj);
//                    						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//                    					//System.out.println(obj);
//                    					if(obj==null){
//                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
//                    						Map<String,Object>obj1 = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj1.put("rabi", obj2);
//                    						obj1.put("cropArea", 0.0);
//                    						obj1.put("waterRequired", 0);
//                    						obj1.put("waterRequiredUnit", "mm");
//                    						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
//                        					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//
//                    					}
//                    					//System.out.println(obj);
//                    					if(obj.get("rabi")==null){
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						
//                    					}
//                    					if(obj.get("rabi")==null){
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedBySurfaceWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						
//                    					}
//                    					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(nonpaddyrabi.get(id).get(source));
//                    					irrigatedArea+=nonpaddyrabi.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area "+totalArea);
//                    					totalArea+=nonpaddyrabi.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            					}
//            					
//
//            				}
//                						
//            			}
//                	}
//                	
//                	if(source.equalsIgnoreCase("Tube well")){
//            			
//            			if(villageId.containsKey(id)){
//            				String IWMName = villageId.get(id);
//            				if(village_details.containsKey(IWMName)){
//            					//System.out.println(IWMName + id);
//            					Map<String,Object> obj;
//            					Map<String,Map<String,Double>> obj2;
//            					Map<String,Double> water=new HashMap<>();
//            					if(computeDugwell.containsKey(IWMName)){
//            						if(computeDugwell.get(IWMName)>0){
//            							if(village_details.get(IWMName).crop_info.get("command")==null){
//                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						nonpaddy.put("nonPaddy", obj);
//                    						village_details.get(IWMName).crop_info.put("command", nonpaddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
//                    					//System.out.println(obj);
//                    					if(obj.get("rabi")==null){
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedBySurfaceWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						
//                    					}
//                    					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(nonpaddyrabi.get(id).get(source));
//                    					irrigatedArea+=nonpaddyrabi.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area command "+totalArea);
//                    					totalArea+=nonpaddyrabi.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            						else{
//            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
//                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
//                    						obj = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						obj.put("cropArea", 0.0);
//                    						obj.put("waterRequired", 0);
//                    						obj.put("waterRequiredUnit", "mm");
//                    						nonpaddy.put("nonPaddy", obj);
//                    						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						
//
//                    					}
//                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//                    					if(obj==null){
//                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
//                    						Map<String,Object>obj1 = new HashMap<>();
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj1.put("rabi", obj2);
//                    						obj1.put("cropArea", 0.0);
//                    						obj1.put("waterRequired", 0);
//                    						obj1.put("waterRequiredUnit", "mm");
//                    						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
//                        					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
//
//                    					}
//                    					//System.out.println(obj);
//                    					if(obj.get("rabi")==null){	
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedByGroundWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						
//                    					}
//                    					//System.out.println(obj);
//                    					if(obj.get("rabi")==null){
//                    						obj2 = new HashMap<>();
//                    						water.put("areaIrrigatedBySurfaceWater", 0.0);
//                    						obj2.put("irrigationAreaInfo", water);
//                    						obj.put("rabi", obj2);
//                    						
//                    					}
//                    					if(((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater")==null){
//                    						((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", 0.0);
//                    					}
//                    					double irrigatedArea=(double)((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").get("areaIrrigatedByGroundWater");
//                    					//System.out.println(irrigatedArea);
//                    					//System.out.println(nonpaddyrabi.get(id).get(source));
//                    					irrigatedArea+=nonpaddyrabi.get(id).get(source);
//                    					((Map<String, Map<String, Double>>)obj.get("rabi")).get("irrigationAreaInfo").put("areaIrrigatedByGroundWater", irrigatedArea);
//                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
//                    					double totalArea = (double)(obj.get("cropArea"));
//                    					//System.out.println("total area noncommand"+totalArea);
//                    					totalArea+=nonpaddyrabi.get(id).get(source);
//                    					obj.put("cropArea", totalArea);
//            						}
//            					}
//            					
//
//            				}
//                						
//            			}
//                	}
//                }
//             }
//        
//        }catch(Exception e){
//        	e.printStackTrace();
//        }
//        
//      /*//json for crop
//        Gson crop = new Gson();
//        //inserting into crop json Object
//        try(BufferedReader iem = new BufferedReader(new FileReader(cropfile))) {
//        	int count =0;
//            record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//            while((record = iem.readLine()) != null) {
//                String fields[] = record.split(",");
//                if(fields.length==40){
//                	Double cpaddyarea = Double.parseDouble(fields[28])+Double.parseDouble(fields[16])+Double.parseDouble(fields[4])+Double.parseDouble(fields[29])+Double.parseDouble(fields[17])+Double.parseDouble(fields[5]);
//                	Kharif cpk= new Kharif((Double.parseDouble(fields[28])),(Double.parseDouble(fields[16])),(Double.parseDouble(fields[4])));
//                	Rabi cpr= new Rabi((Double.parseDouble(fields[29])),(Double.parseDouble(fields[17])),(Double.parseDouble(fields[5])));
//                	Paddy cp= new Paddy(cpaddyarea,cpk,cpr);
//                	Double cnonpaddyarea = Double.parseDouble(fields[30])+Double.parseDouble(fields[18])+Double.parseDouble(fields[6])+Double.parseDouble(fields[31])+Double.parseDouble(fields[19])+Double.parseDouble(fields[7]);
//                	Kharif cnpk= new Kharif((Double.parseDouble(fields[30])),(Double.parseDouble(fields[18])),(Double.parseDouble(fields[6])));
//                	Rabi cnpr= new Rabi((Double.parseDouble(fields[31])),(Double.parseDouble(fields[19])),(Double.parseDouble(fields[7])));
//                	NonPaddy cnp= new NonPaddy(cnonpaddyarea,cnpk,cnpr);
//                	CropCommand cropCommand = new CropCommand(cp,cnp); 
// 
//                	Double cnpaddyarea = Double.parseDouble(fields[32])+Double.parseDouble(fields[10])+Double.parseDouble(fields[8])+Double.parseDouble(fields[33])+Double.parseDouble(fields[21])+Double.parseDouble(fields[9]);
//                	Double cnnonpaddyarea = Double.parseDouble(fields[34])+Double.parseDouble(fields[22])+Double.parseDouble(fields[10])+Double.parseDouble(fields[35])+Double.parseDouble(fields[23])+Double.parseDouble(fields[11]);
//                	Kharif ncpk= new Kharif((Double.parseDouble(fields[32])),(Double.parseDouble(fields[20])),(Double.parseDouble(fields[8])));
//                	Rabi ncpr= new Rabi((Double.parseDouble(fields[33])),(Double.parseDouble(fields[21])),(Double.parseDouble(fields[9])));
//                	Paddy ncp= new Paddy(cnpaddyarea,ncpk,ncpr);
//                	Kharif ncnpk= new Kharif((Double.parseDouble(fields[34])),(Double.parseDouble(fields[22])),(Double.parseDouble(fields[10])));
//                	Rabi ncnpr= new Rabi((Double.parseDouble(fields[35])),(Double.parseDouble(fields[23])),(Double.parseDouble(fields[11])));
//                	NonPaddy ncnp= new NonPaddy(cnnonpaddyarea,ncnpk,ncnpr);
//                	CropNonCommand cropNonCommand = new CropNonCommand(ncp,ncnp); 
//                	
//                  	Double commandPoorQualitypaddyarea = Double.parseDouble(fields[36])+Double.parseDouble(fields[24])+Double.parseDouble(fields[12])+Double.parseDouble(fields[37])+Double.parseDouble(fields[25])+Double.parseDouble(fields[13]);
//                    Double commandPoorQualitynonpaddyarea = Double.parseDouble(fields[38])+Double.parseDouble(fields[26])+Double.parseDouble(fields[14])+Double.parseDouble(fields[39])+Double.parseDouble(fields[27])+Double.parseDouble(fields[15]);
//                    Kharif pqpk= new Kharif((Double.parseDouble(fields[36])),(Double.parseDouble(fields[24])),(Double.parseDouble(fields[12])));
//                	Rabi pqpr= new Rabi((Double.parseDouble(fields[37])),(Double.parseDouble(fields[25])),(Double.parseDouble(fields[13])));
//                	Paddy pqp= new Paddy(commandPoorQualitypaddyarea,pqpk,pqpr);
//                	Kharif pqnpk= new Kharif((Double.parseDouble(fields[38])),(Double.parseDouble(fields[26])),(Double.parseDouble(fields[14])));
//                	Rabi pqnpr= new Rabi((Double.parseDouble(fields[39])),(Double.parseDouble(fields[27])),(Double.parseDouble(fields[15])));
//                	NonPaddy pqnp= new NonPaddy(commandPoorQualitynonpaddyarea,pqnpk,pqnpr);
//                	CropPoorQuality cropPoorQuality = new CropPoorQuality(pqp,pqnp); 
//                	
//                	Crop cropObj = new Crop(cropCommand,cropNonCommand,cropPoorQuality);
//                	String cropjson = crop.toJson(cropObj);
//                	
//                	if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
//                		//c2++;
//                		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getCrop()==null){
//                        	count++;
//                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setCrop(cropjson);
//                		}
//                		else{
//                			Crop JCrop= crop.fromJson(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getCrop(), Crop.class);
//                			cpaddyarea+=JCrop.command.paddy.cropArea;
//                        	cpk= new Kharif((Double.parseDouble(fields[28]))+JCrop.command.paddy.kharif.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[16]))+JCrop.command.paddy.kharif.areaIrrigatedByMITank,(Double.parseDouble(fields[4]))+JCrop.command.paddy.kharif.areaIrrigatedByGroundWater);
//                        	cpr= new Rabi((Double.parseDouble(fields[29]))+JCrop.command.paddy.rabi.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[17]))+JCrop.command.paddy.rabi.areaIrrigatedByMITank,(Double.parseDouble(fields[5]))+JCrop.command.paddy.rabi.areaIrrigatedByGroundWater);
//                        	cp= new Paddy(cpaddyarea,cpk,cpr);
//                        	cnonpaddyarea+= JCrop.command.nonPaddy.cropArea;
//                        	cnpk= new Kharif((Double.parseDouble(fields[30]))+JCrop.command.nonPaddy.kharif.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[18]))+JCrop.command.nonPaddy.kharif.areaIrrigatedByMITank,(Double.parseDouble(fields[6]))+JCrop.nonCommand.nonPaddy.kharif.areaIrrigatedByGroundWater);
//                        	cnpr= new Rabi((Double.parseDouble(fields[31]))+JCrop.command.nonPaddy.rabi.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[19]))+JCrop.command.nonPaddy.rabi.areaIrrigatedByMITank,(Double.parseDouble(fields[7]))+JCrop.command.nonPaddy.rabi.areaIrrigatedByGroundWater);
//                        	cnp= new NonPaddy(cnonpaddyarea,cnpk,cnpr);
//                        	cropCommand = new CropCommand(cp,cnp); 
//         
//                        	cnpaddyarea += JCrop.nonCommand.paddy.cropArea;
//                        	cnnonpaddyarea +=JCrop.nonCommand.nonPaddy.cropArea;
//                        	ncpk= new Kharif((Double.parseDouble(fields[32]))+JCrop.nonCommand.paddy.kharif.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[20]))+JCrop.nonCommand.paddy.kharif.areaIrrigatedByMITank,(Double.parseDouble(fields[8]))+JCrop.nonCommand.paddy.kharif.areaIrrigatedByGroundWater);
//                        	ncpr= new Rabi((Double.parseDouble(fields[33]))+JCrop.nonCommand.paddy.rabi.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[21]))+JCrop.nonCommand.paddy.rabi.areaIrrigatedByMITank,(Double.parseDouble(fields[9]))+JCrop.nonCommand.paddy.rabi.areaIrrigatedByGroundWater);
//                        	ncp= new Paddy(cnpaddyarea,ncpk,ncpr);
//                        	ncnpk= new Kharif((Double.parseDouble(fields[34])),(Double.parseDouble(fields[22])),(Double.parseDouble(fields[10])));
//                        	ncnpr= new Rabi((Double.parseDouble(fields[35])),(Double.parseDouble(fields[23])),(Double.parseDouble(fields[11])));
//                        	ncnp= new NonPaddy(cnnonpaddyarea,ncnpk,ncnpr);
//                        	cropNonCommand = new CropNonCommand(ncp,ncnp); 
//                        	
//                          	commandPoorQualitypaddyarea += JCrop.poorQuality.paddy.cropArea;
//                            commandPoorQualitynonpaddyarea += JCrop.poorQuality.nonPaddy.cropArea;
//                            pqpk= new Kharif((Double.parseDouble(fields[36]))+JCrop.poorQuality.paddy.kharif.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[24]))+JCrop.poorQuality.paddy.kharif.areaIrrigatedByMITank,(Double.parseDouble(fields[12]))+JCrop.poorQuality.paddy.kharif.areaIrrigatedByGroundWater);
//                        	pqpr= new Rabi((Double.parseDouble(fields[37]))+JCrop.poorQuality.paddy.rabi.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[25]))+JCrop.poorQuality.paddy.rabi.areaIrrigatedByMITank,(Double.parseDouble(fields[13]))+JCrop.poorQuality.paddy.rabi.areaIrrigatedByGroundWater);
//                        	pqp= new Paddy(commandPoorQualitypaddyarea,pqpk,pqpr);
//                        	pqnpk= new Kharif((Double.parseDouble(fields[38]))+JCrop.poorQuality.nonPaddy.kharif.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[26]))+JCrop.poorQuality.nonPaddy.kharif.areaIrrigatedByMITank,(Double.parseDouble(fields[14]))+JCrop.poorQuality.nonPaddy.kharif.areaIrrigatedByGroundWater);
//                        	pqnpr= new Rabi((Double.parseDouble(fields[39]))+JCrop.poorQuality.nonPaddy.rabi.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[27]))+JCrop.poorQuality.nonPaddy.rabi.areaIrrigatedByMITank,(Double.parseDouble(fields[15]))+JCrop.poorQuality.nonPaddy.rabi.areaIrrigatedByGroundWater);
//                        	pqnp= new NonPaddy(commandPoorQualitynonpaddyarea,pqnpk,pqnpr);
//                        	cropPoorQuality = new CropPoorQuality(pqp,pqnp); 
//                        	
//                        	cropObj = new Crop(cropCommand,cropNonCommand,cropPoorQuality);
//                        	cropjson = crop.toJson(cropObj);
//                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setCrop(cropjson);
//
//                		}
//                	}
//                	//System.out.println(cropjson);
//                }
//            }
//            System.out.println("crop= "+count);
//        }catch (IOException e) {
//            e.printStackTrace();
//        }*/
//        
//        
//        //json for water bodies
//        Gson waterbody = new Gson();
//        //inserting into waterbodies json Object
//        try(BufferedReader iem = new BufferedReader(new FileReader(waterbodiesfile))) {
//        	int count =0;
//            record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//
//            while((record = iem.readLine()) != null) {
//                String fields[] = record.split(",");
//                if(fields.length==10){
//                	Mitank cmitank= new Mitank((Double.parseDouble(fields[4])),Double.parseDouble(fields[5]),0,120,150,0.00144);
//                	Command wbcommand = new Command(cmitank);
//                	Mitank ncmitank= new Mitank((Double.parseDouble(fields[6])),Double.parseDouble(fields[7]),0,120,150,0.00144);
//                	NonCommand nonCommand = new NonCommand(ncmitank);
//                	Mitank cpqmitank= new Mitank((Double.parseDouble(fields[8])),Double.parseDouble(fields[9])*0.6,0,120,150,0.00144);
//                	CommandPoorQuality commandpoorQuality = new CommandPoorQuality(cpqmitank);
//                    waterbodies waterbd = new waterbodies(wbcommand,nonCommand,commandpoorQuality);
//                    String waterbodyjson = waterbody.toJson(waterbd);
//                    if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
//                    	if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getWaterbodies()==null){
//                        	count++;
//                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setWaterbodies(waterbd);
//                		}
//                    	else{
//                    		waterbd= village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getWaterbodies();
//                    		cmitank= new Mitank((Double.parseDouble(fields[4]))+waterbd.command.mitank.count,((Double.parseDouble(fields[5])/*+waterbd.command.mitank.spreadArea_monsoon*/)),0,120,150,0.00144);
//                        	wbcommand = new Command(cmitank);
//                        	ncmitank= new Mitank((Double.parseDouble(fields[6]))+waterbd.non_command.mitank.count,((Double.parseDouble(fields[7])/*+waterbd.nonCommand.mitank.spreadArea_monsoon*/)),0,120,150,0.00144);
//                        	nonCommand = new NonCommand(ncmitank);
//                        	cpqmitank= new Mitank((Double.parseDouble(fields[8]))+waterbd.command_poor_quality.mitank.count,((Double.parseDouble(fields[9])/*+waterbd.commandPoorQuality.mitank.spreadArea_monsoon*/)),0,120,150,0.00144);
//                        	commandpoorQuality = new CommandPoorQuality(cpqmitank);
//                            waterbd = new waterbodies(wbcommand,nonCommand,commandpoorQuality);
//                            waterbodyjson = waterbody.toJson(waterbd);
//                   			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setWaterbodies(waterbd);
//
//                    	}
//                	}
//                   // System.out.println(waterbodyjson);
//                }
//            }
//            
//            /*for(String key:village_details.keySet()){
//            	if(village_details.get(key).water_bodies==null){
//            		//System.out.println(key);
//            		System.out.println(village_details.get(key).getVillageName());
//            	}
//            }*/
//            System.out.println(" water body count= "+count);
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        //inserting into canal json Object
//        try(BufferedReader iem = new BufferedReader(new FileReader(canalfile))) {
//            
//        	int count =0;
//            record = iem.readLine();
//            record = iem.readLine();
//
//            while((record = iem.readLine()) != null) {
//            	ArrayList<Map<String,Object>> canal = new ArrayList<>();
//                //json for Canal
//                HashMap<String,Object> canalMap = new HashMap<>();
//            	
//                String fields[] = record.split(",");
//                if(fields.length==16){
//                	Map<String,Integer> runningDays = new HashMap<>();
//                	canalMap.put("length", Double.parseDouble(fields[4]));
//                	canalMap.put("type","0");
//                    canalMap.put("sideSlopes",Double.parseDouble(fields[7]));//sideSlopes
//                    canalMap.put("wettedPerimeter",Double.parseDouble(fields[8]));//wettedperimeter
//                    canalMap.put("wettedArea",Double.parseDouble(fields[9]));//wettedarea
//                    canalMap.put("seepageFactor",Double.parseDouble(fields[10]));//canalseepagefactor
//                    canalMap.put("bedWidth",Double.parseDouble(fields[6]));//bedwidth
//                    canalMap.put("designDepthFlow",Double.parseDouble("0"));//designDepthOfFlow
//                    runningDays.put("monsoon",Integer.parseInt(fields[11]));//runningDays_monsoon
//                    runningDays.put("non_monsoon",Integer.parseInt(fields[12]));//runningDays_nonmonsoon
//                    canalMap.put("noOfRunningDays", runningDays);
//                    canal.add(canalMap);
//                    if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
//                			
//                    	
//                    	if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getCanal()==null){
//                        	count++;
//
//                        	village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setCanal(canal);
//                        	
//                        }else{
//                        	
//
//                       	village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getCanal().add(canalMap);
//
//                        }
//                                        	
//                	}
//                   // System.out.println(canal);
//                }
//        	}
//            System.out.println("canal count= "+count);
//            
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        
//      //json for artificial wc
//        Gson artificialwc = new Gson();
//        //inserting into artificial wc json Object
//        try(BufferedReader iem = new BufferedReader(new FileReader(artificialWCfile))) {
//        	int count =0;
//            record = iem.readLine();
//            while((record = iem.readLine()) != null) {
//                String fields[] = record.split(",");
//                if(fields.length==64){
//
//                	PercolationTanks cpt= new PercolationTanks((Double.parseDouble(fields[4])),(Double.parseDouble(fields[5])),(Double.parseDouble("1.5")),40);
//                	MiniPercolationTanks cmpt= new MiniPercolationTanks((Double.parseDouble(fields[8])),(Double.parseDouble(fields[9])),(Double.parseDouble("1.5")),40);
//                	CheckDams ccd= new CheckDams((Double.parseDouble(fields[12])),(Double.parseDouble(fields[13])),(Double.parseDouble("6")),40);
//                	FarmPonds fp = new FarmPonds((Double.parseDouble(fields[format.convert("q")])),(Double.parseDouble(fields[format.convert("r")])),(Double.parseDouble("22")),40);
//                	Other other = new Other((Double.parseDouble(fields[format.convert("u")])),(Double.parseDouble(fields[format.convert("v")])),(Double.parseDouble("10")),40);
//                	ArtificialWCCommand artificialWCCommand = new ArtificialWCCommand(cpt,cmpt,ccd,fp,other);
//                	
//                	PercolationTanks ncpt= new PercolationTanks((Double.parseDouble(fields[24])),(Double.parseDouble(fields[25])),(Double.parseDouble("1.5")),40);
//                	MiniPercolationTanks ncmpt= new MiniPercolationTanks((Double.parseDouble(fields[28])),(Double.parseDouble(fields[29])),(Double.parseDouble("1.5")),40);
//                	CheckDams nccd= new CheckDams((Double.parseDouble(fields[32])),(Double.parseDouble(fields[33])),(Double.parseDouble("6")),40);
//                	FarmPonds ncfp = new FarmPonds((Double.parseDouble(fields[format.convert("ak")])),(Double.parseDouble(fields[format.convert("al")])),(Double.parseDouble("22")),40);
//                	Other ncother = new Other((Double.parseDouble(fields[format.convert("ao")])),(Double.parseDouble(fields[format.convert("ap")])),(Double.parseDouble("10")),40);
//                	ArtificialWCNonCommand artificialWCNonCommand = new ArtificialWCNonCommand(ncpt,ncmpt,nccd,ncfp,ncother);
//                	
//                	PercolationTanks pqpt= new PercolationTanks((Double.parseDouble(fields[format.convert("as")])),(Double.parseDouble(fields[format.convert("at")])),(Double.parseDouble("1.5")),40);
//                	MiniPercolationTanks pqmpt= new MiniPercolationTanks((Double.parseDouble(fields[format.convert("aw")])),(Double.parseDouble(fields[format.convert("ax")])),(Double.parseDouble("1.5")),40);
//                	CheckDams pqcd= new CheckDams((Double.parseDouble(fields[format.convert("ba")])),(Double.parseDouble(fields[format.convert("bb")])),(Double.parseDouble("6")),40);
//                	FarmPonds pqfp = new FarmPonds((Double.parseDouble(fields[format.convert("be")])),(Double.parseDouble(fields[format.convert("bf")])),(Double.parseDouble("22")),40);
//                	Other pqother = new Other((Double.parseDouble(fields[format.convert("bi")])),(Double.parseDouble(fields[format.convert("bj")])),(Double.parseDouble("10")),40);
//                	ArtificialWCPPoorQuality artificialWCPPoorQuality = new ArtificialWCPPoorQuality(pqpt,pqmpt,pqcd,pqfp,pqother);
//
//                	ArtificialWC artificialWC = new ArtificialWC(artificialWCCommand,artificialWCNonCommand,artificialWCPPoorQuality);
//                	String artificialwcjson = artificialwc.toJson(artificialWC);
//                	if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
//                		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getArtificialWC()==null){
//                        	count++;
//                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setArtificialWC(artificialWC);
//                		}else{
//                			artificialWC= village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getArtificialWC();
//                			cpt= new PercolationTanks((Double.parseDouble(fields[4]))+artificialWC.command.pt.count,(Double.parseDouble(fields[5]))+artificialWC.command.pt.capacity,(Double.parseDouble("1.5")),40);
//                        	cmpt= new MiniPercolationTanks((Double.parseDouble(fields[8]))+artificialWC.command.mpt.count,(Double.parseDouble(fields[9]))+artificialWC.command.mpt.capacity,(Double.parseDouble("1.5")),40);
//                        	ccd= new CheckDams((Double.parseDouble(fields[12]))+artificialWC.command.cd.count,(Double.parseDouble(fields[13]))+artificialWC.command.cd.capacity,(Double.parseDouble("6")),40);
//                        	fp = new FarmPonds((Double.parseDouble(fields[format.convert("q")]))+artificialWC.command.fp.count,(Double.parseDouble(fields[format.convert("r")]))+artificialWC.command.fp.capacity,(Double.parseDouble("22")),40);
//                        	other = new Other((Double.parseDouble(fields[format.convert("u")]))+artificialWC.command.others.count,(Double.parseDouble(fields[format.convert("v")]))+artificialWC.command.others.capacity,(Double.parseDouble("10")),40);
//                        	artificialWCCommand = new ArtificialWCCommand(cpt,cmpt,ccd,fp,other);
//                        	
//                        	ncpt= new PercolationTanks((Double.parseDouble(fields[24]))+artificialWC.non_command.pt.count,(Double.parseDouble(fields[25]))+artificialWC.non_command.pt.capacity,(Double.parseDouble("1.5")),40);
//                        	ncmpt= new MiniPercolationTanks((Double.parseDouble(fields[28]))+artificialWC.non_command.mpt.count,(Double.parseDouble(fields[29]))+artificialWC.non_command.mpt.capacity,(Double.parseDouble("1.5")),40);
//                        	nccd= new CheckDams((Double.parseDouble(fields[32]))+artificialWC.non_command.cd.count,(Double.parseDouble(fields[33]))+artificialWC.non_command.cd.capacity,(Double.parseDouble("6")),40);
//                        	ncfp = new FarmPonds((Double.parseDouble(fields[format.convert("ak")]))+artificialWC.non_command.fp.count,(Double.parseDouble(fields[format.convert("al")]))+artificialWC.non_command.fp.capacity,(Double.parseDouble("22")),40);
//                        	ncother = new Other((Double.parseDouble(fields[format.convert("ao")]))+artificialWC.non_command.others.count,(Double.parseDouble(fields[format.convert("ap")]))+artificialWC.non_command.others.capacity,(Double.parseDouble("10")),40);
//                        	artificialWCNonCommand = new ArtificialWCNonCommand(ncpt,ncmpt,nccd,ncfp,ncother);
//                        	
//                        	pqpt= new PercolationTanks((Double.parseDouble(fields[format.convert("as")])),(Double.parseDouble(fields[format.convert("at")])),(Double.parseDouble("1.5")),40);
//                        	pqmpt= new MiniPercolationTanks((Double.parseDouble(fields[format.convert("aw")])),(Double.parseDouble(fields[format.convert("ax")])),(Double.parseDouble("1.5")),40);
//                        	pqcd= new CheckDams((Double.parseDouble(fields[format.convert("ba")])),(Double.parseDouble(fields[format.convert("bb")])),(Double.parseDouble("6")),40);
//                        	pqfp = new FarmPonds((Double.parseDouble(fields[format.convert("be")])),(Double.parseDouble(fields[format.convert("bf")])),(Double.parseDouble("22")),40);
//                        	pqother = new Other((Double.parseDouble(fields[format.convert("bi")])),(Double.parseDouble(fields[format.convert("bj")])),(Double.parseDouble("10")),40);
//                        	artificialWCPPoorQuality = new ArtificialWCPPoorQuality(pqpt,pqmpt,pqcd,pqfp,pqother);
//
//                        	artificialWC = new ArtificialWC(artificialWCCommand,artificialWCNonCommand,artificialWCPPoorQuality);
//                        	artificialwcjson = artificialwc.toJson(artificialWC);
//                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setArtificialWC(artificialWC);
//
//                		}
//                	}
//                   // System.out.println(artificialwcjson);
//                }
//            }
//            for(String key:village_details.keySet()){
//            	if(village_details.get(key).aritificial_wc==null){
//            		//System.out.println(key);
//            		System.out.println(village_details.get(key).getVillageName());
//            	}
//            }
//            System.out.println("artificial wc count= "+count);
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        //json for Irrigation Utilization wc
//        Gson IrrigationUtilization = new Gson();
//        //inserting into Irrigation Utilization json Object
//        try(BufferedReader iem = new BufferedReader(new FileReader(irrigationUtilizationfile))) {
//        	int count =0;
//            record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//           
//            while((record = iem.readLine()) != null) {
//                String fields[] = record.split(",");
//                /*if(fields.length!=20 && fields.length!=28){
//                	System.out.println("fields"+fields.length);
//                	for(String f:fields){
//                		System.out.print(f);
//                		
//                	}
//                	System.out.println();
//                }*/
//
//               if(fields.length==20||fields.length==28){
//            	   
//            	    irrOperativenon = irrOperativenonT.get("command");
//            	    irrOperative = irrOperativeT.get("command");
//            	   fillOperativeDaysIrr(format.removeQuotes(fields[format.convert("c")]));
//                	//doubt growth rate
//                	Borewell cborewell;
//                	Dugwell cdugwell;
//                	DugwellPumpSet cdugwellPumpSet;
//                	ShallowTubeWell cshallowTubeWell;
//                	MediumTubeWell cmediumTubeWell;
//                	DeepTubeWell cdeepTubeWell;
//                	FilterPoints cfilterPoints;
//                	cborewell= new Borewell(2011,(Double.parseDouble(fields[format.convert("g")])),60,mBWs,nmBWs,3);
//                	cdugwell= new Dugwell(2011,(Double.parseDouble(fields[format.convert("e")])),10,mDW,nmDW,0);
//                	cdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble(fields[format.convert("f")])),0,mPS,nmPS,0);
//                	cshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble(fields[format.convert("h")])),0,mSTW,nmSTW,0);
//                	cmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble(fields[format.convert("i")])),120,mMTW,nmMTW,0);
//                	cdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble(fields[format.convert("j")])),0,mDTW,nmDTW,0);
//                	cfilterPoints = new FilterPoints(2011,(Double.parseDouble(fields[format.convert("k")])),0,mFP,nmFP,0);
//                	IrrigationUtilizationCommand irrigationUtilizationCommand = new IrrigationUtilizationCommand(cborewell,cdugwell,cdugwellPumpSet,cshallowTubeWell,cmediumTubeWell,cdeepTubeWell,cfilterPoints);
//
//                	//non-command
//            	    irrOperativenon = irrOperativenonT.get("non_command");
//            	    irrOperative = irrOperativeT.get("non_command");
//             	    fillOperativeDaysIrr(format.removeQuotes(fields[format.convert("c")]));
//             	   
//                	Borewell ncborewell;
//                	Dugwell ncdugwell;
//                	DugwellPumpSet ncdugwellPumpSet;
//                	ShallowTubeWell ncshallowTubeWell;
//                	MediumTubeWell ncmediumTubeWell;
//                	DeepTubeWell ncdeepTubeWell;
//                	FilterPoints ncfilterPoints;
//                	ncborewell= new Borewell(2011,(Double.parseDouble(fields[format.convert("o")])),60,mBWs,nmBWs,3);
//                	ncdugwell= new Dugwell(2011,(Double.parseDouble(fields[format.convert("m")])),10,mDW,nmDW,0);
//                	ncdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble(fields[format.convert("n")])),0,mPS,nmPS,0);
//                	ncshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble(fields[format.convert("p")])),0,mSTW,nmSTW,0);
//                	ncmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble(fields[format.convert("q")])),120,mMTW,nmMTW,0);
//                	ncdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble(fields[format.convert("r")])),0,mDTW,nmDTW,0);
//                	ncfilterPoints = new FilterPoints(2011,(Double.parseDouble(fields[format.convert("s")])),0,mFP,nmFP,0);
//                	IrrigationUtilizationNonCommand irrigationUtilizationNonCommand = new IrrigationUtilizationNonCommand(ncborewell,ncdugwell,ncdugwellPumpSet,ncshallowTubeWell,ncmediumTubeWell,ncdeepTubeWell,ncfilterPoints);
//                	
//                	// Poor Quality
//            	    irrOperativenon = irrOperativenonT.get("poor_quality");
//            	    irrOperative = irrOperativeT.get("poor_quality");                	
//             	    fillOperativeDaysIrr(format.removeQuotes(fields[format.convert("c")]));
//             	   
//                	Borewell pqcborewell;
//                	Dugwell pqcdugwell;
//                	DugwellPumpSet pqcdugwellPumpSet;
//                	ShallowTubeWell pqcshallowTubeWell ;
//                	MediumTubeWell pqcmediumTubeWell;
//                	DeepTubeWell pqcdeepTubeWell;
//                	FilterPoints pqcfilterPoints;
//                	if(fields.length==20){
//                		pqcborewell= new Borewell(2011,(Double.parseDouble("0")),60,mBWs,nmBWs,3);
//                	}
//                	else{
//                		pqcborewell= new Borewell(2011,(Double.parseDouble(fields[format.convert("w")])),60,mBWs,nmBWs,0);
//                	}
//                	if(fields.length==20){
//                		pqcdugwell= new Dugwell(2011,(Double.parseDouble("0")),10,mDW,nmDW,0);
//                	}
//                	else{
//                		pqcdugwell= new Dugwell(2011,(Double.parseDouble(fields[format.convert("u")])),10,mDW,nmDW,0);
//                	}
//                	if(fields.length==20){
//                		pqcdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble("0")),0,mPS,nmPS,0);
//                	}
//                	else{
//                		pqcdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble(fields[format.convert("v")])),0,mPS,nmPS,0);
//                	}
//                	if(fields.length==20){
//                		pqcshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble("0")),0,mSTW,nmSTW,0);
//                	}
//                	else{
//                		pqcshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble(fields[format.convert("x")])),0,mSTW,nmSTW,0);
//                	}
//                	if(fields.length==20){
//                		pqcmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble("0")),120,mMTW,nmMTW,0);
//                	}
//                	else{
//                		pqcmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble(fields[format.convert("y")])),120,mMTW,nmMTW,0);
//                	}
//                	if(fields.length==20){
//                		pqcdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble("0")),0,mDTW,nmDTW,0);
//                	}
//                	else{
//                		pqcdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble(fields[format.convert("z")])),0,mDTW,nmDTW,0);
//                	}
//                	if(fields.length==20){
//                		pqcfilterPoints = new FilterPoints(2011,(Double.parseDouble("0")),0,mFP,nmFP,0);
//                	}
//                	else{
//                		pqcfilterPoints = new FilterPoints(2011,(Double.parseDouble(fields[format.convert("aa")])),0,mFP,nmFP,0);
//                	}
//                	IrrigationUtilizationCommandPoorQuality irrigationUtilizationCommandPoorQuality = new IrrigationUtilizationCommandPoorQuality(pqcborewell,pqcdugwell,pqcdugwellPumpSet,pqcshallowTubeWell,pqcmediumTubeWell,pqcdeepTubeWell,pqcfilterPoints);
//                	
//                	
//                	
//                	IrrigationUtilization irrigationUtilization = new IrrigationUtilization(irrigationUtilizationCommand,irrigationUtilizationNonCommand,irrigationUtilizationCommandPoorQuality);
//                	String IrrigationUtilizationjson = IrrigationUtilization.toJson(irrigationUtilization);
//                	if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
//                		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getIrrigationUtilization()==null){
//                        	count++;
//                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setIrrigationUtilization(irrigationUtilization);
//                		}
//                		else{
//                			irrigationUtilization= village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getIrrigationUtilization();
//                			cborewell= new Borewell(2011,(Double.parseDouble(fields[format.convert("g")]))+irrigationUtilization.command.borewell.noOfWells,60,40,80,3);
//                        	cdugwell= new Dugwell(2011,(Double.parseDouble(fields[format.convert("e")]))+irrigationUtilization.command.dugwell.noOfWells,10,40,90,0);
//                        	cdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble(fields[format.convert("f")]))+irrigationUtilization.command.dugwellPumpSet.noOfWells,0,30,50,0);
//                        	cshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble(fields[format.convert("h")]))+irrigationUtilization.command.shallowTubeWell.noOfWells,0,40,90,0);
//                        	cmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble(fields[format.convert("i")]))+irrigationUtilization.command.mediumTubeWell.noOfWells,120,40,90,0);
//                        	cdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble(fields[format.convert("j")]))+irrigationUtilization.command.deepTubeWell.noOfWells,0,40,90,0);
//                        	cfilterPoints = new FilterPoints(2011,(Double.parseDouble(fields[format.convert("k")]))+irrigationUtilization.command.filterPoints.noOfWells,0,40,60,0);
//                        	irrigationUtilizationCommand = new IrrigationUtilizationCommand(cborewell,cdugwell,cdugwellPumpSet,cshallowTubeWell,cmediumTubeWell,cdeepTubeWell,cfilterPoints);
//                        	
//                           	ncborewell= new Borewell(2011,(Double.parseDouble(fields[format.convert("o")]))+irrigationUtilization.non_command.borewell.noOfWells,60,40,80,3);
//                        	ncdugwell= new Dugwell(2011,(Double.parseDouble(fields[format.convert("m")]))+irrigationUtilization.non_command.dugwell.noOfWells,10,40,90,0);
//                        	ncdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble(fields[format.convert("n")]))+irrigationUtilization.non_command.dugwellPumpSet.noOfWells,0,30,50,0);
//                        	ncshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble(fields[format.convert("p")]))+irrigationUtilization.non_command.shallowTubeWell.noOfWells,0,40,90,0);
//                        	ncmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble(fields[format.convert("q")]))+irrigationUtilization.non_command.mediumTubeWell.noOfWells,120,40,90,0);
//                        	ncdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble(fields[format.convert("r")]))+irrigationUtilization.non_command.deepTubeWell.noOfWells,0,40,90,0);
//                        	ncfilterPoints = new FilterPoints(2011,(Double.parseDouble(fields[format.convert("s")]))+irrigationUtilization.non_command.filterPoints.noOfWells,0,40,60,0);
//                        	irrigationUtilizationNonCommand = new IrrigationUtilizationNonCommand(ncborewell,ncdugwell,ncdugwellPumpSet,ncshallowTubeWell,ncmediumTubeWell,ncdeepTubeWell,ncfilterPoints);
//                        	
//                        	
//                        	if(fields.length==20){
//                        		pqcborewell= new Borewell(2011,(Double.parseDouble("0"))+irrigationUtilization.poor_quality.borewell.noOfWells,60,40,80,3);
//                        	}
//                        	else{
//                        		pqcborewell= new Borewell(2011,(Double.parseDouble(fields[format.convert("w")]))+irrigationUtilization.poor_quality.borewell.noOfWells,60,40,80,3);
//                        	}
//                        	if(fields.length==20){
//                        		pqcdugwell= new Dugwell(2011,(Double.parseDouble("0"))+irrigationUtilization.poor_quality.dugwell.noOfWells,10,40,90,0);
//                        	}
//                        	else{
//                        		pqcdugwell= new Dugwell(2011,(Double.parseDouble(fields[format.convert("u")]))+irrigationUtilization.poor_quality.dugwell.noOfWells,10,40,90,0);
//                        	}
//                        	if(fields.length==20){
//                        		pqcdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble("0"))+irrigationUtilization.poor_quality.dugwellPumpSet.noOfWells,0,30,50,0);
//                        	}
//                        	else{
//                        		pqcdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble(fields[format.convert("v")]))+irrigationUtilization.poor_quality.dugwellPumpSet.noOfWells,0,30,50,0);
//                        	}
//                        	if(fields.length==20){
//                        		pqcshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble("0"))+irrigationUtilization.poor_quality.shallowTubeWell.noOfWells,0,40,90,0);
//                        	}
//                        	else{
//                        		pqcshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble(fields[format.convert("x")]))+irrigationUtilization.poor_quality.shallowTubeWell.noOfWells,0,40,90,0);
//                        	}
//                        	if(fields.length==20){
//                        		pqcmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble("0"))+irrigationUtilization.poor_quality.mediumTubeWell.noOfWells,120,40,90,0);
//                        	}
//                        	else{
//                        		pqcmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble(fields[format.convert("y")]))+irrigationUtilization.poor_quality.mediumTubeWell.noOfWells,120,40,90,0);
//                        	}
//                        	if(fields.length==20){
//                        		pqcdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble("0"))+irrigationUtilization.poor_quality.deepTubeWell.noOfWells,0,40,90,0);
//                        	}
//                        	else{
//                        		pqcdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble(fields[format.convert("z")]))+irrigationUtilization.poor_quality.deepTubeWell.noOfWells,0,40,90,0);
//                        	}
//                        	if(fields.length==20){
//                        		pqcfilterPoints = new FilterPoints(2011,(Double.parseDouble("0"))+irrigationUtilization.poor_quality.filterPoints.noOfWells,0,40,60,0);
//                        	}
//                        	else{
//                        		pqcfilterPoints = new FilterPoints(2011,(Double.parseDouble(fields[format.convert("aa")]))+irrigationUtilization.poor_quality.filterPoints.noOfWells,0,40,60,0);
//                        	}
//                        	irrigationUtilizationCommandPoorQuality = new IrrigationUtilizationCommandPoorQuality(pqcborewell,pqcdugwell,pqcdugwellPumpSet,pqcshallowTubeWell,pqcmediumTubeWell,pqcdeepTubeWell,pqcfilterPoints);
//                        	
//                        	irrigationUtilization = new IrrigationUtilization(irrigationUtilizationCommand,irrigationUtilizationNonCommand,irrigationUtilizationCommandPoorQuality);
//                        	IrrigationUtilizationjson = IrrigationUtilization.toJson(irrigationUtilization);
//                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setIrrigationUtilization(irrigationUtilization);
//                		}
//                	}
//                   // System.out.println(IrrigationUtilizationjson);
//                }
//            }
//            System.out.println("irrigation count= "+count);
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        
//        
//        //json for Residential Utilization wc
//        Gson ResidentialUtilization = new Gson();
//        //inserting into Residential Utilization json Object
//        try(BufferedReader iem = new BufferedReader(new FileReader(residentialUtilizationfile))) {
//        
//        	int count =0;
//            record = iem.readLine();
//            record = iem.readLine();
//            record = iem.readLine();
//           
//            while((record = iem.readLine()) != null) {
//                String fields[] = record.split(",");
//
//               if(fields.length==11){
//            	  
//           	   	//System.out.println(mDW);
//           		
//                	//doubt Ward No 70 and Ward No 70 int coloum has #ref
//	           	    resOperative = resOperativeT.get("command");
//	           	    resOperativenon = resOperativenonT.get("command");
//            	   	fillOperativeDaysRes(format.removeQuotes(fields[format.convert("c")]));
//            	    ResidentialUtilizationPws cpws= new ResidentialUtilizationPws(2011,(Integer.parseInt(fields[format.convert("f")])),7,120,mPS,nmPS,0);
//                	ResidentialUtilizationHandpump chp= new ResidentialUtilizationHandpump(2011,(Integer.parseInt(fields[format.convert("e")])),7,mDW,nmDW,240,0);
//                	ResidentialUtilizationCommand residentialUtilizationCommand = new ResidentialUtilizationCommand(cpws,chp);
//
//	           	    resOperative = resOperativeT.get("non_command");
//	           	    resOperativenon = resOperativenonT.get("non_command");
//            	   	fillOperativeDaysRes(format.removeQuotes(fields[format.convert("c")]));
//                	ResidentialUtilizationPws ncpws= new ResidentialUtilizationPws(2011,(Integer.parseInt(fields[format.convert("h")])),7,50,mPS,nmPS,0);
//                	ResidentialUtilizationHandpump ncmpt= new ResidentialUtilizationHandpump(2011,(Integer.parseInt(fields[format.convert("g")])),7,mDW,nmDW,240,0);
//                	ResidentialUtilizationNonCommand residentialUtilizationNonCommand = new ResidentialUtilizationNonCommand(ncpws,ncmpt);
//
//	           	    resOperative = resOperativeT.get("poor_quality");
//	           	    resOperativenon = resOperativenonT.get("poor_quality");
//            	   	fillOperativeDaysRes(format.removeQuotes(fields[format.convert("c")]));
//                	ResidentialUtilizationPws pqpws= new ResidentialUtilizationPws(2011,(Integer.parseInt(fields[format.convert("j")])),7,50,mPS,nmPS,0);
//                	ResidentialUtilizationHandpump pqhp= new ResidentialUtilizationHandpump(2011,(Integer.parseInt(fields[format.convert("i")])),7,mDW,nmDW,240,0);
//                	ResidentialUtilizationPoorQuality residentialUtilizationPoorQuality = new ResidentialUtilizationPoorQuality(pqpws,pqhp);
//
//                	ResidentialUtilization residentialUtilization = new ResidentialUtilization(residentialUtilizationCommand,residentialUtilizationNonCommand,residentialUtilizationPoorQuality);
//                	String ResidentialUtilizationjson = ResidentialUtilization.toJson(residentialUtilization);
//                	if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
//                		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getResidentialUtilization()==null){
//                        	count++;
//                    		village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setResidentialUtilization(residentialUtilization);
//                		}
//                		else{
//                			residentialUtilization= village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getResidentialUtilization();
//                			cpws= new ResidentialUtilizationPws(2011,(Integer.parseInt(fields[format.convert("f")]))+residentialUtilization.command.Pws.noOfWells,7,120,240,90,0);
//                        	chp= new ResidentialUtilizationHandpump(2011,(Integer.parseInt(fields[format.convert("e")]))+residentialUtilization.command.Handpump.noOfWells,7,10,120,240,0);
//                        	residentialUtilizationCommand = new ResidentialUtilizationCommand(cpws,chp);
//                        	
//                        	ncpws= new ResidentialUtilizationPws(2011,(Integer.parseInt(fields[format.convert("h")]))+residentialUtilization.non_command.Pws.noOfWells,7,50,120,240,0);
//                        	ncmpt= new ResidentialUtilizationHandpump(2011,(Integer.parseInt(fields[format.convert("g")]))+residentialUtilization.non_command.Handpump.noOfWells,7,10,120,240,0);
//                        	residentialUtilizationNonCommand = new ResidentialUtilizationNonCommand(ncpws,ncmpt);
//                        	
//                        	pqpws= new ResidentialUtilizationPws(2011,(Integer.parseInt(fields[format.convert("j")]))+residentialUtilization.poor_quality.Pws.noOfWells,7,50,120,240,0);
//                        	pqhp= new ResidentialUtilizationHandpump(2011,(Integer.parseInt(fields[format.convert("i")]))+residentialUtilization.poor_quality.Handpump.noOfWells,7,10,120,240,0);
//                        	residentialUtilizationPoorQuality = new ResidentialUtilizationPoorQuality(pqpws,pqhp);
//
//                        	residentialUtilization = new ResidentialUtilization(residentialUtilizationCommand,residentialUtilizationNonCommand,residentialUtilizationPoorQuality);
//                        	ResidentialUtilizationjson = ResidentialUtilization.toJson(residentialUtilization);
//                    		village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setResidentialUtilization(residentialUtilization);
//
//                		}
//                	}
//                    //System.out.println(count+" "+ResidentialUtilizationjson);
//                	
//                }
//            }
//            System.out.println("residential count= "+count);
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        
//        
//        
//        
//     // json for population
//        Gson populationjsn = new Gson();
//
//        try(BufferedReader iem = new BufferedReader(new FileReader(populationfile))) {
//              
//          	//readXLSXFile();
//              record = iem.readLine();
//             
//              while((record = iem.readLine()) != null) {
//                  //System.out.println("record"+record);
//                  String fields[] = record.split(",");
//                 // System.out.println("fields"+fields);
//                  int lpcd =0;
//                  int totalPopulation=0;
//                  int command = 0;
//                  int noncommand =0;
//                  if(fields.length==9){
//                  	if(!fields[format.convert("i")].isEmpty()){
//                  		lpcd = (int) Double.parseDouble(fields[format.convert("i")]);
//                                        	}
//                  	if(!fields[format.convert("h")].isEmpty()){
//                  		totalPopulation=(int) Double.parseDouble(fields[format.convert("h")]);
//                 	}
//                  	if(!fields[format.convert("f")].isEmpty()){
//                  		command = (int) Double.parseDouble(fields[format.convert("f")]);
//                    }
//                  	if(!fields[format.convert("g")].isEmpty()){
//                  	   	noncommand = (int) Double.parseDouble(fields[format.convert("g")]);
//                   	}
//                  	
//                  	Population populationObj = new Population(lpcd,totalPopulation,command,noncommand);
//                  	String populationJson = populationjsn.toJson(populationObj);
//                  	if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("e")]))){
//                      //	System.out.println("hello");
//                  		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")])))==null){
//                            village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("e")]))).setPopulation(populationJson);
//                		}
//                  		else{
//                  			populationObj= populationjsn.fromJson(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getPopulation(),Population.class);
//                  			lpcd+=populationObj.lpcd;
//                  			totalPopulation+=populationObj.totalPopulation;
//                  			command+=populationObj.command;
//                  			noncommand+=populationObj.non_command;
//                  			populationObj = new Population(lpcd,totalPopulation,command,noncommand);
//                          	populationJson = populationjsn.toJson(populationObj);
//                            village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("e")]))).setPopulation(populationJson);
//                  		}
//                      }
//                      
//                  }
//        }
//              
//              
//              
//          }catch (IOException e) {
//              e.printStackTrace();
//          }
//        
//        //json for village
//        try (BufferedWriter file = new BufferedWriter(new FileWriter("/home/akshay/Desktop/loc_meta_data.cql"))) {
//	        
//        	Gson Village = new Gson();
//	        for(String villageName:village_details.keySet()){
//	        	Village villageObj = village_details.get(villageName);
//	        	Village_Data village_obj=new Village_Data(villageObj);
//	            String villagejson = Village.toJson(village_obj);
//	            
//	                String query = "Insert into location_md JSON '"+villagejson+"';";
//	      //System.out.println(query);
//	
//	            	file.write(query + "\n");
//	                file.newLine();
//	            
//				if(villageName.equalsIgnoreCase("Dabbagaruvu")){
//					System.out.println(villagejson);
//				}
//
//	        }
//	        System.out.println("end");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }    
//
//    }
//	
//	
//	public static void fillOperativeDaysIrr(String basinName) {
//		mBWs=0;
//	   	mDW =0;
//	   	mPS=0;
//	   	mSTW=0;
//	   	mMTW=0;
//	   	mDTW=0;
//	   	mFP=0;
//	   	nmBWs=0;
//	   	nmDW =0;
//	   	nmPS=0;
//	   	nmSTW=0;
//	   	nmMTW=0;
//	   	nmDTW=0;
//	   	nmFP=0;
//	   	
//	   	if(irrOperativenon.containsKey(basinName) && irrOperative.get(basinName).containsKey("BWs")){
//	   		nmBWs=irrOperativenon.get(basinName).get("BWs");            	   		
//	   	}
//	   	if(irrOperative.containsKey(basinName) && irrOperative.get(basinName).containsKey("BWs")){
//	   		mBWs=irrOperative.get(basinName).get("BWs");            	   		
//	   	}
//		if(irrOperativenon.containsKey(basinName) && irrOperative.get(basinName).containsKey("DW")){
//			nmDW=irrOperativenon.get(basinName).get("DW");            	   		
//	   	}
//	   	if(irrOperative.containsKey(basinName) && irrOperative.get(basinName).containsKey("DW")){
//	   		mDW=irrOperative.get(basinName).get("DW");            	   		
//	   	}
//		if(irrOperativenon.containsKey(basinName) && irrOperative.get(basinName).containsKey("DW+PS/DCB")){
//			nmPS=irrOperativenon.get(basinName).get("DW+PS/DCB");            	   		
//	   	}
//	   	if(irrOperative.containsKey(basinName) && irrOperative.get(basinName).containsKey("DW+PS/DCB")){
//	   		mPS=irrOperative.get(basinName).get("DW+PS/DCB");            	   		
//	   	}
//		if(irrOperativenon.containsKey(basinName) && irrOperative.get(basinName).containsKey("S TW")){
//			nmSTW=irrOperativenon.get(basinName).get("S TW");            	   		
//	   	}
//	   	if(irrOperative.containsKey(basinName) && irrOperative.get(basinName).containsKey("S TW")){
//	   		mSTW=irrOperative.get(basinName).get("S TW");            	   		
//	   	}
//		if(irrOperativenon.containsKey(basinName) && irrOperative.get(basinName).containsKey("M TW")){
//			nmMTW=irrOperativenon.get(basinName).get("M TW");            	   		
//	   	}
//	   	if(irrOperative.containsKey(basinName) && irrOperative.get(basinName).containsKey("M TW")){
//	   		mMTW=irrOperative.get(basinName).get("M TW");            	   		
//	   	}
//		if(irrOperativenon.containsKey(basinName) && irrOperative.get(basinName).containsKey("D TW")){
//			nmDTW=irrOperativenon.get(basinName).get("D TW");            	   		
//	   	}
//	   	if(irrOperative.containsKey(basinName) && irrOperative.get(basinName).containsKey("D TW")){
//	   		mDTW=irrOperative.get(basinName).get("D TW");            	   		
//	   	}
//		if(irrOperativenon.containsKey(basinName) && irrOperative.get(basinName).containsKey("Filter Points")){
//			nmFP=irrOperativenon.get(basinName).get("Filter Points");            	   		
//	   	}
//	   	if(irrOperative.containsKey(basinName) && irrOperative.get(basinName).containsKey("Filter Points")){
//	   		mFP=irrOperative.get(basinName).get("Filter Points");            	   		
//	   	}
//	}
//	
//	public static void fillOperativeDaysRes(String basinName) {
//		
//   	   	mDW =0;
//   	   	mPS=0;
//   	   	nmDW =0;
//   	   	nmPS=0;
//   	   	
//		if(resOperativenon.containsKey(basinName) && resOperativenon.get(basinName).containsKey("DW+PS/DCB")){
//			nmPS=resOperativenon.get(basinName).get("DW+PS/DCB");            	   		
//	   	}
//	   	if(resOperative.containsKey(basinName) && resOperative.get(basinName).containsKey("DW+PS/DCB")){
//	   		mPS=resOperative.get(basinName).get("DW+PS/DCB");            	   		
//	   	}
//	   	if(resOperativenon.containsKey(basinName) && resOperativenon.get(basinName).containsKey("BWs")){
//	   		nmBWs=resOperativenon.get(basinName).get("BWs");            	   		
//	   	}
//	   	if(resOperative.containsKey(basinName) && resOperative.get(basinName).containsKey("BWs")){
//	   		mBWs=resOperative.get(basinName).get("BWs");            	   		
//	   	}
//	}
//	
//
//}
//
//
