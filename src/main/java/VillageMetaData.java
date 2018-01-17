import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;


public class VillageMetaData {

    
	public static void compute(String districtName, String path, BufferedWriter bw) {

		String filePath = path+districtName+"/";

		
		/**
		 * Output files
		 */
		String assessmentUnitCQLScriptFile = path+"final_scripts/"+districtName+"-loc_meta_data.cql";

        String gwlocToIWMloc = filePath+"final_mapping.csv";      // Input File
        String areafile = filePath+"area.csv";
        String waterbodiesfile =filePath+"mi_tanks.csv";
        String canalfile = filePath+"canals.csv";
        String artificialWCfile= filePath+"wc_structures.csv";
        String irrigationUtilizationfile = filePath+"bw_irrigated.csv";
        String residentialUtilizationFile = filePath+"bw_domestic.csv";
        String industryUtilizationFile = filePath+"bw_industrial.csv";
        String wellsSpecificYieldFile = filePath+"rainfall_unit_drift.csv";
        String populationfile = filePath+"population.csv";
        String gw_rf_file = filePath+"rf_gw_data.csv";

        
        /**
         * Crop base files.
         */
        String paddykhariffile = filePath+"../base_md/crop/village_area_sown_paddy_kharif_2016.csv"; 
        String paddyrabifile = filePath+"../base_md/crop/village_area_sown_paddy_rabi_2016.csv";
        String nonpaddykhariffile = filePath+"../base_md/crop/village_area_sown_non_paddy_kharif_2016.csv";
        String nonpaddyrabifile = filePath+"../base_md/crop/village_area_sown_non_paddy_rabi_2016.csv";
        String cropvillfile = filePath+"../base_md/crop/ecrop_to_iwm_location_map.csv";
        
        /**
         * Location meta data IWM base files
         */
        String basinIdfile = filePath+"../base_md/location/iwm_basin_name_id_map.csv";
        String villageIdfile = filePath+"../base_md/location/iwm_village_name_id_map.csv";
        
        /**
         * Prepare wells yield / operative days info
         * basinName vs typeofwell vs command/non-command vs category(agriculture/domestic) vs well data
         */
        Map<String, Map<String, Map<String, Map<String, WellsUtilizationData>>>> basinWiseWellsMD = new HashMap<>();
        
        String record = "";
        HashMap<String,String> basinmapping = new HashMap<>();
        HashMap<String,String> locmapping = new HashMap<>();
        HashMap<String,Integer> Basin_Id = new HashMap<>();
        HashMap<Integer,String> villageId = new HashMap<>();
        JSONObject infiltrationRate = new JSONObject(); 
        infiltrationRate.put("infiltrationRate", new Integer(1));
        JSONObject specificYield = new JSONObject(); 
        specificYield.put("specificYield", new Integer(1));
        JSONObject transmissivity = new JSONObject(); 
        transmissivity.put("transmissivity", new Integer(1));
        JSONObject storageCoefficient = new JSONObject(); 
        storageCoefficient.put("storageCoefficient", new Integer(1));
        HashMap<String,Village> village_details=new HashMap<>();
        Map<Integer,Map<String,Double>> paddykharif = new HashMap<>();
        Map<Integer,Map<String,Double>> paddyrabi = new HashMap<>();
        Map<Integer,Map<String,Double>> nonpaddykharif = new HashMap<>();
        Map<Integer,Map<String,Double>> nonpaddyrabi = new HashMap<>();
        Map<String,Double> computeDugwell = new HashMap<>();
        Map<String,Double> computeTubewell = new HashMap<>();
        Map<Integer,Integer> cropId = new HashMap<>();

       
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
                        locmapping.put(MicroBasin_GWvillage_key,IWMvillageName);
                        basinmapping.put(MicroBasinName,IWMMicroBasinName);
                	}
                	
                }
               
                //System.out.println("**"+MicroBasin_GWvillage_key);
            }
            int c1=0;
