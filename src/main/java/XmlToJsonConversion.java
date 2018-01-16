
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import com.google.gson.Gson;


public class XmlToJsonConversion {
	public static void main(String k[]) {

        String gwlocToIWMloc = "/home/megha/Downloads/final vsp - Copy/location hirearchy MD/a.Ap admin hirarchy.csv";      // Input File
        String areafile = "/home/megha/Downloads/final vsp - Copy/location hirearchy MD/d.Village  Resource Distribution/Area.csv";
        String cropfile ="/home/megha/Downloads/final vsp - Copy/location hirearchy MD/d.Village  Resource Distribution/Applied Irrigation-f.csv";
        String waterbodiesfile ="/home/megha/Downloads/final vsp - Copy/location hirearchy MD/d.Village  Resource Distribution/MI tanks.csv";
        String canalfile = "/home/megha/Downloads/final vsp - Copy/location hirearchy MD/d.Village  Resource Distribution/Canals.csv";
        String artificialWCfile="/home/megha/Downloads/final vsp - Copy/location hirearchy MD/d.Village  Resource Distribution/Wc structures.csv";
        String irrigationUtilizationfile ="/home/megha/Downloads/final vsp - Copy/location hirearchy MD/d.Village  Resource Distribution/Borewell irrigated.csv";
        String residentialUtilizationfile="/home/megha/Downloads/final vsp - Copy/location hirearchy MD/d.Village  Resource Distribution/Borewell residential.csv";
        String villageIdfile = "/home/megha/Downloads/iwm_village_name_id_map.csv";
        String basinAssociation ="/home/megha/Downloads/final vsp - Copy/location hirearchy MD/b.Microbasin_village _mapping.csv";
        String villageAssociation ="/home/megha/Downloads/final vsp - Copy/location hirearchy MD/c.Village mapping area ap.csv";
        String basinIdfile ="/home/megha/Downloads/iwm_basin_name_id_map.csv";
        String populationfile="/home/megha/Downloads/final vsp - Copy/Drafts/b. population lpcd.csv";
        String record = "";
        HashMap<String,String> basinmapping = new HashMap<>();
        HashMap<String,String> locmapping = new HashMap<>();
        HashMap<String,Double> Village_area = new HashMap<>();
        HashMap<String,Integer> Basin_Id = new HashMap<>();
        JSONObject infiltrationRate = new JSONObject(); 
        infiltrationRate.put("infiltrationRate", new Integer(1));
        JSONObject specificYield = new JSONObject(); 
        specificYield.put("specificYield", new Integer(1));
        JSONObject transmissivity = new JSONObject(); 
        transmissivity.put("transmissivity", new Integer(1));
        JSONObject storageCoefficient = new JSONObject(); 
        storageCoefficient.put("storageCoefficient", new Integer(1));
        Village village = new Village();
        HashMap<String,Village> village_details=new HashMap<>();
       
        //loc mapping
        try(BufferedReader iem = new BufferedReader(new FileReader(gwlocToIWMloc))) {
            
        	//readXLSXFile();
            record = iem.readLine();
            while((record = iem.readLine()) != null) {
                //System.out.println("record"+record);

                String fields[] = record.split(",");
               // System.out.println("fields"+fields);
                if(fields.length==4){
                	String MicroBasinName;
                	String GWvillageName;
                	String IWMvillageName;
                	if(!format.removeQuotes(fields[format.convert("a")]).isEmpty() && !format.removeQuotes(fields[format.convert("c")]).isEmpty() && !format.removeQuotes(fields[format.convert("d")]).isEmpty()&& !format.removeQuotes(fields[format.convert("d")]).equalsIgnoreCase("#N/A")){
                    	MicroBasinName = format.removeQuotes(fields[format.convert("a")]);
                    	String IWMMicroBasinName=format.removeQuotes(fields[format.convert("b")]);
                		GWvillageName = format.removeQuotes(fields[format.convert("c")]);
                        IWMvillageName = format.removeQuotes(fields[format.convert("d")]);
                        String MicroBasin_GWvillage_key = MicroBasinName + "##" +GWvillageName;
                        //if(MicroBasin_GWvillage_key.equalsIgnoreCase("VSP_14_NC_SARADA-1_ANANTHAGIRI##Chittamvalasa")){
                        	//System.out.println(MicroBasin_GWvillage_key + " "+IWMvillageName);
                        //}
                        locmapping.put(MicroBasin_GWvillage_key,IWMvillageName);
                        basinmapping.put(IWMMicroBasinName,MicroBasinName);
                	}
                	
                }
               
                //System.out.println("**"+MicroBasin_GWvillage_key);
                
               
            }
            int c1=0;
            for(String key:locmapping.keySet()){
            	c1++;
            	village_details.put(locmapping.get(key),new Village());
            }
           // System.out.println("b4 naming"+c1);
            for(String key:village_details.keySet()){
            	
            		village_details.get(key).setVillageName(key);
            	
            }
            int c=0;
            for(String vill:village_details.keySet()){
            	c++;
            //	System.out.println(vill);
            }
            System.out.println("village name count"+c1);
            
        }catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject json = new JSONObject(locmapping);
        System.out.println("location "+locmapping.size());
        
        //village area----
        try(BufferedReader iem = new BufferedReader(new FileReader(villageAssociation))) {
            
        
            record = iem.readLine();
            while((record = iem.readLine()) != null) {
                //System.out.println("record"+record);

                String fields[] = record.split(",");
               // System.out.println("fields"+fields);
                if(fields.length==2){
                	String VillageName = format.removeQuotes(fields[format.convert("a")]);
                    Double VillageArea =Double.parseDouble(format.removeQuotes(fields[format.convert("b")]));
                    Village_area.put(VillageName, VillageArea);
                }
               
                //System.out.println("**"+MicroBasin_GWvillage_key);
  
            }
           
            
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        //Basin Id----
        try(BufferedReader iem = new BufferedReader(new FileReader(basinIdfile))) {
            
        
            record = iem.readLine();
            while((record = iem.readLine()) != null) {
                //System.out.println("record"+record);

                String fields[] = record.split(",");
               // System.out.println("fields"+fields.length);
                if(fields.length==3){
                	String BasinName = format.removeQuotes(fields[format.convert("b")]);
                    int BasinCode =Integer.parseInt(format.removeQuotes(fields[format.convert("c")]));
                    Basin_Id.put(BasinName, BasinCode);
                }
               
                //System.out.println("**"+MicroBasin_GWvillage_key);
                
               
            }
           
            
        }catch (IOException e) {
            e.printStackTrace();
        }
        
//village ID-----
        try(BufferedReader iem = new BufferedReader(new FileReader(villageIdfile))) {
            
        	//readXLSXFile();
            record = iem.readLine();
            while((record = iem.readLine()) != null) {
                //System.out.println("record"+record);

                String fields[] = record.split(",");
               // System.out.println("fields"+fields);
                if(fields.length==2){
                	int VillageId = Integer.parseInt(fields[1]);
                    String IWMvillageName = format.removeQuotes(fields[0]);
                    if(village_details.get(IWMvillageName)!=null){
                    	village_details.get(IWMvillageName).setVillageId(VillageId);
                	}
                }
      }
            int c=0;
            for(String vill:village_details.keySet()){
            	c++;
            	//System.out.println(village_details.get(vill));
            }
            System.out.println("village id count"+c);
            
            
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        //json for area
        Gson Area = new Gson();
        JSONObject jsn = new JSONObject();
        //inserting into area json Object
        try(BufferedReader iem = new BufferedReader(new FileReader(areafile))) {
            
        	int count =0;
        	int c2=0;
            record = iem.readLine();
            record = iem.readLine();
            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",");
               
                if(fields.length==25 || fields.length==23||fields.length==24){
                	double total = 0;
                	double command = 0;
                	double nonCommand = 0;
                	double poorQuality = 0;
                	
	                if(!fields[4].isEmpty()){
	                	total=Double.parseDouble(fields[4]);
	                }
	                if(!fields[5].isEmpty()){
	                	command = Double.parseDouble(fields[5]);
	                }
	                if(!fields[9].isEmpty()){
	                    nonCommand=Double.parseDouble(fields[9]);
	                }
	                if(!fields[13].isEmpty()){
	                	poorQuality=Double.parseDouble(fields[13]);
	                }
                	RechargeWorthy rw = new RechargeWorthy(command,nonCommand,poorQuality);
                	double hilly=0;
                	double forest =0;
                	if(!fields[6].isEmpty()||!fields[10].isEmpty()||!fields[14].isEmpty()){
	                	hilly= Double.parseDouble(fields[6])+Double.parseDouble(fields[10])+Double.parseDouble(fields[14]);
	                }
                	if(!fields[7].isEmpty()||!fields[11].isEmpty()||!fields[15].isEmpty()){
	                	forest= Double.parseDouble(fields[7])+Double.parseDouble(fields[11])+Double.parseDouble(fields[15]);
	                }
                	NonRechargeWorthy nrw = new NonRechargeWorthy(hilly,forest);
                	
                	Area area = new Area(total,rw,nrw);
                	String areajson = Area.toJson(area);
                	//areajson = areajson.replaceAll("/"", "/"");
                	
                	//JSONObject jsn = new JSONObject(areajson.getBytes());
                	//System.out.println(areajson);
                	
                	if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
                    	
                		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getArea()==null){
                			count++;
                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setArea(areajson);
                			
                		}
                		else{
                			Area areaobj=Area.fromJson(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getArea(), Area.class);
                			total+=areaobj.total;
                			command+=areaobj.rechargeWorthy.command;
                   			nonCommand+=areaobj.rechargeWorthy.non_command;
                			poorQuality+=areaobj.rechargeWorthy.poor_quality;
                			RechargeWorthy rwObj = new RechargeWorthy(command,nonCommand,poorQuality);
                			hilly+=areaobj.nonRechargeWorthy.hilly;
                			forest+=areaobj.nonRechargeWorthy.forest;
                        	NonRechargeWorthy nrwobj = new NonRechargeWorthy(hilly,forest);
                        	Area areaObj = new Area(total,rwObj,nrwobj);  
                        	String areaObjjson = Area.toJson(areaObj);
                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setArea(areaObjjson);
                			
                		}
                	}
                	//System.out.println("#############");
                	//break;
                }

            }
        
