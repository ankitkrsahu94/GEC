import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class VillageMetaData {

    
	public static void compute(String districtName, String path, BufferedWriter bw) {

		String filePath = path+districtName+"/";

		String assesssment_year = Constants.GEC_ASSESSMENT_YEAR.split("_")[0];
		/**
		 * Output files
		 */
		String assessmentUnitCQLScriptFile = path+"final_scripts/villageFiles/"+districtName+"-villMD-"+Constants.GEC_ASSESSMENT_YEAR+".cql";
		String villMBAssocFile = path+"final_scripts/locIntersectionFiles/"+districtName+"-villMBIntersection-"+Constants.GEC_ASSESSMENT_YEAR+".cql";
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
        String cropDataFile = filePath+"applied_irrigation.csv";
        String sw_irr_file = filePath+"sw_irr.csv";

        
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
        String villageUUIDFile = filePath+"../base_md/location/village_uuid.csv";
        
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
        Map<String, Map<String, Area>> villageMBAreaInfo = new HashMap<String, Map<String,Area>>();

       
        //loc mapping
        try(BufferedReader iem = new BufferedReader(new FileReader(gwlocToIWMloc))) {
            Map<String, Integer> keyRepetition = new HashMap<String, Integer>();
        	//readXLSXFile();
            record = iem.readLine();
            while((record = iem.readLine()) != null) {
                //System.out.println("record"+record);

                String fields[] = record.split(",");
               // System.out.println("fields"+fields);
                if(fields.length==4){
                	String gwMBName;
                	String gwVillName;
                	String iwmVillName;
                	String iwmMBName;
                	if(!format.removeQuotes(fields[format.convert("a")]).isEmpty() && !format.removeQuotes(fields[format.convert("c")]).isEmpty() && !format.removeQuotes(fields[format.convert("d")]).isEmpty()&& !format.removeQuotes(fields[format.convert("d")]).equalsIgnoreCase("#N/A")){
                    	gwMBName = format.removeQuotes(fields[format.convert("a")]);
                    	iwmMBName=format.removeQuotes(fields[format.convert("b")]);
                		gwVillName = format.removeQuotes(fields[format.convert("c")]);
                        iwmVillName = format.removeQuotes(fields[format.convert("d")]);
                        
                        String MicroBasin_GWvillage_key = gwMBName + "##" +gwVillName;
                        
                        if(keyRepetition.containsKey(MicroBasin_GWvillage_key)){
                        	int times = keyRepetition.get(MicroBasin_GWvillage_key)+1;
                        	keyRepetition.put(MicroBasin_GWvillage_key, times);
                        	MicroBasin_GWvillage_key = MicroBasin_GWvillage_key + "-" + String.valueOf(times);
                        }else
                        	keyRepetition.put(MicroBasin_GWvillage_key, 0);
                        	
                        locmapping.put(MicroBasin_GWvillage_key,iwmVillName);
                        basinmapping.put(gwMBName,iwmMBName);
                	}
                	
                }
               
                //System.out.println("**"+MicroBasin_GWvillage_key);
            }
            int c1=0;
            for(String key:locmapping.keySet()){
            	//c1++;
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
                    double area = Utils.parseDouble(fields[format.convert("c")]);
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
                    double area = Utils.parseDouble(fields[format.convert("c")]);
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
                    double area = Utils.parseDouble(fields[format.convert("c")]);
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
                    double area = Utils.parseDouble(fields[format.convert("c")]);
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
            			yield = (yield == 0.0)?Constants.DEFAULT_AGRO_WELL_YIELD:yield;
            			yield = yield * (7*Constants.LITRES_TO_HAM);
            			yield = Utils.round(yield, Constants.DECIMAL);
            			monsoonDays = (fields[5+(12*index)].isEmpty())?0.0:Utils.parseDouble(fields[5+(12*index)]);
                		operationDaysAgriculture.put(Constants.MONSOON, monsoonDays);
                		nonMonsoonDays = (fields[6+(12*index)].isEmpty())?0.0:Utils.parseDouble(fields[6+(12*index)]);
                		operationDaysAgriculture.put(Constants.NON_MONSOON, nonMonsoonDays);
                		wellDataAgriculture.setYield(yield);
                		wellDataAgriculture.setOperativeDays(operationDaysAgriculture);
                		basinWiseWellsMD.get(basinName).get(typeOfWell).get(areaType).put(Constants.AGRICULTURE, wellDataAgriculture);
                		
                		/**
                		 * Domestic
                		 */
                		yield = (fields[7+(12*index)].isEmpty())?0.0:Utils.parseDouble(fields[7+(12*index)]);
                		yield = (yield == 0.0)?Constants.DEFAULT_DOMS_WELL_YIELD:yield;
                		yield = yield * (7*Constants.LITRES_TO_HAM);
                		yield = Utils.round(yield, Constants.DECIMAL);
                		monsoonDays = (fields[9+(12*index)].isEmpty())?0.0:Utils.parseDouble(fields[9+(12*index)]);
                		operationDaysDomestic.put(Constants.MONSOON, monsoonDays);
                		nonMonsoonDays = (fields[10+(12*index)].isEmpty())?0.0:Utils.parseDouble(fields[10+(12*index)]);
                		operationDaysDomestic.put(Constants.NON_MONSOON, nonMonsoonDays);
                		wellDataDomestic.setYield(yield);
                		wellDataDomestic.setOperativeDays(operationDaysDomestic);
                		basinWiseWellsMD.get(basinName).get(typeOfWell).get(areaType).put(Constants.DOMESTIC, wellDataDomestic);
                		/**
                		 * Industry
                		 */
//                		System.out.println("index : " + index);
//                		System.out.println(" fields : " + record);
                		yield = (fields[11+(12*index)].isEmpty())?0.0:Utils.parseDouble(fields[11+(12*index)]);
                		yield = (yield == 0.0)?Constants.DEFAULT_INDS_WELL_YIELD:yield;
                		yield = yield * (7*Constants.LITRES_TO_HAM);
                		yield = Utils.round(yield, Constants.DECIMAL);
                		monsoonDays = (fields[13+(12*index)].isEmpty())?0.0:Utils.parseDouble(fields[13+(12*index)]);
                		operationDaysIndustry.put(Constants.MONSOON, monsoonDays);
                		nonMonsoonDays = (fields[14+(12*index)].isEmpty())?0.0:Utils.parseDouble(fields[14+(12*index)]);
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
        	        	
        	try(BufferedReader iem = new BufferedReader(new FileReader(utilizationFiles.get(category)))) {
            	record = iem.readLine();
                while((record = iem.readLine()) != null) {
                   String fields[] = record.split(",",-1);
                   Map<String, Integer> keyRepetition = new HashMap<String, Integer>();
                   if(!format.removeQuotes(fields[format.convert("c")]).isEmpty() && format.removeQuotes(fields[format.convert("c")]).length() > 0){
                	   String basinName = format.removeQuotes(fields[format.convert("c")]);
                	   String villageName = format.removeQuotes(fields[format.convert("d")]);
                	   String MicroBasin_GWvillage_key = basinName + "##" + villageName;
                	   
                	   if(keyRepetition.containsKey(MicroBasin_GWvillage_key)){
                       	int times = keyRepetition.get(MicroBasin_GWvillage_key)+1;
                       	keyRepetition.put(MicroBasin_GWvillage_key, times);
                       	MicroBasin_GWvillage_key = MicroBasin_GWvillage_key + "-" + String.valueOf(times);
                       }else
                       	keyRepetition.put(MicroBasin_GWvillage_key, 0);
                	   
                	   if(basinWiseWellsMD.get(basinName) == null){
                		   System.out.println(category+" Well MD information not found for basin : " + basinName);
                		   continue;
                	   }
                	   
                	   /**
                        * Category : AreaType : WellName vs wellInfo
                        */
                	   if(village_details.get(locmapping.get(MicroBasin_GWvillage_key)) == null){
                		   if(locmapping.get(MicroBasin_GWvillage_key) == null)
                			   continue;
                		   village_details.put(locmapping.get(MicroBasin_GWvillage_key), new Village());
                	   }
                	  
                	   Map<String, Map<String, Map<String, WellsUtilizationData>>> villWellDistributionInfo = village_details.get(locmapping.get(MicroBasin_GWvillage_key)).getResourceDistribution();
                	   
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
//                			   double growthRate = (well == "BWs")?3.0:0.0;
                			   double growthRate = (well.equals("DW+PS/DCB") || well.equals("DW"))?0.0:3.0;
                			   
//                			   //TODO : must change
//                			   growthRate = 0.0;
                			   double pumpingHours = Constants.PUMPING_HOURS;
                			   int count = 0;
                			   int referenceYear = 2008;
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
                			   }
                				   
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
                	   village_details.get(locmapping.get(MicroBasin_GWvillage_key)).setResourceDistribution(villWellDistributionInfo);
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
            Map<String, Integer> keyRepetition = new HashMap<String, Integer>();
        	while((record = iem.readLine()) != null) {

                String fields[] = record.split(",",-1);
               // System.out.println("fields"+fields.length);
                String MicroBasinName = format.removeQuotes(fields[format.convert("c")]);
                String GWvillageName = format.removeQuotes(fields[format.convert("d")]);
                String MicroBasin_GWvillage_key = MicroBasinName + "##" +GWvillageName;
                
                if(keyRepetition.containsKey(MicroBasin_GWvillage_key)){
                	int times = keyRepetition.get(MicroBasin_GWvillage_key)+1;
                	keyRepetition.put(MicroBasin_GWvillage_key, times);
                	MicroBasin_GWvillage_key = MicroBasin_GWvillage_key + "-" + String.valueOf(times);
                }else
                	keyRepetition.put(MicroBasin_GWvillage_key, 0);
               
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
            Map<String, Integer> keyRepetition = new HashMap<String, Integer>();
            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",", -1);

                if(fields.length == 17 ||  fields.length==25 || fields.length==23||fields.length==24||fields.length==26 || fields.length==31){
                	double total = 0;
                	double totalRec = 0;
                	double totalNonRec = 0;
                	double totCommand = 0;
                	double totNonCommand = 0;
                	double totPoorQuality = 0;
                	double recCommand = 0;
                	double recNonCommand = 0;
                	double recPoorQuality = 0;
                	double nonRecCommand = 0;
                	double nonRecNonCommand = 0;
                	double nonRecPoorQuality = 0;
                	
                	String basinName = format.removeQuotes(fields[format.convert("c")]);
                    String villageName = format.removeQuotes(fields[format.convert("d")]);
                	String MicroBasin_GWvillage_key = basinName + "##" + villageName;
             	   
             	   	if(keyRepetition.containsKey(MicroBasin_GWvillage_key)){
                    	int times = keyRepetition.get(MicroBasin_GWvillage_key)+1;
                    	keyRepetition.put(MicroBasin_GWvillage_key, times);
                    	MicroBasin_GWvillage_key = MicroBasin_GWvillage_key + "-" + String.valueOf(times);
                    }else
                    	keyRepetition.put(MicroBasin_GWvillage_key, 0);
             	   
                	if(!fields[4].isEmpty()){
	                	total=Utils.parseDouble(fields[4]);
	                }
	                if(!fields[5].isEmpty()){
	                	recCommand = Utils.parseDouble(fields[5]);
	                }
	                if(!fields[9].isEmpty()){
	                	recNonCommand=Utils.parseDouble(fields[9]);
	                }
	                if(!fields[13].isEmpty()){
	                	recPoorQuality=Utils.parseDouble(fields[13]);
	                }
	                
	                totalRec = recCommand + recNonCommand + recPoorQuality;
	                
	                nonRecCommand = Utils.parseDouble(fields[7]); //Utils.parseDouble(fields[6])
            		nonRecNonCommand = Utils.parseDouble(fields[11]); //Utils.parseDouble(fields[10]) + 
            		nonRecPoorQuality = Utils.parseDouble(fields[15]); //Utils.parseDouble(fields[14]) + 
                	
            		totalNonRec = nonRecCommand + nonRecNonCommand + nonRecPoorQuality;
            		
            		totCommand = recCommand + nonRecCommand;
            		totNonCommand = recNonCommand + nonRecNonCommand;
            		totPoorQuality = recPoorQuality + nonRecPoorQuality;
            		
            		total = totalRec + totalNonRec;
            		
            		Map<String, Area> areaInfo = new HashMap<>();
            		//Total across all categories
            		Area totalArea = new Area(totCommand, totNonCommand, totPoorQuality, total);
            		areaInfo.put(Constants.TOTAL, totalArea);
            		//For recharge worthy
            		Area recArea = new Area(recCommand, recNonCommand, recPoorQuality, totalRec);
                	areaInfo.put(Constants.RECHARGE_WORTHY, recArea);
                	//For non recharge worthy
            		Area nonRecArea = new Area(nonRecCommand, nonRecNonCommand, nonRecPoorQuality, totalNonRec);
                	areaInfo.put(Constants.NON_RECHARGE_WORTHY, nonRecArea);
                	
                	String areajson = Area.toJson(areaInfo);
                	
                	if(locmapping.keySet().contains(MicroBasin_GWvillage_key)){
                    	
                		if(village_details.get(locmapping.get(MicroBasin_GWvillage_key)).getArea()==null){
                			count++;
                			village_details.get(locmapping.get(MicroBasin_GWvillage_key)).setArea(areajson);
                			if(total==0){
                    			test++;
                    		}
                		}
                		else{
                			Type type;
                			type = new TypeToken<Map<String, Area>>() {}.getType();
            				
            				Map<String, Area> areaobj= Area.fromJson(village_details.get(locmapping.get(MicroBasin_GWvillage_key)).getArea(), type);

                			total += areaobj.get(Constants.TOTAL).total;
                			totCommand += areaobj.get(Constants.TOTAL).command;
                   			totNonCommand += areaobj.get(Constants.TOTAL).non_command;
                			totPoorQuality += areaobj.get(Constants.TOTAL).poor_quality;
                			
                			totalRec += areaobj.get(Constants.RECHARGE_WORTHY).total;
                			recCommand += areaobj.get(Constants.RECHARGE_WORTHY).command;
                   			recNonCommand += areaobj.get(Constants.RECHARGE_WORTHY).non_command;
                			recPoorQuality += areaobj.get(Constants.RECHARGE_WORTHY).poor_quality;
                			
                			totalNonRec += areaobj.get(Constants.NON_RECHARGE_WORTHY).total;
                			nonRecCommand += areaobj.get(Constants.NON_RECHARGE_WORTHY).command;
                   			nonRecNonCommand += areaobj.get(Constants.NON_RECHARGE_WORTHY).non_command;
                			nonRecPoorQuality += areaobj.get(Constants.NON_RECHARGE_WORTHY).poor_quality;
                			
                			//Total across all categories
                    		totalArea = new Area(totCommand, totNonCommand, totPoorQuality, total);
                    		areaInfo.put(Constants.TOTAL, totalArea);
                    		//For recharge worthy
                    		recArea = new Area(recCommand, recNonCommand, recPoorQuality, totalRec);
                        	areaInfo.put(Constants.RECHARGE_WORTHY, recArea);
                        	//For non recharge worthy
                    		nonRecArea = new Area(nonRecCommand, nonRecNonCommand, nonRecPoorQuality, totalNonRec);
                        	areaInfo.put(Constants.NON_RECHARGE_WORTHY, nonRecArea);
                        	
                			areaobj.put(Constants.TOTAL, totalArea);
                			areaobj.put(Constants.RECHARGE_WORTHY, recArea);
                			areaobj.put(Constants.NON_RECHARGE_WORTHY, nonRecArea);
                        	String areaObjjson = Area.toJson(areaobj);
                        	village_details.get(locmapping.get(MicroBasin_GWvillage_key)).setArea(areaObjjson);
                			
                		}
                	} else {
                		System.out.println("Mapping doesn't exist for locaiton : " + MicroBasin_GWvillage_key);
                	}
                } else {
                	System.out.println("Area fields length :: " + fields.length);
                }

            }
        	System.out.println("test count value ######"+test);
//        	int c=0;
//            for(String key:village_details.keySet()){
//	        	Area area= Area.fromJson(village_details.get(key).getArea(), Area.class);
//	        	if(area.total==0){
//					c++;
//				}
//	        }
            System.out.println("area count= "+count);
            
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        // json for basinAssociation
        try(BufferedReader iem = new BufferedReader(new FileReader(areafile))) {
              
          	//readXLSXFile();
              record = iem.readLine();
              record = iem.readLine();
              Map<String, Integer> keyRepetition = new HashMap<String, Integer>();
              while((record = iem.readLine()) != null) {
                  //System.out.println("record"+record);

                  String fields[] = record.split(",",-1);
                 // System.out.println("fields"+fields);
                  if(fields.length == 17 || fields.length==25 || fields.length==23||fields.length==24||fields.length==26||fields.length==31){
                  	int BasinCode;
                  	double Villagearea = 0;
                  	String BasinName = format.removeQuotes(fields[format.convert("c")]);
                  	double fractionArea = Utils.parseDouble(fields[format.convert("e")]);
                    
                    String villageName = format.removeQuotes(fields[format.convert("d")]);
                	String MicroBasin_GWvillage_key = BasinName + "##" + villageName;
             	   
                	
             	   	if(keyRepetition.containsKey(MicroBasin_GWvillage_key)){
                    	int times = keyRepetition.get(MicroBasin_GWvillage_key)+1;
                    	keyRepetition.put(MicroBasin_GWvillage_key, times);
                    	MicroBasin_GWvillage_key = MicroBasin_GWvillage_key + "-" + String.valueOf(times);
                    }else
                    	keyRepetition.put(MicroBasin_GWvillage_key, 0);
             	   
                   if(locmapping.keySet().contains(MicroBasin_GWvillage_key)){
                	   double commandArea = Utils.parseDouble(fields[format.convert("f")]);
                       double nonCommandArea = Utils.parseDouble(fields[format.convert("j")]);
                       double poorQualityArea = Utils.parseDouble(fields[format.convert("n")]);
                       double hilly = Utils.parseDouble(fields[format.convert("g")]) + Utils.parseDouble(fields[format.convert("k")]) + Utils.parseDouble(fields[format.convert("o")]);
                       double forest = Utils.parseDouble(fields[format.convert("h")]) + Utils.parseDouble(fields[format.convert("l")]) + Utils.parseDouble(fields[format.convert("p")]);
                       
                       Type type = new TypeToken<Map<String, Area>>(){}.getType();
                       Map<String, Area> areaobj= Area.fromJson(village_details.get(locmapping.get(MicroBasin_GWvillage_key)).getArea(), type);
                       double tCommandArea = (areaobj.get(Constants.RECHARGE_WORTHY).command > 0.0)?areaobj.get(Constants.RECHARGE_WORTHY).command:1;
                       double tNonCommandArea = (areaobj.get(Constants.RECHARGE_WORTHY).non_command > 0.0)?areaobj.get(Constants.RECHARGE_WORTHY).non_command:1;
                       double tPoorQualityArea = (areaobj.get(Constants.RECHARGE_WORTHY).poor_quality > 0.0)?areaobj.get(Constants.RECHARGE_WORTHY).poor_quality:1;
                       Villagearea = (areaobj.get(Constants.RECHARGE_WORTHY).total > 0.0)?areaobj.get(Constants.RECHARGE_WORTHY).total:1;
                       
                       if(Basin_Id.containsKey(basinmapping.get(BasinName))) {

                    	    BasinCode = Basin_Id.get(basinmapping.get(BasinName));
                    		Map<Integer, Map<String, Double>> locAssociation = village_details.get(locmapping.get(MicroBasin_GWvillage_key)).getBasinAssociation();
                              
                    		if(locAssociation == null)
                    			locAssociation = new HashMap<Integer, Map<String,Double>>();
                    		
                    		if(locAssociation.get(BasinCode) == null)
                    			locAssociation.put(BasinCode, new HashMap<String, Double>());
                    		
                    		locAssociation.get(BasinCode).put(Constants.COMMAND, Utils.round((commandArea/tCommandArea), Constants.DECIMAL));
                    		locAssociation.get(BasinCode).put(Constants.NON_COMMAND, Utils.round((nonCommandArea/tNonCommandArea), Constants.DECIMAL));
                    		locAssociation.get(BasinCode).put(Constants.POOR_QUALITY, Utils.round((poorQualityArea/tPoorQualityArea), Constants.DECIMAL));
                    		locAssociation.get(BasinCode).put(Constants.TOTAL, Utils.round((fractionArea/Villagearea), Constants.DECIMAL));
//                    		village_details.get(locmapping.get(MicroBasin_GWvillage_key)).setBasinAssociation(locAssociation);
                    		
                    		/**
                    		 * This is to migrate area information to iwm_data table;
                    		 */
                    		Area a = new Area(commandArea, nonCommandArea, poorQualityArea, Villagearea);
                    		System.out.println(locmapping.get(MicroBasin_GWvillage_key));
                    		villageMBAreaInfo.computeIfAbsent(locmapping.get(MicroBasin_GWvillage_key), k->new HashMap<String, Area>())
                    						.put(basinmapping.get(MicroBasin_GWvillage_key.split("##")[0]), a);
                    		villageMBAreaInfo.computeIfAbsent(locmapping.get(MicroBasin_GWvillage_key), k->new HashMap<String, Area>())
    						.putAll(areaobj);
                    	}
                    }
                      
                  }
                  else{
                	  System.out.println("Error : area row fields.length : " + fields.length);
                  }
        }
              
          }catch (IOException e) {
              e.printStackTrace();
          }
        
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
        if (!assesssment_year.equals("2012-2013")) {
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
            					//source vs AreaType vs CropType vs CropName vs CropData
            					Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            					obj = village_details.get(IWMName).getCrop();
            					if(obj == null)
            						obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            					
            					if(obj.get(Constants.SURFACE_WATER_IRRIGATION) == null){
            						obj.put(Constants.SURFACE_WATER_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
            					}
            					
            					if(obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND) == null){
            						obj.get(Constants.SURFACE_WATER_IRRIGATION).put(Constants.COMMAND, new HashMap<String, Map<String,CropData>>());
            						obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).put(Constants.PADDY, new HashMap<String, CropData>());
            						
            						CropData crop = new CropData();
            						crop.setCropArea(new HashMap<String, Double>());
            						crop.setWaterRequired(new HashMap<String, Double>());
            						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.PADDY_WATER_REQUIREMENT);
            						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.PADDY_WATER_REQUIREMENT);
            						crop.getCropArea().put(Constants.MONSOON, 0.0);
            						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
            						obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).put(Constants.PADDY, crop);
            						village_details.get(IWMName).setCrop(obj);          	
            					}
//            					obj = village_details.get(IWMName).getCrop();
            					double irrigatedArea = obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).get(Constants.PADDY).getCropArea().get(Constants.MONSOON);
            					irrigatedArea += (paddykharif.get(id).get(source)*Constants.HECTARE_PER_ACRE);
            					obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).get(Constants.PADDY).getCropArea().put(Constants.MONSOON, irrigatedArea);
            					village_details.get(IWMName).setCrop(obj);
            				}
                						
            			}
                	}
                	/**
                	 * Tanks = surface water irrigation source
                	 */
                	if(source.equalsIgnoreCase("Tanks") || source.equalsIgnoreCase("MI Tanks")){
                		Integer idd = cropId.get(id);
            			if((idd!= null && villageId.containsKey(idd)) || villageId.containsKey(id)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//source vs AreaType vs CropType vs CropName vs CropData
            					Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            					obj = village_details.get(IWMName).crop_info;
            					if(obj == null)
            						obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            					if(obj.get(Constants.MI_IRRIGATION) == null)
            						obj.put(Constants.MI_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
            					
            					if(obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND) == null){
            						obj.get(Constants.MI_IRRIGATION).put(Constants.NON_COMMAND, new HashMap<String, Map<String,CropData>>());
            						obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).put(Constants.PADDY, new HashMap<String, CropData>());
            						
            						CropData crop = new CropData();
            						crop.setCropArea(new HashMap<String, Double>());
            						crop.setWaterRequired(new HashMap<String, Double>());
            						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.PADDY_WATER_REQUIREMENT);
            						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.PADDY_WATER_REQUIREMENT);
            						crop.getCropArea().put(Constants.MONSOON, 0.0);
            						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
            						obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).put(Constants.PADDY, crop);
            						village_details.get(IWMName).crop_info = obj;             					
            					}
