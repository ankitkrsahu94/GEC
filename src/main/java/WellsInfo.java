import java.util.HashMap;
import java.util.Map;

public class WellsInfo {
	public BasinWellsCommand Command;
	public BasinWellsCommandPoorQuality PoorQuality;
	public BasinWellsNonCommand NonCommand;
	
	WellsInfo(BasinWellsCommand BasinWellscommand,BasinWellsNonCommand BasinWellsnonCommand,BasinWellsCommandPoorQuality BasinWellscommandPoorQuality){
		this.Command= BasinWellscommand;
		this.NonCommand=BasinWellsnonCommand;
		this.PoorQuality=BasinWellscommandPoorQuality;
	}

}
class BasinWellsCommand{
	BasinWellsIrrigation IrrigationUtilization;
	BasinWellsResidentialUtilization ResidentialUtilization;
	BasinWellsIndustrialUtilization IndustrialUtilization;
	BasinWellsCommand(BasinWellsIrrigation basinWellsIrrigation,BasinWellsResidentialUtilization basinWellsResidentialUtilization,BasinWellsIndustrialUtilization basinWellsIndustrialUtilization){
		this.IrrigationUtilization=basinWellsIrrigation;
		this.ResidentialUtilization=basinWellsResidentialUtilization;
		this.IndustrialUtilization=basinWellsIndustrialUtilization;
	}
}
class BasinWellsCommandPoorQuality{
	BasinWellsIrrigation IrrigationUtilization;
	BasinWellsResidentialUtilization ResidentialUtilization;
	BasinWellsIndustrialUtilization IndustrialUtilization;
	BasinWellsCommandPoorQuality(BasinWellsIrrigation basinWellsIrrigation,BasinWellsResidentialUtilization basinWellsResidentialUtilization,BasinWellsIndustrialUtilization basinWellsIndustrialUtilization){
		this.IrrigationUtilization=basinWellsIrrigation;
		this.ResidentialUtilization=basinWellsResidentialUtilization;
		this.IndustrialUtilization=basinWellsIndustrialUtilization;
	}
}
class BasinWellsNonCommand{
	BasinWellsIrrigation IrrigationUtilization;
	BasinWellsResidentialUtilization ResidentialUtilization;
	BasinWellsIndustrialUtilization IndustrialUtilization;
	BasinWellsNonCommand(BasinWellsIrrigation basinWellsIrrigation,BasinWellsResidentialUtilization basinWellsResidentialUtilization,BasinWellsIndustrialUtilization basinWellsIndustrialUtilization){
		this.IrrigationUtilization=basinWellsIrrigation;
		this.ResidentialUtilization=basinWellsResidentialUtilization;
		this.IndustrialUtilization=basinWellsIndustrialUtilization;
	}
}
class BasinWellsIrrigation{
	public BasinWellsBorewell borewell;
	public BasinWellsDugwell dugwell;
	public BasinWellsDugwellPumpSet dugwellPumpSet;
	public BasinWellsShallowTubeWell shallowTubeWell;
	public BasinWellsMediumTubeWell mediumTubeWell;
	public BasinWellsDeepTubeWell deepTubeWell;
	public BasinWellsFilterPoints filterPoints;
	BasinWellsIrrigation(BasinWellsBorewell borewell,BasinWellsDugwell dugwell,BasinWellsDugwellPumpSet dugwellPumpSet,BasinWellsShallowTubeWell shallowTubeWell,BasinWellsMediumTubeWell mediumTubeWell,BasinWellsDeepTubeWell deepTubeWell,BasinWellsFilterPoints filterPoints){
		this.borewell=borewell;
		this.dugwell=dugwell;
		this.dugwellPumpSet=dugwellPumpSet;
		this.shallowTubeWell=shallowTubeWell;
		this.mediumTubeWell=mediumTubeWell;
		this.deepTubeWell=deepTubeWell;
		this.filterPoints=filterPoints;
	}
	
}
class BasinWellsResidentialUtilization{
	public BasinWellsResidentialUtilizationPws Pws;
	public BasinWellsResidentialUtilizationHandpump Handpump;
	BasinWellsResidentialUtilization(BasinWellsResidentialUtilizationPws residentialUtilizationPws,BasinWellsResidentialUtilizationHandpump residentialUtilizationHandpump){
		this.Pws=residentialUtilizationPws;
		this.Handpump=residentialUtilizationHandpump;
	}
	
	
}

