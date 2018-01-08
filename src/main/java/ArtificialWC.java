
public class ArtificialWC {
	ArtificialWCCommand command;
	ArtificialWCNonCommand non_command;
	ArtificialWCPPoorQuality PoorQuality;
	ArtificialWC(ArtificialWCCommand artificialWCCommand,ArtificialWCNonCommand artificialWCNonCommand,ArtificialWCPPoorQuality artificialWCPPoorQuality){
		this.command = artificialWCCommand;
		this.non_command = artificialWCNonCommand;
	}
}
class ArtificialWCCommand{
	PercolationTanks pt;
	MiniPercolationTanks mpt;
	CheckDams cd;
	FarmPonds fp;
	Other others;
	ArtificialWCCommand(PercolationTanks pt,MiniPercolationTanks mpt,CheckDams cd,FarmPonds fp,Other others){
		this.pt=pt;
		this.mpt=mpt;
		this.cd=cd;
		this.fp=fp;
		this.others = others;
		
	}
	
}
class ArtificialWCNonCommand{
	PercolationTanks pt;
	MiniPercolationTanks mpt;
	CheckDams cd;
	FarmPonds fp;
	Other others;
	ArtificialWCNonCommand(PercolationTanks pt,MiniPercolationTanks mpt,CheckDams cd,FarmPonds fp,Other others){
		this.pt=pt;
		this.mpt=mpt;
		this.cd=cd;
		this.fp=fp;
		this.others = others;
	}
	
}
class ArtificialWCPPoorQuality{
	PercolationTanks pt;
	MiniPercolationTanks mpt;
	CheckDams cd;
	FarmPonds fp;
	Other others;
	ArtificialWCPPoorQuality(PercolationTanks pt,MiniPercolationTanks mpt,CheckDams cd,FarmPonds fp,Other others){
		this.pt=pt;
		this.mpt=mpt;
		this.cd=cd;
		this.fp=fp;
		this.others = others;
		
	}
	
}
class PercolationTanks{
	//add growthrate and reference year =2011
	double count;
	double capacity;
	double noOfFillings;
	double infiltrationFactor;
	PercolationTanks(double count,double capacity,double noOfFillings,double infiltrationFactor){
		this.count=count;
		this.capacity=capacity;
		this.noOfFillings=noOfFillings;
		this.infiltrationFactor=infiltrationFactor;
	}
}
class MiniPercolationTanks{
	double count;
	double capacity;
	double noOfFillings;
	double infiltrationFactor;
	MiniPercolationTanks(double count,double capacity,double noOfFillings,double infiltrationFactor){
		this.count=count;
		this.capacity=capacity;
		this.noOfFillings=noOfFillings;
		this.infiltrationFactor=infiltrationFactor;
	}
}
class CheckDams{
	double count;
	double capacity;
	double noOfFillings;
	double infiltrationFactor;
	CheckDams(double count,double capacity,double noOfFillings,double infiltrationFactor){
		this.count=count;
		this.capacity=capacity;
		this.noOfFillings=noOfFillings;
		this.infiltrationFactor=infiltrationFactor;
	}
}

class FarmPonds{
	double count;
	double capacity;
	double noOfFillings;
	double infiltrationFactor;
	FarmPonds(double count,double capacity,double noOfFillings,double infiltrationFactor){
		this.count=count;
		this.capacity=capacity;
		this.noOfFillings=noOfFillings;
		this.infiltrationFactor=infiltrationFactor;
	}
}

class Other{
	double count;
	double capacity;
	double noOfFillings;
	double infiltrationFactor;
	Other(double count,double capacity,double noOfFillings,double infiltrationFactor){
		this.count=count;
		this.capacity=capacity;
		this.noOfFillings=noOfFillings;
		this.infiltrationFactor=infiltrationFactor;
	}
}



