
public class Area{
	Double command;
	Double non_command;
	Double poor_quality;
	Double hilly;
	Double forest;
	Double total;
	
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
	
	public Area(double command, double non_command, double poor_quality, double hilly, double forest, double total) {
		super();
		this.command = command;
		this.non_command = non_command;
		this.poor_quality = poor_quality;
		this.hilly = hilly;
		this.forest = forest;
		this.total = total;
	}

	@Override
	public String toString() {
		return "Area [command=" + command + ", non_command=" + non_command
				+ ", poor_quality=" + poor_quality + "]";
	}
}