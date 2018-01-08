public class ResidentialUtilization {
	
	public WellsUtilizationTypeData command;
	public WellsUtilizationTypeData poor_quality;
	public WellsUtilizationTypeData non_command;
	public ResidentialUtilization(WellsUtilizationTypeData command,
			WellsUtilizationTypeData poor_quality,
			WellsUtilizationTypeData non_command) {
		super();
		this.command = command;
		this.poor_quality = poor_quality;
		this.non_command = non_command;
	}
}