//            System.out.println("ANKIT ::: locmapping : " + locmapping);
            for(String key:locmapping.keySet()){
            	//c1++;
//            	System.out.println(key + " " + locmapping.get(key));
            	village_details.put(locmapping.get(key),new Village());
            }
            System.out.println("b4 naming"+village_details.size());
            for(String key:village_details.keySet()){
            	
            		village_details.get(key).setVillageName(key);
            	
            }
            int c=0;
            for(String vill:village_details.keySet()){
            	c++;
            //	System.out.println(vill);
            }
            System.out.println("village name count"+c);
            
        }catch (IOException e) {
            e.printStackTrace();
        }
      
        
        //Basin Id----
        try(BufferedReader iem = new BufferedReader(new FileReader(basinIdfile))) {
            
        
            record = iem.readLine();
            record = iem.readLine();

            while((record = iem.readLine()) != null) {
                //System.out.println("record"+record);

                String fields[] = record.split(",");
//                System.out.println("fields" + fields.length);
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
            int c=0;

        	//readXLSXFile();
//            record = iem.readLine();
            record = iem.readLine();

            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",");
                
                if(fields.length==2){
                	int VillageId = Integer.parseInt(fields[1]);
                    String IWMvillageName = format.removeQuotes(fields[0]);
                    villageId.put(VillageId,IWMvillageName);
                    if(village_details.get(IWMvillageName)!=null){
                    	c++;
                    	village_details.get(IWMvillageName).setVillageId(VillageId);
                	}
                    else{
//                    	System.out.println("ANKIT ::: village name detail is null for village : " + IWMvillageName);
                    }
                }

            }
            
        for(String key:village_details.keySet()){
        	if(village_details.get(key).loc_id==0){
        		//System.out.println(key);
        		System.out.println("Village with location id 0 : " + village_details.get(key).getVillageName());
        	}
        }
        System.out.println("village id count"+c);

        }catch (IOException e) {
            e.printStackTrace();
        }
        
        //crop village to iwm village
        try(BufferedReader iem = new BufferedReader(new FileReader(cropvillfile))) {
            record = iem.readLine();

            while((record = iem.readLine()) != null) {
                //System.out.println("record"+record);

                String fields[] = record.split(",");
               // System.out.println("fields"+fields.length);
                if(fields.length==3){
                	int cropcode = Integer.parseInt(fields[format.convert("a")]);
                	int villagecode = Integer.parseInt(fields[format.convert("b")]);
                	cropId.put(cropcode, villagecode);
                }
                //System.out.println("**"+MicroBasin_GWvillage_key);
            }
            System.out.println("crop id size "+cropId.size());
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        //paddykharif
        try(BufferedReader iem = new BufferedReader(new FileReader(paddykhariffile))) {
            
            while((record = iem.readLine()) != null) {
                //System.out.println("record"+record);

                String fields[] = record.split(",");
               // System.out.println("fields"+fields.length);
                if(fields.length==3){
                	int cropcode = Integer.parseInt(fields[format.convert("a")]);
                    String source =format.removeQuotes(fields[format.convert("b")]);
                    double area = Double.parseDouble(fields[format.convert("c")]);
                    if(paddykharif.get(cropcode)==null){
                    	Map<String,Double> water = new HashMap<>();
                        water.put(source, area);
                        paddykharif.put(cropcode, water);
                    }
                    else{
                    	paddykharif.get(cropcode).put(source, area);
                    	
                    }
                }
                //System.out.println("**"+MicroBasin_GWvillage_key);
            }
            System.out.println("paddy kharif size "+paddykharif.size());
        }catch (IOException e) {
            e.printStackTrace();
        }
       
        //paddyrabi
        try(BufferedReader iem = new BufferedReader(new FileReader(paddyrabifile))) {
            
            while((record = iem.readLine()) != null) {
                //System.out.println("record"+record);

                String fields[] = record.split(",");
               // System.out.println("fields"+fields.length);
                if(fields.length==3){
                	int cropcode = Integer.parseInt(fields[format.convert("a")]);
                    String source =format.removeQuotes(fields[format.convert("b")]);
                    double area = Double.parseDouble(fields[format.convert("c")]);
                    if(paddyrabi.get(cropcode)==null){
                    	Map<String,Double> water = new HashMap<>();
                        water.put(source, area);
                        paddyrabi.put(cropcode, water);
                    }
                    else{
                    	paddyrabi.get(cropcode).put(source, area);
                    	
                    }
                }
                //System.out.println("**"+MicroBasin_GWvillage_key);
            }
            System.out.println("paddy rabi size "+paddyrabi.size());
        }catch (IOException e) {
            e.printStackTrace();
        }
        
      //non paddykharif
        try(BufferedReader iem = new BufferedReader(new FileReader(nonpaddykhariffile))) {
            
            while((record = iem.readLine()) != null) {
                //System.out.println("record"+record);

                String fields[] = record.split(",");
               // System.out.println("fields"+fields.length);
                if(fields.length==3){
                	int cropcode = Integer.parseInt(fields[format.convert("a")]);
                    String source =format.removeQuotes(fields[format.convert("b")]);
                    double area = Double.parseDouble(fields[format.convert("c")]);
                    if(nonpaddykharif.get(cropcode)==null){
                    	Map<String,Double> water = new HashMap<>();
                        water.put(source, area);
                        nonpaddykharif.put(cropcode, water);
                    }
                    else{
                    	nonpaddykharif.get(cropcode).put(source, area);
                    	
                    }
                }
                //System.out.println("**"+MicroBasin_GWvillage_key);
            }
            System.out.println("nonpaddy kharif size "+nonpaddykharif.size());
        }catch (IOException e) {
            e.printStackTrace();
        }
       
        //non paddyrabi
        try(BufferedReader iem = new BufferedReader(new FileReader(nonpaddyrabifile))) {
            
            while((record = iem.readLine()) != null) {
                //System.out.println("record"+record);

                String fields[] = record.split(",");
               // System.out.println("fields"+fields.length);
                if(fields.length==3){
                	int cropcode = Integer.parseInt(fields[format.convert("a")]);
                    String source =format.removeQuotes(fields[format.convert("b")]);
                    double area = Double.parseDouble(fields[format.convert("c")]);
                    if(nonpaddyrabi.get(cropcode)==null){
                    	Map<String,Double> water = new HashMap<>();
                        water.put(source, area);
                        nonpaddyrabi.put(cropcode, water);
                    }
                    else{
                    	nonpaddyrabi.get(cropcode).put(source, area);
                    	
                    }
                }
                //System.out.println("**"+MicroBasin_GWvillage_key);
            }
            System.out.println("nonpaddy rabi size "+nonpaddyrabi.size());
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        try(BufferedReader iem = new BufferedReader(new FileReader(wellsSpecificYieldFile))) {
        	record = iem.readLine();
            while((record = iem.readLine()) != null) {
              String []fields = record.split(",",-1);
            	
            	
            	if(!format.removeQuotes(fields[format.convert("b")]).isEmpty() && !format.removeQuotes(fields[format.convert("b")]).contains("Watershed") && format.removeQuotes(fields[format.convert("b")]).length() > 0){
            		String basinName = format.removeQuotes(fields[format.convert("b")]);
            		String typeOfWell = format.removeQuotes(fields[format.convert("c")]);
            		double yield = 0;
            		Map<String, Double> operationDays = new HashMap<>();
            		
        			if(basinWiseWellsMD.get(basinName) == null){
            			basinWiseWellsMD.put(basinName, new HashMap<String, Map<String, Map<String, WellsUtilizationData>>>());
            		}
            			
            		if(basinWiseWellsMD.get(basinName).get(typeOfWell) == null){
            			basinWiseWellsMD.get(basinName).put(typeOfWell, new HashMap<String, Map<String, WellsUtilizationData>>());
            		}
            		
            		for(String areaType : Constants.AREA_TYPES){
            			Map<String, Double> operationDaysAgriculture = new HashMap<>();
            			Map<String, Double> operationDaysIndustry = new HashMap<>();
            			Map<String, Double> operationDaysDomestic = new HashMap<>();
            			
            			WellsUtilizationData wellDataAgriculture = new WellsUtilizationData();
            			WellsUtilizationData wellDataIndustry = new WellsUtilizationData();
            			WellsUtilizationData wellDataDomestic = new WellsUtilizationData();
            			if(basinWiseWellsMD.get(basinName).get(typeOfWell).get(areaType) == null){
            				basinWiseWellsMD.get(basinName).get(typeOfWell).put(areaType, new HashMap<String, WellsUtilizationData>());
            			}
            				
            			int index = Constants.AREA_TYPES.indexOf(areaType);
            			double monsoonDays=0, nonMonsoonDays=0;
            			
            			/**
            			 * We are trying to get the data by recursively iterating over the 
            			 * columns. Column no. has been computed at run time.
            			 */
            			
            			/**
                		 * Agriculture
                		 */
            			yield = (fields[3+(12*index)].isEmpty())?0.0:Utils.parseDouble(fields[3+(12*index)]);
            			yield = (yield == 0.0)?2400.0:yield;
            			monsoonDays = (fields[5+(12*index)].isEmpty())?0.0:Double.parseDouble(fields[5+(12*index)]);
                		operationDaysAgriculture.put(Constants.MONSOON, monsoonDays);
                		nonMonsoonDays = (fields[6+(12*index)].isEmpty())?0.0:Double.parseDouble(fields[6+(12*index)]);
                		operationDaysAgriculture.put(Constants.NON_MONSOON, nonMonsoonDays);
                		wellDataAgriculture.setYield(yield);
                		wellDataAgriculture.setOperativeDays(operationDaysAgriculture);
                		basinWiseWellsMD.get(basinName).get(typeOfWell).get(areaType).put(Constants.AGRICULTURE, wellDataAgriculture);
                		
                		/**
                		 * Domestic
                		 */
                		yield = (fields[7+(12*index)].isEmpty())?0.0:Double.parseDouble(fields[7+(12*index)]);
                		yield = (yield == 0.0)?284.0:yield;
                		monsoonDays = (fields[9+(12*index)].isEmpty())?0.0:Double.parseDouble(fields[9+(12*index)]);
                		operationDaysDomestic.put(Constants.MONSOON, monsoonDays);
                		nonMonsoonDays = (fields[10+(12*index)].isEmpty())?0.0:Double.parseDouble(fields[10+(12*index)]);
                		operationDaysDomestic.put(Constants.NON_MONSOON, nonMonsoonDays);
                		wellDataDomestic.setYield(yield);
                		wellDataDomestic.setOperativeDays(operationDaysDomestic);
                		basinWiseWellsMD.get(basinName).get(typeOfWell).get(areaType).put(Constants.DOMESTIC, wellDataDomestic);
                		/**
                		 * Industry
                		 */
//                		System.out.println("index : " + index);
//                		System.out.println(" fields : " + record);
                		yield = (fields[11+(12*index)].isEmpty())?0.0:Double.parseDouble(fields[11+(12*index)]);
                		yield = (yield == 0.0)?4000.0:yield;
                		monsoonDays = (fields[13+(12*index)].isEmpty())?0.0:Double.parseDouble(fields[13+(12*index)]);
                		operationDaysIndustry.put(Constants.MONSOON, monsoonDays);
                		nonMonsoonDays = (fields[14+(12*index)].isEmpty())?0.0:Double.parseDouble(fields[14+(12*index)]);
                		operationDaysIndustry.put(Constants.NON_MONSOON, nonMonsoonDays);
                		wellDataIndustry.setYield(yield);
                		wellDataIndustry.setOperativeDays(operationDaysIndustry);
                		basinWiseWellsMD.get(basinName).get(typeOfWell).get(areaType).put(Constants.INDUSTRY, wellDataIndustry);
            		}
            		
            	} else {
            		System.out.println("else of draft file " + record);
            	}
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("basinWiseWellsMD : " + basinWiseWellsMD);
        
//        System.out.println("******" + basinWiseWellsMD.get("VSP_14_F_NC_SARADA-9_BUTCHAYYAPETA"));
        		
        Map<String, String> utilizationFiles = new HashMap<>();
        utilizationFiles.put(Constants.AGRICULTURE, irrigationUtilizationfile);
        utilizationFiles.put(Constants.DOMESTIC, residentialUtilizationFile);
        utilizationFiles.put(Constants.INDUSTRY, industryUtilizationFile);
        
        /**
         * Category : AreaType : WellName vs wellInfo
         */
        Map<String, Map<String, Map<String, WellsUtilizationData>>> villageWellUtilData = new HashMap<>();
        for(String category : Constants.CATEGORIES){
        	if(utilizationFiles.get(category) == null)
        		continue;
        	
        	
//        	System.out.println("category : " + category);
//        	villageWellUtilData.put(category, new HashMap<String, Map<String, WellsUtilizationData>>());
//        	System.out.println("&&&&&&&&&&&&77" + basinWiseWellsMD.get("VSP_F_14_C_SARADA-11_V.MADUGULA").keySet());
        	try(BufferedReader iem = new BufferedReader(new FileReader(utilizationFiles.get(category)))) {
            	record = iem.readLine();
                while((record = iem.readLine()) != null) {
                    //System.out.println("record"+record);
                   String fields[] = record.split(",",-1);
//                   System.out.println("length : " + fields.length);
                   
                   if(!format.removeQuotes(fields[format.convert("c")]).isEmpty() && format.removeQuotes(fields[format.convert("c")]).length() > 0){
                	   String basinName = format.removeQuotes(fields[format.convert("c")]);
                	   String villageName = format.removeQuotes(fields[format.convert("d")]);
                	  
                	   if(basinWiseWellsMD.get(basinName) == null){
                		   System.out.println(category+" Well MD information not found for basin : " + basinName);
                		   continue;
                	   }
                	   
                	   /**
                        * Category : AreaType : WellName vs wellInfo
                        */
                	   if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))) == null){
                		   if(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")])) == null)
                			   continue;
                		   village_details.put(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")])), new Village());
                	   }
                	  
                	   Map<String, Map<String, Map<String, WellsUtilizationData>>> villWellDistributionInfo = village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getResourceDistribution();
                	   
                	   if(villWellDistributionInfo == null)
                		   villWellDistributionInfo = new HashMap<>();
                	   if(villWellDistributionInfo.get(category) == null)
                		   villWellDistributionInfo.put(category, new HashMap<String, Map<String, WellsUtilizationData>>());
                	   
                	   WellsUtilizationData villWellData;
                	   for(String areaType : Constants.AREA_TYPES){
                		   int areaIndex = Constants.AREA_TYPES.indexOf(areaType);
                		   int noOfWells = Constants.CATEGORY_WELLS.get(category).size();
                		   
                		   if(villWellDistributionInfo.get(category).get(areaType) == null)
                			   villWellDistributionInfo.get(category).put(areaType, new HashMap<String, WellsUtilizationData>());
                		   
                		   for(String well : Constants.CATEGORY_WELLS.get(category)){
                			   //TODO : later change to BW: 3 : 0
                			   double growthRate = (well == "BWs")?0.0:0.0;
                			   double pumpingHours = Constants.PUMPING_HOURS;
                			   int count = 0;
                			   int referenceYear = 2011;
                			   int wellIndex = Constants.CATEGORY_WELLS.get(category).indexOf(well);
                			   
                			   if(villWellDistributionInfo.get(category).get(areaType).get(well) == null)
                				   villWellDistributionInfo.get(category).get(areaType).put(well, new WellsUtilizationData());
                			   
                			   WellsUtilizationData mbWell = new WellsUtilizationData();
                			   
                			   /**
                			    * If that particular well is not found then set the specific yields and 
                			    * operative days to zero
                			    */
                			   if(basinWiseWellsMD.get(basinName).get(well) == null){
                				   mbWell = new WellsUtilizationData();
                				   Map<String, Double> operativeDays = new HashMap<>();
                				   operativeDays.put(Constants.MONSOON, 0.0);
                				   operativeDays.put(Constants.NON_MONSOON, 0.0);
                				   mbWell.setOperativeDays(operativeDays);
//                				   System.out.println(well);
//                				   System.out.println(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")])));
                			   }
                				   
//                			   System.out.println("basin : " + basinName + " : category : " + category + " : areaType : " + areaType + " : well : " + well);
            				   else{
            					   mbWell = basinWiseWellsMD.get(basinName).get(well).get(areaType).get(category);
            				   }
            					   
                			   
                			   villWellData = villWellDistributionInfo.get(category).get(areaType).get(well);
                			   
                			   /**
                			    * for each type of well and areaType : compute column index at runtime
                			    */
                			   
                			   count = (fields[(4+wellIndex) + (noOfWells*areaIndex)].isEmpty())?0:Utils.parseInt((fields[(4+wellIndex) + (noOfWells*areaIndex)])); 
                			   
                			   villWellData.setCount(count);
                			   villWellData.setGrowthRate(growthRate);
                			   villWellData.setReferenceYear(referenceYear);
                			   villWellData.setPumpingHours(pumpingHours);
                			   villWellData.setYield(mbWell.getYield());
                			   villWellData.setOperativeDays(mbWell.getOperativeDays());
                		   }
                	   }
                	   village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setResourceDistribution(villWellDistributionInfo);
                	   
                	   /**
                        * Assign GW Dependency factor
                        */
                	   Map<String, Double> gwDependency = new HashMap<String, Double>();
                	   gwDependency.put(Constants.DOMESTIC, 1.0);
                	   village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setGwDependencyFactor(gwDependency);
                   }
                   else{
                	   System.out.println("Resource distribution : invalid row : " + record);
                   }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        
        
        //compute dugwell
        try(BufferedReader iem = new BufferedReader(new FileReader(irrigationUtilizationfile))) {
        	record = iem.readLine();
            while((record = iem.readLine()) != null) {

                String fields[] = record.split(",",-1);
               // System.out.println("fields"+fields.length);
                String MicroBasinName = format.removeQuotes(fields[format.convert("c")]);
                String GWvillageName = format.removeQuotes(fields[format.convert("d")]);
                String MicroBasin_GWvillage_key = MicroBasinName + "##" +GWvillageName;
                if(locmapping.containsKey(MicroBasin_GWvillage_key)){
                	double dugwell = Utils.parseDouble(fields[format.convert("e")]);
                	computeDugwell.put(locmapping.get(MicroBasin_GWvillage_key), dugwell);
                	
                	double tubewell = Utils.parseDouble(fields[format.convert("j")]);
                	computeTubewell.put(locmapping.get(MicroBasin_GWvillage_key), tubewell);
//                	System.out.println(MicroBasin_GWvillage_key + " " + dugwell + " " + tubewell);
                } else {
                	System.out.println("Location mapping not exist for :"+MicroBasin_GWvillage_key);
                }
            }
            System.out.println("Dugwell size "+computeDugwell.size());
            System.out.println("Tubewll size "+computeTubewell.size());
        }catch (IOException e) {
            e.printStackTrace();
        }
        
//        System.out.println("ANKIT ::: VILLA : " + village_details);
        
        
      //json for area
        Gson Area = new Gson();
        JSONObject jsn = new JSONObject();
        //inserting into area json Object
        try(BufferedReader iem = new BufferedReader(new FileReader(areafile))) {
//        	System.out.println("locmapping : " + locmapping.keySet());      
        	int count =0;
        	int c2=0;
            record = iem.readLine();
            record = iem.readLine();
            int test =0;
            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",", -1);
               /* if(fields.length!=25&&fields.length!=23&&fields.length!=24){
                	System.out.println(fields.length);
                	for(String f:fields){
                		System.out.print(f +" ");
                	}
                	System.out.println();
                }*/
//                System.out.println("fields length : " + fields.length);
                if(fields.length == 17 ||  fields.length==25 || fields.length==23||fields.length==24||fields.length==26 || fields.length==31){
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
	                	hilly= Utils.parseDouble(fields[6])+Utils.parseDouble(fields[10])+Utils.parseDouble(fields[14]);
	                }
                	if(!fields[7].isEmpty()||!fields[11].isEmpty()||!fields[15].isEmpty()){
	                	forest= Utils.parseDouble(fields[7])+Utils.parseDouble(fields[11])+Utils.parseDouble(fields[15]);
	                }
                	NonRechargeWorthy nrw = new NonRechargeWorthy(hilly,forest);
                	
                	Area area = new Area(total,rw,nrw);
                	String areajson = Area.toJson(area);
                	
                	//JSONObject jsn = new JSONObject(areajson.getBytes());
                	//System.out.println(areajson);
//                	System.out.println("locmapping : " + locmapping.keySet());

                	
//                	System.out.println(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]));
                	if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
                    	
                		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getArea()==null){
                			count++;
                			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setArea(areajson);
                			if(total==0){
                    			test++;
                    			//System.out.println(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))));
                    		}
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
                		if(total==0){
                			//test++;
//                			System.out.println(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))));
                		}
                	} else {
                		System.out.println(" asdasdasd::: " + format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]));
                	}
                	//System.out.println("test count value ######"+test);
                	//break;
                } else {
                	System.out.println("Area fields length :: " + fields.length);
                }

            }
        	System.out.println("test count value ######"+test);
        	int c=0;
            for(String key:village_details.keySet()){
	        	if(village_details.get(key).area==null){
//	        		System.out.println(key);
//	        		System.out.println(village_details.get(key).getVillageName());
	        	}
//	        	System.out.println("ANKIT ::: key : " + key);
//	        	System.out.println("ANKIT ::: wells : " + village_details.get(key).getResourceDistribution());
	        	Area areaobj=Area.fromJson(village_details.get(key).getArea(), Area.class);
				if(areaobj.total==0){
					c++;
				}
	        }
            System.out.println("area count= "+count);
            
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        // json for basinAssociation
        try(BufferedReader iem = new BufferedReader(new FileReader(areafile))) {
              
          	//readXLSXFile();
              record = iem.readLine();
              record = iem.readLine();
              int count =0;
              while((record = iem.readLine()) != null) {
                  //System.out.println("record"+record);

                  String fields[] = record.split(",",-1);
                 // System.out.println("fields"+fields);
                  if(fields.length == 17 || fields.length==25 || fields.length==23||fields.length==24||fields.length==26||fields.length==31){
                  	int BasinCode;
                  	double Villagearea = 0;
                  	String BasinName = format.removeQuotes(fields[format.convert("c")]);
                  	String gwvillageName = format.removeQuotes(fields[format.convert("d")]);
                    double fractionArea = Utils.parseDouble(fields[format.convert("e")]);
                    
//                    System.out.println("ANKIT ::: village : " + gwvillageName + " fraction : " + fractionArea);
//                    System.out.println("ANKIT ::: locMap contains : " + (locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))));
                    if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
                      	/*System.out.println(BasinName);
                      	System.out.println("Basin_Id"+Basin_Id);
                      	System.out.println("basinmapping"+basinmapping);*/
//                    	System.out.println("ANKIT ::: Basin ID : " + Basin_Id);
//                    	System.out.println("ANKIT ::: Basin Name : " + BasinName);
//                    	System.out.println("ANKIT ::: Basin mapping : " + basinmapping);
//                    	System.out.println("ANKIT ::: Basin ID Contains : " + Basin_Id.containsKey(basinmapping.get(BasinName)));
                    	if(Basin_Id.containsKey(basinmapping.get(BasinName))) {
                    		BasinCode = Basin_Id.get(basinmapping.get(BasinName));
                    		Area areaobj=Area.fromJson(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getArea(), Area.class);
                  		  	Villagearea = areaobj.total;
                          	
                              if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getBasinAssociation()==null){
                            	  count++;
                            	HashMap<Integer,Double> basin_assoc = new HashMap<>();
                              	village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setBasinAssociation(basin_assoc);
//                              	System.out.println("ANKIT ::: inside locationAssociation if");
                              }
                              if(Villagearea!=0){
                            	 // System.out.println(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).loc_name+" "+(fractionArea/Villagearea));
                                  village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getBasinAssociation().put(BasinCode,((fractionArea/Villagearea)));
//                                  System.out.println("ANKIT ::: inside setting locationAssociation");
                              }
                             
                    	}
                    }
                      
                  }
                  else{
                	  System.out.println("Error : area row fields.length : " + fields.length);
                  }
        }