            System.out.println("area count= "+count);
            
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        // json for basinAssociation
        try(BufferedReader iem = new BufferedReader(new FileReader(basinAssociation))) {
              
          	//readXLSXFile();
              record = iem.readLine();
             
              while((record = iem.readLine()) != null) {
                  //System.out.println("record"+record);

                  String fields[] = record.split(",");
                 // System.out.println("fields"+fields);
                  if(fields.length==5){
                  	int BasinCode;
                  	double Villagearea;
                  	String BasinName = fields[format.convert("b")];
                      String IWMvillageName = fields[format.convert("c")];
                      double fractionArea = Double.parseDouble(fields[format.convert("d")]);
                      if(locmapping.containsValue(IWMvillageName)){
                      	BasinCode = Basin_Id.get(fields[format.convert("b")]);
                      	Villagearea = Village_area.get(IWMvillageName);
                      	
                          if(village_details.get(IWMvillageName).getBasinAssociation()==null){
                          	HashMap<Integer,Double> basin_assoc = new HashMap<>();
                          	village_details.get(IWMvillageName).setBasinAssociation(basin_assoc);
                          	
                          }
                          village_details.get(IWMvillageName).getBasinAssociation().put(BasinCode,((fractionArea/Villagearea)*100));
                      }
                      
                  }
        }
              int c=0;
              for(String vill:village_details.keySet()){
              	c++;
              	//System.out.println(village_details.get(vill));
              }
              System.out.println("village assoc count"+c);
              
              
          }catch (IOException e) {
              e.printStackTrace();
          }