//            					obj = village_details.get(IWMName).crop_info;
            					double irrigatedArea = obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).get(Constants.PADDY).getCropArea().get(Constants.MONSOON);
            					irrigatedArea += (paddykharif.get(id).get(source)*Constants.HECTARE_PER_ACRE);
            					obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).get(Constants.PADDY).getCropArea().put(Constants.MONSOON, irrigatedArea);
            					village_details.get(IWMName).setCrop(obj);
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
            					if(computeDugwell.containsKey(IWMName)){
            						String area = Constants.NON_COMMAND;
            						if(computeDugwell.get(IWMName)>0)
            							area = Constants.COMMAND;
            						//source vs AreaType vs CropType vs CropName vs CropData
                					Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
                					obj = village_details.get(IWMName).getCrop();
                					if(obj == null)
                						obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
                					
                					if(obj.get(Constants.GROUND_WATER_IRRIGATION) == null){
                						obj.put(Constants.GROUND_WATER_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
                					}
                					
                					if(obj.get(Constants.GROUND_WATER_IRRIGATION).get(area) == null){
                						obj.get(Constants.GROUND_WATER_IRRIGATION).put(area, new HashMap<String, Map<String,CropData>>());
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).put(Constants.PADDY, new HashMap<String, CropData>());
                						
                						CropData crop = new CropData();
                						crop.setCropArea(new HashMap<String, Double>());
                						crop.setWaterRequired(new HashMap<String, Double>());
                						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.PADDY_WATER_REQUIREMENT);
                						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.PADDY_WATER_REQUIREMENT);
                						crop.getCropArea().put(Constants.MONSOON, 0.0);
                						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.PADDY).put(Constants.PADDY, crop);
                						village_details.get(IWMName).setCrop(obj);  
                					}
                					
