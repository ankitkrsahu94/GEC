import java.util.Map;


public class CanalData {
	private String segmentName; 
	private String type;
	private Double length;
	private Double designDepthFlow;
	private Double averageSupplyDepth;
	private Double bedWidth;
	private Double sideSlopes;
	private Double seepageFactor;
	private Map<String, Integer> noOfRunningDays;
	
	CanalData(){}
	
	@Override
	public String toString() {
		return "CanalData [segmentName=" + segmentName + ", type=" + type
				+ ", length=" + length + ", designDepthFlow=" + designDepthFlow
				+ ", averageSupplyDepth=" + averageSupplyDepth + ", bedWidth="
				+ bedWidth + ", sideSlopes=" + sideSlopes + ", seepageFactor="
				+ seepageFactor + ", noOfRunningDays=" + noOfRunningDays + "]";
	}

	public String getSegmentName() {
		return segmentName;
	}

	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getDesignDepthFlow() {
		return designDepthFlow;
	}

	public void setDesignDepthFlow(Double designDepthFlow) {
		this.designDepthFlow = designDepthFlow;
	}

	public Double getAverageSupplyDepth() {
		return averageSupplyDepth;
	}

	public void setAverageSupplyDepth(Double averageSupplyDepth) {
		this.averageSupplyDepth = averageSupplyDepth;
	}

	public Double getBedWidth() {
		return bedWidth;
	}

	public void setBedWidth(Double bedWidth) {
		this.bedWidth = bedWidth;
	}

	public Double getSideSlopes() {
		return sideSlopes;
	}

	public void setSideSlopes(Double sideSlopes) {
		this.sideSlopes = sideSlopes;
	}

	public Double getSeepageFactor() {
		return seepageFactor;
	}

	public void setSeepageFactor(Double seepageFactor) {
		this.seepageFactor = seepageFactor;
	}

	public Map<String, Integer> getNoOfRunningDays() {
		return noOfRunningDays;
	}

	public void setNoOfRunningDays(Map<String, Integer> noOfRunningDays) {
		this.noOfRunningDays = noOfRunningDays;
	}
}
