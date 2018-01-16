import java.util.HashMap;
import java.util.Map;


public class waterbodies {
	
	public Command command;
	public CommandPoorQuality poor_quality;
	public NonCommand non_command;
	
	waterbodies(Command command,NonCommand nonCommand,CommandPoorQuality commandPoorQuality){
		this.command= command;
		this.non_command=nonCommand;
		this.poor_quality=commandPoorQuality;
	}

}
class Command{
	public Mitank mitank;
	Command(Mitank mitank){
		this.mitank=mitank;
	}
}
class NonCommand{
	public Mitank mitank;
	NonCommand(Mitank mitank){
		this.mitank=mitank;
	}
}
class CommandPoorQuality{
	public Mitank mitank;
	CommandPoorQuality(Mitank mitank){
		this.mitank=mitank;
	}
}
class Mitank{
	double count;
	double spreadArea;
	double capacity;
	Map<String, Double> impoundDays;
	double infiltrationFactor;
	Mitank(double count,double spreadArea, double capacity,double impoundDays_monsoon,double impoundDays_nonmonsoon,double infiltrationFactor){
		this.count=count;
		this.spreadArea = spreadArea;
		this.capacity=capacity;
		impoundDays = new HashMap<>();
		this.impoundDays.put("monsoon", impoundDays_monsoon);
		this.impoundDays.put("non_monsoon", impoundDays_nonmonsoon);
		this.infiltrationFactor=infiltrationFactor;
	}
}
