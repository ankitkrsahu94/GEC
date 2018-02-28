
public class Population {
	private Integer referenceYear;
	private Double growthRate;
	private Integer totalPopulation;
	private Double lpcd;
	private Double gwDependency;
	
	Population(){}
	@Override
	public String toString() {
		return "Population [referenceYear=" + referenceYear + ", growthRate="
				+ growthRate + ", totalPopulation=" + totalPopulation
				+ ", lpcd=" + lpcd + ", gwDependency=" + gwDependency + "]";
	}

	public Integer getReferenceYear() {
		return referenceYear;
	}

	public void setReferenceYear(Integer referenceYear) {
		this.referenceYear = referenceYear;
	}

	public Double getGrowthRate() {
		return growthRate;
	}

	public void setGrowthRate(Double growthRate) {
		this.growthRate = growthRate;
	}

	public Integer getTotalPopulation() {
		return totalPopulation;
	}

	public void setTotalPopulation(Integer totalPopulation) {
		this.totalPopulation = totalPopulation;
	}

	public Double getLpcd() {
		return lpcd;
	}

	public void setLpcd(Double lpcd) {
		this.lpcd = lpcd;
	}

	public Double getGwDependency() {
		return gwDependency;
	}

	public void setGwDependency(Double gwDependency) {
		this.gwDependency = gwDependency;
	}
}
