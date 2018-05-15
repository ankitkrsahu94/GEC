
public class Area{
	double command;
	double non_command;
	double poor_quality;
	double total;
	
	Area(){
		super();
	}

	public Area(double command, double non_command, double poor_quality, double total) {
		super();
		this.command = command;
		this.non_command = non_command;
		this.poor_quality = poor_quality;
		this.total = total;
	}

	@Override
	public String toString() {
		return "Area [command=" + command + ", non_command=" + non_command
				+ ", poor_quality=" + poor_quality + "]";
	}
}