//              int c=0;
//              
//              for(String key:village_details.keySet()){
//              	if(village_details.get(key) == null){
//              		System.out.println("ANKIT ::: village details is null for key : " + key);
//              		continue;
//              	}
////              	else if(village_details.get(key).loc_association == null){
////              		System.out.println("ANKIT ::: location association is null for key : " + key);
////              		continue;
////              	}
//              	
//            	if(village_details.get(key).loc_association == null || village_details.get(key).loc_association.isEmpty()){
//              		//System.out.println(key);
//              		c++;
//              		//System.out.println(village_details.get(key).getVillageName());
//              	}
//              }
//              /*for(String vill:village_details.keySet()){
//              	c++;
//              	//System.out.println(village_details.get(vill));
//              }*/
////              System.out.println("village assoc count"+c);
              
              
          }catch (IOException e) {
              e.printStackTrace();
          }
        
        HashSet<Integer> ids = new HashSet<>();
        //testing---
//    try (BufferedWriter file = new BufferedWriter(new FileWriter(basinAssociationTesting))) {
//	        
//    	try(BufferedReader iem = new BufferedReader(new FileReader(assoctestfile))) {
//        	record = iem.readLine();
//           int count=0;
//            while((record = iem.readLine()) != null) {
//                //System.out.println("record"+record);
//
//                String fields[] = record.split(",");
//               // System.out.println("fields"+fields.length);
//                if(fields.length==2){
//                	int vilId = Integer.parseInt(format.removeQuotes(fields[format.convert("b")]));
//                	ids.add(vilId);
//                	for(String key:village_details.keySet()){
//                      	if(village_details.get(key).loc_id==vilId){
//                      		//System.out.println(key);
//                      		//System.out.println(village_details.get(key).getVillageName()+" -- "+village_details.get(key).loc_association);
//                      		file.write(village_details.get(key).getVillageName()+" -- "+village_details.get(key).loc_association+" -- "+village_details.get(key).area+"\n");
//        	                file.newLine();
//        	                count++;
//
//                      	}
//                      	/*else{
//                      		file.write(" -- "+village_details.get(key).getVillageName()+" -- "+village_details.get(key).loc_association+"\n");
//        	                file.newLine();
//        	               // count++;
//                      	}*/
//                      }
//                }
//              
//            } 
//            //System.out.println("testing count - "+count);
//            System.out.println("set size---- "+ids.size());
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } 
        
        
        
        //#TODO
        
        //Map for crop
        /*Map<String,Object> obj=new HashMap<>();
		Map<String,Map<String,Double>> obj2 = new HashMap<>();
		Map<String,Double> water=new HashMap<>();
		Map<String,Map<String,Object>> paddy=new HashMap<>();*/
        
        	/**
        	 * Kharif we are taking as Monsoon
        	 */
        	for(int id:paddykharif.keySet()){
        		for(String source:paddykharif.get(id).keySet()){
                	/**
                	 * Canal = Surface water irrigation source
                	 */
                	if(source.equalsIgnoreCase("Canal")){
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				
            				if(village_details.containsKey(IWMName)){
//            					System.out.println("village details contains : " + IWMName + " : " + village_details.containsKey(IWMName));
            					//System.out.println(IWMName);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("command")==null){
            						Map<String,Map<String,Object>> paddy=new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put("monsoon", new HashMap<String, Double>());
            						
            						obj = new HashMap<>();
            						
            						irrigationAreaInfo.get("monsoon").put("surface_irrigation", 0.0);
            						obj.put("irrigationAreaInfo", irrigationAreaInfo);
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 1100);
            						obj.put("waterRequiredUnit", "mm");
            						paddy.put("paddy", obj);
            						village_details.get(IWMName).crop_info.put("command", paddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("command").get("paddy");
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get("irrigationAreaInfo");
            					if(irrigationAreaInfo == null){
            						irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put("monsoon", new HashMap<String, Double>());
            					}
            					if(irrigationAreaInfo.get("monsoon").get("surface_irrigation") == null){
            						irrigationAreaInfo.get("monsoon").put("surface_irrigation", 0.0);
            					}
//            					if(((Map<String, Double>)obj.get("irrigationAreaInfo").get("monsoon").get("surface_irrigation"))==null){
//            						((Map<String,Double>)obj.get("irrigationAreaInfo").get("monsoon")).put("surface_irrigation", 0.0);
//            					}
            					double irrigatedArea = irrigationAreaInfo.get("monsoon").get("surface_irrigation");
            					irrigatedArea += paddykharif.get(id).get(source);
            					irrigationAreaInfo.get("monsoon").put("surface_irrigation", irrigatedArea);
            					double totalArea = (double)(obj.get("cropArea"));
            					totalArea += paddykharif.get(id).get(source);
            					obj.put("cropArea", totalArea);

            				}
                						
            			}
                	}
                	/**
                	 * For now we will not be considering lift irrigation in our data.
                	 */
                	if(source.equalsIgnoreCase("Lift irrigation")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName);
            					Map<String,Object> obj;
            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
            						obj = new HashMap<>();
            						Map<String,Map<String,Object>> paddy=new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put("monsoon", new HashMap<String, Double>());
            						irrigationAreaInfo.get("monsoon").put("lift_irrigation", 0.0);
            						obj.put("irrigationAreaInfo", irrigationAreaInfo);
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 1100);
            						obj.put("waterRequiredUnit", "mm");
            						paddy.put("paddy", obj);
            						village_details.get(IWMName).crop_info.put("non_command", paddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get("irrigationAreaInfo");
            					if(irrigationAreaInfo.get("monsoon") == null){
            						irrigationAreaInfo.put("monsoon", new HashMap<String, Double>());
            						irrigationAreaInfo.get("monsoon").put("lift_irrigation", 0.0);
            					}
            					if(irrigationAreaInfo.get("monsoon").get("lift_irrigation")==null){
            						irrigationAreaInfo.get("monsoon").put("lift_irrigation", 0.0);
            					}
            					double irrigatedArea = irrigationAreaInfo.get("monsoon").get("lift_irrigation");
            					//System.out.println(irrigatedArea);
            					irrigatedArea+=paddykharif.get(id).get(source);
            					//System.out.println(irrigatedArea);
            					irrigationAreaInfo.get("monsoon").put("lift_irrigation", irrigatedArea);
            					//System.out.println("***in lift irr"+village_details.get(IWMName).crop_info);
            					double totalArea = (double)(obj.get("cropArea"));
            					//System.out.println("total area "+totalArea);
            					totalArea+=paddykharif.get(id).get(source);
            					obj.put("cropArea", totalArea);

            				}
                						
            			}
                	}
                	/**
                	 * Tanks = surface water irrigation source
                	 */
                	if(source.equalsIgnoreCase("Tanks")){

                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
            						Map<String,Map<String,Object>> paddy=new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put("monsoon", new HashMap<String, Double>());
            						
            						obj = new HashMap<>();
            						irrigationAreaInfo.get("monsoon").put("surface_irrigation", 0.0);
            						obj.put("irrigationAreaInfo", irrigationAreaInfo);
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 1100);
            						obj.put("waterRequiredUnit", "mm");
            						paddy.put("paddy", obj);
            						village_details.get(IWMName).crop_info.put("non_command", paddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get("irrigationAreaInfo");
            					if(irrigationAreaInfo == null){
            						irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put("monsoon", new HashMap<String, Double>());
            					}
            					if(irrigationAreaInfo.get("monsoon").get("surface_irrigation") == null){
            						irrigationAreaInfo.get("monsoon").put("surface_irrigation", 0.0);
            					}
//            					if(((Map<String, Double>)obj.get("monsoon")).get("surface_irrigation")==null){
//            						((Map<String, Double>)obj.get("monsoon")).put("surface_irrigation", 0.0);
//            					}
            					double irrigatedArea = irrigationAreaInfo.get("monsoon").get("surface_irrigation");
            					irrigatedArea += paddykharif.get(id).get(source);
            					irrigationAreaInfo.get("monsoon").put("surface_irrigation", irrigatedArea);
            					double totalArea = (double)(obj.get("cropArea"));
            					totalArea += paddykharif.get(id).get(source);
            					obj.put("cropArea", totalArea);

            				}
                						
            			}
                	}
                	/**
                	 * MI Tanks = surface water
                	 */
                	if(source.equalsIgnoreCase("MI Tanks")){
            			
            			if(villageId.containsKey(id)){
            				String IWMName = villageId.get(id);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
            						Map<String,Map<String,Object>> paddy=new HashMap<>();
            						obj = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj2 = new HashMap<>();
            						
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 1100);
            						obj.put("waterRequiredUnit", "mm");
            						paddy.put("paddy", obj);
            						village_details.get(IWMName).crop_info.put("non_command", paddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
            					
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
            					
            					if(irrigationAreaInfo == null)
            						irrigationAreaInfo = new HashMap<>();
            					if(irrigationAreaInfo.get(Constants.MONSOON) == null)
            						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
            					if(irrigationAreaInfo.get(Constants.MONSOON).get(Constants.SURFACE_WATER_IRRIGATION) == null)
            						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            					
            					
            					double irrigatedArea = irrigationAreaInfo.get("monsoon").get(Constants.SURFACE_WATER_IRRIGATION);
            					//System.out.println(irrigatedArea);
            					//System.out.println(paddykharif.get(id).get(source));
            					irrigatedArea+=paddykharif.get(id).get(source);
            					irrigationAreaInfo.get("monsoon").put(Constants.SURFACE_WATER_IRRIGATION, irrigatedArea);
            					//System.out.println("***in MI Tanks"+village_details.get(IWMName).crop_info);
            					double totalArea = (double)(obj.get("cropArea"));
            					//System.out.println("total area "+totalArea);
            					totalArea+=paddykharif.get(id).get(source);
            					obj.put("cropArea", totalArea);

            				}
                						
            			}
                	}
                	/**
                	 * Dug well = Ground water irrigation
                	 */
                	if(source.equalsIgnoreCase("Dug well")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(computeDugwell.containsKey(IWMName)){
            						if(computeDugwell.get(IWMName)>0){
            							if(village_details.get(IWMName).crop_info.get("command")==null){
                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put("monsoon", new HashMap<String, Double>());
                    						obj = new HashMap<>();
                    						irrigationAreaInfo.get("monsoon").put("gw_irrigation", 0.0);
                    						obj.put("irrigationAreaInfo", irrigationAreaInfo);
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 1100);
                    						obj.put("waterRequiredUnit", "mm");
                    						paddy.put("paddy", obj);
                    						village_details.get(IWMName).crop_info.put("command", paddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get("command").get("paddy");
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get("irrigationAreaInfo");
                    					if(irrigationAreaInfo == null){
                    						irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put("monsoon", new HashMap<String, Double>());
                    					}
                    					if(irrigationAreaInfo.get("monsoon").get("gw_irrigation") == null){
                    						irrigationAreaInfo.get("monsoon").put("gw_irrigation", 0.0);
                    					}
//                    					if(((Map<String, Double>)obj.get("monsoon")).get("gw_irrigation")==null){
//                    						((Map<String, Double>)obj.get("monsoon")).put("gw_irrigation", 0.0);
//                    					}
                    					double irrigatedArea = irrigationAreaInfo.get("monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(paddykharif.get(id).get(source));
                    					irrigatedArea+=paddykharif.get(id).get(source);
                    					irrigationAreaInfo.get("monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area "+totalArea);
                    					totalArea+=paddykharif.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            						else{
            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
                    						obj = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put("monsoon", new HashMap<String, Double>());
                    						irrigationAreaInfo.get("monsoon").put("gw_irrigation", 0.0);
                    						obj.put("irrigationAreaInfo", irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 1100);
                    						obj.put("waterRequiredUnit", "mm");
                    						paddy.put("paddy", obj);
                    						village_details.get(IWMName).crop_info.put("non_command", paddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get("irrigationAreaInfo");
                    					if(irrigationAreaInfo.get("monsoon") == null){
                    						irrigationAreaInfo.put("monsoon", new HashMap<String, Double>());
                    						irrigationAreaInfo.get("monsoon").put("irrigationAreaInfo", 0.0);
                    					}
                    					//System.out.println(obj);
                    					if(irrigationAreaInfo.get("monsoon").get("gw_irrigation")==null){
                    						irrigationAreaInfo.get("monsoon").put("gw_irrigation", 0.0);
                    					}
                    					double irrigatedArea = irrigationAreaInfo.get("monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(paddykharif.get(id).get(source));
                    					irrigatedArea+=paddykharif.get(id).get(source);
                    					irrigationAreaInfo.get("monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area "+totalArea);
                    					totalArea+=paddykharif.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            					}
            					

            				}
                						
            			}
                	}
                	
                	/**
                	 * Tube well = ground water irrigation source
                	 */
                	if(source.equalsIgnoreCase("Tube well")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(computeTubewell.containsKey(IWMName)){
            						if(computeTubewell.get(IWMName)>0){
            							if(village_details.get(IWMName).crop_info.get("command")==null){
                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put("monsoon", new HashMap<String, Double>());
                    						obj = new HashMap<>();
                    						irrigationAreaInfo.get("monsoon").put("gw_irrigation", 0.0);
                    						obj.put("irrigationAreaInfo", irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 1100);
                    						obj.put("waterRequiredUnit", "mm");
                    						paddy.put("paddy", obj);
                    						village_details.get(IWMName).crop_info.put("command", paddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get(Constants.COMMAND_AREA).get(Constants.PADDY);
                    					
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
                    					
                    					if(irrigationAreaInfo == null)
                    						irrigationAreaInfo = new HashMap<>();
                    					if(irrigationAreaInfo.get(Constants.MONSOON) == null)
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    					if(irrigationAreaInfo.get(Constants.MONSOON).get(Constants.GROUND_WATER_IRRIGATION) == null)
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    					
                    					double irrigatedArea = irrigationAreaInfo.get(Constants.MONSOON).get(Constants.GROUND_WATER_IRRIGATION);
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(paddykharif.get(id).get(source));
                    					irrigatedArea += paddykharif.get(id).get(source);
                    					irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get(Constants.CROP_AREA));
                    					//System.out.println("total area command "+totalArea);
                    					totalArea += paddykharif.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            						else{
            							if(village_details.get(IWMName).crop_info.get(Constants.NON_COMMAND_AREA)==null){
                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    						obj = new HashMap<>();
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						
                    						obj2 = new HashMap<>();
                    						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj.put(Constants.CROP_AREA, 0.0);
                    						obj.put("waterRequired", 1100);
                    						obj.put("waterRequiredUnit", "mm");
                    						paddy.put("paddy", obj);
                    						village_details.get(IWMName).crop_info.put(Constants.NON_COMMAND_AREA, paddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get(Constants.NON_COMMAND_AREA).get(Constants.PADDY);
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
                    					
                    					if(irrigationAreaInfo.get(Constants.MONSOON) == null) {
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    					}
                    					//System.out.println(obj);
                    					if(irrigationAreaInfo.get(Constants.MONSOON).get(Constants.GROUND_WATER_IRRIGATION)==null){
                    						irrigationAreaInfo.get("monsoon").put("gw_irrigation", 0.0);
                    					}
                    					double irrigatedArea = irrigationAreaInfo.get("monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(paddykharif.get(id).get(source));
                    					irrigatedArea += paddykharif.get(id).get(source);
                    					irrigationAreaInfo.get("monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area noncommand"+totalArea);
                    					totalArea += paddykharif.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            					}
            					

            				}
                						
            			}
                	}
                }
             }
        	
        	//paddy rabi-------------------------------
        	for(int id:paddyrabi.keySet()){
                for(String source:paddyrabi.get(id).keySet()){
                				//System.out.println(source);
                	
                	/**
                	 * Canal = surface water irrigation
                	 */
                	if(source.equalsIgnoreCase("Canal")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("command")==null){
            						Map<String,Map<String,Object>> paddy=new HashMap<>();
            						obj = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj2 = new HashMap<>();
            						////obj2.put("irrigationAreaInfo", water);
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 1100);
            						obj.put("waterRequiredUnit", "mm");
            						paddy.put("paddy", obj);
            						village_details.get(IWMName).crop_info.put("command", paddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("command").get("paddy");
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
            					if(irrigationAreaInfo == null)
            						irrigationAreaInfo = new HashMap<>();
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.SURFACE_WATER_IRRIGATION) == null)
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            					
            					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get("surface_irrigation");
            					//System.out.println(irrigatedArea);
            					//System.out.println(paddykharif.get(id).get(source));
            					irrigatedArea+=paddyrabi.get(id).get(source);
            					//System.out.println(irrigatedArea);
            					irrigationAreaInfo.get("non_monsoon").put("surface_irrigation", irrigatedArea);
            					//System.out.println("**in canal "+village_details.get(IWMName).crop_info);
            					double totalArea = (double)(obj.get("cropArea"));
            					//System.out.println("total area "+totalArea);
            					totalArea+=paddyrabi.get(id).get(source);
            					//System.out.println(totalArea);
            					obj.put("cropArea", totalArea);
            				}
                						
            			}
                	}
                	
                	/**
                	 * Lift irrigation  = lift irrigation
                	 */
                	if(source.equalsIgnoreCase("Lift irrigation")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
            						Map<String,Map<String,Object>> paddy=new HashMap<>();
            						obj = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.LIFT_IRRIGATION, 0.0);
            						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj2 = new HashMap<>();
            						////obj2.put("irrigationAreaInfo", water);
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 1100);
            						obj.put("waterRequiredUnit", "mm");
            						paddy.put("paddy", obj);
            						village_details.get(IWMName).crop_info.put("non_command", paddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
            					
            					if(irrigationAreaInfo == null)
            						irrigationAreaInfo = new HashMap<>();
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.LIFT_IRRIGATION) == null)
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.LIFT_IRRIGATION, 0.0);
            					
            					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get(Constants.LIFT_IRRIGATION);
            					//System.out.println(irrigatedArea);
            					irrigatedArea += paddyrabi.get(id).get(source);
            					//System.out.println(irrigatedArea);
            					irrigationAreaInfo.get("non_monsoon").put(Constants.LIFT_IRRIGATION, irrigatedArea);
            					//System.out.println("***in lift irr"+village_details.get(IWMName).crop_info);
            					double totalArea = (double)(obj.get("cropArea"));
            					//System.out.println("total area "+totalArea);
            					totalArea += paddyrabi.get(id).get(source);
            					obj.put("cropArea", totalArea);

            				}
                						
            			}
                	}
                	
                	/**
                	 * Tanks = Surface water irrigation
                	 */
                	if(source.equalsIgnoreCase("Tanks")){
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
            						Map<String,Map<String,Object>> paddy=new HashMap<>();
            						obj = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);obj2 = new HashMap<>();
            						////obj2.put("irrigationAreaInfo", water);
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 1100);
            						obj.put("waterRequiredUnit", "mm");
            						paddy.put("paddy", obj);
            						village_details.get(IWMName).crop_info.put("non_command", paddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
            					
            					if(irrigationAreaInfo == null)
            						irrigationAreaInfo = new HashMap<>();
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.SURFACE_WATER_IRRIGATION) == null)
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            					
            					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get("surface_irrigation");
            					//System.out.println(irrigatedArea);
            					//System.out.println(paddyrabi.get(id).get(source));
            					irrigatedArea += paddyrabi.get(id).get(source);
            					irrigationAreaInfo.get("non_monsoon").put("surface_irrigation", irrigatedArea);
            					//System.out.println("***in Tanks"+village_details.get(IWMName).crop_info);
            					double totalArea = (double)(obj.get("cropArea"));
            					//System.out.println("total area "+totalArea);
            					totalArea += paddyrabi.get(id).get(source);
            					obj.put("cropArea", totalArea);

            				}
                						
            			}
                	}
                	
                	/**
                	 * MI Tanks = surface water irrigation
                	 */
                	if(source.equalsIgnoreCase("MI Tanks")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
            						Map<String,Map<String,Object>> paddy=new HashMap<>();
            						obj = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj2 = new HashMap<>();
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 1100);
            						obj.put("waterRequiredUnit", "mm");
            						paddy.put("paddy", obj);
            						village_details.get(IWMName).crop_info.put("non_command", paddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
            					
            					if(irrigationAreaInfo == null)
            						irrigationAreaInfo = new HashMap<>();
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.SURFACE_WATER_IRRIGATION) == null)
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            					
            					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get(Constants.SURFACE_WATER_IRRIGATION);
            					//System.out.println(irrigatedArea);
            					//System.out.println(paddyrabi.get(id).get(source));
            					irrigatedArea+=paddyrabi.get(id).get(source);
            					irrigationAreaInfo.get("non_monsoon").put(Constants.SURFACE_WATER_IRRIGATION, irrigatedArea);
            					//System.out.println("***in MI Tanks"+village_details.get(IWMName).crop_info);
            					double totalArea = (double)(obj.get("cropArea"));
            					//System.out.println("total area "+totalArea);
            					totalArea+=paddyrabi.get(id).get(source);
            					obj.put("cropArea", totalArea);

            				}
                						
            			}
                	}
                	
                	/**
                	 * Dug well = ground water irrigation
                	 */
                	if(source.equalsIgnoreCase("Dug well")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(computeDugwell.containsKey(IWMName)){
            						if(computeDugwell.get(IWMName)>0){
            							if(village_details.get(IWMName).crop_info.get("command")==null){
                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
                    						obj = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 1100);
                    						obj.put("waterRequiredUnit", "mm");
                    						paddy.put("paddy", obj);
                    						village_details.get(IWMName).crop_info.put("command", paddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get("command").get("paddy");
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
                    					
                    					if(irrigationAreaInfo == null)
                    						irrigationAreaInfo = new HashMap<>();
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.GROUND_WATER_IRRIGATION) == null)
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    					
                    					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(paddyrabi.get(id).get(source));
                    					irrigatedArea+=paddyrabi.get(id).get(source);
                    					irrigationAreaInfo.get("non_monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area "+totalArea);
                    					totalArea+=paddyrabi.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            						else{
            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
                    						obj = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 1100);
                    						obj.put("waterRequiredUnit", "mm");
                    						paddy.put("paddy", obj);
                    						village_details.get(IWMName).crop_info.put("non_command", paddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
                    					
                    					if(irrigationAreaInfo == null)
                    						irrigationAreaInfo = new HashMap<>();
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.GROUND_WATER_IRRIGATION) == null)
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    					
                    					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(paddyrabi.get(id).get(source));
                    					irrigatedArea+=paddyrabi.get(id).get(source);
                    					irrigationAreaInfo.get("non_monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area "+totalArea);
                    					totalArea += paddyrabi.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            					}
            					

            				}
                						
            			}
                	}
                	
                	/**
                	 * Tube well = ground water irrigation
                	 */
                	if(source.equalsIgnoreCase("Tube well")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(computeTubewell.containsKey(IWMName)){
            						if(computeTubewell.get(IWMName)>0){
            							if(village_details.get(IWMName).crop_info.get("command")==null){
                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
                    						obj = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 1100);
                    						obj.put("waterRequiredUnit", "mm");
                    						paddy.put("paddy", obj);
                    						village_details.get(IWMName).crop_info.put("command", paddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get("command").get("paddy");
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
                    					
                    					if(irrigationAreaInfo == null)
                    						irrigationAreaInfo = new HashMap<>();
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.GROUND_WATER_IRRIGATION) == null)
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    					
                    					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(paddyrabi.get(id).get(source));
                    					irrigatedArea+=paddyrabi.get(id).get(source);
                    					irrigationAreaInfo.get("non_monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area command "+totalArea);
                    					totalArea += paddyrabi.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            						else{
            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
                    						Map<String,Map<String,Object>> paddy=new HashMap<>();
                    						obj = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 1100);
                    						obj.put("waterRequiredUnit", "mm");
                    						paddy.put("paddy", obj);
                    						village_details.get(IWMName).crop_info.put("non_command", paddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("paddy");
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
                    					
                    					if(irrigationAreaInfo == null)
                    						irrigationAreaInfo = new HashMap<>();
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.GROUND_WATER_IRRIGATION) == null)
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    					
                    					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(paddyrabi.get(id).get(source));
                    					irrigatedArea+=paddyrabi.get(id).get(source);
                    					irrigationAreaInfo.get("non_monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area noncommand"+totalArea);
                    					totalArea += paddyrabi.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            					}
            					

            				}
                						
            			}
                	}
                }
             }
        	
        	//#TODO
        	//-------------------------------------non paddy--------------------------------------------
        	

        	
        	for(int id:nonpaddykharif.keySet()){
                for(String source:nonpaddykharif.get(id).keySet()){
                				//System.out.println(source);
                	
                	/**
                	 * Canal = surface water irrigation
                	 */
                	if(source.equalsIgnoreCase("Canal")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("command")==null){
            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
            						obj = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj2 = new HashMap<>();
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 600);
            						obj.put("waterRequiredUnit", "mm");
            						nonpaddy.put("nonPaddy", obj);
            						village_details.get(IWMName).crop_info.put("command", nonpaddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
            					
            					if(obj==null){
            						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
            						Map<String,Object>obj1 = new HashMap<>();
            						obj2 = new HashMap<>();
            						obj1 = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            						obj1.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj1.put("cropArea", 0.0);
            						obj1.put("waterRequired", 600);
            						obj1.put("waterRequiredUnit", "mm");
            						village_details.get(IWMName).crop_info.get("command").put("nonPaddy", obj1);
                					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");

            					}
            					
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
            					
            					if(irrigationAreaInfo == null)
            						irrigationAreaInfo = new HashMap<>();
            					if(irrigationAreaInfo.get(Constants.MONSOON) == null)
            						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
            					if(irrigationAreaInfo.get(Constants.MONSOON).get(Constants.SURFACE_WATER_IRRIGATION) == null)
            						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            					
            					double irrigatedArea = irrigationAreaInfo.get("monsoon").get("surface_irrigation");
            					//System.out.println(irrigatedArea);
            					//System.out.println(nonpaddykharif.get(id).get(source));
            					irrigatedArea += nonpaddykharif.get(id).get(source);
            					//System.out.println(irrigatedArea);
            					irrigationAreaInfo.get("monsoon").put("surface_irrigation", irrigatedArea);
            					//System.out.println("**in canal "+village_details.get(IWMName).crop_info);
            					double totalArea = (double)(obj.get("cropArea"));
            					//System.out.println("total area "+totalArea);
            					totalArea += nonpaddykharif.get(id).get(source);
            					//System.out.println(totalArea);
            					obj.put("cropArea", totalArea);
            				}
                						
            			}
                	}
                	/**
                	 * Lift Irrigation = lift irrigation
                	 */
                	if(source.equalsIgnoreCase("Lift irrigation")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
            						obj = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.LIFT_IRRIGATION, 0.0);
            						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj2 = new HashMap<>();
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 600);
            						obj.put("waterRequiredUnit", "mm");
            						nonpaddy.put("nonPaddy", obj);
            						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
            					if(obj==null){
            						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
            						Map<String,Object>obj1 = new HashMap<>();
            						obj2 = new HashMap<>();
            						obj1 = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.LIFT_IRRIGATION, 0.0);
            						obj1.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj1.put("cropArea", 0.0);
            						obj1.put("waterRequired", 600);
            						obj1.put("waterRequiredUnit", "mm");
            						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
                					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");

            					}
            					
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
            					
            					if(irrigationAreaInfo == null)
            						irrigationAreaInfo = new HashMap<>();
            					if(irrigationAreaInfo.get(Constants.MONSOON) == null)
            						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
            					if(irrigationAreaInfo.get(Constants.MONSOON).get(Constants.LIFT_IRRIGATION) == null)
            						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.LIFT_IRRIGATION, 0.0);
            					
            					
            					double irrigatedArea = irrigationAreaInfo.get("monsoon").get(Constants.LIFT_IRRIGATION);
            					//System.out.println(irrigatedArea);
            					irrigatedArea+=nonpaddykharif.get(id).get(source);
            					//System.out.println(irrigatedArea);
            					irrigationAreaInfo.get("monsoon").put(Constants.LIFT_IRRIGATION, irrigatedArea);
            					//System.out.println("***in lift irr"+village_details.get(IWMName).crop_info);
            					double totalArea = (double)(obj.get("cropArea"));
            					//System.out.println("total area "+totalArea);
            					totalArea+=nonpaddykharif.get(id).get(source);
            					obj.put("cropArea", totalArea);

            				}
                						
            			}
                	}
                	
                	/**
                	 * Tanks = surface water
                	 */
                	if(source.equalsIgnoreCase("Tanks")){
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
            						obj = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 600);
            						obj.put("waterRequiredUnit", "mm");
            						nonpaddy.put("nonPaddy", obj);
            						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
            					if(obj==null){
            						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
            						Map<String,Object>obj1 = new HashMap<>();
            						obj2 = new HashMap<>();
            						obj1 = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            						obj1.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj1.put("cropArea", 0.0);
            						obj1.put("waterRequired", 600);
            						obj1.put("waterRequiredUnit", "mm");
            						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
                					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");

            					}
            					
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
            					
            					if(irrigationAreaInfo == null)
            						irrigationAreaInfo = new HashMap<>();
            					if(irrigationAreaInfo.get(Constants.MONSOON) == null)
            						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
            					if(irrigationAreaInfo.get(Constants.MONSOON).get(Constants.SURFACE_WATER_IRRIGATION) == null)
            						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            					
            					double irrigatedArea = irrigationAreaInfo.get("monsoon").get("surface_irrigation");
            					//System.out.println(irrigatedArea);
            					//System.out.println(nonpaddykharif.get(id).get(source));
            					irrigatedArea += nonpaddykharif.get(id).get(source);
            					irrigationAreaInfo.get("monsoon").put("surface_irrigation", irrigatedArea);
            					//System.out.println("***in Tanks"+village_details.get(IWMName).crop_info);
            					double totalArea = (double)(obj.get("cropArea"));
            					//System.out.println("total area "+totalArea);
            					totalArea += nonpaddykharif.get(id).get(source);
            					obj.put("cropArea", totalArea);

            				}
                						
            			}
                	}
                	
                	/**
                	 * Mi tanks = surface water irrigation
                	 */
                	if(source.equalsIgnoreCase("MI Tanks")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
            						obj = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj2 = new HashMap<>();
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 600);
            						obj.put("waterRequiredUnit", "mm");
            						nonpaddy.put("nonPaddy", obj);
            						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
            					
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
            					
            					if(irrigationAreaInfo == null)
            						irrigationAreaInfo = new HashMap<>();
            					if(irrigationAreaInfo.get(Constants.MONSOON) == null)
            						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
            					if(irrigationAreaInfo.get(Constants.MONSOON).get(Constants.SURFACE_WATER_IRRIGATION) == null)
            						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            					
            					double irrigatedArea = irrigationAreaInfo.get("monsoon").get(Constants.SURFACE_WATER_IRRIGATION);
            					//System.out.println(irrigatedArea);
            					//System.out.println(nonpaddykharif.get(id).get(source));
            					irrigatedArea+=nonpaddykharif.get(id).get(source);
            					irrigationAreaInfo.get("monsoon").put(Constants.SURFACE_WATER_IRRIGATION, irrigatedArea);
            					//System.out.println("***in MI Tanks"+village_details.get(IWMName).crop_info);
            					double totalArea = (double)(obj.get("cropArea"));
            					//System.out.println("total area "+totalArea);
            					totalArea += nonpaddykharif.get(id).get(source);
            					obj.put("cropArea", totalArea);

            				}
                						
            			}
                	}

                	/**
                	 * Dug well = ground water
                	 */
                	if(source.equalsIgnoreCase("Dug well")){
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(computeDugwell.containsKey(IWMName)){
            						if(computeDugwell.get(IWMName)>0){
            							if(village_details.get(IWMName).crop_info.get("command")==null){
                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
                    						obj = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 600);
                    						obj.put("waterRequiredUnit", "mm");
                    						nonpaddy.put("nonPaddy", obj);
                    						village_details.get(IWMName).crop_info.put("command", nonpaddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
                    					//System.out.println(obj);
                    					if(obj==null){
                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
                    						Map<String,Object>obj1 = new HashMap<>();
                    						obj2 = new HashMap<>();
                    						obj1 = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj1.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj1.put("cropArea", 0.0);
                    						obj1.put("waterRequired", 600);
                    						obj1.put("waterRequiredUnit", "mm");
                    						village_details.get(IWMName).crop_info.get("command").put("nonPaddy", obj1);
                        					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");

                    					}
                    					
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
                    					
                    					if(irrigationAreaInfo == null)
                    						irrigationAreaInfo = new HashMap<>();
                    					if(irrigationAreaInfo.get(Constants.MONSOON) == null)
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    					if(irrigationAreaInfo.get(Constants.MONSOON).get(Constants.GROUND_WATER_IRRIGATION) == null)
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    					
                    					
                    					double irrigatedArea = irrigationAreaInfo.get("monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(nonpaddykharif.get(id).get(source));
                    					irrigatedArea+=nonpaddykharif.get(id).get(source);
                    					irrigationAreaInfo.get("monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area "+totalArea);
                    					totalArea+=nonpaddykharif.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            						else{
            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
                    						obj = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 600);
                    						obj.put("waterRequiredUnit", "mm");
                    						nonpaddy.put("nonPaddy", obj);
                    						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
                    					//System.out.println(obj);
                    					if(obj==null){
                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
                    						Map<String,Object>obj1 = new HashMap<>();
                    						obj2 = new HashMap<>();
                    						obj1 = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj1.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						
                    						obj1.put("cropArea", 0.0);
                    						obj1.put("waterRequired", 600);
                    						obj1.put("waterRequiredUnit", "mm");
                    						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
                        					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");

                    					}
                    					
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
                    					
                    					if(irrigationAreaInfo == null)
                    						irrigationAreaInfo = new HashMap<>();
                    					if(irrigationAreaInfo.get(Constants.MONSOON) == null)
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    					if(irrigationAreaInfo.get(Constants.MONSOON).get(Constants.GROUND_WATER_IRRIGATION) == null)
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    					
                    					
                    					double irrigatedArea = irrigationAreaInfo.get("monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(nonpaddykharif.get(id).get(source));
                    					irrigatedArea+=nonpaddykharif.get(id).get(source);
                    					irrigationAreaInfo.get("monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area "+totalArea);
                    					totalArea+=nonpaddykharif.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            					}
            					

            				}
                						
            			}
                	}
                	
                	/**
                	 * Tube well = ground water irrigation
                	 */
                	if(source.equalsIgnoreCase("Tube well")){
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
//            					System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(computeTubewell.containsKey(IWMName)){
            						if(computeTubewell.get(IWMName)>0){
            							if(village_details.get(IWMName).crop_info.get("command")==null){
                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
                    						obj = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 600);
                    						obj.put("waterRequiredUnit", "mm");
                    						nonpaddy.put("nonPaddy", obj);
                    						village_details.get(IWMName).crop_info.put("command", nonpaddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
                    					//System.out.println(obj);
                    					if(obj==null){
                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
                    						Map<String,Object>obj1 = new HashMap<>();
                    						obj1 = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj1.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						obj1.put("cropArea", 0.0);
                    						obj1.put("waterRequired", 600);
                    						obj1.put("waterRequiredUnit", "mm");
                    						village_details.get(IWMName).crop_info.get("command").put("nonPaddy", obj1);
                        					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");

                    					}
                    					
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
                    					
                    					if(irrigationAreaInfo == null)
                    						irrigationAreaInfo = new HashMap<>();
                    					if(irrigationAreaInfo.get(Constants.MONSOON) == null)
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    					if(irrigationAreaInfo.get(Constants.MONSOON).get(Constants.GROUND_WATER_IRRIGATION) == null)
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    					//System.out.println(obj);
                    					
                    					
                    					double irrigatedArea = irrigationAreaInfo.get("monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(nonpaddykharif.get(id).get(source));
                    					irrigatedArea+=nonpaddykharif.get(id).get(source);
                    					irrigationAreaInfo.get("monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area command "+totalArea);
                    					totalArea+=nonpaddykharif.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            						else{
            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
                    						obj = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 600);
                    						obj.put("waterRequiredUnit", "mm");
                    						nonpaddy.put("nonPaddy", obj);
                    						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
                    					//System.out.println(obj);
                    					if(obj==null){
                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
                    						Map<String,Object>obj1 = new HashMap<>();
                    						obj1 = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj1.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj1.put("cropArea", 0.0);
                    						obj1.put("waterRequired", 600);
                    						obj1.put("waterRequiredUnit", "mm");
                    						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
                        					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");

                    					}
                    					
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
                    					
                    					if(irrigationAreaInfo == null)
                    						irrigationAreaInfo = new HashMap<>();
                    					if(irrigationAreaInfo.get(Constants.MONSOON) == null)
                    						irrigationAreaInfo.put(Constants.MONSOON, new HashMap<String, Double>());
                    					if(irrigationAreaInfo.get(Constants.MONSOON).get(Constants.GROUND_WATER_IRRIGATION) == null)
                    						irrigationAreaInfo.get(Constants.MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    					
                    					
                    					double irrigatedArea = irrigationAreaInfo.get("monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(nonpaddykharif.get(id).get(source));
                    					irrigatedArea+=nonpaddykharif.get(id).get(source);
                    					irrigationAreaInfo.get("monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area noncommand"+totalArea);
                    					totalArea += nonpaddykharif.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            					}
            					

            				}
                						
            			}
                	}
                }
             }
        	
        	//nonpaddy rabi-------------------------------
        	for(int id:nonpaddyrabi.keySet()){
                for(String source:nonpaddyrabi.get(id).keySet()){
                				//System.out.println(source);
                	/**
                	 * Canal = surface water irrigation
                	 */
                	if(source.equalsIgnoreCase("Canal")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("command")==null){
            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
            						obj = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj2 = new HashMap<>();
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 600);
            						obj.put("waterRequiredUnit", "mm");
            						nonpaddy.put("nonPaddy", obj);
            						village_details.get(IWMName).crop_info.put("command", nonpaddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
            					if(obj==null){
            						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
            						Map<String,Object>obj1 = new HashMap<>();
            						obj1 = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            						obj1.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj2 = new HashMap<>();
            						
            						obj1.put("cropArea", 0.0);
            						obj1.put("waterRequired", 600);
            						obj1.put("waterRequiredUnit", "mm");
            						village_details.get(IWMName).crop_info.get("command").put("nonPaddy", obj1);
                					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");

            					}
            					
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
            					
            					if(irrigationAreaInfo == null)
            						irrigationAreaInfo = new HashMap<>();
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.SURFACE_WATER_IRRIGATION) == null)
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            					
            					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get("surface_irrigation");
            					//System.out.println(irrigatedArea);
            					//System.out.println(nonpaddykharif.get(id).get(source));
            					irrigatedArea+=nonpaddyrabi.get(id).get(source);
            					//System.out.println(irrigatedArea);
            					irrigationAreaInfo.get("non_monsoon").put("surface_irrigation", irrigatedArea);
            					//System.out.println("**in canal "+village_details.get(IWMName).crop_info);
            					double totalArea = (double)(obj.get("cropArea"));
            					//System.out.println("total area "+totalArea);
            					totalArea+=nonpaddyrabi.get(id).get(source);
            					//System.out.println(totalArea);
            					obj.put("cropArea", totalArea);
            				}
                						
            			}
                	}
                	
                	/**
                	 * lift irrigation = ground water
                	 */
                	if(source.equalsIgnoreCase("Lift irrigation")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
            						obj = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.LIFT_IRRIGATION, 0.0);
            						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj2 = new HashMap<>();
            						
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 600);
            						obj.put("waterRequiredUnit", "mm");
            						nonpaddy.put("nonPaddy", obj);
            						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
            					if(obj==null){
            						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
            						Map<String,Object>obj1 = new HashMap<>();
            						obj1 = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.LIFT_IRRIGATION, 0.0);
            						obj1.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						
            						obj1.put("cropArea", 0.0);
            						obj1.put("waterRequired", 600);
            						obj1.put("waterRequiredUnit", "mm");
            						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
                					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");

            					}
            					
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
            					
            					if(irrigationAreaInfo == null)
            						irrigationAreaInfo = new HashMap<>();
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.LIFT_IRRIGATION) == null)
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.LIFT_IRRIGATION, 0.0);
            					
            					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get(Constants.LIFT_IRRIGATION);
            					//System.out.println(irrigatedArea);
            					irrigatedArea+=nonpaddyrabi.get(id).get(source);
            					//System.out.println(irrigatedArea);
            					irrigationAreaInfo.get("non_monsoon").put(Constants.LIFT_IRRIGATION, irrigatedArea);
            					//System.out.println("***in lift irr"+village_details.get(IWMName).crop_info);
            					double totalArea = (double)(obj.get("cropArea"));
            					//System.out.println("total area "+totalArea);
            					totalArea += nonpaddyrabi.get(id).get(source);
            					obj.put("cropArea", totalArea);

            				}
                						
            			}
                	}
                	
                	/**
                	 * Tanks = surface water
                	 */
                	if(source.equalsIgnoreCase("Tanks")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
            						obj = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj2 = new HashMap<>();
            						
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 600);
            						obj.put("waterRequiredUnit", "mm");
            						nonpaddy.put("nonPaddy", obj);
            						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
            					if(obj==null){
            						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
            						Map<String,Object>obj1 = new HashMap<>();
            						obj1 = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            						obj1.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj2 = new HashMap<>();
            						
            						obj1.put("cropArea", 0.0);
            						obj1.put("waterRequired", 600);
            						obj1.put("waterRequiredUnit", "mm");
            						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
                					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");

            					}
            					
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
            					
            					if(irrigationAreaInfo == null)
            						irrigationAreaInfo = new HashMap<>();
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.SURFACE_WATER_IRRIGATION) == null)
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            					
            					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get("surface_irrigation");
            					//System.out.println(irrigatedArea);
            					//System.out.println(nonpaddyrabi.get(id).get(source));
            					irrigatedArea+=nonpaddyrabi.get(id).get(source);
            					irrigationAreaInfo.get("non_monsoon").put("surface_irrigation", irrigatedArea);
            					//System.out.println("***in Tanks"+village_details.get(IWMName).crop_info);
            					double totalArea = (double)(obj.get("cropArea"));
            					//System.out.println("total area "+totalArea);
            					totalArea+=nonpaddyrabi.get(id).get(source);
            					obj.put("cropArea", totalArea);

            				}
                						
            			}
                	}
                	
                	/**
                	 * mi tanks = surface water
                	 */
                	if(source.equalsIgnoreCase("MI Tanks")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(village_details.get(IWMName).crop_info.get("non_command")==null){
            						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
            						obj = new HashMap<>();
            						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
            						obj2 = new HashMap<>();
            						
            						obj.put("cropArea", 0.0);
            						obj.put("waterRequired", 600);
            						obj.put("waterRequiredUnit", "mm");
            						nonpaddy.put("nonPaddy", obj);
            						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						

            					}
            					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
            					
            					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
            					
            					if(irrigationAreaInfo == null)
            						irrigationAreaInfo = new HashMap<>();
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
            						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
            					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.SURFACE_WATER_IRRIGATION) == null)
            						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.SURFACE_WATER_IRRIGATION, 0.0);
            					
            					
            					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get(Constants.SURFACE_WATER_IRRIGATION);
            					//System.out.println(irrigatedArea);
            					//System.out.println(nonpaddyrabi.get(id).get(source));
            					irrigatedArea+=nonpaddyrabi.get(id).get(source);
            					irrigationAreaInfo.get("non_monsoon").put(Constants.SURFACE_WATER_IRRIGATION, irrigatedArea);
            					//System.out.println("***in MI Tanks"+village_details.get(IWMName).crop_info);
            					double totalArea = (double)(obj.get("cropArea"));
            					//System.out.println("total area "+totalArea);
            					totalArea+=nonpaddyrabi.get(id).get(source);
            					obj.put("cropArea", totalArea);

            				}
                						
            			}
                	}
                	
                	/**
                	 * Dug well = ground water irrigation
                	 */
                	if(source.equalsIgnoreCase("Dug well")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(computeDugwell.containsKey(IWMName)){
            						if(computeDugwell.get(IWMName)>0){
            							if(village_details.get(IWMName).crop_info.get("command")==null){
                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
                    						obj = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 600);
                    						obj.put("waterRequiredUnit", "mm");
                    						nonpaddy.put("nonPaddy", obj);
                    						village_details.get(IWMName).crop_info.put("command", nonpaddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
                    					//System.out.println(obj);
                    					if(obj==null){
                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
                    						Map<String,Object>obj1 = new HashMap<>();
                    						obj1 = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj1.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						
                    						obj1.put("cropArea", 0.0);
                    						obj1.put("waterRequired", 600);
                    						obj1.put("waterRequiredUnit", "mm");
                    						village_details.get(IWMName).crop_info.get("command").put("nonPaddy", obj1);
                        					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");

                    					}
                    					
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
                    					
                    					if(irrigationAreaInfo == null)
                    						irrigationAreaInfo = new HashMap<>();
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.GROUND_WATER_IRRIGATION) == null)
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    					
                    					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(nonpaddyrabi.get(id).get(source));
                    					irrigatedArea+=nonpaddyrabi.get(id).get(source);
                    					irrigationAreaInfo.get("non_monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area "+totalArea);
                    					totalArea += nonpaddyrabi.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            						else{
            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
                    						obj = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 600);
                    						obj.put("waterRequiredUnit", "mm");
                    						nonpaddy.put("nonPaddy", obj);
                    						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
                    					//System.out.println(obj);
                    					if(obj==null){
                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
                    						Map<String,Object>obj1 = new HashMap<>();
                    						obj1 = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj1.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						
                    						obj1.put("cropArea", 0.0);
                    						obj1.put("waterRequired", 600);
                    						obj1.put("waterRequiredUnit", "mm");
                    						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
                        					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");

                    					}
                    					
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
                    					
                    					if(irrigationAreaInfo == null)
                    						irrigationAreaInfo = new HashMap<>();
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.GROUND_WATER_IRRIGATION) == null)
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    					
                    					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(nonpaddyrabi.get(id).get(source));
                    					irrigatedArea+=nonpaddyrabi.get(id).get(source);
                    					irrigationAreaInfo.get("non_monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area "+totalArea);
                    					totalArea+=nonpaddyrabi.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            					}
            					

            				}
                						
            			}
                	}
                	
                	/**
                	 * Tube well = ground water irrigation
                	 */
                	if(source.equalsIgnoreCase("Tube well")){
            			
                		Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//System.out.println(IWMName + id);
            					Map<String,Object> obj;
            					Map<String,Map<String,Double>> obj2;
            					Map<String,Double> water=new HashMap<>();
            					if(computeTubewell.containsKey(IWMName)){
            						if(computeTubewell.get(IWMName)>0){
            							if(village_details.get(IWMName).crop_info.get("command")==null){
                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
                    						obj = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 600);
                    						obj.put("waterRequiredUnit", "mm");
                    						nonpaddy.put("nonPaddy", obj);
                    						village_details.get(IWMName).crop_info.put("command", nonpaddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");
                    					//System.out.println(obj);
                    					if(obj==null){
                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
                    						Map<String,Object>obj1 = new HashMap<>();
                    						obj1 = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj1.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						
                    						obj1.put("cropArea", 0.0);
                    						obj1.put("waterRequired", 600);
                    						obj1.put("waterRequiredUnit", "mm");
                    						village_details.get(IWMName).crop_info.get("command").put("nonPaddy", obj1);
                        					obj = village_details.get(IWMName).crop_info.get("command").get("nonPaddy");

                    					}
                    					
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
                    					
                    					if(irrigationAreaInfo == null)
                    						irrigationAreaInfo = new HashMap<>();
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.GROUND_WATER_IRRIGATION) == null)
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    					
                    					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(nonpaddyrabi.get(id).get(source));
                    					irrigatedArea+=nonpaddyrabi.get(id).get(source);
                    					irrigationAreaInfo.get("non_monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area command "+totalArea);
                    					totalArea+=nonpaddyrabi.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            						else{
            							if(village_details.get(IWMName).crop_info.get("non_command")==null){
                    						Map<String,Map<String,Object>> nonpaddy=new HashMap<>();
                    						obj = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						
                    						obj.put("cropArea", 0.0);
                    						obj.put("waterRequired", 600);
                    						obj.put("waterRequiredUnit", "mm");
                    						nonpaddy.put("nonPaddy", obj);
                    						village_details.get(IWMName).crop_info.put("non_command", nonpaddy);             						

                    					}
                    					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");
                    					if(obj==null){
                    						//System.out.println(village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy"));
                    						Map<String,Object>obj1 = new HashMap<>();
                    						obj1 = new HashMap<>();
                    						Map<String, Map<String, Double>> irrigationAreaInfo = new HashMap<>();
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    						obj1.put(Constants.IRRIGATION_AREA_INFO, irrigationAreaInfo);
                    						obj2 = new HashMap<>();
                    						
                    						obj1.put("cropArea", 0.0);
                    						obj1.put("waterRequired", 600);
                    						obj1.put("waterRequiredUnit", "mm");
                    						village_details.get(IWMName).crop_info.get("non_command").put("nonPaddy", obj1);
                        					obj = village_details.get(IWMName).crop_info.get("non_command").get("nonPaddy");

                    					}
                    					
                    					Map<String, Map<String, Double>> irrigationAreaInfo = (Map<String, Map<String, Double>>) obj.get(Constants.IRRIGATION_AREA_INFO);
                    					
                    					if(irrigationAreaInfo == null)
                    						irrigationAreaInfo = new HashMap<>();
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON) == null)
                    						irrigationAreaInfo.put(Constants.NON_MONSOON, new HashMap<String, Double>());
                    					if(irrigationAreaInfo.get(Constants.NON_MONSOON).get(Constants.GROUND_WATER_IRRIGATION) == null)
                    						irrigationAreaInfo.get(Constants.NON_MONSOON).put(Constants.GROUND_WATER_IRRIGATION, 0.0);
                    					
                    					double irrigatedArea = irrigationAreaInfo.get("non_monsoon").get("gw_irrigation");
                    					//System.out.println(irrigatedArea);
                    					//System.out.println(nonpaddyrabi.get(id).get(source));
                    					irrigatedArea+=nonpaddyrabi.get(id).get(source);
                    					irrigationAreaInfo.get("non_monsoon").put("gw_irrigation", irrigatedArea);
                    					//System.out.println("***in dugwells"+village_details.get(IWMName).crop_info);
                    					double totalArea = (double)(obj.get("cropArea"));
                    					//System.out.println("total area noncommand"+totalArea);
                    					totalArea+=nonpaddyrabi.get(id).get(source);
                    					obj.put("cropArea", totalArea);
            						}
            					}
            					

            				}
                						
            			}
                	}
                }
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
                String fields[] = record.split(",", -1);
                if(fields.length==10 || fields.length==format.convert("ah")){
                	Mitank cmitank= new Mitank((Utils.parseDouble(fields[4])),Utils.parseDouble(fields[5]),0,120,150,0.00144);
                	Command wbcommand = new Command(cmitank);
                	Mitank ncmitank= new Mitank((Utils.parseDouble(fields[6])),Utils.parseDouble(fields[7]),0,120,150,0.00144);
                	NonCommand nonCommand = new NonCommand(ncmitank);
                	Mitank cpqmitank= new Mitank((Utils.parseDouble(fields[8])),Utils.parseDouble(fields[9])*0.6,0,120,150,0.00144);
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
                    		cmitank= new Mitank((Utils.parseDouble(fields[4]))+waterbd.command.mitank.count,((Utils.parseDouble(fields[5])+waterbd.command.mitank.spreadArea)),0,120,150,0.00144);
                        	wbcommand = new Command(cmitank);
                        	ncmitank= new Mitank((Utils.parseDouble(fields[6]))+waterbd.non_command.mitank.count,((Utils.parseDouble(fields[7])+waterbd.non_command.mitank.spreadArea)),0,120,150,0.00144);
                        	nonCommand = new NonCommand(ncmitank);
                        	cpqmitank= new Mitank((Utils.parseDouble(fields[8]))+waterbd.poor_quality.mitank.count,((Utils.parseDouble(fields[9])+waterbd.poor_quality.mitank.spreadArea)),0,120,150,0.00144);
                        	commandpoorQuality = new CommandPoorQuality(cpqmitank);
                            waterbd = new waterbodies(wbcommand,nonCommand,commandpoorQuality);
                            waterbodyjson = waterbody.toJson(waterbd);
                   			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setWaterbodies(waterbd);

                    	}
                	}
                   // System.out.println(waterbodyjson);
                } else {
                	System.out.println("waterbodies : field length not matching" +fields.length);
                }
            }
            
            /*for(String key:village_details.keySet()){
            	if(village_details.get(key).water_bodies==null){
            		//System.out.println(key);
            		System.out.println(village_details.get(key).getVillageName());
            	}
            }*/
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
            	
                String fields[] = record.split(",",-1);
