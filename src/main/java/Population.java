import java.util.Map;

public class Population {
//	int lpcd;
//	int year = 2011;
//	int totalPopulation;
//	double populationGrowthRate=3.76;
//	int command;
//	int non_command;
//	Population(int lpcd, int totalPopulation,int command,int noncommand){
//		this.lpcd=lpcd;
//		this.totalPopulation = totalPopulation;
//		this.command=command;
//		this.non_command=noncommand;
//	}
	
	int lpcd;
	int referenceYear;
	int totalPopulation;
	double growthRate;
	//Command/Non-command vs count
	Map<String, Integer> areaWisePopulation;
	public int getLpcd() {
		return lpcd;
	}
	public void setLpcd(int lpcd) {
		this.lpcd = lpcd;
	}
	public int getReferenceYear() {
		return referenceYear;
	}
	public void setReferenceYear(int referenceYear) {
		this.referenceYear = referenceYear;
	}
	public int getTotalPopulation() {
		return totalPopulation;
	}
	public void setTotalPopulation(int totalPopulation) {
		this.totalPopulation = totalPopulation;
	}
	public double getGrowthRate() {
		return growthRate;
	}
	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}
	public Map<String, Integer> getAreaWisePopulation() {
		return areaWisePopulation;
	}
	public void setAreaWisePopulation(Map<String, Integer> areaWisePopulation) {
		this.areaWisePopulation = areaWisePopulation;
	}
	
	public Population(int lpcd, int referenceYear, int totalPopulation, double growthRate,
			Map<String, Integer> areaWisePopulation) {
		super();
		this.lpcd = lpcd;
		this.referenceYear = referenceYear;
		this.totalPopulation = totalPopulation;
		this.growthRate = growthRate;
		this.areaWisePopulation = areaWisePopulation;
	}
	public Population(){
		
	}
	
	
}
