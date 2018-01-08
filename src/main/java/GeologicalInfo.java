
public class GeologicalInfo {
	GeologicalInfoWeathered_granite WeatheredGranite;
	GeologicalInfoWeathered_gneiss WeatheredGneiss;
	public GeologicalInfoWeathered_granite getWeatheredGranite() {
		return WeatheredGranite;
	}
	public void setWeatheredGranite(GeologicalInfoWeathered_granite weatheredGranite) {
		WeatheredGranite = weatheredGranite;
	}
	public GeologicalInfoWeathered_gneiss getWeatheredGneiss() {
		return WeatheredGneiss;
	}
	public void setWeatheredGneiss(GeologicalInfoWeathered_gneiss weatheredGneiss) {
		WeatheredGneiss = weatheredGneiss;
	}
	
}

class GeologicalInfoWeathered_granite{
	
	double fraction;
	double specificYield;
	double transmissivity;
	double storageCoefficient;
	GeologicalInfoWeathered_granite(double fraction,double specificYield,double transmissivity,double storageCoefficient){
		this.fraction=fraction;
		this.specificYield=specificYield;
		this.transmissivity=transmissivity;
		this.storageCoefficient=storageCoefficient;
	}
}
class GeologicalInfoWeathered_gneiss{
	double fraction;
	double specificYield;
	double transmissivity;
	double storageCoefficient;
	GeologicalInfoWeathered_gneiss(double fraction,double specificYield,double transmissivity,double storageCoefficient){
		this.fraction=fraction;
		this.specificYield=specificYield;
		this.transmissivity=transmissivity;
		this.storageCoefficient=storageCoefficient;
	}
}