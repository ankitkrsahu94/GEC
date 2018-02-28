import java.util.Map;

class ArtificialWC{
	double noOfFillings;
	double infiltrationFactor;
	//areaType vs capacity
	Map<String,Double> capacity;
	
	ArtificialWC(){}
	@Override
	public String toString() {
		return "ArtificialWC [noOfFillings=" + noOfFillings
				+ ", infiltrationFactor=" + infiltrationFactor + ", capacity="
				+ capacity + "]";
	}
	public double getNoOfFillings() {
		return noOfFillings;
	}
	public void setNoOfFillings(double noOfFillings) {
		this.noOfFillings = noOfFillings;
	}
	public double getInfiltrationFactor() {
		return infiltrationFactor;
	}
	public void setInfiltrationFactor(double infiltrationFactor) {
		this.infiltrationFactor = infiltrationFactor;
	}
	public Map<String, Double> getCapacity() {
		return capacity;
	}
	public void setCapacity(Map<String, Double> capacity) {
		this.capacity = capacity;
	}
	
	
}