//                					obj = village_details.get(IWMName).crop_info;
                					double irrigatedArea = obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.PADDY).get(Constants.PADDY).getCropArea().get(Constants.MONSOON);
                					irrigatedArea += (paddykharif.get(id).get(source)*Constants.HECTARE_PER_ACRE);
                					obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.PADDY).get(Constants.PADDY).getCropArea().put(Constants.MONSOON, irrigatedArea);
                					village_details.get(IWMName).setCrop(obj);
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
            					if(computeTubewell.containsKey(IWMName)){
            						String area = Constants.NON_COMMAND;
            						if(computeTubewell.get(IWMName)>0)
            							area = Constants.COMMAND;
            						//source vs AreaType vs CropType vs CropName vs CropData
        							Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
        							obj = village_details.get(IWMName).getCrop();
        							if(obj == null)
        								obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
        							
        							if(obj.get(Constants.GROUND_WATER_IRRIGATION) == null ||
                							village_details.get(IWMName).crop_info.get(Constants.GROUND_WATER_IRRIGATION).get(area) == null){
                						obj.put(Constants.GROUND_WATER_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
                					}
        							
        							if(obj.get(Constants.GROUND_WATER_IRRIGATION).get(area) == null){
        								obj.get(Constants.GROUND_WATER_IRRIGATION).put(area, new HashMap<String, Map<String,CropData>>());
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).put(Constants.PADDY, new HashMap<String, CropData>());
                						
                						CropData crop = new CropData();
                						crop.setCropArea(new HashMap<String, Double>());
                						crop.setWaterRequired(new HashMap<String, Double>());
                						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.PADDY_WATER_REQUIREMENT);
                						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.PADDY_WATER_REQUIREMENT);
                						crop.getCropArea().put(Constants.MONSOON, 0.0);
                						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.PADDY).put(Constants.PADDY, crop);
                						village_details.get(IWMName).crop_info = obj;   
        							}
//        							obj = village_details.get(IWMName).crop_info;
                					double irrigatedArea = obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.PADDY).get(Constants.PADDY).getCropArea().get(Constants.MONSOON);
                					irrigatedArea += (paddykharif.get(id).get(source)*Constants.HECTARE_PER_ACRE);
                					obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.PADDY).get(Constants.PADDY).getCropArea().put(Constants.MONSOON, irrigatedArea);
                					village_details.get(IWMName).setCrop(obj);
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
            					//source vs AreaType vs CropType vs CropName vs CropData
            					Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            					obj = village_details.get(IWMName).getCrop();
    							if(obj == null)
    								obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
    							
    							if(obj.get(Constants.SURFACE_WATER_IRRIGATION) == null){
            						obj.put(Constants.SURFACE_WATER_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
            						
            					}
    							
    							if(obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND) == null){
    								obj.get(Constants.SURFACE_WATER_IRRIGATION).put(Constants.COMMAND, new HashMap<String, Map<String,CropData>>());
            						obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).put(Constants.PADDY, new HashMap<String, CropData>());
            						
            						CropData crop = new CropData();
            						crop.setCropArea(new HashMap<String, Double>());
            						crop.setWaterRequired(new HashMap<String, Double>());
            						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.PADDY_WATER_REQUIREMENT);
            						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.PADDY_WATER_REQUIREMENT);
            						crop.getCropArea().put(Constants.MONSOON, 0.0);
            						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
            						obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).put(Constants.PADDY, crop);
            						village_details.get(IWMName).setCrop(obj);
    							}
            					
//            					obj = village_details.get(IWMName).crop_info;
            					double irrigatedArea = obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).get(Constants.PADDY).getCropArea().get(Constants.NON_MONSOON);
            					irrigatedArea += (paddyrabi.get(id).get(source)*Constants.HECTARE_PER_ACRE);
            					obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).get(Constants.PADDY).getCropArea().put(Constants.NON_MONSOON, irrigatedArea);
            					village_details.get(IWMName).setCrop(obj);
            				}
                						
            			}
                	}
                	
                	/**
                	 * Tanks = Surface water irrigation
                	 */
                	if(source.equalsIgnoreCase("Tanks") || source.equalsIgnoreCase("MI Tanks")){
                		Integer idd = cropId.get(id);
            			if((idd!= null && villageId.containsKey(idd)) || villageId.containsKey(id)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//source vs AreaType vs CropType vs CropName vs CropData
            					Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            					obj = village_details.get(IWMName).getCrop();
    							if(obj == null)
    								obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
    							
    							if(obj.get(Constants.MI_IRRIGATION) == null){
            						obj.put(Constants.MI_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
            					}
    							
    							if(obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND) == null){
    								obj.get(Constants.MI_IRRIGATION).put(Constants.NON_COMMAND, new HashMap<String, Map<String,CropData>>());
            						obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).put(Constants.PADDY, new HashMap<String, CropData>());
            						
            						CropData crop = new CropData();
            						crop.setCropArea(new HashMap<String, Double>());
            						crop.setWaterRequired(new HashMap<String, Double>());
            						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.PADDY_WATER_REQUIREMENT);
            						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.PADDY_WATER_REQUIREMENT);
            						crop.getCropArea().put(Constants.MONSOON, 0.0);
            						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
            						obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).put(Constants.PADDY, crop);
            						village_details.get(IWMName).setCrop(obj);           
    							}
            					
//    							obj = village_details.get(IWMName).crop_info;
            					double irrigatedArea = obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).get(Constants.PADDY).getCropArea().get(Constants.NON_MONSOON);
            					irrigatedArea += (paddyrabi.get(id).get(source)*Constants.HECTARE_PER_ACRE);
            					obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).get(Constants.PADDY).getCropArea().put(Constants.NON_MONSOON, irrigatedArea);
            					village_details.get(IWMName).setCrop(obj);
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
            					if(computeDugwell.containsKey(IWMName)){
            						String areaType = Constants.NON_COMMAND;
            						if(computeDugwell.get(IWMName)>0)
            							areaType = Constants.COMMAND;
            							
        							//source vs AreaType vs CropType vs CropName vs CropData
            						Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            						obj = village_details.get(IWMName).getCrop();
            						if(obj == null)
        								obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            						
            						if(obj.get(Constants.GROUND_WATER_IRRIGATION) == null){
                						obj.put(Constants.GROUND_WATER_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
                					}
            						
            						if(obj.get(Constants.GROUND_WATER_IRRIGATION).get(areaType) == null){
            							obj.get(Constants.GROUND_WATER_IRRIGATION).put(areaType, new HashMap<String, Map<String,CropData>>());
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(areaType).put(Constants.PADDY, new HashMap<String, CropData>());
                						
                						CropData crop = new CropData();
                						crop.setCropArea(new HashMap<String, Double>());
                						crop.setWaterRequired(new HashMap<String, Double>());
                						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.PADDY_WATER_REQUIREMENT);
                						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.PADDY_WATER_REQUIREMENT);
                						crop.getCropArea().put(Constants.MONSOON, 0.0);
                						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(areaType).get(Constants.PADDY).put(Constants.PADDY, crop);
                						village_details.get(IWMName).setCrop(obj);
            						}
            						
//                					obj = village_details.get(IWMName).crop_info;
                					double irrigatedArea = obj.get(Constants.GROUND_WATER_IRRIGATION).get(areaType).get(Constants.PADDY).get(Constants.PADDY).getCropArea().get(Constants.NON_MONSOON);
                					irrigatedArea += (paddyrabi.get(id).get(source)*Constants.HECTARE_PER_ACRE);
                					obj.get(Constants.GROUND_WATER_IRRIGATION).get(areaType).get(Constants.PADDY).get(Constants.PADDY).getCropArea().put(Constants.NON_MONSOON, irrigatedArea);
                					village_details.get(IWMName).setCrop(obj);
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
            					if(computeTubewell.containsKey(IWMName)){
            						String areaType = Constants.NON_COMMAND;
            						if(computeTubewell.get(IWMName)>0)
            							areaType = Constants.COMMAND;
        							//source vs AreaType vs CropType vs CropName vs CropData
            						Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            						obj = village_details.get(IWMName).getCrop();
            						if(obj == null)
            							obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            						
            						if(obj.get(Constants.GROUND_WATER_IRRIGATION) == null){
                						obj.put(Constants.GROUND_WATER_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
                					}
            						if(obj.get(Constants.GROUND_WATER_IRRIGATION).get(areaType) == null){
            							obj.get(Constants.GROUND_WATER_IRRIGATION).put(areaType, new HashMap<String, Map<String,CropData>>());
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(areaType).put(Constants.PADDY, new HashMap<String, CropData>());
                						
                						CropData crop = new CropData();
                						crop.setCropArea(new HashMap<String, Double>());
                						crop.setWaterRequired(new HashMap<String, Double>());
                						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.PADDY_WATER_REQUIREMENT);
                						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.PADDY_WATER_REQUIREMENT);
                						crop.getCropArea().put(Constants.MONSOON, 0.0);
                						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(areaType).get(Constants.PADDY).put(Constants.PADDY, crop);
                						village_details.get(IWMName).setCrop(obj);            
            						}
