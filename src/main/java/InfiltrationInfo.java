
public class InfiltrationInfo {
	
	Weathered_granite WeatheredGranite;
	Weathered_gneiss WeatheredGneiss;
	InfiltrationInfo(Weathered_granite WeatheredGranite,Weathered_gneiss WeatheredGneiss){
		this.WeatheredGranite=WeatheredGranite;
		this.WeatheredGneiss=WeatheredGneiss;
	}
}

class Weathered_granite{
	double fraction;
	int rfInfiltration;
	Weathered_granite(double fraction,int rfInfiltration){
		this.fraction=fraction;
		this.rfInfiltration=rfInfiltration;
	}
}
class Weathered_gneiss{
	double fraction;
	int rfInfiltration;
	Weathered_gneiss(double fraction,int rfInfiltration){
		this.fraction=fraction;
		this.rfInfiltration=rfInfiltration;
	}
}