import java.util.Map;

class WaterBody{
	double count;
	double rechargeFactor;
	double spreadArea;
	Map<String, Double> avgSpreadArea;
	Map<String, Integer> impoundDays;
	
	WaterBody(){
	}
	
	@Override
	public String toString() {
		return "MiTank [count=" + count + ", rechargeFactor=" + rechargeFactor
				+ ", spreadArea=" + spreadArea + ", avgSpreadArea="
				+ avgSpreadArea + ", impoundDays=" + impoundDays + "]";
	}

	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}
	public double getRechargeFactor() {
		return rechargeFactor;
	}
	public void setRechargeFactor(double rechargeFactor) {
		this.rechargeFactor = rechargeFactor;
	}
	public double getSpreadArea() {
		return spreadArea;
	}
	public void setSpreadArea(double spreadArea) {
		this.spreadArea = spreadArea;
	}
	public Map<String, Double> getAvgSpreadArea() {
		return avgSpreadArea;
	}
	public void setAvgSpreadArea(Map<String, Double> avgSpreadArea) {
		this.avgSpreadArea = avgSpreadArea;
	}
	public Map<String, Integer> getImpoundDays() {
		return impoundDays;
	}
	public void setImpoundDays(Map<String, Integer> impoundDays) {
		this.impoundDays = impoundDays;
	}
	
	
}