//            						obj = village_details.get(IWMName).crop_info;
            						double irrigatedArea = obj.get(Constants.GROUND_WATER_IRRIGATION).get(areaType).get(Constants.PADDY).get(Constants.PADDY).getCropArea().get(Constants.NON_MONSOON);
                					irrigatedArea += (paddyrabi.get(id).get(source)*Constants.HECTARE_PER_ACRE);
                					obj.get(Constants.GROUND_WATER_IRRIGATION).get(areaType).get(Constants.PADDY).get(Constants.PADDY).getCropArea().put(Constants.NON_MONSOON, irrigatedArea);
                					village_details.get(IWMName).setCrop(obj);
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
                	/**
                	 * Canal = surface water irrigation
                	 */
                	if(source.equalsIgnoreCase("Canal")){
            			Integer idd = cropId.get(id);
            			if(idd!= null && villageId.containsKey(idd)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//source vs AreaType vs CropType vs CropName vs CropData
            					Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            					obj = village_details.get(IWMName).getCrop();
            					if(obj == null)
        							obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            					
            					if(obj.get(Constants.SURFACE_WATER_IRRIGATION) == null){
            						obj.put(Constants.SURFACE_WATER_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
            					}
            					if(obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND) == null){
            						obj.get(Constants.SURFACE_WATER_IRRIGATION).put(Constants.COMMAND, new HashMap<String, Map<String,CropData>>());
            						
            					}
            					
            					if(obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY) == null){
            						obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).put(Constants.NON_PADDY, new HashMap<String, CropData>());
            						
            						CropData crop = new CropData();
            						crop.setCropArea(new HashMap<String, Double>());
            						crop.setWaterRequired(new HashMap<String, Double>());
            						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.NON_PADDY_WATER_REQUIREMENT);
            						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.NON_PADDY_WATER_REQUIREMENT);
            						crop.getCropArea().put(Constants.MONSOON, 0.0);
            						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
            						obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).put(Constants.NON_PADDY, crop);
            						village_details.get(IWMName).setCrop(obj);  
            					}
            						
//            					obj = village_details.get(IWMName).crop_info;
            					double irrigatedArea = obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().get(Constants.MONSOON);
            					irrigatedArea += (nonpaddykharif.get(id).get(source)*Constants.HECTARE_PER_ACRE);
            					obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().put(Constants.MONSOON, irrigatedArea);
            					village_details.get(IWMName).setCrop(obj);
            				}			
            			}
                	}
                	
                	/**
                	 * Tanks = surface water
                	 */
                	if(source.equalsIgnoreCase("Tanks") || source.equalsIgnoreCase("MI Tanks")){
                		Integer idd = cropId.get(id);
            			if((idd!= null && villageId.containsKey(idd)) || villageId.containsKey(id)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//source vs AreaType vs CropType vs CropName vs CropData
            					Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            					obj = village_details.get(IWMName).getCrop();
            					if(obj == null)
        							obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            					
            					if(obj.get(Constants.MI_IRRIGATION) == null){
            						obj.put(Constants.MI_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
            					}
            					if(obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND) == null){
            						obj.get(Constants.MI_IRRIGATION).put(Constants.NON_COMMAND, new HashMap<String, Map<String,CropData>>());
            						  
            					}
            					if(obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY) == null){
            						obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).put(Constants.NON_PADDY, new HashMap<String, CropData>());
            						
            						CropData crop = new CropData();
            						crop.setCropArea(new HashMap<String, Double>());
            						crop.setWaterRequired(new HashMap<String, Double>());
            						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.NON_PADDY_WATER_REQUIREMENT);
            						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.NON_PADDY_WATER_REQUIREMENT);
            						crop.getCropArea().put(Constants.MONSOON, 0.0);
            						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
            						obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).put(Constants.NON_PADDY, crop);
            						village_details.get(IWMName).setCrop(obj);
            					}	
//            					obj = village_details.get(IWMName).crop_info;
            					double irrigatedArea = obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().get(Constants.MONSOON);
            					irrigatedArea += (nonpaddykharif.get(id).get(source)*Constants.HECTARE_PER_ACRE);
            					obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().put(Constants.MONSOON, irrigatedArea);
            					village_details.get(IWMName).setCrop(obj);
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
            					if(computeDugwell.containsKey(IWMName)){
            						String area = Constants.NON_COMMAND;
            						if(computeDugwell.get(IWMName)>0)
            							area = Constants.COMMAND;
            						
            						//source vs AreaType vs CropType vs CropName vs CropData
            						Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            						obj = village_details.get(IWMName).getCrop();
            						if(obj == null)
            							obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            						
            						if(obj.get(Constants.GROUND_WATER_IRRIGATION) == null){
                						obj.put(Constants.GROUND_WATER_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
                						
                					}
            						if(obj.get(Constants.GROUND_WATER_IRRIGATION).get(area) == null){
            							obj.get(Constants.GROUND_WATER_IRRIGATION).put(area, new HashMap<String, Map<String,CropData>>());
            						}
            						if(obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY) == null){
            							obj.get(Constants.GROUND_WATER_IRRIGATION).put(area, new HashMap<String, Map<String,CropData>>());
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).put(Constants.NON_PADDY, new HashMap<String, CropData>());
                						
                						CropData crop = new CropData();
                						crop.setCropArea(new HashMap<String, Double>());
                						crop.setWaterRequired(new HashMap<String, Double>());
                						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.NON_PADDY_WATER_REQUIREMENT);
                						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.NON_PADDY_WATER_REQUIREMENT);
                						crop.getCropArea().put(Constants.MONSOON, 0.0);
                						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY).put(Constants.NON_PADDY, crop);
                						village_details.get(IWMName).setCrop(obj);
            						}
//                					obj = village_details.get(IWMName).crop_info;
                					double irrigatedArea = obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().get(Constants.MONSOON);
                					irrigatedArea += (nonpaddykharif.get(id).get(source)*Constants.HECTARE_PER_ACRE);
                					obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().put(Constants.MONSOON, irrigatedArea);
                					village_details.get(IWMName).setCrop(obj);
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
            					if(computeTubewell.containsKey(IWMName)){
            						String area = Constants.NON_COMMAND;
            						if(computeTubewell.get(IWMName)>0)
            							area = Constants.COMMAND;
            						
            						//source vs AreaType vs CropType vs CropName vs CropData
            						Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
                					obj = village_details.get(IWMName).getCrop();
                					if(obj == null)
                						obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            						
                					if(obj.get(Constants.GROUND_WATER_IRRIGATION) == null){
                						obj.put(Constants.GROUND_WATER_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
                						  
                					}
                					if(obj.get(Constants.GROUND_WATER_IRRIGATION).get(area) == null){
                						obj.get(Constants.GROUND_WATER_IRRIGATION).put(area, new HashMap<String, Map<String,CropData>>());
                						
                					}
                					if(obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY) == null){
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).put(Constants.NON_PADDY, new HashMap<String, CropData>());
                						CropData crop = new CropData();
                						crop.setCropArea(new HashMap<String, Double>());
                						crop.setWaterRequired(new HashMap<String, Double>());
                						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.NON_PADDY_WATER_REQUIREMENT);
                						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.NON_PADDY_WATER_REQUIREMENT);
                						crop.getCropArea().put(Constants.MONSOON, 0.0);
                						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY).put(Constants.NON_PADDY, crop);
                						village_details.get(IWMName).setCrop(obj);
                					}
                					
//                					obj = village_details.get(IWMName).crop_info;
                					double irrigatedArea = obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().get(Constants.MONSOON);
                					irrigatedArea += (nonpaddykharif.get(id).get(source)*Constants.HECTARE_PER_ACRE);
                					obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().put(Constants.MONSOON, irrigatedArea);
                					village_details.get(IWMName).setCrop(obj);	
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
            					//source vs AreaType vs CropType vs CropName vs CropData
            					Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            					obj = village_details.get(IWMName).getCrop();
            					
            					if(obj == null)
            						obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            					
            					if(obj.get(Constants.SURFACE_WATER_IRRIGATION) == null){
            						obj.put(Constants.SURFACE_WATER_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
            						
            					}
            					if(obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND) == null)
            						obj.get(Constants.SURFACE_WATER_IRRIGATION).put(Constants.COMMAND, new HashMap<String, Map<String,CropData>>());
            					
            					if(obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY) == null){
            						obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).put(Constants.NON_PADDY, new HashMap<String, CropData>());
            						CropData crop = new CropData();
            						crop.setCropArea(new HashMap<String, Double>());
            						crop.setWaterRequired(new HashMap<String, Double>());
            						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.NON_PADDY_WATER_REQUIREMENT);
            						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.NON_PADDY_WATER_REQUIREMENT);
            						crop.getCropArea().put(Constants.MONSOON, 0.0);
            						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
            						obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).put(Constants.NON_PADDY, crop);
            						village_details.get(IWMName).setCrop(obj);
            					}
            					
//            					obj = village_details.get(IWMName).crop_info;
            					double irrigatedArea = obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().get(Constants.NON_MONSOON);
            					irrigatedArea += (nonpaddyrabi.get(id).get(source)*Constants.HECTARE_PER_ACRE);
            					obj.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().put(Constants.NON_MONSOON, irrigatedArea);
            					village_details.get(IWMName).setCrop(obj);
            				}			
            			}
                	}
                	
                	
                	/**
                	 * Tanks = surface water
                	 */
                	if(source.equalsIgnoreCase("Tanks") || source.equalsIgnoreCase("MI Tanks")){
            			
                		Integer idd = cropId.get(id);
            			if((idd!= null && villageId.containsKey(idd)) || villageId.containsKey(id)){
            				String IWMName = villageId.get(idd);
            				if(village_details.containsKey(IWMName)){
            					//source vs AreaType vs CropType vs CropName vs CropData
            					Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            					obj = village_details.get(IWMName).getCrop();
            					if(obj == null)
            						obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            					if(obj.get(Constants.MI_IRRIGATION)==null)
            						obj.put(Constants.MI_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
            					
            					if(obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND) == null)
            						obj.get(Constants.MI_IRRIGATION).put(Constants.NON_COMMAND, new HashMap<String, Map<String,CropData>>());
            					
            					if(obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY) == null){
            						obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).put(Constants.NON_PADDY, new HashMap<String, CropData>());
            						
            						CropData crop = new CropData();
            						crop.setCropArea(new HashMap<String, Double>());
            						crop.setWaterRequired(new HashMap<String, Double>());
            						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.NON_PADDY_WATER_REQUIREMENT);
            						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.NON_PADDY_WATER_REQUIREMENT);
            						crop.getCropArea().put(Constants.MONSOON, 0.0);
            						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
            						obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).put(Constants.NON_PADDY, crop);
            						village_details.get(IWMName).setCrop(obj);
            					}
            						