class BasinWellsIndustrialUtilization{
	public BasinWellsIndustrialUtilizationWell Well;
	BasinWellsIndustrialUtilization(BasinWellsIndustrialUtilizationWell industrialUtilizationWell){
		this.Well=industrialUtilizationWell;
	}
	
	
}
class BasinWellsIndustrialUtilizationWell{
	int forYear;
	int noOfWells;
	double pumpingHours;
	double yield;
	Map<String,Double> operativeDays = new HashMap<>();
	double growthRate;
	BasinWellsIndustrialUtilizationWell(int forYear,int noOfWells,double pumpingHours,double yield,double pumpingHours_monsoon, double pumpingHours_nonmonsoon, double growthRate ){
		this.forYear=forYear;
		this.noOfWells=noOfWells;
		this.pumpingHours=pumpingHours;
		this.yield=yield;
		operativeDays.put("monsoon", pumpingHours_monsoon);
		operativeDays.put("non_monsoon", pumpingHours_nonmonsoon);
		this.growthRate=growthRate;
	}
}
class BasinWellsBorewell{
	int referenceYear;
	double count;
	int pumpingHours;
	double yield;

	Map<String,Integer> operativeDays=new HashMap<>();
	double growthRate;
	BasinWellsBorewell(int forYear,int noOfWells,int pumpingHours,double yield,int operativeDays_monsoon, int operativeDays_nonmonsoon, double growthRate ){
		this.referenceYear=forYear;
		this.count=noOfWells;
		this.pumpingHours=pumpingHours;
		this.yield=yield;
		operativeDays.put("monsoon", operativeDays_monsoon);
		operativeDays.put("non_monsoon", operativeDays_nonmonsoon);
		this.growthRate=growthRate;
	}
}

class BasinWellsDugwell{
	int forYear;
	int noOfWells;
	int pumpingHours;
	double yield;

	Map<String,Integer> operativeDays = new HashMap<>();
	double growthRate;
	BasinWellsDugwell(int forYear,int noOfWells,int pumpingHours,double yield,int operativeDays_monsoon, int operativeDays_nonmonsoon, double growthRate ){
		this.forYear=forYear;
		this.noOfWells=noOfWells;
		this.pumpingHours=pumpingHours;
		this.yield=yield;
		operativeDays.put("monsoon", operativeDays_monsoon);
		operativeDays.put("non_monsoon", operativeDays_nonmonsoon);
		this.growthRate=growthRate;
	}
}

class BasinWellsDugwellPumpSet{
	int forYear;
	int noOfWells;
	int pumpingHours;
	double yield;

	Map<String,Integer> operativeDays= new HashMap<>();
	double growthRate;
	BasinWellsDugwellPumpSet(int forYear,int noOfWells,int pumpingHours,double yield,int operativeDays_monsoon, int operativeDays_nonmonsoon, double growthRate ){
		this.forYear=forYear;
		this.noOfWells=noOfWells;
		this.pumpingHours=pumpingHours;
		this.yield=yield;
		operativeDays.put("monsoon", operativeDays_monsoon);
		operativeDays.put("non_monsoon", operativeDays_nonmonsoon);
		this.growthRate=growthRate;
	}
}
class BasinWellsShallowTubeWell{
	int forYear;
	int noOfWells;
	int pumpingHours;
	double yield;