//                if(fields.length==16){
                if(!fields[format.convert("b")].contains(" ") && !fields[format.convert("b")].isEmpty()){
                	String villageName = fields[format.convert("d")];
                	String mb = fields[format.convert("c")];
                	Map<String,Integer> runningDays = new HashMap<>();

                	canalMap.put("length", Utils.parseDouble(fields[4]));
                	canalMap.put("type","0");
                    canalMap.put("sideSlopes",Utils.parseDouble(fields[7]));//sideSlopes
                    canalMap.put("wettedPerimeter",Utils.parseDouble(fields[8]));//wettedperimeter
                    canalMap.put("wettedArea",Utils.parseDouble(fields[9]));//wettedarea
                    canalMap.put("seepageFactor",Utils.parseDouble(fields[10]));//canalseepagefactor
                    canalMap.put("bedWidth",Utils.parseDouble(fields[6]));//bedwidth
                    canalMap.put("designDepthFlow",Utils.parseDouble("0"));//designDepthOfFlow
                    runningDays.put("monsoon",Utils.parseInt(fields[11]));//runningDays_monsoon
                    runningDays.put("non_monsoon",Utils.parseInt(fields[12]));//runningDays_nonmonsoon
                    canalMap.put("noOfRunningDays", runningDays);
                    canal.add(canalMap);
//                    if(villageName.equals("PEDARAMABHADRAPURAM")){
//                    	System.out.println("canal : " + canalMap);
//                    }
//                    
                    if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
                		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getCanal()==null){
                        	count++;
                        	village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setCanal(canal);
                        }else{
                        	village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getCanal().add(canalMap);
                        }
                                        	
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
//            System.out.println(locmapping.keySet());
            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",",-1);