//            					obj = village_details.get(IWMName).crop_info;
            					double irrigatedArea = obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().get(Constants.NON_MONSOON);
            					irrigatedArea += (nonpaddyrabi.get(id).get(source)*Constants.HECTARE_PER_ACRE);
            					obj.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().put(Constants.NON_MONSOON, irrigatedArea);
            					village_details.get(IWMName).setCrop(obj);
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
            					if(computeDugwell.containsKey(IWMName)){
            						String area = Constants.NON_COMMAND;
            						if(computeDugwell.get(IWMName)>0)
            							area = Constants.COMMAND;
            						//source vs AreaType vs CropType vs CropName vs CropData
                					Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
                					obj = village_details.get(IWMName).getCrop();
                					if(obj == null)
                						obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
                					
                					if(obj.get(Constants.GROUND_WATER_IRRIGATION)==null)
                						obj.put(Constants.GROUND_WATER_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
                					
                					if(obj.get(Constants.GROUND_WATER_IRRIGATION).get(area)==null)
                						obj.get(Constants.GROUND_WATER_IRRIGATION).put(area, new HashMap<String, Map<String,CropData>>());
                					
                					if(obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY) == null){
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).put(Constants.NON_PADDY, new HashMap<String, CropData>());
                						CropData crop = new CropData();
                						crop.setCropArea(new HashMap<String, Double>());
                						crop.setWaterRequired(new HashMap<String, Double>());
                						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.NON_PADDY_WATER_REQUIREMENT);
                						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.NON_PADDY_WATER_REQUIREMENT);
                						crop.getCropArea().put(Constants.MONSOON, 0.0);
                						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY).put(Constants.NON_PADDY, crop);
                						village_details.get(IWMName).setCrop(obj);  
                					}
                					
//                					obj = village_details.get(IWMName).crop_info;
                					double irrigatedArea = obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().get(Constants.NON_MONSOON);
                					irrigatedArea += (nonpaddyrabi.get(id).get(source)*Constants.HECTARE_PER_ACRE);
                					obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().put(Constants.NON_MONSOON, irrigatedArea);
                					village_details.get(IWMName).setCrop(obj);  
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
            					if(computeTubewell.containsKey(IWMName)){
            						String area = Constants.NON_COMMAND;
            						if(computeTubewell.get(IWMName)>0)
            							area = Constants.COMMAND;
            						//source vs AreaType vs CropType vs CropName vs CropData
            						Map<String, Map<String, Map<String, Map<String, CropData>>>> obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            						obj = village_details.get(IWMName).getCrop();
            						if(obj == null)
            							obj = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
            						
            						if(obj.get(Constants.GROUND_WATER_IRRIGATION)==null){
                						obj.put(Constants.GROUND_WATER_IRRIGATION, new HashMap<String, Map<String,Map<String,CropData>>>());
                						  
                					}
            						if(obj.get(Constants.GROUND_WATER_IRRIGATION).get(area) == null){
            							obj.get(Constants.GROUND_WATER_IRRIGATION).put(area, new HashMap<String, Map<String,CropData>>());
                						
            						}
                					if(obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY) == null){
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).put(Constants.NON_PADDY, new HashMap<String, CropData>());
                						CropData crop = new CropData();
                						crop.setCropArea(new HashMap<String, Double>());
                						crop.setWaterRequired(new HashMap<String, Double>());
                						crop.getWaterRequired().put(Constants.MONSOON, 2*Constants.NON_PADDY_WATER_REQUIREMENT);
                						crop.getWaterRequired().put(Constants.NON_MONSOON, Constants.NON_PADDY_WATER_REQUIREMENT);
                						crop.getCropArea().put(Constants.MONSOON, 0.0);
                						crop.getCropArea().put(Constants.NON_MONSOON, 0.0);
                						obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY).put(Constants.NON_PADDY, crop);
                						village_details.get(IWMName).setCrop(obj);  
                					}
                					
//                					obj = village_details.get(IWMName).crop_info;
                					double irrigatedArea = obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().get(Constants.NON_MONSOON);
                					irrigatedArea += (nonpaddyrabi.get(id).get(source)*Constants.HECTARE_PER_ACRE);
                					obj.get(Constants.GROUND_WATER_IRRIGATION).get(area).get(Constants.NON_PADDY).get(Constants.NON_PADDY).getCropArea().put(Constants.NON_MONSOON, irrigatedArea);
                					village_details.get(IWMName).setCrop(obj);  
            					}
            				}		
            			}
                	}
                }
             }
		}
        
        //inserting into waterbodies json Object
        try(BufferedReader iem = new BufferedReader(new FileReader(waterbodiesfile))) {
        	int count =0;
            record = iem.readLine();
            record = iem.readLine();
            record = iem.readLine();
            Map<String, Integer> keyRepetition = new HashMap<String, Integer>();
            
            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",", -1);
                if(fields.length==10){
                	String basinName = format.removeQuotes(fields[format.convert("c")]);
                    String villageName = format.removeQuotes(fields[format.convert("d")]);
                	String MicroBasin_GWvillage_key = basinName + "##" + villageName;
             	   
             	   	if(keyRepetition.containsKey(MicroBasin_GWvillage_key)){
                    	int times = keyRepetition.get(MicroBasin_GWvillage_key)+1;
                    	keyRepetition.put(MicroBasin_GWvillage_key, times);
                    	MicroBasin_GWvillage_key = MicroBasin_GWvillage_key + "-" + String.valueOf(times);
                    }else
                    	keyRepetition.put(MicroBasin_GWvillage_key, 0);
                	
//                	waterBodyType vs AreaType vs IStorageStructureData
                	Map<String, Map<String, WaterBody>> miTank = new HashMap<String, Map<String,WaterBody>>();
            		miTank.put(Constants.MI_TANK, new HashMap<String, WaterBody>());
            		for(String areaType : Constants.AREA_TYPES){
                		WaterBody mi = new WaterBody();
                		mi.setRechargeFactor(Constants.MI_INFILTRATION_RATE);
                		mi.setImpoundDays(new HashMap<String, Integer>());
                		mi.getImpoundDays().put(Constants.MONSOON, Constants.MI_MONSOON_DAYS);
                		mi.getImpoundDays().put(Constants.NON_MONSOON, Constants.MI_NON_MONSOON_DAYS);
                		
                		miTank.get(Constants.MI_TANK).put(areaType, mi);
                	}
                	
                	miTank.get(Constants.MI_TANK).get(Constants.COMMAND).setCount(Utils.parseDouble(fields[format.convert("e")]));
                	miTank.get(Constants.MI_TANK).get(Constants.COMMAND).setSpreadArea(Utils.parseDouble(fields[format.convert("f")]));
                	miTank.get(Constants.MI_TANK).get(Constants.NON_COMMAND).setCount(Utils.parseDouble(fields[format.convert("g")]));
                	miTank.get(Constants.MI_TANK).get(Constants.NON_COMMAND).setSpreadArea(Utils.parseDouble(fields[format.convert("h")]));
                	miTank.get(Constants.MI_TANK).get(Constants.POOR_QUALITY).setCount(Utils.parseDouble(fields[format.convert("i")]));
                	miTank.get(Constants.MI_TANK).get(Constants.POOR_QUALITY).setSpreadArea(Utils.parseDouble(fields[format.convert("j")]));
                   
                	if(locmapping.keySet().contains(MicroBasin_GWvillage_key)){
                    	if(village_details.get(locmapping.get(MicroBasin_GWvillage_key)).getWaterbodies()==null){
                        	count++;
                			village_details.get(locmapping.get(MicroBasin_GWvillage_key)).setWaterbodies(miTank);
                		}
                    	else{
                    		miTank = village_details.get(locmapping.get(MicroBasin_GWvillage_key)).getWaterbodies();
                    		
                    		miTank.get(Constants.MI_TANK).get(Constants.COMMAND).setCount(Utils.parseDouble(fields[format.convert("e")])+miTank.get(Constants.MI_TANK).get(Constants.COMMAND).getCount());
                    		miTank.get(Constants.MI_TANK).get(Constants.COMMAND).setSpreadArea(Utils.parseDouble(fields[format.convert("f")])+miTank.get(Constants.MI_TANK).get(Constants.COMMAND).getSpreadArea());
                    		
                    		miTank.get(Constants.MI_TANK).get(Constants.NON_COMMAND).setCount(Utils.parseDouble(fields[format.convert("g")])+miTank.get(Constants.MI_TANK).get(Constants.NON_COMMAND).getCount());
                    		miTank.get(Constants.MI_TANK).get(Constants.NON_COMMAND).setSpreadArea(Utils.parseDouble(fields[format.convert("h")])+miTank.get(Constants.MI_TANK).get(Constants.NON_COMMAND).getSpreadArea());
                    		
                    		miTank.get(Constants.MI_TANK).get(Constants.POOR_QUALITY).setCount(Utils.parseDouble(fields[format.convert("i")])+miTank.get(Constants.MI_TANK).get(Constants.POOR_QUALITY).getCount());
                    		miTank.get(Constants.MI_TANK).get(Constants.POOR_QUALITY).setSpreadArea(Utils.parseDouble(fields[format.convert("j")])+miTank.get(Constants.MI_TANK).get(Constants.POOR_QUALITY).getSpreadArea());
                    		
                   			village_details.get(locmapping.get(MicroBasin_GWvillage_key)).setWaterbodies(miTank);

                    	}
                	}
                   // System.out.println(waterbodyjson);
                } else {
                	System.out.println("waterbodies : field length not matching" +fields.length);
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
            Map<String, Integer> keyRepetition = new HashMap<String, Integer>();
            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",",-1);
