import java.util.HashMap;
import java.util.Map;

class WellsUtilizationData{
	int referenceYear;
	int count;
	double pumpingHours;
	double yield;
	Map<String,Double> operativeDays = new HashMap<>();
	double growthRate;
	
	WellsUtilizationData(){
		
	}
	WellsUtilizationData(int forYear,int noOfWells,double pumpingHours,double yield,double pumpingHours_monsoon, double pumpingHours_nonmonsoon, double growthRate ){
		this.referenceYear=forYear;
		this.count=noOfWells;
		this.pumpingHours=pumpingHours;
		this.yield=yield;
		operativeDays.put("monsoon", pumpingHours_monsoon);
		operativeDays.put("non_monsoon", pumpingHours_nonmonsoon);
		this.growthRate=growthRate;
	}
	
	
	@Override
	public String toString() {
		return "WellsUtilizationData [referenceYear=" + referenceYear + ", count=" + count + ", pumpingHours="
				+ pumpingHours + ", yield=" + yield + ", operativeDays=" + operativeDays + ", growthRate=" + growthRate
				+ "]";
	}
	public int getReferenceYear() {
		return referenceYear;
	}
	public void setReferenceYear(int referenceYear) {
		this.referenceYear = referenceYear;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getPumpingHours() {
		return pumpingHours;
	}
	public void setPumpingHours(double pumpingHours) {
		this.pumpingHours = pumpingHours;
	}
	public double getYield() {
		return yield;
	}
	public void setYield(double yield) {
		this.yield = yield;
	}
	public Map<String, Double> getOperativeDays() {
		return operativeDays;
	}
	public void setOperativeDays(Map<String, Double> operativeDays) {
		this.operativeDays = operativeDays;
	}
	public double getGrowthRate() {
		return growthRate;
	}
	public void setGrowthRate(double growthRate) {
		this.growthRate = growthRate;
	}
}