	Map<String,Integer> operativeDays= new HashMap<>();
	double growthRate;
	BasinWellsShallowTubeWell(int forYear,int noOfWells,int pumpingHours,double yield,int operativeDays_monsoon, int operativeDays_nonmonsoon, double growthRate ){
		this.forYear=forYear;
		this.noOfWells=noOfWells;
		this.pumpingHours=pumpingHours;
		this.yield=yield;
		operativeDays.put("monsoon", operativeDays_monsoon);
		operativeDays.put("non_monsoon", operativeDays_nonmonsoon);
		this.growthRate=growthRate;
	}
}
class BasinWellsMediumTubeWell{
	int forYear;
	int noOfWells;
	int pumpingHours;
	double yield;

	Map<String,Integer> operativeDays= new HashMap<>();
	double growthRate;
	BasinWellsMediumTubeWell(int forYear,int noOfWells,int pumpingHours,double yield,int operativeDays_monsoon, int operativeDays_nonmonsoon, double growthRate ){
		this.forYear=forYear;
		this.noOfWells=noOfWells;
		this.pumpingHours=pumpingHours;
		this.yield=yield;
		operativeDays.put("monsoon", operativeDays_monsoon);
		operativeDays.put("non_monsoon", operativeDays_nonmonsoon);
		this.growthRate=growthRate;
	}
}
class BasinWellsDeepTubeWell{
	int forYear;
	int noOfWells;
	int pumpingHours;
	double yield;

	Map<String,Integer> operativeDays= new HashMap<>();
	double growthRate;
	BasinWellsDeepTubeWell(int forYear,int noOfWells,int pumpingHours,double yield,int operativeDays_monsoon, int operativeDays_nonmonsoon, double growthRate ){
		this.forYear=forYear;
		this.noOfWells=noOfWells;
		this.pumpingHours=pumpingHours;
		this.yield=yield;
		operativeDays.put("monsoon", operativeDays_monsoon);
		operativeDays.put("non_monsoon", operativeDays_nonmonsoon);
		this.growthRate=growthRate;
	}
}

class BasinWellsFilterPoints{
	int forYear;
	int noOfWells;
	int pumpingHours;
	double yield;
	Map<String,Integer> operativeDays= new HashMap<>();
	double growthRate;
	BasinWellsFilterPoints(int forYear,int noOfWells,int pumpingHours,double yield,int operativeDays_monsoon, int operativeDays_nonmonsoon, double growthRate ){
		this.forYear=forYear;
		this.noOfWells=noOfWells;
		this.pumpingHours=pumpingHours;
		this.yield=yield;
		operativeDays.put("monsoon", operativeDays_monsoon);
		operativeDays.put("non_monsoon", operativeDays_nonmonsoon);
		
		this.growthRate=growthRate;
	}
}


class BasinWellsResidentialUtilizationPws{
	int forYear;
	int noOfWells;
	double pumpingHours;
	double yield;
	Map<String,Double> operativeDays = new HashMap<>();
	double growthRate;
	BasinWellsResidentialUtilizationPws(int forYear,int noOfWells,double pumpingHours,double yield,double pumpingHours_monsoon, double pumpingHours_nonmonsoon, double growthRate ){
		this.forYear=forYear;
		this.noOfWells=noOfWells;
		this.pumpingHours=pumpingHours;
		this.yield=yield;
		operativeDays.put("monsoon", pumpingHours_monsoon);
		operativeDays.put("non_monsoon", pumpingHours_nonmonsoon);
		this.growthRate=growthRate;
	}
}	

class BasinWellsResidentialUtilizationHandpump{
	int forYear;
	int noOfWells;
	double pumpingHours;
	double yield;
	Map<String,Double> operativeDays = new HashMap<>();
	double growthRate;
	BasinWellsResidentialUtilizationHandpump(int forYear,int noOfWells,double pumpingHours,double yield,double pumpingHours_monsoon, double pumpingHours_nonmonsoon, double growthRate ){
		this.forYear=forYear;
		this.noOfWells=noOfWells;
		this.pumpingHours=pumpingHours;
		this.yield=yield;
		operativeDays.put("monsoon", pumpingHours_monsoon);
		operativeDays.put("non_monsoon", pumpingHours_nonmonsoon);
		this.growthRate=growthRate;
	}
}