//                if(fields.length==16){
                if(!fields[format.convert("b")].contains(" ") && !fields[format.convert("b")].isEmpty()){
                	String basinName = format.removeQuotes(fields[format.convert("c")]);
                    String villageName = format.removeQuotes(fields[format.convert("d")]);
                	String MicroBasin_GWvillage_key = basinName + "##" + villageName;
             	   
             	   	if(keyRepetition.containsKey(MicroBasin_GWvillage_key)){
                    	int times = keyRepetition.get(MicroBasin_GWvillage_key)+1;
                    	keyRepetition.put(MicroBasin_GWvillage_key, times);
                    	MicroBasin_GWvillage_key = MicroBasin_GWvillage_key + "-" + String.valueOf(times);
                    }else
                    	keyRepetition.put(MicroBasin_GWvillage_key, 0);
             	   	
                	//AreaType vs CanalName vs CanalData
                	Map<String, Map<String, CanalData>> canalData = new HashMap<String, Map<String,CanalData>>();
                	canalData.put(Constants.COMMAND, new HashMap<String, CanalData>());
                	canalData.put(Constants.NON_COMMAND, new HashMap<String, CanalData>());
                	canalData.put(Constants.POOR_QUALITY, new HashMap<String, CanalData>());
                	CanalData canal = new CanalData();
                	String canalName = String.valueOf(Math.random());
                	canal.setLength(Utils.parseDouble(fields[4]));
                	canal.setSideSlopes(Utils.parseDouble(fields[7]));
                	canal.setDesignDepthFlow(Utils.parseDouble(fields[5]));
                	canal.setBedWidth(Utils.parseDouble(fields[6]));
                	canal.setNoOfRunningDays(new HashMap<String, Integer>());
                	canal.getNoOfRunningDays().put(Constants.MONSOON, Utils.parseInt(fields[11]));
                	canal.getNoOfRunningDays().put(Constants.NON_MONSOON, Utils.parseInt(fields[12]));
                	canal.setSeepageFactor(Utils.parseDouble(fields[10]));
                	canal.setSegmentName(canalName);
                	
                	canalData.get(Constants.COMMAND).put(canalName, canal);
                	
                    if(locmapping.keySet().contains(MicroBasin_GWvillage_key)){
                		if(village_details.get(locmapping.get(MicroBasin_GWvillage_key)).getCanal()==null){
                        	count++;
                        	village_details.get(locmapping.get(MicroBasin_GWvillage_key)).setCanal(canalData);
                        }else{
                        	village_details.get(locmapping.get(MicroBasin_GWvillage_key)).getCanal().get(Constants.COMMAND).putAll(canalData.get(Constants.COMMAND));
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
            Map<String, Integer> keyRepetition = new HashMap<String, Integer>();
            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",",-1);
                 if(fields.length==34){
                	String basinName = format.removeQuotes(fields[format.convert("c")]);
                    String villageName = format.removeQuotes(fields[format.convert("d")]);
                 	String MicroBasin_GWvillage_key = basinName + "##" + villageName;
              	   
              	   	if(keyRepetition.containsKey(MicroBasin_GWvillage_key)){
                     	int times = keyRepetition.get(MicroBasin_GWvillage_key)+1;
                     	keyRepetition.put(MicroBasin_GWvillage_key, times);
                     	MicroBasin_GWvillage_key = MicroBasin_GWvillage_key + "-" + String.valueOf(times);
                     }else
                     	keyRepetition.put(MicroBasin_GWvillage_key, 0);
                	 
                	//waterBodyType vs ArtificialStructureData
            		Map<String, ArtificialWC> artificialWC = new HashMap<String, ArtificialWC>();
            		
            		for(String arsType : Constants.ARS_TYPES){
            			ArtificialWC ars = new ArtificialWC();
            			ars.setNoOfFillings(Constants.FILLINGS.get(arsType));
            			ars.setInfiltrationFactor(Constants.ARS_INFIL_FACTOR);
            			ars.setCapacity(new HashMap<String, Double>());
            			ars.setCount(new HashMap<String, Integer>());
            			artificialWC.put(arsType, ars);
            		}
                 	
            		artificialWC.get(Constants.PT).getCount().put(Constants.COMMAND, Utils.parseInt(fields[format.convert("e")]));
            		artificialWC.get(Constants.PT).getCount().put(Constants.NON_COMMAND, Utils.parseInt(fields[format.convert("o")]));
            		artificialWC.get(Constants.PT).getCount().put(Constants.POOR_QUALITY, Utils.parseInt(fields[format.convert("y")]));
            		artificialWC.get(Constants.MPT).getCount().put(Constants.COMMAND, Utils.parseInt(fields[format.convert("g")]));
            		artificialWC.get(Constants.MPT).getCount().put(Constants.NON_COMMAND, Utils.parseInt(fields[format.convert("q")]));
            		artificialWC.get(Constants.MPT).getCount().put(Constants.POOR_QUALITY, Utils.parseInt(fields[format.convert("aa")]));
            		artificialWC.get(Constants.CD).getCount().put(Constants.COMMAND, Utils.parseInt(fields[format.convert("i")]));
            		artificialWC.get(Constants.CD).getCount().put(Constants.NON_COMMAND, Utils.parseInt(fields[format.convert("s")]));
            		artificialWC.get(Constants.CD).getCount().put(Constants.POOR_QUALITY, Utils.parseInt(fields[format.convert("ac")]));
            		artificialWC.get(Constants.FP).getCount().put(Constants.COMMAND, Utils.parseInt(fields[format.convert("k")]));
            		artificialWC.get(Constants.FP).getCount().put(Constants.NON_COMMAND, Utils.parseInt(fields[format.convert("u")]));
            		artificialWC.get(Constants.FP).getCount().put(Constants.POOR_QUALITY, Utils.parseInt(fields[format.convert("ae")]));
            		artificialWC.get(Constants.OTHER).getCount().put(Constants.COMMAND, Utils.parseInt(fields[format.convert("m")]));
            		artificialWC.get(Constants.OTHER).getCount().put(Constants.NON_COMMAND, Utils.parseInt(fields[format.convert("w")]));
            		artificialWC.get(Constants.OTHER).getCount().put(Constants.POOR_QUALITY, Utils.parseInt(fields[format.convert("ag")]));
            		
            		artificialWC.get(Constants.PT).getCapacity().put(Constants.COMMAND, Utils.parseDouble(fields[format.convert("f")]));
            		artificialWC.get(Constants.PT).getCapacity().put(Constants.NON_COMMAND, Utils.parseDouble(fields[format.convert("p")]));
            		artificialWC.get(Constants.PT).getCapacity().put(Constants.POOR_QUALITY, Utils.parseDouble(fields[format.convert("z")]));
            		artificialWC.get(Constants.MPT).getCapacity().put(Constants.COMMAND, Utils.parseDouble(fields[format.convert("h")]));
            		artificialWC.get(Constants.MPT).getCapacity().put(Constants.NON_COMMAND, Utils.parseDouble(fields[format.convert("r")]));
            		artificialWC.get(Constants.MPT).getCapacity().put(Constants.POOR_QUALITY, Utils.parseDouble(fields[format.convert("ab")]));
            		artificialWC.get(Constants.CD).getCapacity().put(Constants.COMMAND, Utils.parseDouble(fields[format.convert("j")]));
            		artificialWC.get(Constants.CD).getCapacity().put(Constants.NON_COMMAND, Utils.parseDouble(fields[format.convert("t")]));
            		artificialWC.get(Constants.CD).getCapacity().put(Constants.POOR_QUALITY, Utils.parseDouble(fields[format.convert("ad")]));
            		artificialWC.get(Constants.FP).getCapacity().put(Constants.COMMAND, Utils.parseDouble(fields[format.convert("l")]));
            		artificialWC.get(Constants.FP).getCapacity().put(Constants.NON_COMMAND, Utils.parseDouble(fields[format.convert("v")]));
            		artificialWC.get(Constants.FP).getCapacity().put(Constants.POOR_QUALITY, Utils.parseDouble(fields[format.convert("af")]));
            		artificialWC.get(Constants.OTHER).getCapacity().put(Constants.COMMAND, Utils.parseDouble(fields[format.convert("n")]));
            		artificialWC.get(Constants.OTHER).getCapacity().put(Constants.NON_COMMAND, Utils.parseDouble(fields[format.convert("x")]));
            		artificialWC.get(Constants.OTHER).getCapacity().put(Constants.POOR_QUALITY, Utils.parseDouble(fields[format.convert("ah")]));
            		
                 	if(locmapping.containsKey(MicroBasin_GWvillage_key)){
                 		if(village_details.get(locmapping.get(MicroBasin_GWvillage_key)).getArtificialWC()==null){
                         	count++;
                         	village_details.get(locmapping.get(MicroBasin_GWvillage_key)).setArtificialWC(artificialWC);
                 		}else{
                 			artificialWC.get(Constants.PT).getCount().put(Constants.COMMAND, Utils.parseInt(fields[format.convert("e")]));
                    		artificialWC.get(Constants.PT).getCount().put(Constants.NON_COMMAND, Utils.parseInt(fields[format.convert("o")]));
                    		artificialWC.get(Constants.PT).getCount().put(Constants.POOR_QUALITY, Utils.parseInt(fields[format.convert("y")]));
                    		artificialWC.get(Constants.MPT).getCount().put(Constants.COMMAND, Utils.parseInt(fields[format.convert("g")]));
                    		artificialWC.get(Constants.MPT).getCount().put(Constants.NON_COMMAND, Utils.parseInt(fields[format.convert("q")]));
                    		artificialWC.get(Constants.MPT).getCount().put(Constants.POOR_QUALITY, Utils.parseInt(fields[format.convert("aa")]));
                    		artificialWC.get(Constants.CD).getCount().put(Constants.COMMAND, Utils.parseInt(fields[format.convert("i")]));
                    		artificialWC.get(Constants.CD).getCount().put(Constants.NON_COMMAND, Utils.parseInt(fields[format.convert("s")]));
                    		artificialWC.get(Constants.CD).getCount().put(Constants.POOR_QUALITY, Utils.parseInt(fields[format.convert("ac")]));
                    		artificialWC.get(Constants.FP).getCount().put(Constants.COMMAND, Utils.parseInt(fields[format.convert("k")]));
                    		artificialWC.get(Constants.FP).getCount().put(Constants.NON_COMMAND, Utils.parseInt(fields[format.convert("u")]));
                    		artificialWC.get(Constants.FP).getCount().put(Constants.POOR_QUALITY, Utils.parseInt(fields[format.convert("ae")]));
                    		artificialWC.get(Constants.OTHER).getCount().put(Constants.COMMAND, Utils.parseInt(fields[format.convert("m")]));
                    		artificialWC.get(Constants.OTHER).getCount().put(Constants.NON_COMMAND, Utils.parseInt(fields[format.convert("w")]));
                    		artificialWC.get(Constants.OTHER).getCount().put(Constants.POOR_QUALITY, Utils.parseInt(fields[format.convert("ag")]));
                    		
                 			artificialWC.get(Constants.PT).getCapacity().put(Constants.COMMAND, artificialWC.get(Constants.PT).getCapacity().get(Constants.COMMAND)+Utils.parseDouble(fields[format.convert("f")]));
                 			artificialWC.get(Constants.PT).getCapacity().put(Constants.NON_COMMAND, artificialWC.get(Constants.PT).getCapacity().get(Constants.NON_COMMAND)+Utils.parseDouble(fields[format.convert("p")]));
                 			artificialWC.get(Constants.PT).getCapacity().put(Constants.POOR_QUALITY, artificialWC.get(Constants.PT).getCapacity().get(Constants.POOR_QUALITY)+Utils.parseDouble(fields[format.convert("z")]));
                 			artificialWC.get(Constants.MPT).getCapacity().put(Constants.COMMAND, artificialWC.get(Constants.MPT).getCapacity().get(Constants.COMMAND)+Utils.parseDouble(fields[format.convert("h")]));
                 			artificialWC.get(Constants.MPT).getCapacity().put(Constants.NON_COMMAND, artificialWC.get(Constants.MPT).getCapacity().get(Constants.NON_COMMAND)+Utils.parseDouble(fields[format.convert("r")]));
                 			artificialWC.get(Constants.MPT).getCapacity().put(Constants.POOR_QUALITY, artificialWC.get(Constants.MPT).getCapacity().get(Constants.POOR_QUALITY)+Utils.parseDouble(fields[format.convert("ab")]));
                 			artificialWC.get(Constants.CD).getCapacity().put(Constants.COMMAND, artificialWC.get(Constants.CD).getCapacity().get(Constants.COMMAND)+Utils.parseDouble(fields[format.convert("j")]));
                 			artificialWC.get(Constants.CD).getCapacity().put(Constants.NON_COMMAND, artificialWC.get(Constants.CD).getCapacity().get(Constants.NON_COMMAND)+Utils.parseDouble(fields[format.convert("t")]));
                 			artificialWC.get(Constants.CD).getCapacity().put(Constants.POOR_QUALITY, artificialWC.get(Constants.CD).getCapacity().get(Constants.POOR_QUALITY)+Utils.parseDouble(fields[format.convert("ad")]));
                 			artificialWC.get(Constants.FP).getCapacity().put(Constants.COMMAND, artificialWC.get(Constants.FP).getCapacity().get(Constants.COMMAND)+Utils.parseDouble(fields[format.convert("l")]));
                 			artificialWC.get(Constants.FP).getCapacity().put(Constants.NON_COMMAND, artificialWC.get(Constants.FP).getCapacity().get(Constants.NON_COMMAND)+Utils.parseDouble(fields[format.convert("v")]));
                 			artificialWC.get(Constants.FP).getCapacity().put(Constants.POOR_QUALITY, artificialWC.get(Constants.FP).getCapacity().get(Constants.POOR_QUALITY)+Utils.parseDouble(fields[format.convert("af")]));
                 			artificialWC.get(Constants.OTHER).getCapacity().put(Constants.COMMAND, artificialWC.get(Constants.OTHER).getCapacity().get(Constants.COMMAND)+Utils.parseDouble(fields[format.convert("n")]));
                 			artificialWC.get(Constants.OTHER).getCapacity().put(Constants.NON_COMMAND, artificialWC.get(Constants.OTHER).getCapacity().get(Constants.NON_COMMAND)+Utils.parseDouble(fields[format.convert("x")]));
                 			artificialWC.get(Constants.OTHER).getCapacity().put(Constants.POOR_QUALITY, artificialWC.get(Constants.OTHER).getCapacity().get(Constants.POOR_QUALITY)+Utils.parseDouble(fields[format.convert("ah")]));
                 			
                         	village_details.get(locmapping.get(MicroBasin_GWvillage_key)).setArtificialWC(artificialWC);

                 		}
                 	}
                 	else{
                 		System.out.println("Artificial WC : Mapping not found for location : " + MicroBasin_GWvillage_key);
                 	}
                 }
                 else{
                	 System.out.println("Artificial WC : invalid row : field length : " + fields.length);
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
              String populationJson;
              Map<String, Integer> keyRepetition = new HashMap<String, Integer>();
              while((record = iem.readLine()) != null) {
                  //System.out.println("record"+record);
                  String fields[] = record.split(",",-1);
                 // System.out.println("fields"+fields);
                  if(fields.length==9){
                	  String basinName = format.removeQuotes(fields[format.convert("c")]);
                      String villageName = format.removeQuotes(fields[format.convert("e")]);
                  	String MicroBasin_GWvillage_key = basinName + "##" + villageName;
               	   
               	   	if(keyRepetition.containsKey(MicroBasin_GWvillage_key)){
                      	int times = keyRepetition.get(MicroBasin_GWvillage_key)+1;
                      	keyRepetition.put(MicroBasin_GWvillage_key, times);
                      	MicroBasin_GWvillage_key = MicroBasin_GWvillage_key + "-" + String.valueOf(times);
                      }else
                      	keyRepetition.put(MicroBasin_GWvillage_key, 0);
               	   	
                	// areaType vs Population data
                	Map<String, Population>  population = new HashMap<String, Population>();
                	for(String areaType : Constants.AREA_TYPES){
                		Population pop = new Population();
                		pop.setGrowthRate(Constants.POPULATION_GROWTH_RATE);
                		pop.setReferenceYear(2011);
                		pop.setLpcd(Constants.POPULATION_LPCD);
//                		if(Utils.parseDouble(fields[format.convert("h")]) > 0.0)
                		population.put(areaType, pop);
                		
                	}
                	
                	population.get(Constants.COMMAND).setTotalPopulation(Utils.parseInt(fields[format.convert("f")]));
                	population.get(Constants.NON_COMMAND).setTotalPopulation(Utils.parseInt(fields[format.convert("g")]));
                	
                  	if(locmapping.keySet().contains(MicroBasin_GWvillage_key)){
                  		if(village_details.get(locmapping.get(MicroBasin_GWvillage_key)).population==null){
                  			populationJson = populationjsn.toJson(population);
                  			village_details.get(locmapping.get(MicroBasin_GWvillage_key)).setPopulation(populationJson);
                		}
                  		else{
                  			population = populationjsn.fromJson(village_details.get(locmapping.get(MicroBasin_GWvillage_key)).getPopulation(), new TypeToken<Map<String, Population>>(){}.getType());
                  			if(population == null){
                  				population = new HashMap<String, Population>();
                  				for(String areaType : Constants.AREA_TYPES){
                            		Population pop = new Population();
                            		pop.setGrowthRate(Constants.POPULATION_GROWTH_RATE);
                            		pop.setReferenceYear(2011);
                            		pop.setLpcd(Constants.POPULATION_LPCD);
                            		population.put(areaType, pop);
                            	}
                  			}
                  			
                  			population.get(Constants.COMMAND).setTotalPopulation(population.get(Constants.COMMAND).getTotalPopulation()+Utils.parseInt(fields[format.convert("f")]));
                        	population.get(Constants.NON_COMMAND).setTotalPopulation(population.get(Constants.NON_COMMAND).getTotalPopulation()+Utils.parseInt(fields[format.convert("g")]));
                        	
                          	populationJson = populationjsn.toJson(population);
                            village_details.get(locmapping.get(MicroBasin_GWvillage_key)).setPopulation(populationJson);
                  		}
                      }
                      
                  }
              }
              
          }catch (IOException e) {
              e.printStackTrace();
          }

        
//************************************ Only for 2012-2013   
	if(assesssment_year.equals("2012-2013")) {     
		try(BufferedReader inp = new BufferedReader(new FileReader(cropDataFile))) {
        	Map<String, Integer> keyRepetition = new HashMap<String, Integer>();
        	while((record = inp.readLine()) != null ) {
        		String[] fields = record.split(",",-1);
        	    
        		String basinName = format.removeQuotes(fields[format.convert("c")]);
                String villageName = format.removeQuotes(fields[format.convert("d")]);
            	String MicroBasin_GWvillage_key = basinName + "##" + villageName;
         	   
         	   	if(keyRepetition.containsKey(MicroBasin_GWvillage_key)){
                	int times = keyRepetition.get(MicroBasin_GWvillage_key)+1;
                	keyRepetition.put(MicroBasin_GWvillage_key, times);
                	MicroBasin_GWvillage_key = MicroBasin_GWvillage_key + "-" + String.valueOf(times);
                }else
                	keyRepetition.put(MicroBasin_GWvillage_key, 0);
         	   	
        		if(locmapping.get(MicroBasin_GWvillage_key) == null) {
        			System.out.println("Crop Information : location not found "+MicroBasin_GWvillage_key);
        			continue;
        		} 
        		
//        		source vs AreaType vs CropType vs CropName vs CropData
        		Map<String, Map<String, Map<String, Map<String, CropData>>>> cropInfo = new HashMap<String, Map<String,Map<String,Map<String,CropData>>>>();
        		
        		for(String source : Constants.IRRIGATION_SOURCES){
        			cropInfo.put(source, new HashMap<String, Map<String,Map<String,CropData>>>());
        			for(String areaType : Constants.AREA_TYPES){
        				cropInfo.get(source).put(areaType, new HashMap<String, Map<String,CropData>>());
        				for(String cropType : Constants.CROP_TYPES){
        					cropInfo.get(source).get(areaType).put(cropType, new HashMap<String, CropData>());
        					String cropName = "A";
        					CropData cropData = new CropData();
        					cropData.setCropName(cropName);
        					cropData.setCropArea(new HashMap<String, Double>());
        					cropData.setWaterRequired(new HashMap<String, Double>());
        					if(cropType.equals(Constants.PADDY)){
        						cropData.getWaterRequired().put(Constants.MONSOON, Constants.PADDY_WATER_REQUIREMENT);
        						cropData.getWaterRequired().put(Constants.NON_MONSOON, 2*Constants.PADDY_WATER_REQUIREMENT);
        					}else{
        						cropData.getWaterRequired().put(Constants.MONSOON, Constants.NON_PADDY_WATER_REQUIREMENT);
        						cropData.getWaterRequired().put(Constants.NON_MONSOON, 2*Constants.NON_PADDY_WATER_REQUIREMENT);
        					}
        					cropInfo.get(source).get(areaType).get(cropType).put(cropName, cropData);
        				}
        			}
        		}
        		
        		//---------------------------------GW Irr
        		//------------Command
        		//Paddy
        		//monsoon//non-monsoon
        		cropInfo.get(Constants.GROUND_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("e")]));
        		cropInfo.get(Constants.GROUND_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("f")]));
        		
        		//Non-paddy
        		//monsoon//non-monsoon
        		cropInfo.get(Constants.GROUND_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("g")]));
        		cropInfo.get(Constants.GROUND_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("h")]));
        		
        		//----------------Non-command	
        		//Paddy
        		//monsoon//non-monsoon
        		cropInfo.get(Constants.GROUND_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("i")]));
        		cropInfo.get(Constants.GROUND_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("j")]));
        		
        		//Non-paddy
        		//monsoon//non-monsoon
        		cropInfo.get(Constants.GROUND_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("k")]));
        		cropInfo.get(Constants.GROUND_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("l")]));
        		
        		//----------------Poor-Quality	
        		//Paddy
        		//monsoon//non-monsoon
        		cropInfo.get(Constants.GROUND_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("m")]));
        		cropInfo.get(Constants.GROUND_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("n")]));
        		
        		//Non-paddy
        		//monsoon//non-monsoon
        		cropInfo.get(Constants.GROUND_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("o")]));
        		cropInfo.get(Constants.GROUND_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("p")]));
        		
        		
        		
        		//-------------------------------- MI IRR
        		//------------Command
        		//Paddy
        		//monsoon//non-monsoon
        		cropInfo.get(Constants.MI_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("q")]));
        		cropInfo.get(Constants.MI_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("r")]));
        		
        		//Non-paddy
        		//monsoon//non-monsoon
        		cropInfo.get(Constants.MI_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("s")]));
        		cropInfo.get(Constants.MI_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("t")]));
        		
        		//----------------Non-command	
        		//Paddy
        		//monsoon//non-monsoon
        		cropInfo.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("u")]));
        		cropInfo.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("v")]));
        		
        		//Non-paddy
        		//monsoon//non-monsoon
        		cropInfo.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("w")]));
        		cropInfo.get(Constants.MI_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("x")]));
        		
        		//----------------Poor-Quality	
        		//Paddy
        		//monsoon//non-monsoon
        		cropInfo.get(Constants.MI_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("y")]));
        		cropInfo.get(Constants.MI_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("z")]));
        		
        		//Non-paddy
        		//monsoon//non-monsoon
        		cropInfo.get(Constants.MI_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("aa")]));
        		cropInfo.get(Constants.MI_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("ab")]));
        		
        		