//                System.out.println("ANKIT ::: artificial length : " + fields.length);
                 if(fields.length==34){
//                	System.out.println("ANKIT ::: record.length : " + fields.length);
//                	System.out.println("ANKIT :: record : " + record);
                 	PercolationTanks cpt= new PercolationTanks(fields[format.convert("e")].isEmpty() ? 0 : (Double.parseDouble(fields[format.convert("e")])),(fields[format.convert("f")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("f")])),(Double.parseDouble("1.5")),0.5);
                 	MiniPercolationTanks cmpt= new MiniPercolationTanks(fields[format.convert("g")].isEmpty() ? 0 :(Double.parseDouble(fields[format.convert("g")])),(fields[format.convert("h")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("h")])),(Double.parseDouble("1.5")),0.5);
                 	CheckDams ccd= new CheckDams(fields[format.convert("i")].isEmpty() ? 0 :(Double.parseDouble(fields[format.convert("i")])),(fields[format.convert("j")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("j")])),(Double.parseDouble("6")),0.5);
                 	FarmPonds fp = new FarmPonds((fields[format.convert("k")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("k")])),(fields[format.convert("l")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("l")])),(Double.parseDouble("22")),0.5);
                 	Other other = new Other(fields[format.convert("m")].isEmpty() ? 0 :(Double.parseDouble(fields[format.convert("m")])),(fields[format.convert("n")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("n")])),(Double.parseDouble("10")),0.5);
                 	ArtificialWCCommand artificialWCCommand = new ArtificialWCCommand(cpt,cmpt,ccd,fp,other);
                 	
                 	PercolationTanks ncpt= new PercolationTanks(fields[format.convert("o")].isEmpty() ? 0 :(Double.parseDouble(fields[format.convert("o")])),fields[format.convert("p")].isEmpty() ? 0 :(Double.parseDouble(fields[format.convert("p")])),(Double.parseDouble("1.5")),0.5);
                 	MiniPercolationTanks ncmpt= new MiniPercolationTanks(fields[format.convert("q")].isEmpty() ? 0 :(Double.parseDouble(fields[format.convert("q")])),(fields[format.convert("r")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("r")])),(Double.parseDouble("1.5")),0.5);
                 	CheckDams nccd= new CheckDams(fields[format.convert("s")].isEmpty() ? 0 :(Double.parseDouble(fields[format.convert("s")])),(fields[format.convert("t")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("t")])),(Double.parseDouble("6")),0.5);
                 	FarmPonds ncfp = new FarmPonds(fields[format.convert("u")].isEmpty() ? 0 :(Double.parseDouble(fields[format.convert("u")])),(fields[format.convert("v")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("v")])),(Double.parseDouble("22")),0.5);
                 	Other ncother = new Other(fields[format.convert("w")].isEmpty() ? 0 :(Double.parseDouble(fields[format.convert("w")])),(fields[format.convert("x")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("x")])),(Double.parseDouble("10")),0.5);
                 	ArtificialWCNonCommand artificialWCNonCommand = new ArtificialWCNonCommand(ncpt,ncmpt,nccd,ncfp,ncother);
                 	
                 	PercolationTanks pqpt= new PercolationTanks(fields[format.convert("y")].isEmpty() ? 0 :(Double.parseDouble(fields[format.convert("y")])),(fields[format.convert("z")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("z")])),(Double.parseDouble("1.5")),0.5);
                 	MiniPercolationTanks pqmpt= new MiniPercolationTanks(fields[format.convert("aa")].isEmpty() ? 0 :(Double.parseDouble(fields[format.convert("aa")])),(fields[format.convert("ab")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("ab")])),(Double.parseDouble("1.5")),0.5);
                 	CheckDams pqcd= new CheckDams(fields[format.convert("ac")].isEmpty() ? 0 :(Double.parseDouble(fields[format.convert("ac")])),(fields[format.convert("ad")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("ad")])),(Double.parseDouble("6")),0.5);
                 	FarmPonds pqfp = new FarmPonds(fields[format.convert("ae")].isEmpty() ? 0 :(Double.parseDouble(fields[format.convert("ae")])),(fields[format.convert("af")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("af")])),(Double.parseDouble("22")),0.5);
                 	Other pqother = new Other(fields[format.convert("ag")].isEmpty() ? 0 :(Double.parseDouble(fields[format.convert("ag")])),(fields[format.convert("ah")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("ah")])),(Double.parseDouble("10")),0.5);
                 	ArtificialWCPPoorQuality artificialWCPPoorQuality = new ArtificialWCPPoorQuality(pqpt,pqmpt,pqcd,pqfp,pqother);

                 	ArtificialWC artificialWC = new ArtificialWC(artificialWCCommand,artificialWCNonCommand,artificialWCPPoorQuality);
                 	String artificialwcjson = artificialwc.toJson(artificialWC);
