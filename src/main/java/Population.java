import java.util.Map;

public class Population {
	int lpcd;
	int referenceYear;
	int totalPopulation;
	double growthRate;
	//Command/Non-command vs count
	Map<String, Integer> areaWisePopulation;
	//Command/Non-command vs noOfDays
	Map<String, Integer> noOfDays;
	
	public Map<String, Integer> getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(Map<String, Integer> noOfDays) {
		this.noOfDays = noOfDays;
	}
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
	
	@Override
	public String toString() {
		return "Population [lpcd=" + lpcd + ", referenceYear=" + referenceYear
				+ ", totalPopulation=" + totalPopulation + ", growthRate="
				+ growthRate + ", areaWisePopulation=" + areaWisePopulation
				+ ", noOfDays=" + noOfDays + "]";
	}
}