//        		//--------------------------------Surface Irr
//        		//------------Command
//        		//Paddy
//        		//monsoon//non-monsoon
//        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("ac")]));
//        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("ad")]));
//        		
//        		//Non-paddy
//        		//monsoon//non-monsoon
//        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("ae")]));
//        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("af")]));
//        		
//        		//----------------Non-command	
//        		//Paddy
//        		//monsoon//non-monsoon
//        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("ag")]));
//        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("ah")]));
//        		
//        		//Non-paddy
//        		//monsoon//non-monsoon
//        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("ai")]));
//        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("aj")]));
//        		
//        		//----------------Poor-Quality	
//        		//Paddy
//        		//monsoon//non-monsoon
//        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("ak")]));
//        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("al")]));
//        		
//        		//Non-paddy
//        		//monsoon//non-monsoon
//        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("am")]));
//        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("an")]));
        		
        		village_details.get(locmapping.get(MicroBasin_GWvillage_key)).crop_info = cropInfo;
        	}
        	

        } catch (IOException e) {
        	e.printStackTrace();
        }
		
		try(BufferedReader iem = new BufferedReader(new FileReader(sw_irr_file))) {
            
            record = iem.readLine();
            Map<String, Integer> keyRepetition = new HashMap<String, Integer>();
            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",",-1);
                String basinName = format.removeQuotes(fields[format.convert("c")]);
                String villageName = format.removeQuotes(fields[format.convert("d")]);
            	String MicroBasin_GWvillage_key = basinName + "##" + villageName;
         	   	double stored;
         	   	if(keyRepetition.containsKey(MicroBasin_GWvillage_key)){
                	int times = keyRepetition.get(MicroBasin_GWvillage_key)+1;
                	keyRepetition.put(MicroBasin_GWvillage_key, times);
                	MicroBasin_GWvillage_key = MicroBasin_GWvillage_key + "-" + String.valueOf(times);
                }else
                	keyRepetition.put(MicroBasin_GWvillage_key, 0);
         	   	
        		if(locmapping.get(MicroBasin_GWvillage_key) == null) {
        			System.out.println("Crop Information : location not found "+MicroBasin_GWvillage_key);
        			continue;
        		}
        		
        		Map<String, Map<String, Map<String, Map<String, CropData>>>> cropInfo = village_details.get(locmapping.get(MicroBasin_GWvillage_key)).crop_info;
        		if(cropInfo == null)	continue;
        	
        		//--------------------------------Surface Irr
        		//------------Command
        		//Paddy
        		//monsoon//non-monsoon
        		stored = Utils.validateValue(cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).get("A").getCropArea().get(Constants.MONSOON));
        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.MONSOON, stored + Utils.parseDouble(fields[format.convert("e")]));
        		stored = Utils.validateValue(cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).get("A").getCropArea().get(Constants.NON_MONSOON));
        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("f")]));
        		
        		//Non-paddy
        		//monsoon//non-monsoon
        		stored = Utils.validateValue(cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().get(Constants.MONSOON));
        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("g")]));
        		stored = Utils.validateValue(cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().get(Constants.NON_MONSOON));
        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("h")]));
        		
        		//----------------Non-command	
        		//Paddy
        		//monsoon//non-monsoon
        		stored = Utils.validateValue(cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).get("A").getCropArea().get(Constants.MONSOON));
        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("i")]));
        		stored = Utils.validateValue(cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).get("A").getCropArea().get(Constants.NON_MONSOON));
        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("j")]));
        		
        		//Non-paddy
        		//monsoon//non-monsoon
        		stored = Utils.validateValue(cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().get(Constants.MONSOON));
        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("k")]));
        		stored = Utils.validateValue(cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().get(Constants.NON_MONSOON));
        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.NON_COMMAND).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("l")]));
        		
        		//----------------Poor-Quality	
        		//Paddy
        		//monsoon//non-monsoon
        		stored = Utils.validateValue(cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.PADDY).get("A").getCropArea().get(Constants.MONSOON));
        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("m")]));
        		stored = Utils.validateValue(cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.PADDY).get("A").getCropArea().get(Constants.NON_MONSOON));
        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("n")]));
        		
        		//Non-paddy
        		//monsoon//non-monsoon
        		stored = Utils.validateValue(cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.NON_PADDY).get("A").getCropArea().get(Constants.MONSOON));
        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("o")]));
        		stored = Utils.validateValue(cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.NON_PADDY).get("A").getCropArea().get(Constants.NON_MONSOON));
        		cropInfo.get(Constants.SURFACE_WATER_IRRIGATION).get(Constants.POOR_QUALITY).get(Constants.NON_PADDY).get("A").getCropArea().put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("p")]));
        		
        		village_details.get(locmapping.get(MicroBasin_GWvillage_key)).crop_info = cropInfo;
            }
		}catch (Exception e) {
			System.out.println("VIllageMetaData : Some exception happened while asssigning SURFACE WATER IRRIGATION");
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
                		 
                		  //assessmentYear vs areaType vs Season vs pre/post vs value
                		  Map<String, Map<String, Map<String, Map<String, Double>>>> gw = new HashMap<>();
                		  gw.put(Constants.GEC_ASSESSMENT_YEAR, new HashMap<String, Map<String,Map<String,Double>>>());
                		  gw.get(Constants.GEC_ASSESSMENT_YEAR).put(Constants.COMMAND, new HashMap<String, Map<String,Double>>());
                		  gw.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.COMMAND).put(Constants.MONSOON, new HashMap<String, Double>());
                		  gw.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.COMMAND).get(Constants.MONSOON).put(Constants.PRE, Utils.parseDouble(fields[format.convert("h")]));
                		  gw.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.COMMAND).get(Constants.MONSOON).put(Constants.POST, Utils.parseDouble(fields[format.convert("i")]));
                		  gw.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.COMMAND).put(Constants.NON_MONSOON, new HashMap<String, Double>());
                		  gw.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.COMMAND).get(Constants.NON_MONSOON).put(Constants.PRE, Utils.parseDouble(fields[format.convert("i")]));
                		  gw.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.COMMAND).get(Constants.NON_MONSOON).put(Constants.POST, Utils.parseDouble(fields[format.convert("j")]));
                		  
                		  gw.get(Constants.GEC_ASSESSMENT_YEAR).put(Constants.NON_COMMAND, new HashMap<String, Map<String,Double>>());
                		  gw.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_COMMAND).put(Constants.MONSOON, new HashMap<String, Double>());
                		  gw.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_COMMAND).get(Constants.MONSOON).put(Constants.PRE, Utils.parseDouble(fields[format.convert("d")]));
                		  gw.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_COMMAND).get(Constants.MONSOON).put(Constants.POST, Utils.parseDouble(fields[format.convert("e")]));
                		  gw.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_COMMAND).put(Constants.NON_MONSOON, new HashMap<String, Double>());
                		  gw.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_COMMAND).get(Constants.NON_MONSOON).put(Constants.PRE, Utils.parseDouble(fields[format.convert("e")]));
                		  gw.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_COMMAND).get(Constants.NON_MONSOON).put(Constants.POST, Utils.parseDouble(fields[format.convert("f")]));
                		  