//                 	System.out.println("ANKIT ::: outside locMaping");
                 	if(locmapping.containsKey(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))){
//                 		System.out.println("ANKIT ::: inside locMaping");
                 		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getArtificialWC()==null){
                         	count++;
                         	village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setArtificialWC(artificialWC);
                 		}else{
                 			artificialWC= village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).getArtificialWC();
                 			cpt= new PercolationTanks((fields[format.convert("e")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("e")]))+artificialWC.command.pt.count,(fields[format.convert("f")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("f")]))+artificialWC.command.pt.capacity,(Double.parseDouble("1.5")),0.5);
                         	cmpt= new MiniPercolationTanks((fields[format.convert("g")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("g")]))+artificialWC.command.mpt.count,(fields[format.convert("h")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("h")]))+artificialWC.command.mpt.capacity,(Double.parseDouble("1.5")),0.5);
                         	ccd= new CheckDams((fields[format.convert("i")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("i")]))+artificialWC.command.cd.count,(fields[format.convert("j")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("j")]))+artificialWC.command.cd.capacity,(Double.parseDouble("6")),0.5);
                         	fp = new FarmPonds((fields[format.convert("k")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("k")]))+artificialWC.command.fp.count,(fields[format.convert("l")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("l")]))+artificialWC.command.fp.capacity,(Double.parseDouble("22")),0.5);
                         	other = new Other((fields[format.convert("m")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("m")]))+artificialWC.command.others.count,(fields[format.convert("n")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("n")]))+artificialWC.command.others.capacity,(Double.parseDouble("10")),0.5);
                         	artificialWCCommand = new ArtificialWCCommand(cpt,cmpt,ccd,fp,other);
                         	
                         	ncpt= new PercolationTanks((fields[format.convert("o")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("o")]))+artificialWC.non_command.pt.count,(fields[format.convert("p")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("p")]))+artificialWC.non_command.pt.capacity,(Double.parseDouble("1.5")),0.5);
                         	ncmpt= new MiniPercolationTanks((fields[format.convert("q")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("q")]))+artificialWC.non_command.mpt.count,(fields[format.convert("r")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("r")]))+artificialWC.non_command.mpt.capacity,(Double.parseDouble("1.5")),0.5);
                         	nccd= new CheckDams((fields[format.convert("s")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("s")]))+artificialWC.non_command.cd.count,(fields[format.convert("t")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("t")]))+artificialWC.non_command.cd.capacity,(Double.parseDouble("6")),0.5);
                         	ncfp = new FarmPonds((fields[format.convert("u")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("u")]))+artificialWC.non_command.fp.count,(fields[format.convert("v")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("v")]))+artificialWC.non_command.fp.capacity,(Double.parseDouble("22")),0.5);
                         	ncother = new Other((fields[format.convert("w")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("w")]))+artificialWC.non_command.others.count,(fields[format.convert("x")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("x")]))+artificialWC.non_command.others.capacity,(Double.parseDouble("10")),0.5);
                         	artificialWCNonCommand = new ArtificialWCNonCommand(ncpt,ncmpt,nccd,ncfp,ncother);
                         	
                         	pqpt= new PercolationTanks((fields[format.convert("y")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("y")])),(fields[format.convert("z")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("z")])),(Double.parseDouble("1.5")),0.5);
                         	pqmpt= new MiniPercolationTanks((fields[format.convert("aa")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("aa")])),(fields[format.convert("ab")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("ab")])),(Double.parseDouble("1.5")),0.5);
                         	pqcd= new CheckDams((fields[format.convert("ac")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("ac")])),(fields[format.convert("ad")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("ad")])),(Double.parseDouble("6")),0.5);
                         	pqfp = new FarmPonds((fields[format.convert("ae")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("ae")])),(fields[format.convert("af")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("af")])),(Double.parseDouble("22")),0.5);
                         	pqother = new Other((fields[format.convert("ag")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("ag")])),(fields[format.convert("ah")].isEmpty() ? 0 :Double.parseDouble(fields[format.convert("ah")])),(Double.parseDouble("10")),0.5);
                         	artificialWCPPoorQuality = new ArtificialWCPPoorQuality(pqpt,pqmpt,pqcd,pqfp,pqother);

                         	artificialWC = new ArtificialWC(artificialWCCommand,artificialWCNonCommand,artificialWCPPoorQuality);
                         	artificialwcjson = artificialwc.toJson(artificialWC);
                         	village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("d")]))).setArtificialWC(artificialWC);

                 		}
                 	}
                 	else{
                 		System.out.println(" location : " + format.removeQuotes(format.removeQuotes(fields[format.convert("c")])
                 				+"##"+format.removeQuotes(fields[format.convert("d")])));
                 	}
                    // System.out.println(artificialwcjson);
                 }
                 else{
                	 System.out.println("Artificial WC : invalid row : field length : " + fields.length);
                 }
             }
            for(String key:village_details.keySet()){ 
            	if(village_details.get(key).aritificial_wc==null){
            		//System.out.println(key);
            		//System.out.println(village_details.get(key).getVillageName());
            	}
            }
            System.out.println("artificial wc count= "+count);
        }catch (IOException e) {
            e.printStackTrace();
        }
    
        
     // json for population
        Gson populationjsn = new Gson();

        try(BufferedReader iem = new BufferedReader(new FileReader(populationfile))) {
              
          	//readXLSXFile();
              record = iem.readLine();
              Population populationObj;
              String populationJson;
              while((record = iem.readLine()) != null) {
                  //System.out.println("record"+record);
                  String fields[] = record.split(",",-1);
                 // System.out.println("fields"+fields);
                  int lpcd =0;
                  int referenceYear = 2011;
                  double growthRate = 3.76;
                  Map<String, Integer> populationMap = new HashMap<>();
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
                  	
                  	lpcd = (totalPopulation == 0)?0:(lpcd/totalPopulation);
                  	populationMap.put(Constants.COMMAND_AREA, command);
                  	populationMap.put(Constants.NON_COMMAND_AREA, noncommand);
                  	if(locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("e")]))){
                      //	System.out.println("hello");
                  		if(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("e")]))).population==null){
                  			populationObj = new Population(lpcd, referenceYear, totalPopulation, growthRate, populationMap);
                          	populationJson = populationjsn.toJson(populationObj);
                  			village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("e")]))).setPopulation(populationJson);
                		}
                  		else{
//                  			System.out.println(fields[format.convert("e")] + "else me aya");
                  			populationObj= populationjsn.fromJson(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("e")]))).getPopulation(),Population.class);
                  			if(populationObj == null)
                  				populationObj = new Population();
                  			populationObj.setLpcd(lpcd);
                  			populationObj.setTotalPopulation(totalPopulation);
                  			if(populationObj.getAreaWisePopulation() == null)
                  				populationObj.setAreaWisePopulation(new HashMap<String, Integer>());
                  			
                  			populationObj.getAreaWisePopulation().put(Constants.COMMAND_AREA, command);
                  			populationObj.getAreaWisePopulation().put(Constants.NON_COMMAND_AREA, noncommand);
