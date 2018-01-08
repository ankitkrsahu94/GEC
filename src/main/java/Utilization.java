import java.util.HashMap;
import java.util.Map;


public class Utilization {

	public Map<String, WellsUtilizationData> command;
	public Map<String, WellsUtilizationData> non_command;
	public Map<String, WellsUtilizationData> poor_quality;
	
	public Utilization() {
		command = new HashMap<String, WellsUtilizationData>();
		non_command = new HashMap<String, WellsUtilizationData>();
		poor_quality = new HashMap<String, WellsUtilizationData>();
	}
	
//	public Utilization(WellsUtilizationTypeData command,
//			WellsUtilizationTypeData poor_quality,
//			WellsUtilizationTypeData non_command) {
//		super();
//		this.command = command;
//		this.poor_quality = poor_quality;
//		this.non_command = non_command;
//	}
	
}