//                		  village_details.get(locmapping.get(basinKey)).gw_data = "";//(new Gson()).toJson(gw);
                		  village_details.get(locmapping.get(basinKey)).old_gw_data = (new Gson()).toJson(gw);
                		  
                		  //assessmentYear vs areaType vs season vs actual/Normal vs value
                		  Map<String, Map<String, Map<String, Map<String, Double>>>> rf = new HashMap<String, Map<String,Map<String,Map<String,Double>>>>();
                		  
                		  rf.put(Constants.GEC_ASSESSMENT_YEAR, new HashMap<String, Map<String,Map<String,Double>>>());
                		  rf.get(Constants.GEC_ASSESSMENT_YEAR).put(Constants.COMMAND, new HashMap<String, Map<String,Double>>());
                		  rf.get(Constants.GEC_ASSESSMENT_YEAR).put(Constants.NON_COMMAND, new HashMap<String, Map<String,Double>>());
                		  
                		  rf.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.COMMAND).put(Constants.MONSOON, new HashMap<String, Double>());
                		  rf.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.COMMAND).get(Constants.MONSOON).put(Constants.ACTUAL, Utils.parseDouble(fields[format.convert("r")]));
                		  rf.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.COMMAND).get(Constants.MONSOON).put(Constants.NORMAL, Utils.parseDouble(fields[format.convert("o")]));
                		  
                		  rf.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.COMMAND).put(Constants.NON_MONSOON, new HashMap<String, Double>());
                		  rf.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.COMMAND).get(Constants.NON_MONSOON).put(Constants.ACTUAL, Utils.parseDouble(fields[format.convert("s")]));
                		  rf.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.COMMAND).get(Constants.NON_MONSOON).put(Constants.NORMAL, Utils.parseDouble(fields[format.convert("p")]));
                		  
                		  rf.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_COMMAND).put(Constants.MONSOON, new HashMap<String, Double>());
                		  rf.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_COMMAND).get(Constants.MONSOON).put(Constants.ACTUAL, Utils.parseDouble(fields[format.convert("r")]));
                		  rf.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_COMMAND).get(Constants.MONSOON).put(Constants.NORMAL, Utils.parseDouble(fields[format.convert("o")]));
                		  
                		  rf.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_COMMAND).put(Constants.NON_MONSOON, new HashMap<String, Double>());
                		  rf.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_COMMAND).get(Constants.NON_MONSOON).put(Constants.ACTUAL, Utils.parseDouble(fields[format.convert("s")]));
                		  rf.get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_COMMAND).get(Constants.NON_MONSOON).put(Constants.NORMAL, Utils.parseDouble(fields[format.convert("p")]));
                		  
//                		  village_details.get(locmapping.get(basinKey)).rf_data =  "";//(new Gson()).toJson(rf);
                		  village_details.get(locmapping.get(basinKey)).old_rf_data =  (new Gson()).toJson(rf);

                		  
                		  Map<String, Population> population = populationjsn.fromJson(village_details.get(locmapping.get(basinKey)).getPopulation(), new TypeToken<Map<String, Population>>(){}.getType());
                		  if(population == null)
                			  population = new HashMap<String, Population>();
                		  
                		  if(population.get(Constants.COMMAND) == null)
                			  population.put(Constants.COMMAND, new Population());
                		  
                		  if(population.get(Constants.NON_COMMAND) == null)
                			  population.put(Constants.NON_COMMAND, new Population());
                		  
                		  population.get(Constants.COMMAND).setGwDependency(Utils.parseDouble(fields[format.convert("m")]));
	                   	  population.get(Constants.NON_COMMAND).setGwDependency(Utils.parseDouble(fields[format.convert("l")]));
	                   	  village_details.get(locmapping.get(basinKey)).setPopulation(populationjsn.toJson(population));
                		  
                	  }
                  }
              }
              
          }catch (IOException e) {
              e.printStackTrace();
          }
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
        
        /**
         * Prepare location name UUID map
         */
        Map<String, String> locNameUUIDMap = new HashMap<String, String>();
        
        try(BufferedReader iem = new BufferedReader(new FileReader(villageUUIDFile))) {
            while((record = iem.readLine()) != null) {
                String fields[] = record.split(",");
                
                if(fields.length==2){
                	String location = format.removeQuotes(fields[0]).trim();
                	String uuid = format.removeQuotes(fields[1]).trim();
                    locNameUUIDMap.put(location, uuid);
                }

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        
      //json for village
        try (BufferedWriter file = new BufferedWriter(new FileWriter(villMBAssocFile))) {
        	
        	bw.newLine();
        	bw.write("/* **** Scripts for village Microbasin intersection for : " + districtName + " **** */");
        	bw.newLine();
        	bw.newLine();
	        
        	Gson gson = new Gson();
        	
        	for(String village : villageMBAreaInfo.keySet()){
        		village = village.trim();
        		String villUUID = locNameUUIDMap.get(village);
        		if(villUUID == null){
        			System.out.println("ANKIT ::: " + village);
        		}
        		Map<String, Area> formattedData = new HashMap<String, Area>();
        		for(String mb : villageMBAreaInfo.get(village).keySet()){
        			mb = mb.trim();
        			if(mb.equals(Constants.TOTAL))
        				formattedData.put(mb, villageMBAreaInfo.get(village).get(mb));
        			else{
        				String mbUUID = locNameUUIDMap.get(mb);
        				formattedData.put(mbUUID, villageMBAreaInfo.get(village).get(mb));
        			}
        		}
        		String data = gson.toJson(formattedData);
        		IwmData iwmData = new IwmData("VILLAGE", villUUID, data);
        		file.write("Insert into iwm_data JSON '" + gson.toJson(iwmData) + "';\n");
        	}
	        System.out.println("Finished creating location association.");

        } catch (IOException e) {
            e.printStackTrace();
        }	  
    }
}