//                  			lpcd+=populationObj.lpcd;
//                  			totalPopulation+=populationObj.totalPopulation;
//                  			command += populationMap.get(Constants.COMMAND_AREA);
//                  			noncommand += populationMap.get(Constants.NON_COMMAND_AREA);
//                  			populationObj = new Population(lpcd,totalPopulation,command,noncommand);
                          	populationJson = populationjsn.toJson(populationObj);
                            village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])+"##"+format.removeQuotes(fields[format.convert("e")]))).setPopulation(populationJson);
                  		}
                      }
                      
                  }
              }
              
          }catch (IOException e) {
              e.printStackTrace();
          }

        
        
        // json for gw and rainfall
        try(BufferedReader iem = new BufferedReader(new FileReader(gw_rf_file))) {
              
              record = iem.readLine();
              while((record = iem.readLine()) != null) {
                  String fields[] = record.split(",",-1);

                  
                  String basinName = format.removeQuotes(fields[format.convert("b")]);
                  
                  for (String basinKey : locmapping.keySet()) {
                	
                	  if(basinKey.split("##")[0].equals(basinName) ) {
                		  
                		  Map<String, Map<String,Double>> gw = new HashMap<String, Map<String,Double>>();
                		  gw.put("command", new HashMap<String, Double>());
                		  gw.put("non_command", new HashMap<String, Double>());
                		  gw.get("command").put("pre", Utils.parseDouble(fields[format.convert("f")]));
                		  gw.get("command").put("post", Utils.parseDouble(fields[format.convert("g")]));
                		  gw.get("non_command").put("pre", Utils.parseDouble(fields[format.convert("d")]));
                		  gw.get("non_command").put("post", Utils.parseDouble(fields[format.convert("e")]));
                		  village_details.get(locmapping.get(basinKey)).gw_data = (new Gson()).toJson(gw);
                		  
                		  
                		  Map<String, Map<String,Double>> rf = new HashMap<String, Map<String,Double>>();
                		  rf.put("monsoon", new HashMap<String, Double>());
                		  rf.put("non_monsoon", new HashMap<String, Double>());
                		  rf.get("monsoon").put("normal", Utils.parseDouble(fields[format.convert("j")]));
                		  rf.get("non_monsoon").put("normal", Utils.parseDouble(fields[format.convert("k")]));
                		  rf.get("monsoon").put("actual", Utils.parseDouble(fields[format.convert("m")]));
                		  rf.get("non_monsoon").put("actual", Utils.parseDouble(fields[format.convert("n")]));                		  
                		  village_details.get(locmapping.get(basinKey)).rf_data =  (new Gson()).toJson(rf);

                		  
                		  //TODO verify it .... Domestic
	                   	  Map<String, Double> gwDependency = new HashMap<String, Double>();
	                   	  gwDependency.put(Constants.DOMESTIC, Utils.parseDouble(fields[format.convert("h")]));
                		  village_details.get(locmapping.get(basinKey)).setGwDependencyFactor(gwDependency);
                		  
                	  }
                  }
              }
              
          }catch (IOException e) {
              e.printStackTrace();
          }
        
        
        //json for village
        try (BufferedWriter file = new BufferedWriter(new FileWriter(assessmentUnitCQLScriptFile))) {
        	
        	bw.newLine();
        	bw.write("/* **** Scripts for district : " + districtName + " **** */");
        	bw.newLine();
        	bw.newLine();
	        
        	Gson Village = new Gson();
	        for(String villageName:village_details.keySet()){
	        	Village villageObj = village_details.get(villageName);
	        	Village_Data village_obj=new Village_Data(villageObj);
	            String villagejson = Village.toJson(village_obj);
	            
	                String query = "Insert into location_md JSON '"+villagejson+"';";
	                file.write(query + "\n");
	                file.newLine();
	                
	                bw.write(query + "\n");
	                bw.newLine();
	        }
	        System.out.println("Finished creating script file. Total Villages : " + village_details.size());

        } catch (IOException e) {
            e.printStackTrace();
        }    

    }
}


