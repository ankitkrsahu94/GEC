import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
	public static final String GEC_ASSESSMENT_YEAR = "2017-2018";
	public static final int PARENT_ID = 1;
	public static final int LOC_TYPE_VILLAGE = 10;
	public static final int LOC_TYPE_MB = 25;
	public static final double PADDY_WATER_REQUIREMENT = 600.00;
	public static final double NON_PADDY_WATER_REQUIREMENT = 300.00;
	public static final double POPULATION_GROWTH_RATE = 3.76;
	public static final double MI_INFILTRATION_RATE = 0.00144;
	public static final double DEFAULT_AGRO_WELL_YIELD = 2400.0;
	public static final double DEFAULT_DOMS_WELL_YIELD = 284.0;
	public static final double DEFAULT_INDS_WELL_YIELD = 284.0;
	public static final int MI_MONSOON_DAYS = 120;
	public static final int MI_NON_MONSOON_DAYS = 150;
	public static final String MONSOON = "monsoon";
	public static final String NON_MONSOON = "non_monsoon";
	public static final String ANNUAL = "annual";
	public static final String PRE_MONSOON = "pre_monsoon";
	public static final String POST_MONSOON = "post_monsoon";
	public static final double POPULATION_LPCD = 60;
	//Area
	public static final String POOR_QUALITY = "poor_quality";
	public static final String COMMAND = "command";
	public static final String NON_COMMAND = "non_command";
	public static final String HILLY = "hilly";
	public static final String FOREST = "forest";
	public static final String RECHARGE_WORTHY = "recharge_worthy";
	public static final String NON_RECHARGE_WORTHY = "non_recharge_worthy";
	//Table columns
	public static final String GEOLOGICAL_INFO = "geological_info";
	public static final String INFILTRATION_INFO = "infiltration_info";
	public static final String ACQUIFER_INFO = "acquifer_info";
	public static final String RESOURCE_DISTRIBUTION = "resource_distribution";
	public static final String INFILTRATION_RATE = "infiltration_rate";
	public static final String TRANSMISSIVITY = "transmissivity";
	public static final String STORAGE_COEFFICIENT = "storage_coefficient";
	public static final String SPECIFIC_YIELD = "specific_yield";
	//Views
	public static final String ADMIN = "admin";
	public static final String BASIN = "basin";
	public static final String POLITICAL = "political";
	//User Type
	public static final String GEC_FIELD_USER = "gec_field_user";
	public static final String GEC_DEPARTMENT_USER = "gec_department_user";
	//Component
	public static final String AGRICULTURE = "agriculture";
	public static final String DOMESTIC = "domestic";
	public static final String INDUSTRY = "industry";
	public static final String RAINFALL = "rainfall";
	public static final String WATER_BODY = "water_body";
	public static final String CANAL = "canal";
	public static final String ARTIFICIAL_STRUCTURE = "artificial_structure";
	public static final String SURFACE_WATER_IRRIGATION = "surface_irrigation";
	public static final String LIFT_IRRIGATION = "lift_irrigation";
	public static final String MI_IRRIGATION = "mi_irrigation";
	public static final String GROUND_WATER_IRRIGATION = "gw_irrigation";
	//Crop
	public static final String PADDY = "paddy";
	public static final String NON_PADDY = "nonPaddy";
	public static final List<String> CROP_TYPES = new ArrayList<String>(Arrays.asList(PADDY, NON_PADDY));
	public static final String KHARIF = "kharif";
	public static final String RABI = "rabi";
	//Others
	public static final int VERIFIED = 1;
	public static final int UNVERIFIED = 0;
	public static final String SAFE = "safe";
	public static final String SEMI_CRITICAL = "semi_critical";
	public static final String CRITICAL = "critical";
	public static final String OVER_EXPLOITED = "over_exploited";
	public static final String STAGE = "stage";
	public static final List<String> SEASONS = new ArrayList<>(Arrays.asList(MONSOON, NON_MONSOON));
	public static final int PROCESSED = 1;
	public static final int UNPROCESSED = 0;
	public static final List<String> AREA_TYPES = new ArrayList<String>(Arrays.asList(COMMAND, NON_COMMAND, POOR_QUALITY));
	public static final String RECHARGE = "recharge";
	public static final String DRAFT = "draft";
	public static final String OTHER = "other";
	public static final double DEFAULT_CROP_WATER_REQUIREMENT = 300;
	public static final int EXPIRATION_HOURS = 24;
	public static final String LOSS = "loss";
	public static final String TOTAL = "total";
	public static final String LOCATION_NAME = "locName";
	public static final String AREA = "area";
	public static final String GW_LEVEL_CHANGE = "gwLevelChange";
	public static final String THRESHOLD = "threshold";
	public static final String DEVIATION = "deviation";
	public static final String STAGE_OF_EXTRACTION = "stage";
	public static final String ACTUAL = "actual";
	public static final String NORMAL = "normal";
	public static final List<String> COMPUTATION_TYPES = new ArrayList<String>(Arrays.asList(ACTUAL, NORMAL));
	public static final int TARGET_YEAR = 2025;
	public static final String AVAILABILITY = "availability";
	public static final String AVERAGE_GW_LEVEL = "avg_gw_lvl";
	public static final String START_OF_PERIOD = "period_start";
	//Computation Methods
	public static final String UNIT_DRAFT = "unit_draft";
	public static final String CROP_WATER_REQUIREMENT = "crop_water_requirement";
	public static final String UNCONFINED_AQUIFER = "unconfined_aquifer";
	public static final String CONFINED_AQUIFER = "confined_aquifer";
	public static final String SEMI_CONFINED_AQUIFER = "semi_confined_aquifer";
	public static final String DYNAMIC_GROUND_WATER = "dynamic_gw";
	public static final String IN_STORAGE_GW = "in_storage_gw";
	public static final int DECIMAL = 5;
	public static final String IRRIGATION_AREA_INFO = "irrigationAreaInfo";
	public static final String CROP_AREA = "cropArea";
	public static final List<String> CATEGORIES = new ArrayList<String>(Arrays.asList(DOMESTIC, INDUSTRY, AGRICULTURE));
	public static final int PUMPING_HOURS = 7;
	public static final String LEVEL = "level";
	public static final String PRE = "pre";
	public static final String POST = "post";
	public static final String MI_TANK = "mi_tank";
	public static final String PT = "pt";
	public static final String MPT = "mpt";
	public static final String CD = "cd";
	public static final String FP = "fp";
	public static final List<String> ARS_TYPES = new ArrayList<String>(Arrays.asList(PT, MPT, CD, FP, OTHER));
	public static final Map<String, Double> FILLINGS = new HashMap<String, Double>();
	public static final Double ARS_INFIL_FACTOR = 0.4;
	public static final Double LITRES_TO_HAM = 0.0000001;
	public static final double HECTARE_PER_ACRE = 0.404686;
	public static final List<String> IRRIGATION_SOURCES = new ArrayList<String>(Arrays.asList(SURFACE_WATER_IRRIGATION, GROUND_WATER_IRRIGATION, MI_IRRIGATION));
	
	public static final String AVERAGE = "avg";
    /**
     * For each category what is the type of wells from which we can get yield
     */
    public static final Map<String, List<String>> CATEGORY_WELLS = new HashMap<>();
    static{
    	FILLINGS.put(FP, 22.0);
    	FILLINGS.put(PT, 1.5);
    	FILLINGS.put(MPT, 1.5);
    	FILLINGS.put(CD, 6.0);
    	FILLINGS.put(OTHER, 10.0);
    	
    	CATEGORY_WELLS.put(INDUSTRY, new ArrayList<String>());
    	CATEGORY_WELLS.put(DOMESTIC, new ArrayList<String>());
    	CATEGORY_WELLS.put(AGRICULTURE, new ArrayList<String>());
    	
    	CATEGORY_WELLS.get(AGRICULTURE).addAll(Arrays.asList("DW", "DW+PS/DCB", "BWs", "STW", "MTW", "Deep TW", "Filter Points"));
    	CATEGORY_WELLS.get(DOMESTIC).addAll(Arrays.asList("DW", "BWs"));
    	CATEGORY_WELLS.get(INDUSTRY).addAll(Arrays.asList("BWs"));
    }
	
}