      //json for crop
        Gson crop = new Gson();
        //inserting into crop json Object
        try(BufferedReader iem = new BufferedReader(new FileReader(cropfile))) {
        	int count =0;
            record = iem.readLine();
            record = iem.readLine();
            record = iem.readLine();
            record = iem.readLine();
            record = iem.readLine();
            record = iem.readLine();
            record = iem.readLine();
            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",");
                if(fields.length==40){
                	Double cpaddyarea = Double.parseDouble(fields[28])+Double.parseDouble(fields[16])+Double.parseDouble(fields[4])+Double.parseDouble(fields[29])+Double.parseDouble(fields[17])+Double.parseDouble(fields[5]);
                	Kharif cpk= new Kharif((Double.parseDouble(fields[28])),(Double.parseDouble(fields[16])),(Double.parseDouble(fields[4])));
                	Rabi cpr= new Rabi((Double.parseDouble(fields[29])),(Double.parseDouble(fields[17])),(Double.parseDouble(fields[5])));
                	Paddy cp= new Paddy(cpaddyarea,cpk,cpr);
                	Double cnonpaddyarea = Double.parseDouble(fields[30])+Double.parseDouble(fields[18])+Double.parseDouble(fields[6])+Double.parseDouble(fields[31])+Double.parseDouble(fields[19])+Double.parseDouble(fields[7]);
                	Kharif cnpk= new Kharif((Double.parseDouble(fields[30])),(Double.parseDouble(fields[18])),(Double.parseDouble(fields[6])));
                	Rabi cnpr= new Rabi((Double.parseDouble(fields[31])),(Double.parseDouble(fields[19])),(Double.parseDouble(fields[7])));
                	NonPaddy cnp= new NonPaddy(cnonpaddyarea,cnpk,cnpr);
                	CropCommand cropCommand = new CropCommand(cp,cnp); 
 
                	Double cnpaddyarea = Double.parseDouble(fields[32])+Double.parseDouble(fields[10])+Double.parseDouble(fields[8])+Double.parseDouble(fields[33])+Double.parseDouble(fields[21])+Double.parseDouble(fields[9]);
                	Double cnnonpaddyarea = Double.parseDouble(fields[34])+Double.parseDouble(fields[22])+Double.parseDouble(fields[10])+Double.parseDouble(fields[35])+Double.parseDouble(fields[23])+Double.parseDouble(fields[11]);
                	Kharif ncpk= new Kharif((Double.parseDouble(fields[32])),(Double.parseDouble(fields[20])),(Double.parseDouble(fields[8])));
                	Rabi ncpr= new Rabi((Double.parseDouble(fields[33])),(Double.parseDouble(fields[21])),(Double.parseDouble(fields[9])));
                	Paddy ncp= new Paddy(cnpaddyarea,ncpk,ncpr);
                	Kharif ncnpk= new Kharif((Double.parseDouble(fields[34])),(Double.parseDouble(fields[22])),(Double.parseDouble(fields[10])));
                	Rabi ncnpr= new Rabi((Double.parseDouble(fields[35])),(Double.parseDouble(fields[23])),(Double.parseDouble(fields[11])));
                	NonPaddy ncnp= new NonPaddy(cnnonpaddyarea,ncnpk,ncnpr);
                	CropNonCommand cropNonCommand = new CropNonCommand(ncp,ncnp); 
                	
                  	Double commandPoorQualitypaddyarea = Double.parseDouble(fields[36])+Double.parseDouble(fields[24])+Double.parseDouble(fields[12])+Double.parseDouble(fields[37])+Double.parseDouble(fields[25])+Double.parseDouble(fields[13]);
                    Double commandPoorQualitynonpaddyarea = Double.parseDouble(fields[38])+Double.parseDouble(fields[26])+Double.parseDouble(fields[14])+Double.parseDouble(fields[39])+Double.parseDouble(fields[27])+Double.parseDouble(fields[15]);
                    Kharif pqpk= new Kharif((Double.parseDouble(fields[36])),(Double.parseDouble(fields[24])),(Double.parseDouble(fields[12])));
                	Rabi pqpr= new Rabi((Double.parseDouble(fields[37])),(Double.parseDouble(fields[25])),(Double.parseDouble(fields[13])));
                	Paddy pqp= new Paddy(commandPoorQualitypaddyarea,pqpk,pqpr);
                	Kharif pqnpk= new Kharif((Double.parseDouble(fields[38])),(Double.parseDouble(fields[26])),(Double.parseDouble(fields[14])));
                	Rabi pqnpr= new Rabi((Double.parseDouble(fields[39])),(Double.parseDouble(fields[27])),(Double.parseDouble(fields[15])));
                	NonPaddy pqnp= new NonPaddy(commandPoorQualitynonpaddyarea,pqnpk,pqnpr);
                	CropPoorQuality cropPoorQuality = new CropPoorQuality(pqp,pqnp); 
                	
                	Crop cropObj = new Crop(cropCommand,cropNonCommand,cropPoorQuality);
                	String cropjson = crop.toJson(cropObj);
                	
                	if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
                		//c2++;
                		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getCrop()==null){
                        	count++;
                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setCrop(cropjson);
                		}
                		else{
                			Crop JCrop= crop.fromJson(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getCrop(), Crop.class);
                			cpaddyarea+=JCrop.command.paddy.cropArea;
                        	cpk= new Kharif((Double.parseDouble(fields[28]))+JCrop.command.paddy.kharif.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[16]))+JCrop.command.paddy.kharif.areaIrrigatedByMITank,(Double.parseDouble(fields[4]))+JCrop.command.paddy.kharif.areaIrrigatedByGroundWater);
                        	cpr= new Rabi((Double.parseDouble(fields[29]))+JCrop.command.paddy.rabi.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[17]))+JCrop.command.paddy.rabi.areaIrrigatedByMITank,(Double.parseDouble(fields[5]))+JCrop.command.paddy.rabi.areaIrrigatedByGroundWater);
                        	cp= new Paddy(cpaddyarea,cpk,cpr);
                        	cnonpaddyarea+= JCrop.command.nonPaddy.cropArea;
                        	cnpk= new Kharif((Double.parseDouble(fields[30]))+JCrop.command.nonPaddy.kharif.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[18]))+JCrop.command.nonPaddy.kharif.areaIrrigatedByMITank,(Double.parseDouble(fields[6]))+JCrop.nonCommand.nonPaddy.kharif.areaIrrigatedByGroundWater);
                        	cnpr= new Rabi((Double.parseDouble(fields[31]))+JCrop.command.nonPaddy.rabi.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[19]))+JCrop.command.nonPaddy.rabi.areaIrrigatedByMITank,(Double.parseDouble(fields[7]))+JCrop.command.nonPaddy.rabi.areaIrrigatedByGroundWater);
                        	cnp= new NonPaddy(cnonpaddyarea,cnpk,cnpr);
                        	cropCommand = new CropCommand(cp,cnp); 
         
                        	cnpaddyarea += JCrop.nonCommand.paddy.cropArea;
                        	cnnonpaddyarea +=JCrop.nonCommand.nonPaddy.cropArea;
                        	ncpk= new Kharif((Double.parseDouble(fields[32]))+JCrop.nonCommand.paddy.kharif.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[20]))+JCrop.nonCommand.paddy.kharif.areaIrrigatedByMITank,(Double.parseDouble(fields[8]))+JCrop.nonCommand.paddy.kharif.areaIrrigatedByGroundWater);
                        	ncpr= new Rabi((Double.parseDouble(fields[33]))+JCrop.nonCommand.paddy.rabi.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[21]))+JCrop.nonCommand.paddy.rabi.areaIrrigatedByMITank,(Double.parseDouble(fields[9]))+JCrop.nonCommand.paddy.rabi.areaIrrigatedByGroundWater);
                        	ncp= new Paddy(cnpaddyarea,ncpk,ncpr);
                        	ncnpk= new Kharif((Double.parseDouble(fields[34])),(Double.parseDouble(fields[22])),(Double.parseDouble(fields[10])));
                        	ncnpr= new Rabi((Double.parseDouble(fields[35])),(Double.parseDouble(fields[23])),(Double.parseDouble(fields[11])));
                        	ncnp= new NonPaddy(cnnonpaddyarea,ncnpk,ncnpr);
                        	cropNonCommand = new CropNonCommand(ncp,ncnp); 
                        	
                          	commandPoorQualitypaddyarea += JCrop.poorQuality.paddy.cropArea;
                            commandPoorQualitynonpaddyarea += JCrop.poorQuality.nonPaddy.cropArea;
                            pqpk= new Kharif((Double.parseDouble(fields[36]))+JCrop.poorQuality.paddy.kharif.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[24]))+JCrop.poorQuality.paddy.kharif.areaIrrigatedByMITank,(Double.parseDouble(fields[12]))+JCrop.poorQuality.paddy.kharif.areaIrrigatedByGroundWater);
                        	pqpr= new Rabi((Double.parseDouble(fields[37]))+JCrop.poorQuality.paddy.rabi.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[25]))+JCrop.poorQuality.paddy.rabi.areaIrrigatedByMITank,(Double.parseDouble(fields[13]))+JCrop.poorQuality.paddy.rabi.areaIrrigatedByGroundWater);
                        	pqp= new Paddy(commandPoorQualitypaddyarea,pqpk,pqpr);
                        	pqnpk= new Kharif((Double.parseDouble(fields[38]))+JCrop.poorQuality.nonPaddy.kharif.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[26]))+JCrop.poorQuality.nonPaddy.kharif.areaIrrigatedByMITank,(Double.parseDouble(fields[14]))+JCrop.poorQuality.nonPaddy.kharif.areaIrrigatedByGroundWater);
                        	pqnpr= new Rabi((Double.parseDouble(fields[39]))+JCrop.poorQuality.nonPaddy.rabi.areaIrrigatedBySurfaceWater,(Double.parseDouble(fields[27]))+JCrop.poorQuality.nonPaddy.rabi.areaIrrigatedByMITank,(Double.parseDouble(fields[15]))+JCrop.poorQuality.nonPaddy.rabi.areaIrrigatedByGroundWater);
                        	pqnp= new NonPaddy(commandPoorQualitynonpaddyarea,pqnpk,pqnpr);
                        	cropPoorQuality = new CropPoorQuality(pqp,pqnp); 
                        	
                        	cropObj = new Crop(cropCommand,cropNonCommand,cropPoorQuality);
                        	cropjson = crop.toJson(cropObj);
                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setCrop(cropjson);

                		}
                	}
                	//System.out.println(cropjson);
                }
            }
            System.out.println("crop= "+count);
        }catch (IOException e) {
            e.printStackTrace();
        }
        
    
        
        
        
        //json for water bodies
        Gson waterbody = new Gson();
        //inserting into waterbodies json Object
        try(BufferedReader iem = new BufferedReader(new FileReader(waterbodiesfile))) {
        	int count =0;
            record = iem.readLine();
            record = iem.readLine();
            record = iem.readLine();

            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",");
                if(fields.length==10){
                	Mitank cmitank= new Mitank((Double.parseDouble(fields[4])),(Double.parseDouble(fields[5])*0.6),(Double.parseDouble(fields[5])*0.6),0,120,150,0.00144);
                	Command wbcommand = new Command(cmitank);
                	Mitank ncmitank= new Mitank((Double.parseDouble(fields[6])),(Double.parseDouble(fields[7])*0.6),(Double.parseDouble(fields[7])*0.6),0,120,150,0.00144);
                	NonCommand nonCommand = new NonCommand(ncmitank);
                	Mitank cpqmitank= new Mitank((Double.parseDouble(fields[8])),(Double.parseDouble(fields[9])*0.6),(Double.parseDouble(fields[9])*0.6),0,120,150,0.00144);
                	CommandPoorQuality commandpoorQuality = new CommandPoorQuality(cpqmitank);
                    waterbodies waterbd = new waterbodies(wbcommand,nonCommand,commandpoorQuality);
                    String waterbodyjson = waterbody.toJson(waterbd);
                    if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
                    	if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getWaterbodies()==null){
                        	count++;
                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setWaterbodies(waterbd);
                		}
                    	else{
                    		waterbd= village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getWaterbodies();
                    		cmitank= new Mitank((Double.parseDouble(fields[4]))+waterbd.command.mitank.count,((Double.parseDouble(fields[5])/*+waterbd.command.mitank.spreadArea_monsoon*/)*0.6),((Double.parseDouble(fields[5])/*+waterbd.command.mitank.spreadArea_nonmonsoon*/)*0.6),0,120,150,0.00144);
                        	wbcommand = new Command(cmitank);
                        	ncmitank= new Mitank((Double.parseDouble(fields[6]))+waterbd.non_command.mitank.count,((Double.parseDouble(fields[7])/*+waterbd.nonCommand.mitank.spreadArea_monsoon*/)*0.6),((Double.parseDouble(fields[7]/*+waterbd.nonCommand.mitank.spreadArea_nonmonsoon*/))*0.6),0,120,150,0.00144);
                        	nonCommand = new NonCommand(ncmitank);
                        	cpqmitank= new Mitank((Double.parseDouble(fields[8]))+waterbd.poor_quality.mitank.count,((Double.parseDouble(fields[9])/*+waterbd.commandPoorQuality.mitank.spreadArea_monsoon*/)*0.6),((Double.parseDouble(fields[9])/*+waterbd.commandPoorQuality.mitank.spreadArea_nonmonsoon*/)*0.6),0,120,150,0.00144);
                        	commandpoorQuality = new CommandPoorQuality(cpqmitank);
                            waterbd = new waterbodies(wbcommand,nonCommand,commandpoorQuality);
                            waterbodyjson = waterbody.toJson(waterbd);
                   			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setWaterbodies(waterbd);

                    	}
                	}
                   // System.out.println(waterbodyjson);
                }
            }
            System.out.println(" water body count= "+count);
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        
        //inserting into canal json Object
        try(BufferedReader iem = new BufferedReader(new FileReader(canalfile))) {
            
        	int count =0;
            record = iem.readLine();
            record = iem.readLine();

            while((record = iem.readLine()) != null) {
            	ArrayList<Map<String,Object>> canal = new ArrayList<>();
                //json for Canal
                HashMap<String,Object> canalMap = new HashMap<>();
            	count++;
            	
                String fields[] = record.split(",");
                if(fields.length==16){
                	Map<String,Integer> runningDays = new HashMap<>();
                	canalMap.put("length", Double.parseDouble(fields[4]));
                	canalMap.put("type","0");
                    canalMap.put("sideSlopes",Double.parseDouble(fields[7]));//sideSlopes
                    canalMap.put("wettedPerimeter",Double.parseDouble(fields[8]));//wettedperimeter
                    canalMap.put("wettedArea",Double.parseDouble(fields[9]));//wettedarea
                    canalMap.put("seepageFactor",Double.parseDouble(fields[10]));//canalseepagefactor
                    canalMap.put("bedWidth",Double.parseDouble(fields[6]));//bedwidth
                    canalMap.put("designDepthFlow",Double.parseDouble("0"));//designDepthOfFlow
                    runningDays.put("runningDays_monsoon",Integer.parseInt(fields[11]));//runningDays_monsoon
                    runningDays.put("runningDays_nonmonsoon",Integer.parseInt(fields[12]));//runningDays_nonmonsoon
                    canalMap.put("noOfRunningDays", runningDays);
                    canal.add(canalMap);
                    if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
                		//#TODO write code	
                    	village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setCanal(canal);
                	}
                   // System.out.println(canal);
                }
        	}
            System.out.println("canal count= "+count);
            
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        //json for artificial wc
        Gson artificialwc = new Gson();
        //inserting into artificial wc json Object
        try(BufferedReader iem = new BufferedReader(new FileReader(artificialWCfile))) {
        	int count =0;
            record = iem.readLine();
            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",");
                if(fields.length==64){

                	PercolationTanks cpt= new PercolationTanks((Double.parseDouble(fields[4])),(Double.parseDouble(fields[5])),(Double.parseDouble("1.5")),40);
                	MiniPercolationTanks cmpt= new MiniPercolationTanks((Double.parseDouble(fields[8])),(Double.parseDouble(fields[9])),(Double.parseDouble("1.5")),40);
                	CheckDams ccd= new CheckDams((Double.parseDouble(fields[12])),(Double.parseDouble(fields[13])),(Double.parseDouble("6")),40);
                	FarmPonds fp = new FarmPonds((Double.parseDouble(fields[format.convert("q")])),(Double.parseDouble(fields[format.convert("r")])),(Double.parseDouble("22")),40);
                	Other other = new Other((Double.parseDouble(fields[format.convert("u")])),(Double.parseDouble(fields[format.convert("v")])),(Double.parseDouble("10")),40);
                	ArtificialWCCommand artificialWCCommand = new ArtificialWCCommand(cpt,cmpt,ccd,fp,other);
                	
                	PercolationTanks ncpt= new PercolationTanks((Double.parseDouble(fields[24])),(Double.parseDouble(fields[25])),(Double.parseDouble("1.5")),40);
                	MiniPercolationTanks ncmpt= new MiniPercolationTanks((Double.parseDouble(fields[28])),(Double.parseDouble(fields[29])),(Double.parseDouble("1.5")),40);
                	CheckDams nccd= new CheckDams((Double.parseDouble(fields[32])),(Double.parseDouble(fields[33])),(Double.parseDouble("6")),40);
                	FarmPonds ncfp = new FarmPonds((Double.parseDouble(fields[format.convert("ak")])),(Double.parseDouble(fields[format.convert("al")])),(Double.parseDouble("22")),40);
                	Other ncother = new Other((Double.parseDouble(fields[format.convert("ao")])),(Double.parseDouble(fields[format.convert("ap")])),(Double.parseDouble("10")),40);
                	ArtificialWCNonCommand artificialWCNonCommand = new ArtificialWCNonCommand(ncpt,ncmpt,nccd,ncfp,ncother);
                	
                	PercolationTanks pqpt= new PercolationTanks((Double.parseDouble(fields[format.convert("as")])),(Double.parseDouble(fields[format.convert("at")])),(Double.parseDouble("1.5")),40);
                	MiniPercolationTanks pqmpt= new MiniPercolationTanks((Double.parseDouble(fields[format.convert("aw")])),(Double.parseDouble(fields[format.convert("ax")])),(Double.parseDouble("1.5")),40);
                	CheckDams pqcd= new CheckDams((Double.parseDouble(fields[format.convert("ba")])),(Double.parseDouble(fields[format.convert("bb")])),(Double.parseDouble("6")),40);
                	FarmPonds pqfp = new FarmPonds((Double.parseDouble(fields[format.convert("be")])),(Double.parseDouble(fields[format.convert("bf")])),(Double.parseDouble("22")),40);
                	Other pqother = new Other((Double.parseDouble(fields[format.convert("bi")])),(Double.parseDouble(fields[format.convert("bj")])),(Double.parseDouble("10")),40);
                	ArtificialWCPPoorQuality artificialWCPPoorQuality = new ArtificialWCPPoorQuality(pqpt,pqmpt,pqcd,pqfp,pqother);

                	ArtificialWC artificialWC = new ArtificialWC(artificialWCCommand,artificialWCNonCommand,artificialWCPPoorQuality);
                	String artificialwcjson = artificialwc.toJson(artificialWC);
                	if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
                		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getArtificialWC()==null){
                        	count++;
                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setArtificialWC(artificialWC);
                		}else{
                			artificialWC= village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getArtificialWC();
                			cpt= new PercolationTanks((Double.parseDouble(fields[4]))+artificialWC.command.pt.count,(Double.parseDouble(fields[5]))+artificialWC.command.pt.capacity,(Double.parseDouble("1.5")),40);
                        	cmpt= new MiniPercolationTanks((Double.parseDouble(fields[8]))+artificialWC.command.mpt.count,(Double.parseDouble(fields[9]))+artificialWC.command.mpt.capacity,(Double.parseDouble("1.5")),40);
                        	ccd= new CheckDams((Double.parseDouble(fields[12]))+artificialWC.command.cd.count,(Double.parseDouble(fields[13]))+artificialWC.command.cd.capacity,(Double.parseDouble("6")),40);
                        	fp = new FarmPonds((Double.parseDouble(fields[format.convert("q")]))+artificialWC.command.fp.count,(Double.parseDouble(fields[format.convert("r")]))+artificialWC.command.fp.capacity,(Double.parseDouble("22")),40);
                        	other = new Other((Double.parseDouble(fields[format.convert("u")]))+artificialWC.command.others.count,(Double.parseDouble(fields[format.convert("v")]))+artificialWC.command.others.capacity,(Double.parseDouble("10")),40);
                        	artificialWCCommand = new ArtificialWCCommand(cpt,cmpt,ccd,fp,other);
                        	
                        	ncpt= new PercolationTanks((Double.parseDouble(fields[24]))+artificialWC.non_command.pt.count,(Double.parseDouble(fields[25]))+artificialWC.non_command.pt.capacity,(Double.parseDouble("1.5")),40);
                        	ncmpt= new MiniPercolationTanks((Double.parseDouble(fields[28]))+artificialWC.non_command.mpt.count,(Double.parseDouble(fields[29]))+artificialWC.non_command.mpt.capacity,(Double.parseDouble("1.5")),40);
                        	nccd= new CheckDams((Double.parseDouble(fields[32]))+artificialWC.non_command.cd.count,(Double.parseDouble(fields[33]))+artificialWC.non_command.cd.capacity,(Double.parseDouble("6")),40);
                        	ncfp = new FarmPonds((Double.parseDouble(fields[format.convert("ak")]))+artificialWC.non_command.fp.count,(Double.parseDouble(fields[format.convert("al")]))+artificialWC.non_command.fp.capacity,(Double.parseDouble("22")),40);
                        	ncother = new Other((Double.parseDouble(fields[format.convert("ao")]))+artificialWC.non_command.others.count,(Double.parseDouble(fields[format.convert("ap")]))+artificialWC.non_command.others.capacity,(Double.parseDouble("10")),40);
                        	artificialWCNonCommand = new ArtificialWCNonCommand(ncpt,ncmpt,nccd,ncfp,ncother);
                        	
                        	pqpt= new PercolationTanks((Double.parseDouble(fields[format.convert("as")])),(Double.parseDouble(fields[format.convert("at")])),(Double.parseDouble("1.5")),40);
                        	pqmpt= new MiniPercolationTanks((Double.parseDouble(fields[format.convert("aw")])),(Double.parseDouble(fields[format.convert("ax")])),(Double.parseDouble("1.5")),40);
                        	pqcd= new CheckDams((Double.parseDouble(fields[format.convert("ba")])),(Double.parseDouble(fields[format.convert("bb")])),(Double.parseDouble("6")),40);
                        	pqfp = new FarmPonds((Double.parseDouble(fields[format.convert("be")])),(Double.parseDouble(fields[format.convert("bf")])),(Double.parseDouble("22")),40);
                        	pqother = new Other((Double.parseDouble(fields[format.convert("bi")])),(Double.parseDouble(fields[format.convert("bj")])),(Double.parseDouble("10")),40);
                        	artificialWCPPoorQuality = new ArtificialWCPPoorQuality(pqpt,pqmpt,pqcd,pqfp,pqother);

                        	artificialWC = new ArtificialWC(artificialWCCommand,artificialWCNonCommand,artificialWCPPoorQuality);
                        	artificialwcjson = artificialwc.toJson(artificialWC);
                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setArtificialWC(artificialWC);

                		}
                	}
                   // System.out.println(artificialwcjson);
                }
            }
            System.out.println("artificial wc count= "+count);
        }catch (IOException e) {
            e.printStackTrace();
        }
     
        //json for Irrigation Utilization wc
        Gson IrrigationUtilization = new Gson();
        //inserting into Irrigation Utilization json Object
        try(BufferedReader iem = new BufferedReader(new FileReader(irrigationUtilizationfile))) {
        	int count =0;
            record = iem.readLine();
            record = iem.readLine();
            record = iem.readLine();
           
            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",");
                /*if(fields.length!=20 && fields.length!=28){
                	System.out.println("fields"+fields.length);
                	for(String f:fields){
                		System.out.print(f);
                		
                	}
                	System.out.println();
                }*/

               if(fields.length==20||fields.length==28){
                	//doubt growth rate
                	Borewell cborewell;
                	Dugwell cdugwell;
                	DugwellPumpSet cdugwellPumpSet;
                	ShallowTubeWell cshallowTubeWell;
                	MediumTubeWell cmediumTubeWell;
                	DeepTubeWell cdeepTubeWell;
                	FilterPoints cfilterPoints;
                	cborewell= new Borewell(2011,(Double.parseDouble(fields[format.convert("g")])),60,40,80,0);
                	cdugwell= new Dugwell(2011,(Double.parseDouble(fields[format.convert("e")])),10,40,90,0);
                	cdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble(fields[format.convert("f")])),0,30,50,0);
                	cshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble(fields[format.convert("h")])),0,40,90,0);
                	cmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble(fields[format.convert("i")])),120,40,90,0);
                	cdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble(fields[format.convert("j")])),0,40,90,0);
                	cfilterPoints = new FilterPoints(2011,(Double.parseDouble(fields[format.convert("k")])),0,40,60,0);
                	IrrigationUtilizationCommand irrigationUtilizationCommand = new IrrigationUtilizationCommand(cborewell,cdugwell,cdugwellPumpSet,cshallowTubeWell,cmediumTubeWell,cdeepTubeWell,cfilterPoints);
                	
                	Borewell ncborewell;
                	Dugwell ncdugwell;
                	DugwellPumpSet ncdugwellPumpSet;
                	ShallowTubeWell ncshallowTubeWell;
                	MediumTubeWell ncmediumTubeWell;
                	DeepTubeWell ncdeepTubeWell;
                	FilterPoints ncfilterPoints;
                	ncborewell= new Borewell(2011,(Double.parseDouble(fields[format.convert("o")])),60,40,80,0);
                	ncdugwell= new Dugwell(2011,(Double.parseDouble(fields[format.convert("m")])),10,40,90,0);
                	ncdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble(fields[format.convert("n")])),0,30,50,0);
                	ncshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble(fields[format.convert("p")])),0,40,90,0);
                	ncmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble(fields[format.convert("q")])),120,40,90,0);
                	ncdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble(fields[format.convert("r")])),0,40,90,0);
                	ncfilterPoints = new FilterPoints(2011,(Double.parseDouble(fields[format.convert("s")])),0,40,60,0);
                	IrrigationUtilizationNonCommand irrigationUtilizationNonCommand = new IrrigationUtilizationNonCommand(ncborewell,ncdugwell,ncdugwellPumpSet,ncshallowTubeWell,ncmediumTubeWell,ncdeepTubeWell,ncfilterPoints);
                	
                	Borewell pqcborewell;
                	Dugwell pqcdugwell;
                	DugwellPumpSet pqcdugwellPumpSet;
                	ShallowTubeWell pqcshallowTubeWell ;
                	MediumTubeWell pqcmediumTubeWell;
                	DeepTubeWell pqcdeepTubeWell;
                	FilterPoints pqcfilterPoints;
                	if(fields.length==20){
                		pqcborewell= new Borewell(2011,(Double.parseDouble("0")),60,40,80,0);
                	}
                	else{
                		pqcborewell= new Borewell(2011,(Double.parseDouble(fields[format.convert("w")])),60,40,80,0);
                	}
                	if(fields.length==20){
                		pqcdugwell= new Dugwell(2011,(Double.parseDouble("0")),10,40,90,0);
                	}
                	else{
                		pqcdugwell= new Dugwell(2011,(Double.parseDouble(fields[format.convert("u")])),10,40,90,0);
                	}
                	if(fields.length==20){
                		pqcdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble("0")),0,30,50,0);
                	}
                	else{
                		pqcdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble(fields[format.convert("v")])),0,30,50,0);
                	}
                	if(fields.length==20){
                		pqcshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble("0")),0,40,90,0);
                	}
                	else{
                		pqcshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble(fields[format.convert("x")])),0,40,90,0);
                	}
                	if(fields.length==20){
                		pqcmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble("0")),120,40,90,0);
                	}
                	else{
                		pqcmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble(fields[format.convert("y")])),120,40,90,0);
                	}
                	if(fields.length==20){
                		pqcdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble("0")),0,40,90,0);
                	}
                	else{
                		pqcdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble(fields[format.convert("z")])),0,40,90,0);
                	}
                	if(fields.length==20){
                		pqcfilterPoints = new FilterPoints(2011,(Double.parseDouble("0")),0,40,60,0);
                	}
                	else{
                		pqcfilterPoints = new FilterPoints(2011,(Double.parseDouble(fields[format.convert("aa")])),0,40,60,0);
                	}
                	IrrigationUtilizationCommandPoorQuality irrigationUtilizationCommandPoorQuality = new IrrigationUtilizationCommandPoorQuality(pqcborewell,pqcdugwell,pqcdugwellPumpSet,pqcshallowTubeWell,pqcmediumTubeWell,pqcdeepTubeWell,pqcfilterPoints);
                	
                	IrrigationUtilization irrigationUtilization = new IrrigationUtilization(irrigationUtilizationCommand,irrigationUtilizationNonCommand,irrigationUtilizationCommandPoorQuality);
                	String IrrigationUtilizationjson = IrrigationUtilization.toJson(irrigationUtilization);
                	if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
                		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getIrrigationUtilization()==null){
                        	count++;
                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setIrrigationUtilization(irrigationUtilization);
                		}
                		else{
                			irrigationUtilization= village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getIrrigationUtilization();
                			cborewell= new Borewell(2011,(Double.parseDouble(fields[format.convert("g")]))+irrigationUtilization.command.borewell.noOfWells,60,40,80,0);
                        	cdugwell= new Dugwell(2011,(Double.parseDouble(fields[format.convert("e")]))+irrigationUtilization.command.dugwell.noOfWells,10,40,90,0);
                        	cdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble(fields[format.convert("f")]))+irrigationUtilization.command.dugwellPumpSet.noOfWells,0,30,50,0);
                        	cshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble(fields[format.convert("h")]))+irrigationUtilization.command.shallowTubeWell.noOfWells,0,40,90,0);
                        	cmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble(fields[format.convert("i")]))+irrigationUtilization.command.mediumTubeWell.noOfWells,120,40,90,0);
                        	cdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble(fields[format.convert("j")]))+irrigationUtilization.command.deepTubeWell.noOfWells,0,40,90,0);
                        	cfilterPoints = new FilterPoints(2011,(Double.parseDouble(fields[format.convert("k")]))+irrigationUtilization.command.filterPoints.noOfWells,0,40,60,0);
                        	irrigationUtilizationCommand = new IrrigationUtilizationCommand(cborewell,cdugwell,cdugwellPumpSet,cshallowTubeWell,cmediumTubeWell,cdeepTubeWell,cfilterPoints);
                        	
                           	ncborewell= new Borewell(2011,(Double.parseDouble(fields[format.convert("o")]))+irrigationUtilization.non_command.borewell.noOfWells,60,40,80,0);
                        	ncdugwell= new Dugwell(2011,(Double.parseDouble(fields[format.convert("m")]))+irrigationUtilization.non_command.dugwell.noOfWells,10,40,90,0);
                        	ncdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble(fields[format.convert("n")]))+irrigationUtilization.non_command.dugwellPumpSet.noOfWells,0,30,50,0);
                        	ncshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble(fields[format.convert("p")]))+irrigationUtilization.non_command.shallowTubeWell.noOfWells,0,40,90,0);
                        	ncmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble(fields[format.convert("q")]))+irrigationUtilization.non_command.mediumTubeWell.noOfWells,120,40,90,0);
                        	ncdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble(fields[format.convert("r")]))+irrigationUtilization.non_command.deepTubeWell.noOfWells,0,40,90,0);
                        	ncfilterPoints = new FilterPoints(2011,(Double.parseDouble(fields[format.convert("s")]))+irrigationUtilization.non_command.filterPoints.noOfWells,0,40,60,0);
                        	irrigationUtilizationNonCommand = new IrrigationUtilizationNonCommand(ncborewell,ncdugwell,ncdugwellPumpSet,ncshallowTubeWell,ncmediumTubeWell,ncdeepTubeWell,ncfilterPoints);
                        	
                        	
                        	if(fields.length==20){
                        		pqcborewell= new Borewell(2011,(Double.parseDouble("0"))+irrigationUtilization.poor_quality.borewell.noOfWells,60,40,80,0);
                        	}
                        	else{
                        		pqcborewell= new Borewell(2011,(Double.parseDouble(fields[format.convert("w")]))+irrigationUtilization.poor_quality.borewell.noOfWells,60,40,80,0);
                        	}
                        	if(fields.length==20){
                        		pqcdugwell= new Dugwell(2011,(Double.parseDouble("0"))+irrigationUtilization.poor_quality.dugwell.noOfWells,10,40,90,0);
                        	}
                        	else{
                        		pqcdugwell= new Dugwell(2011,(Double.parseDouble(fields[format.convert("u")]))+irrigationUtilization.poor_quality.dugwell.noOfWells,10,40,90,0);
                        	}
                        	if(fields.length==20){
                        		pqcdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble("0"))+irrigationUtilization.poor_quality.dugwellPumpSet.noOfWells,0,30,50,0);
                        	}
                        	else{
                        		pqcdugwellPumpSet= new DugwellPumpSet(2011,(Double.parseDouble(fields[format.convert("v")]))+irrigationUtilization.poor_quality.dugwellPumpSet.noOfWells,0,30,50,0);
                        	}
                        	if(fields.length==20){
                        		pqcshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble("0"))+irrigationUtilization.poor_quality.shallowTubeWell.noOfWells,0,40,90,0);
                        	}
                        	else{
                        		pqcshallowTubeWell = new ShallowTubeWell(2011,(Double.parseDouble(fields[format.convert("x")]))+irrigationUtilization.poor_quality.shallowTubeWell.noOfWells,0,40,90,0);
                        	}
                        	if(fields.length==20){
                        		pqcmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble("0"))+irrigationUtilization.poor_quality.mediumTubeWell.noOfWells,120,40,90,0);
                        	}
                        	else{
                        		pqcmediumTubeWell = new MediumTubeWell(2011,(Double.parseDouble(fields[format.convert("y")]))+irrigationUtilization.poor_quality.mediumTubeWell.noOfWells,120,40,90,0);
                        	}
                        	if(fields.length==20){
                        		pqcdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble("0"))+irrigationUtilization.poor_quality.deepTubeWell.noOfWells,0,40,90,0);
                        	}
                        	else{
                        		pqcdeepTubeWell = new DeepTubeWell(2011,(Double.parseDouble(fields[format.convert("z")]))+irrigationUtilization.poor_quality.deepTubeWell.noOfWells,0,40,90,0);
                        	}
                        	if(fields.length==20){
                        		pqcfilterPoints = new FilterPoints(2011,(Double.parseDouble("0"))+irrigationUtilization.poor_quality.filterPoints.noOfWells,0,40,60,0);
                        	}
                        	else{
                        		pqcfilterPoints = new FilterPoints(2011,(Double.parseDouble(fields[format.convert("aa")]))+irrigationUtilization.poor_quality.filterPoints.noOfWells,0,40,60,0);
                        	}
                        	irrigationUtilizationCommandPoorQuality = new IrrigationUtilizationCommandPoorQuality(pqcborewell,pqcdugwell,pqcdugwellPumpSet,pqcshallowTubeWell,pqcmediumTubeWell,pqcdeepTubeWell,pqcfilterPoints);
                        	
                        	irrigationUtilization = new IrrigationUtilization(irrigationUtilizationCommand,irrigationUtilizationNonCommand,irrigationUtilizationCommandPoorQuality);
                        	IrrigationUtilizationjson = IrrigationUtilization.toJson(irrigationUtilization);
                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setIrrigationUtilization(irrigationUtilization);

                		}
                	}
                   // System.out.println(IrrigationUtilizationjson);
                }
            }
            System.out.println("irrigation count= "+count);
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        //json for Residential Utilization wc
        Gson ResidentialUtilization = new Gson();
        //inserting into Residential Utilization json Object
        try(BufferedReader iem = new BufferedReader(new FileReader(residentialUtilizationfile))) {
        
        	int count =0;
            record = iem.readLine();
            record = iem.readLine();
            record = iem.readLine();
           
            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",");
                

               if(fields.length==11){
                	//doubt Ward No 70 and Ward No 70 int coloum has #ref
                	WellsUtilizationData cpws= new WellsUtilizationData(2011,(Integer.parseInt(fields[format.convert("f")])),7.0,120.0,240.0,90.0,0.0);
                	ResidentialUtilizationHandpump chp= new ResidentialUtilizationHandpump(2011,(Double.parseDouble(fields[format.convert("e")])),7,10,120,240,0);
                	ResidentialUtilizationType residentialUtilizationCommand = new ResidentialUtilizationType(cpws,chp);
                	
                	WellsUtilizationData ncpws= new WellsUtilizationData(2011,(Double.parseDouble(fields[format.convert("h")])),7,50,120,240,0);
                	ResidentialUtilizationHandpump ncmpt= new ResidentialUtilizationHandpump(2011,(Double.parseDouble(fields[format.convert("g")])),7,10,120,240,0);
                	ResidentialUtilizationNonCommand residentialUtilizationNonCommand = new ResidentialUtilizationNonCommand(ncpws,ncmpt);
                	
                	WellsUtilizationData pqpws= new WellsUtilizationData(2011,(Double.parseDouble(fields[format.convert("j")])),7,50,120,240,0);
                	ResidentialUtilizationHandpump pqhp= new ResidentialUtilizationHandpump(2011,(Double.parseDouble(fields[format.convert("i")])),7,10,120,240,0);
                	ResidentialUtilizationPoorQuality residentialUtilizationPoorQuality = new ResidentialUtilizationPoorQuality(pqpws,pqhp);

                	ResidentialUtilization residentialUtilization = new ResidentialUtilization(residentialUtilizationCommand,residentialUtilizationNonCommand,residentialUtilizationPoorQuality);
                	String ResidentialUtilizationjson = ResidentialUtilization.toJson(residentialUtilization);
                	if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
                		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getResidentialUtilization()==null){
                        	count++;
                    		village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setResidentialUtilization(residentialUtilization);
                		}
                		else{
                			residentialUtilization= village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getResidentialUtilization();
                			cpws= new WellsUtilizationData(2011,(Double.parseDouble(fields[format.convert("f")]))+residentialUtilization.command.Pws.count,7,120,240,90,0);
                        	chp= new ResidentialUtilizationHandpump(2011,(Double.parseDouble(fields[format.convert("e")]))+residentialUtilization.command.Handpump.count,7,10,120,240,0);
                        	residentialUtilizationCommand = new ResidentialUtilizationType(cpws,chp);
                        	
                        	ncpws= new WellsUtilizationData(2011,(Double.parseDouble(fields[format.convert("h")]))+residentialUtilization.non_command.Pws.count,7,50,120,240,0);
                        	ncmpt= new ResidentialUtilizationHandpump(2011,(Double.parseDouble(fields[format.convert("g")]))+residentialUtilization.non_command.Handpump.count,7,10,120,240,0);
                        	residentialUtilizationNonCommand = new ResidentialUtilizationNonCommand(ncpws,ncmpt);
                        	
                        	pqpws= new WellsUtilizationData(2011,(Double.parseDouble(fields[format.convert("j")]))+residentialUtilization.poor_quality.Pws.count,7,50,120,240,0);
                        	pqhp= new ResidentialUtilizationHandpump(2011,(Double.parseDouble(fields[format.convert("i")]))+residentialUtilization.poor_quality.Handpump.count,7,10,120,240,0);
                        	residentialUtilizationPoorQuality = new ResidentialUtilizationPoorQuality(pqpws,pqhp);

                        	residentialUtilization = new ResidentialUtilization(residentialUtilizationCommand,residentialUtilizationNonCommand,residentialUtilizationPoorQuality);
                        	ResidentialUtilizationjson = ResidentialUtilization.toJson(residentialUtilization);
                    		village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setResidentialUtilization(residentialUtilization);

                		}
                	}
                    //System.out.println(count+" "+ResidentialUtilizationjson);
                	
                }
            }
            System.out.println("residential count= "+count);
        }catch (IOException e) {
            e.printStackTrace();
        }
        
     // json for population
        Gson populationjsn = new Gson();

        try(BufferedReader iem = new BufferedReader(new FileReader(populationfile))) {
              
          	//readXLSXFile();
              record = iem.readLine();
             
              while((record = iem.readLine()) != null) {
                  //System.out.println("record"+record);
                  String fields[] = record.split(",");
                 // System.out.println("fields"+fields);
                  int lpcd =0;
                  int totalPopulation=0;
                  int command = 0;
                  int noncommand =0;
                  if(fields.length==9){
                  	if(!fields[format.convert("i")].isEmpty()){
                  		lpcd = (int) Double.parseDouble(fields[format.convert("i")]);
                                        	}
                  	if(!fields[format.convert("h")].isEmpty()){
                  		totalPopulation=(int) Double.parseDouble(fields[format.convert("h")]);
                 	}
                  	if(!fields[format.convert("f")].isEmpty()){
                  		command = (int) Double.parseDouble(fields[format.convert("f")]);
                    }
                  	if(!fields[format.convert("g")].isEmpty()){
                  	   	noncommand = (int) Double.parseDouble(fields[format.convert("g")]);
                   	}
                  	
                  	Population populationObj = new Population(lpcd,totalPopulation,command,noncommand);
                  	String populationJson = populationjsn.toJson(populationObj);
                  	if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("e")]))){
                      //	System.out.println("hello");
                  		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")])))==null){
                            village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("e")]))).setPopulation(populationJson);
                		}
                  		else{
                  			populationObj= populationjsn.fromJson(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getPopulation(),Population.class);
                  			lpcd+=populationObj.lpcd;
                  			totalPopulation+=populationObj.totalPopulation;
                  			command+=populationObj.command;
                  			noncommand+=populationObj.non_command;
                  			populationObj = new Population(lpcd,totalPopulation,command,noncommand);
                          	populationJson = populationjsn.toJson(populationObj);
                            village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("e")]))).setPopulation(populationJson);
                  		}
                      }
                      
                  }
        }
              int c=0;
              for(String vill:village_details.keySet()){
              	c++;
              	//System.out.println(village_details.get(vill));
              }
              System.out.println("village id count"+c);
              
              
          }catch (IOException e) {
              e.printStackTrace();
          }
        
        
        //doubt no village name
        //json for Industrial Utilization wc
        Gson IndustrialUtilization = new Gson();
        //inserting into Industrial Utilization json Object
        
        //json for village
        Gson Village = new Gson();
        for(String villageName:village_details.keySet()){
        	Village villageObj = village_details.get(villageName);
        	Village_Data village_obj=new Village_Data(villageObj);
            String villagejson = Village.toJson(village_obj);
            try (FileWriter file = new FileWriter("/home/megha/Downloads/final vsp - Copy/insertVillageScript.json",true)) {
                String query = "Insert into location_md JSON '"+villagejson+"';";
     // System.out.println(query);
                

            	//file.write(query);
                file.flush();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            
			
				System.out.println(villagejson);
			    break;  

        }
         System.out.println("end");

    }
	
	public static void readXLSXFile() throws IOException
	{
		InputStream ExcelFileToRead = new FileInputStream("/home/megha/Downloads/final vsp - Copy/Drafts/b. population lpcd.xlsx");
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		
		
		XSSFSheet sheet = wb.getSheetAt(1);
		XSSFRow row; 
		XSSFCell cell;
		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			while (cells.hasNext())
			{
				cell=(XSSFCell) cells.next();
				
				
		
				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
				{
					System.out.print(cell.getStringCellValue()+" ,");
				}
				else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
				{
					System.out.print(cell.getNumericCellValue()+" ");
				}
				else
				{
					//For Handling Boolean, Formula, Errors
				}
			}
			System.out.println();
		}
		
	}
	
	

}
