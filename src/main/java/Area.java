import java.util.HashMap;
import java.util.Map;

public class Area{
	double command;
	double non_command;
	double poor_quality;
	double hilly;
	double forest;
	double total;
	
	
	Area(){
		super();
	}


	@Override
	public String toString() {
		return "Area [command=" + command + ", non_command=" + non_command
				+ ", poor_quality=" + poor_quality + ", hilly=" + hilly
				+ ", forest=" + forest + ", total=" + total + "]";
	}

}