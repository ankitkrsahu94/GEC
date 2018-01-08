

public class Area{
	double total;
	RechargeWorthy rechargeWorthy;
	NonRechargeWorthy nonRechargeWorthy;
	Area(double total,RechargeWorthy rechargeWorthy,NonRechargeWorthy nonRechargeWorthy){
		this.total=total;
		this.rechargeWorthy=rechargeWorthy;
		this.nonRechargeWorthy=nonRechargeWorthy;
	}

}
class RechargeWorthy{
	
	double command;
	double non_command;
	double poor_quality;
	public RechargeWorthy(double command, double nonCommand, double poorQuality) {
		this.command=command;
		this.non_command=nonCommand;
		this.poor_quality=poorQuality;
		
		
	}
}
class NonRechargeWorthy{
	double hilly;
	double forest;
	NonRechargeWorthy(double hilly,double forest){
		this.hilly=hilly;
		this.forest=forest;
	}
}

