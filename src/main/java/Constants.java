import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
	public static final String MONSOON = "monsoon";
	public static final String NON_MONSOON = "non_monsoon";
	public static final String ANNUAL = "annual";
	public static final String PRE_MONSOON = "pre_monsoon";
	public static final String POST_MONSOON = "post_monsoon";
	//Area
	public static final String POOR_QUALITY = "poor_quality";
	public static final String COMMAND_AREA = "command";
	public static final String NON_COMMAND_AREA = "non_command";
	public static final String NON_COMMAND_POOR_QUALITY_AREA = "non_command_poor_quality";
	public static final String RECHARGE_WORTHY_AREA = "recharge_worthy";
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
	public static final String GROUND_WATER_IRRIGATION = "gw_irrigation";
	//Crop
	public static final String PADDY = "paddy";
	public static final String NON_PADDY = "nonPaddy";
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
	public static final List<String> ASSESSMENT_YEAR = new ArrayList<>(Arrays.asList("2016-2017"));
	public static final List<String> SEASONS = new ArrayList<>(Arrays.asList(MONSOON, NON_MONSOON, ANNUAL));
	public static final int PROCESSED = 1;
	public static final int UNPROCESSED = 0;
	public static final List<String> AREA_TYPES = new ArrayList<String>(Arrays.asList(COMMAND_AREA, NON_COMMAND_AREA, POOR_QUALITY));
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
    /**
     * For each category what is the type of wells from which we can get yield
     */
    public static final Map<String, List<String>> CATEGORY_WELLS = new HashMap<>();
    static{
    	CATEGORY_WELLS.put(INDUSTRY, new ArrayList<String>());
    	CATEGORY_WELLS.put(DOMESTIC, new ArrayList<String>());
    	CATEGORY_WELLS.put(AGRICULTURE, new ArrayList<String>());
    	
    	CATEGORY_WELLS.get(AGRICULTURE).addAll(Arrays.asList("DW", "DW+PS/DCB", "BWs", "STW", "MTW", "Deep TW", "Filter Points"));
    	CATEGORY_WELLS.get(DOMESTIC).addAll(Arrays.asList("DW", "BWs"));
    	CATEGORY_WELLS.get(INDUSTRY).addAll(Arrays.asList("Deep TW"));
    }
	
}
