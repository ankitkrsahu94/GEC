import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class BasinXmltojson {

	public static void compute(String distName, String path, BufferedWriter bw) {
		
		String filePath = path+distName+"/";
		String assesssment_year = Village_Data.assesYear; 


		/**
		 * output files
		 */
		String basinCQLOutputFileName = path+"final_scripts/"+distName+"-InsertBasin.cql";

		/**
		 * Source files for data creation
		 */
		String gwlocToIWMloc = filePath+"final_mapping.csv"; // Input
																										// File
		String areafile = filePath+"area.csv";
		String geologicalFile = filePath+"geological_data.csv";
		String wellsSpecificYieldFile = filePath+"rainfall_unit_drift.csv";
		String waterbodiesfile = filePath+"mi_tanks.csv";
		String canalfile = filePath+"canals.csv";
		String artificialWCfile = filePath+"wc_structures.csv";
		String populationfile = filePath+"population.csv";
        String gw_rf_file = filePath+"rf_gw_data.csv";

		
		/**
         * Location meta data IWM base files
         */
        String basinIdfile = filePath+"../base_md/location/iwm_basin_name_id_map.csv";
        String villageIdfile = filePath+"../base_md/location/iwm_village_name_id_map.csv";
        
		String record = "";
		HashMap<String, String> locmapping = new HashMap<>();
		HashMap<String, String> villageMap = new HashMap<>();
		HashMap<String, Integer> Basin_Id = new HashMap<>();
		HashMap<String, Integer> village_id = new HashMap<>();
		Map<String, MicroBasin> Basin_details = new HashMap<>();
		Map<String, Double> Area = new HashMap<>();
		Map<String, String> wellsNameMapping = new HashMap<String, String>();
		wellsNameMapping.put("DW", "dugwell");
		wellsNameMapping.put("DW+PS/DCB", "dugwellPumpSet");
		wellsNameMapping.put("BWs", "borewell");
		wellsNameMapping.put("STW", "shallowTubeWell");
		wellsNameMapping.put("MTW", "mediumTubeWell");
		wellsNameMapping.put("Deep TW", "deepTubeWell");
		wellsNameMapping.put("Filter Points", "filterPoints");

		int tempCount;
		// loc mapping
		try (BufferedReader iem = new BufferedReader(new FileReader(gwlocToIWMloc))) {
			record = iem.readLine();
			tempCount = 0;
			while ((record = iem.readLine()) != null) {
				// System.out.println("record"+record);
				String GWvillageName;
				String IWMvillageName;
				String fields[] = record.split(",");
				// System.out.println("fields"+fields);
				if (fields.length == 4) {
					String MicroBasinName = format.removeQuotes(fields[format.convert("a")]);
					String IWMMicroBasinName = format.removeQuotes(fields[format.convert("b")]);
					GWvillageName = format.removeQuotes(fields[format.convert("c")]);
					IWMvillageName = format.removeQuotes(fields[format.convert("d")]);

					if (IWMvillageName.equalsIgnoreCase("#N/A"))
						continue;
					String MicroBasin_GWvillage_key = MicroBasinName + "##" + GWvillageName;
					locmapping.put(MicroBasinName, IWMMicroBasinName);
					villageMap.put(MicroBasin_GWvillage_key, IWMvillageName);
					// System.out.println(MicroBasin_GWvillage_key);
//					if(MicroBasinName.equals("ATP_C_65_Budedu")){
//						System.out.println("ANKIT ::: " + locmapping.get(MicroBasinName));
//					}
					Basin_details.put(locmapping.get(MicroBasinName), new MicroBasin());
					Basin_details.get(locmapping.get(MicroBasinName)).setMicroBasinName(locmapping.get(MicroBasinName));
//					System.out.println("ANKIT ::: " + Basin_details.get(locmapping.get(MicroBasinName)));
				}
			}

			System.out.println("loc size " + locmapping.size());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Basin Id----
		try (BufferedReader iem = new BufferedReader(new FileReader(basinIdfile))) {
			record = iem.readLine();
			while ((record = iem.readLine()) != null) {

				String fields[] = record.split(",");
				if (fields.length == 3) {
					String BasinName = format.removeQuotes(fields[format.convert("b")]);
					int BasinCode = Integer.parseInt(format.removeQuotes(fields[format.convert("c")]));
					Basin_Id.put(BasinName, BasinCode);
					if (Basin_details.get(BasinName) != null) {
						Basin_details.get(BasinName).setMicroBasinID(BasinCode);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// for(String basinName:Basin_details.keySet()){
		// MicroBasin basinObj = Basin_details.get(basinName);
		//// System.out.println(basinjson);
		//// break;
		// }

		// village ID-----
		try (BufferedReader iem = new BufferedReader(new FileReader(villageIdfile))) {
			record = iem.readLine();

			while ((record = iem.readLine()) != null) {
				String fields[] = record.split(",");
				if (fields.length == 2) {
					int VillageId = Integer.parseInt(fields[1]);
					String IWMvillageName = format.removeQuotes(fields[0]);
					village_id.put(IWMvillageName, VillageId);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// json for area
		try (BufferedReader iem = new BufferedReader(new FileReader(areafile))) {
			record = iem.readLine();
			record = iem.readLine();

			while ((record = iem.readLine()) != null) {
				String fields[] = record.split(",", -1);
				if (fields.length == 17 || fields.length == 25 || fields.length == 23 || fields.length == 24 || fields.length == 26 
						|| fields.length >= 30) {

					double total = 0;

					if (!fields[4].isEmpty()) {
						total = Double.parseDouble(fields[format.convert("e")]);
					}
					String iwmBasinName = locmapping.get(format.removeQuotes(fields[format.convert("c")]));
					String gwVillageName = format.removeQuotes(fields[format.convert("d")]);
					String villKey = format.removeQuotes(fields[format.convert("c")]) + "##" + gwVillageName;
					if (iwmBasinName != null) {

						if (Area.get(iwmBasinName) == null) {
							Area.put(iwmBasinName, total);
						} else {
							total = Area.get(iwmBasinName) + total;
							Area.put(iwmBasinName, total);
						}
//						 Basin_details.get(iwmBasinName).setArea(total);;
					}
				}else{
					System.out.println("line : 164 : Error : area row fields.length : " + fields.length);
				}
			}
			System.out.println("area count= " + Area.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// json for area
		Gson Areajs = new Gson();
		JSONObject jsn = new JSONObject();
		// inserting into area json Object
		try (BufferedReader iem = new BufferedReader(new FileReader(areafile))) {

			int count = 0;
			int c2 = 0;
			record = iem.readLine();
			record = iem.readLine();
			int test = 0;
			while ((record = iem.readLine()) != null) {
				String fields[] = record.split(",", -1);
				/*
				 * if(fields.length!=25&&fields.length!=23&&fields.length!=24){
				 * System.out.println(fields.length); for(String f:fields){
				 * System.out.print(f +" "); } System.out.println(); }
				 */
				if (fields.length == 17 || fields.length == 25 || fields.length == 23 || fields.length == 24 
						|| fields.length == 26 || fields.length == 31) {
					double total = 0;
					double command = 0;
					double nonCommand = 0;
					double poorQuality = 0;

					if (!fields[4].isEmpty()) {
						total = Double.parseDouble(fields[4]);
					}
					if (!fields[5].isEmpty()) {
						command = Double.parseDouble(fields[5]);
					}
					if (!fields[9].isEmpty()) {
						nonCommand = Double.parseDouble(fields[9]);
					}
					if (!fields[13].isEmpty()) {
						poorQuality = Double.parseDouble(fields[13]);
					}
					double hilly = 0;
					double forest = 0;
					if (!fields[6].isEmpty() || !fields[10].isEmpty() || !fields[14].isEmpty()) {
						hilly = Utils.parseDouble(fields[6]) + Utils.parseDouble(fields[10])
								+ Utils.parseDouble(fields[14]);
					}
					if (!fields[7].isEmpty() || !fields[11].isEmpty() || !fields[15].isEmpty()) {
						forest = Utils.parseDouble(fields[7]) + Utils.parseDouble(fields[11])
								+ Utils.parseDouble(fields[15]);
					}
					Area area = new Area();
					total = command + nonCommand + poorQuality + hilly + forest;
					
					area.command = command;
					area.non_command = nonCommand;
					area.poor_quality = poorQuality;
					area.hilly = hilly;
					area.forest = forest;
					area.total = total;
					
					String areajson = Areajs.toJson(area);
					// JSONObject jsn = new JSONObject(areajson.getBytes());
					// System.out.println(areajson);
//					System.out.println("locmaping : " + locmapping.keySet());
					if (locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")]))) {

						if (Basin_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])))
								.getArea() == null) {
							count++;
							Basin_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])))
									.setArea(areajson);
							if (total == 0) {
								test++;
								// System.out.println(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")]))));
							}
						} else {
							Area areaobj = Areajs.fromJson(Basin_details
									.get(locmapping.get(format.removeQuotes(fields[format.convert("c")]))).getArea(),
									Area.class);
							
							total += areaobj.total;
							command += areaobj.command;
							nonCommand += areaobj.non_command;
							poorQuality += areaobj.poor_quality;
							hilly += areaobj.hilly;
							forest += areaobj.forest;
							Area areaObj = new Area();
							areaObj.command = command;
							areaObj.non_command = nonCommand;
							areaObj.poor_quality = poorQuality;
							areaObj.hilly = hilly;
							areaObj.forest = forest;
							areaObj.total = total;
							String areaObjjson = Areajs.toJson(areaObj);
							Basin_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])))
									.setArea(areaObjjson);

						}
						if (total == 0) {
							// test++;
							// System.out.println(village_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")]))));
						}
					} else {
						System.out.println("asdas12312 :: " + format.removeQuotes(fields[format.convert("c")]));
					}
					// System.out.println("test count value ######"+test);
					// break;
				}

			}
			System.out.println("test count value ######" + test);
			int c = 0;
			for (String key : Basin_details.keySet()) {
				if (Basin_details.get(key).area == null) {
					// System.out.println(key);
					System.out.println(Basin_details.get(key).loc_name);
				}
//				System.out.println("ANKIT ::: key : " + key);
//				System.out.println(Basin_details.get(key).getArea());
				Area areaobj = Areajs.fromJson(Basin_details.get(key).getArea(), Area.class);
				if (areaobj.total == 0) {
					c++;
				}
			}
			System.out.println("area count where total area=0 : " + c);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// json for basinAssociation
		try (BufferedReader iem = new BufferedReader(new FileReader(areafile))) {

			record = iem.readLine();
			int c = 1;
			while ((record = iem.readLine()) != null) {

				String fields[] = record.split(",", -1);
				if (fields.length == 17 || fields.length == 25 || fields.length == 23 || fields.length == 24 
						|| fields.length == 26 || fields.length == 31) {
					int villageId;
					double fractionArea = 0;
					String iwmBasinName = locmapping.get(format.removeQuotes(fields[format.convert("c")]));
					String gwVillageName = format.removeQuotes(fields[format.convert("d")]);
					String villKey = format.removeQuotes(fields[format.convert("c")]) + "##" + gwVillageName;

					if (!fields[format.convert("e")].isEmpty()) {
						fractionArea = Double.parseDouble(fields[format.convert("e")]);
						if (iwmBasinName != null && village_id.containsKey(villageMap.get(villKey))) {
							villageId = village_id.get(villageMap.get(villKey));

							Map<Integer, Double> basin_assoc = Basin_details.get(iwmBasinName).getBasinAssociation();
							if (basin_assoc == null) {
								basin_assoc = new HashMap<>();
								c++;
							}
							if(Area.get(iwmBasinName) != 0){
								basin_assoc.put(villageId, (fractionArea / Area.get(iwmBasinName)));
								Basin_details.get(iwmBasinName).setBasinAssociation(basin_assoc);
							}else{
								System.out.println("Basin area 0 for basin : " + iwmBasinName);
							}
						}
					}
				}else{
					System.out.println("Error : area row fields.length : " + fields.length);
				}
			}

			System.out.println("village assoc " + c);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// json for the resource distribution data
		try (BufferedReader file = new BufferedReader(new FileReader(wellsSpecificYieldFile))) {

			record = file.readLine();
			record = file.readLine();
			record = file.readLine();
			record = file.readLine();
			tempCount = 1;

			int forYear = 2011;
			double growthRate = 0;
			int pumpingHours = 7;
			int noOfWells;
			double yield;
			double pumpingHours_monsoon;
			double pumpingHours_nonmonsoon;
			WellsUtilizationData wellsData;
			Map<String, Utilization> uti;

			while ((record = file.readLine()) != null) {

				growthRate = 0;
				String fields[] = record.split(",");
//				if (fields.length == format.convert("aj") + 1) {
				if (fields.length == format.convert("aj") + 5) {
					++tempCount;

					String gwBasinName = format.removeQuotes(fields[format.convert("b")]);
					String wellname = format.removeQuotes(fields[format.convert("c")]);
					String iwmBasinName = locmapping.get(gwBasinName);
					if (iwmBasinName == null || wellsNameMapping.get(wellname) == null)
						continue;

					if (Basin_details.get(iwmBasinName).getResource_distribution() == null) {
						Map<String, Utilization> resource_distribution = new HashMap<>();
						resource_distribution.put("agriculture", new Utilization());
						resource_distribution.put("domestic", new Utilization());
						resource_distribution.put("industry", new Utilization());
						Basin_details.get(iwmBasinName).setResource_distribution(resource_distribution);
					}

					if (wellname.equals("BWs"))
						growthRate = 3;

					yield = fields[format.convert("d")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("d")]);
					noOfWells = fields[format.convert("e")].isEmpty() ? 0
							: Integer.parseInt(fields[format.convert("e")]);
					pumpingHours_monsoon = fields[format.convert("f")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("f")]);
					pumpingHours_nonmonsoon = fields[format.convert("g")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("g")]);
					wellsData = new WellsUtilizationData(forYear, noOfWells, pumpingHours, yield, pumpingHours_monsoon,
							pumpingHours_nonmonsoon, growthRate);
					uti = Basin_details.get(iwmBasinName).getResource_distribution();
					uti.get("agriculture").command.put(wellsNameMapping.get(wellname), wellsData);
					Basin_details.get(iwmBasinName).setResource_distribution(uti);

					yield = fields[format.convert("h")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("h")]);
					noOfWells = fields[format.convert("i")].isEmpty() ? 0
							: Integer.parseInt(fields[format.convert("i")]);
					pumpingHours_monsoon = fields[format.convert("j")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("j")]);
					pumpingHours_nonmonsoon = fields[format.convert("k")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("k")]);
					wellsData = new WellsUtilizationData(forYear, noOfWells, pumpingHours, yield, pumpingHours_monsoon,
							pumpingHours_nonmonsoon, growthRate);
					uti = Basin_details.get(iwmBasinName).getResource_distribution();
					uti.get("domestic").command.put(wellsNameMapping.get(wellname), wellsData);
					Basin_details.get(iwmBasinName).setResource_distribution(uti);

					yield = fields[format.convert("l")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("l")]);
					noOfWells = fields[format.convert("m")].isEmpty() ? 0
							: Integer.parseInt(fields[format.convert("m")]);
					pumpingHours_monsoon = fields[format.convert("n")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("n")]);
					pumpingHours_nonmonsoon = fields[format.convert("o")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("o")]);
					wellsData = new WellsUtilizationData(forYear, noOfWells, pumpingHours, yield, pumpingHours_monsoon,
							pumpingHours_nonmonsoon, growthRate);
					uti = Basin_details.get(iwmBasinName).getResource_distribution();
					uti.get("industry").command.put(wellsNameMapping.get(wellname), wellsData);
					Basin_details.get(iwmBasinName).setResource_distribution(uti);

					// --------------------

					yield = fields[format.convert("p")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("p")]);
					noOfWells = fields[format.convert("q")].isEmpty() ? 0
							: Integer.parseInt(fields[format.convert("q")]);
					pumpingHours_monsoon = fields[format.convert("r")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("r")]);
					pumpingHours_nonmonsoon = fields[format.convert("s")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("s")]);
					wellsData = new WellsUtilizationData(forYear, noOfWells, pumpingHours, yield, pumpingHours_monsoon,
							pumpingHours_nonmonsoon, growthRate);
					uti = Basin_details.get(iwmBasinName).getResource_distribution();
					uti.get("agriculture").non_command.put(wellsNameMapping.get(wellname), wellsData);
					Basin_details.get(iwmBasinName).setResource_distribution(uti);

					yield = fields[format.convert("t")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("t")]);
					noOfWells = fields[format.convert("u")].isEmpty() ? 0
							: Integer.parseInt(fields[format.convert("u")]);
					pumpingHours_monsoon = fields[format.convert("v")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("v")]);
					pumpingHours_nonmonsoon = fields[format.convert("w")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("w")]);
					wellsData = new WellsUtilizationData(forYear, noOfWells, pumpingHours, yield, pumpingHours_monsoon,
							pumpingHours_nonmonsoon, growthRate);
					uti.get("domestic").non_command.put(wellsNameMapping.get(wellname), wellsData);
					Basin_details.get(iwmBasinName).setResource_distribution(uti);

					yield = fields[format.convert("x")].isEmpty() ? 0 : Double.parseDouble(fields[format.convert("x")]);
					noOfWells = fields[format.convert("y")].isEmpty() ? 0
							: Integer.parseInt(fields[format.convert("y")]);
					pumpingHours_nonmonsoon = fields[format.convert("z")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("z")]);
					pumpingHours_monsoon = fields[format.convert("aa")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("aa")]);
					wellsData = new WellsUtilizationData(forYear, noOfWells, pumpingHours, yield, pumpingHours_monsoon,
							pumpingHours_nonmonsoon, growthRate);
					uti.get("industry").non_command.put(wellsNameMapping.get(wellname), wellsData);
					Basin_details.get(iwmBasinName).setResource_distribution(uti);

					// --------------------

					yield = fields[format.convert("ab")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("ab")]);
					noOfWells = fields[format.convert("ac")].isEmpty() ? 0
							: Integer.parseInt(fields[format.convert("ac")]);
					pumpingHours_monsoon = fields[format.convert("ad")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("ad")]);
					pumpingHours_nonmonsoon = fields[format.convert("ae")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("ae")]);
					wellsData = new WellsUtilizationData(forYear, noOfWells, pumpingHours, yield, pumpingHours_monsoon,
							pumpingHours_nonmonsoon, growthRate);
					uti.get("agriculture").poor_quality.put(wellsNameMapping.get(wellname), wellsData);
					Basin_details.get(iwmBasinName).setResource_distribution(uti);

					yield = fields[format.convert("af")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("af")]);
					noOfWells = fields[format.convert("ag")].isEmpty() ? 0
							: Integer.parseInt(fields[format.convert("ag")]);
					pumpingHours_monsoon = fields[format.convert("ah")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("ah")]);
					pumpingHours_nonmonsoon = fields[format.convert("ai")].isEmpty() ? 0
							: Double.parseDouble(fields[format.convert("ai")]);
					wellsData = new WellsUtilizationData(forYear, noOfWells, pumpingHours, yield, pumpingHours_monsoon,
							pumpingHours_nonmonsoon, growthRate);
					uti.get("industry").poor_quality.put(wellsNameMapping.get(wellname), wellsData);
					Basin_details.get(iwmBasinName).setResource_distribution(uti);

					yield = 0;
					noOfWells = 0;
					pumpingHours_monsoon = 0;
					pumpingHours_nonmonsoon = 0;
					wellsData = new WellsUtilizationData(forYear, noOfWells, pumpingHours, yield, pumpingHours_monsoon,
							pumpingHours_nonmonsoon, growthRate);
					uti.get("domestic").poor_quality.put(wellsNameMapping.get(wellname), wellsData);
					Basin_details.get(iwmBasinName).setResource_distribution(uti);
				}
			}

			System.out.println("Basin having resource utilization :" + tempCount);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// json for geological
		Gson geological = new Gson();
		// inserting into geological json Object
		try (BufferedReader iem = new BufferedReader(new FileReader(geologicalFile))) {

			int count = 0;
			record = iem.readLine();
			record = iem.readLine();

			while ((record = iem.readLine()) != null) {
				String fields[] = record.split(",", -1);
				Map<String, Double> geoInfo = new HashMap<>();
				Map<String, Map<String, Double>> GeologicalInfo = new HashMap<>();
				// System.out.println("fields = "+fields.length);

				if (fields.length == 20) {
					count++;

					geoInfo.put("fraction", 1.0);
					geoInfo.put("specific_yield", Utils.parseDouble(fields[format.convert("d")]));
					geoInfo.put("transmissivity", Utils.parseDouble("0"));
					geoInfo.put("storage_coefficient", Utils.parseDouble("0"));

					if (fields[format.convert("c")].equalsIgnoreCase("Weathered granite(Weathered granite)"))
						GeologicalInfo.put("Weathered granite", geoInfo);
					else
						GeologicalInfo.put("Weathered gneiss", geoInfo);

					if (Basin_details.containsKey(locmapping.get(format.removeQuotes(fields[format.convert("b")])))) { // #TODO
																														// required
						Basin_details.get(locmapping.get(format.removeQuotes(fields[format.convert("b")])))
								.setGeologicalInfo(GeologicalInfo);
					}else{
						System.out.println("ANKIT ::: no geo distname : " + distName + " : " + format.removeQuotes(fields[format.convert("b")]));
						System.out.println("\n\n\n\n\n\n\n\n\n\n\n\\nn\\n\n\n\n\n\n\n\n\n\n\n");
					}

				}else{
//					System.out.println("Geological info : invalid row : length : " + fields.length + " : " + record);
				}
			}
			System.out.println("geo count= " + count);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// json for InfiltrationInfo
		Gson InfiltrationInfo = new Gson();
		// inserting into InfiltrationInfo json Object
		try (BufferedReader iem = new BufferedReader(new FileReader(geologicalFile))) {

			int count = 0;
			record = iem.readLine();
			record = iem.readLine();

			while ((record = iem.readLine()) != null) {
				String fields[] = record.split(",", -1);
				Map<String, Double> geoInfo = new HashMap<>();
				Map<String, Map<String, Double>> GeologicalInfo = new HashMap<>();
				// System.out.println("fields = "+fields.length);

				if (fields.length == 20) {
					count++;
					if (fields[format.convert("c")].equalsIgnoreCase("Weathered granite(Weathered granite)")) {
						geoInfo.put("fraction", 1.0);
						geoInfo.put("infiltration_rate", Double.parseDouble(fields[format.convert("e")]));
						GeologicalInfo.put("Weathered granite", geoInfo);
					} else {
						geoInfo.put("fraction", 0.0);
						geoInfo.put("infiltration_rate", Double.parseDouble(fields[format.convert("e")]));
						GeologicalInfo.put("Weathered gneiss", geoInfo);
					}
					
					if (locmapping.keySet().contains(format.removeQuotes(fields[format.convert("b")]))
							|| Basin_details.keySet().contains(format.removeQuotes(fields[format.convert("b")]))) {
						Basin_details.get(locmapping.get(format.removeQuotes(fields[format.convert("b")])))
								.setInfiltrationInfo(GeologicalInfo);
					}else{
						System.out.println("ANKIT ::: no infiltration distname : " + distName + " : " + format.removeQuotes(fields[format.convert("b")]));
					}
					// System.out.println(count+" "+ResidentialUtilizationjson);

				}
			}
			System.out.println("residential count= " + count);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// inserting into canal json Object
		try (BufferedReader iem = new BufferedReader(new FileReader(canalfile))) {

			int count = 0;
			record = iem.readLine();
			record = iem.readLine();

			while ((record = iem.readLine()) != null) {
				ArrayList<Map<String, Object>> canal = new ArrayList<>();
				// json for Canal
				HashMap<String, Object> canalMap = new HashMap<>();

				String fields[] = record.split(",");
				// 16
				if (fields.length == 20) {
					Map<String, Integer> runningDays = new HashMap<>();
					canalMap.put("length", Double.parseDouble(fields[4]));
					canalMap.put("type", "0");
					canalMap.put("sideSlopes", Double.parseDouble(fields[7]));// sideSlopes
					canalMap.put("wettedPerimeter", Double.parseDouble(fields[8]));// wettedperimeter
					canalMap.put("wettedArea", Double.parseDouble(fields[9]));// wettedarea
					canalMap.put("seepageFactor", Double.parseDouble(fields[10]));// canalseepagefactor
					canalMap.put("bedWidth", Double.parseDouble(fields[6]));// bedwidth
					canalMap.put("designDepthFlow", Double.parseDouble("0"));// designDepthOfFlow
					runningDays.put("monsoon", Integer.parseInt(fields[11]));// runningDays_monsoon
					runningDays.put("non_monsoon", Integer.parseInt(fields[12]));// runningDays_nonmonsoon
					canalMap.put("noOfRunningDays", runningDays);
					canal.add(canalMap);
					if (locmapping.keySet().contains(format.removeQuotes(fields[format.convert("c")]))) {

						if (Basin_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])))
								.getCanal_info() == null) {
							count++;

							Basin_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])))
									.setCanal_info(canal);

						} else {

							Basin_details.get(locmapping.get(format.removeQuotes(fields[format.convert("c")])))
									.getCanal_info().add(canalMap);

						}

					}
					// System.out.println(canal);
				}
			}
			System.out.println("canal count= " + count);

		} catch (IOException e) {
			e.printStackTrace();
		}

        // json for gw and rainfall
		if(assesssment_year.equals("2012-2013")) {     
	
	        try(BufferedReader iem = new BufferedReader(new FileReader(gw_rf_file))) {
	              
	              record = iem.readLine();
	              while((record = iem.readLine()) != null) {
	                  String fields[] = record.split(",",-1);
	
	                  
	                  String basinName = format.removeQuotes(fields[format.convert("c")]);
	                		  
	                  //AreaType vs 'avg' vs 'level' vs Assessmentyear vs season vs pre-post vs data
            		  Map<String, Map<String, Map<String, Map<String, Map<String, Map<String, Double>>>>>> gw = new HashMap<String, Map<String,Map<String,Map<String,Map<String,Map<String,Double>>>>>>();
            		  gw.put(Constants.COMMAND, new HashMap<String, Map<String,Map<String,Map<String,Map<String,Double>>>>>());
            		  gw.put(Constants.NON_COMMAND, new HashMap<String, Map<String,Map<String,Map<String,Map<String,Double>>>>>());
            		  gw.put(Constants.POOR_QUALITY, new HashMap<String, Map<String,Map<String,Map<String,Map<String,Double>>>>>());
            		  
            		  gw.get(Constants.COMMAND).put(Constants.AVERAGE, new HashMap<String, Map<String,Map<String,Map<String,Double>>>>());
            		  gw.get(Constants.NON_COMMAND).put(Constants.AVERAGE, new HashMap<String, Map<String,Map<String,Map<String,Double>>>>());
            		  gw.get(Constants.POOR_QUALITY).put(Constants.AVERAGE, new HashMap<String, Map<String,Map<String,Map<String,Double>>>>());
            		  
            		  gw.get(Constants.COMMAND).get(Constants.AVERAGE).put(Constants.LEVEL, new HashMap<String, Map<String,Map<String,Double>>>());
            		  gw.get(Constants.NON_COMMAND).get(Constants.AVERAGE).put(Constants.LEVEL, new HashMap<String, Map<String,Map<String,Double>>>());
            		  gw.get(Constants.POOR_QUALITY).get(Constants.AVERAGE).put(Constants.LEVEL, new HashMap<String, Map<String,Map<String,Double>>>());
            		  
            		  gw.get(Constants.COMMAND).get(Constants.AVERAGE).get(Constants.LEVEL).put(Constants.GEC_ASSESSMENT_YEAR, new HashMap<String, Map<String,Double>>());
            		  gw.get(Constants.NON_COMMAND).get(Constants.AVERAGE).get(Constants.LEVEL).put(Constants.GEC_ASSESSMENT_YEAR, new HashMap<String, Map<String,Double>>());
            		  gw.get(Constants.POOR_QUALITY).get(Constants.AVERAGE).get(Constants.LEVEL).put(Constants.GEC_ASSESSMENT_YEAR, new HashMap<String, Map<String,Double>>());
            		  
            		  gw.get(Constants.COMMAND).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).put(Constants.MONSOON, new HashMap<String, Double>());
            		  gw.get(Constants.COMMAND).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.MONSOON).put(Constants.PRE, Utils.parseDouble(fields[format.convert("h")]));
            		  gw.get(Constants.COMMAND).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.MONSOON).put(Constants.POST, Utils.parseDouble(fields[format.convert("i")]));
            		  
            		  gw.get(Constants.COMMAND).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).put(Constants.NON_MONSOON, new HashMap<String, Double>());
            		  gw.get(Constants.COMMAND).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_MONSOON).put(Constants.PRE, Utils.parseDouble(fields[format.convert("i")]));
            		  gw.get(Constants.COMMAND).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_MONSOON).put(Constants.POST, Utils.parseDouble(fields[format.convert("j")]));
            		  
            		  gw.get(Constants.NON_COMMAND).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).put(Constants.MONSOON, new HashMap<String, Double>());
            		  gw.get(Constants.NON_COMMAND).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.MONSOON).put(Constants.PRE, Utils.parseDouble(fields[format.convert("d")]));
            		  gw.get(Constants.NON_COMMAND).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.MONSOON).put(Constants.POST, Utils.parseDouble(fields[format.convert("e")]));
            		  
            		  gw.get(Constants.NON_COMMAND).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).put(Constants.NON_MONSOON, new HashMap<String, Double>());
            		  gw.get(Constants.NON_COMMAND).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_MONSOON).put(Constants.PRE, Utils.parseDouble(fields[format.convert("e")]));
            		  gw.get(Constants.NON_COMMAND).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_MONSOON).put(Constants.POST, Utils.parseDouble(fields[format.convert("f")]));
            		  
            		  gw.get(Constants.POOR_QUALITY).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).put(Constants.MONSOON, new HashMap<String, Double>());
            		  gw.get(Constants.POOR_QUALITY).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.MONSOON).put(Constants.PRE, Utils.parseDouble(fields[format.convert("d")]));
            		  gw.get(Constants.POOR_QUALITY).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.MONSOON).put(Constants.POST, Utils.parseDouble(fields[format.convert("e")]));
            		  
            		  gw.get(Constants.POOR_QUALITY).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).put(Constants.NON_MONSOON, new HashMap<String, Double>());
            		  gw.get(Constants.POOR_QUALITY).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_MONSOON).put(Constants.PRE, Utils.parseDouble(fields[format.convert("e")]));
            		  gw.get(Constants.POOR_QUALITY).get(Constants.AVERAGE).get(Constants.LEVEL).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NON_MONSOON).put(Constants.POST, Utils.parseDouble(fields[format.convert("f")]));

            		  
            		  Basin_details.get(basinName).gw_data = (new Gson()).toJson(gw);
            		  
            		  //areaType vs AssessmentYear vs mon/nonmonsoon vs actual/normal vs value
            		//areaType vs AssessmentYear vs mon/nonmonsoon vs actual/normal vs value
            		  Map<String, Map<String, Map<String, Map<String, Double>>>> rf = new HashMap<String, Map<String,Map<String,Map<String,Double>>>>();
            		  rf.put(Constants.COMMAND, new HashMap<String, Map<String,Map<String,Double>>>());
            		  rf.put(Constants.NON_COMMAND, new HashMap<String, Map<String,Map<String,Double>>>());
            		  
            		  rf.get(Constants.COMMAND).put(Constants.GEC_ASSESSMENT_YEAR, new HashMap<String, Map<String, Double>>());
            		  rf.get(Constants.NON_COMMAND).put(Constants.GEC_ASSESSMENT_YEAR, new HashMap<String, Map<String, Double>>());
            		  rf.get(Constants.COMMAND).get(Constants.GEC_ASSESSMENT_YEAR).put(Constants.ACTUAL, new HashMap<String, Double>());
            		  rf.get(Constants.COMMAND).get(Constants.GEC_ASSESSMENT_YEAR).put(Constants.NORMAL, new HashMap<String, Double>());
            		  rf.get(Constants.NON_COMMAND).get(Constants.GEC_ASSESSMENT_YEAR).put(Constants.ACTUAL, new HashMap<String, Double>());
            		  rf.get(Constants.NON_COMMAND).get(Constants.GEC_ASSESSMENT_YEAR).put(Constants.NORMAL, new HashMap<String, Double>());
            		 
            		  rf.get(Constants.COMMAND).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.ACTUAL).put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("r")]));
            		  rf.get(Constants.COMMAND).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NORMAL).put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("o")]));
            		  rf.get(Constants.COMMAND).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.ACTUAL).put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("s")]));
            		  rf.get(Constants.COMMAND).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NORMAL).put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("p")]));
            		  
            		  rf.get(Constants.NON_COMMAND).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.ACTUAL).put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("r")]));
            		  rf.get(Constants.NON_COMMAND).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NORMAL).put(Constants.MONSOON, Utils.parseDouble(fields[format.convert("o")]));
            		  rf.get(Constants.NON_COMMAND).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.ACTUAL).put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("s")]));
            		  rf.get(Constants.NON_COMMAND).get(Constants.GEC_ASSESSMENT_YEAR).get(Constants.NORMAL).put(Constants.NON_MONSOON, Utils.parseDouble(fields[format.convert("p")]));
            		  
            		  Basin_details.get(basinName).rf_data =  (new Gson()).toJson(rf);
            		  	  
	              }
	              
	          }catch (IOException e) {
	              e.printStackTrace();
	          }
        
		}
		// json for basin
		int count = 0;
		try (BufferedWriter file = new BufferedWriter(new FileWriter(basinCQLOutputFileName))) {
			
			bw.newLine();
        	bw.write("/* **** Scripts for district : " + distName + " **** */");
        	bw.newLine();
        	bw.newLine();
			
			Gson Basin = new Gson();

			for (String basinName : Basin_details.keySet()) {

				MicroBasin basinObj = Basin_details.get(basinName);
				MicroBasin_Data basin_obj = new MicroBasin_Data(basinObj);
				String basinjson = Basin.toJson(basin_obj);
				String query = "Insert into location_md JSON '" + basinjson + "';";
				count++;

				file.write(query + "\n");
				file.newLine();

				bw.write(query + "\n");
				bw.newLine();
//				System.out.println(basinjson);
				// break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("count total = " + count);
		System.out.println("end");

	}

}
