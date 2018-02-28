import java.util.Map;

class CropData{
	String cropName;
	//Monsoon/Non-Monsoon vs value
	Map<String, Double> cropArea;
	//monsoon/non vs data
	Map<String, Double> waterRequired;
	
	
	public CropData() {
	}
	
	public String getCropName() {
		return cropName;
	}
	public void setCropName(String cropName) {
		this.cropName = cropName;
	}
	public Map<String, Double> getCropArea() {
		return cropArea;
	}
	public void setCropArea(Map<String, Double> cropArea) {
		this.cropArea = cropArea;
	}
	public Map<String, Double> getWaterRequired() {
		return waterRequired;
	}
	public void setWaterRequired(Map<String, Double> waterRequired) {
		this.waterRequired = waterRequired;
	}

	@Override
	public String toString() {
		return "CropData [cropName=" + cropName + ", cropArea=" + cropArea
				+ ", waterRequired=" + waterRequired + "]";
	}
}