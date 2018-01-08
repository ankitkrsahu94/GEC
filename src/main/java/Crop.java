
public class Crop {
	CropCommand command;
	CropNonCommand nonCommand;
	CropPoorQuality poorQuality;
	Crop(CropCommand cropCommand,CropNonCommand cropNonCommand,CropPoorQuality cropPoorQuality){
		this.command=cropCommand;
		this.nonCommand=cropNonCommand;
		this.poorQuality=cropPoorQuality;
	}
}
class CropCommand{
	Paddy paddy;
	NonPaddy nonPaddy;
	CropCommand(Paddy paddy,NonPaddy nonPaddy){
		this.paddy=paddy;
		this.nonPaddy=nonPaddy;
	}
}
class CropNonCommand{
	Paddy paddy;
	NonPaddy nonPaddy;
	CropNonCommand(Paddy paddy,NonPaddy nonPaddy){
		this.paddy=paddy;
		this.nonPaddy=nonPaddy;
	}
}
class CropPoorQuality{
	Paddy paddy;
	NonPaddy nonPaddy;
	CropPoorQuality(Paddy paddy,NonPaddy nonPaddy){
		this.paddy=paddy;
		this.nonPaddy=nonPaddy;
	}
}

class Paddy{
	double cropArea;
	double waterRequired;
	String waterRequiredUnit;
	Kharif kharif;
	Rabi rabi;
	Paddy(double area,Kharif kharif,Rabi rabi){
		this.cropArea=area;
		this.waterRequired=0;
		this.waterRequiredUnit="mm";
		this.kharif=kharif;
		this.rabi=rabi;
	}
}
class NonPaddy{
	double cropArea;
	double waterRequired;
	String waterRequiredUnit;
	Kharif kharif;
	Rabi rabi;
	NonPaddy(double area,Kharif kharif,Rabi rabi){
		this.cropArea=area;
		this.waterRequired=0;
		this.waterRequiredUnit="mm";
		this.kharif=kharif;
		this.rabi=rabi;
	}
}

class Kharif{
	double areaIrrigatedBySurfaceWater;
	double areaIrrigatedByMITank;
	double areaIrrigatedByGroundWater;
	Kharif(double areaIrrigatedBySurfaceWater,double areaIrrigatedByMITank,double areaIrrigatedByGroundWater){
		this.areaIrrigatedBySurfaceWater=areaIrrigatedBySurfaceWater;
		this.areaIrrigatedByMITank=areaIrrigatedByMITank;
		this.areaIrrigatedByGroundWater=areaIrrigatedByGroundWater;
	}
}

class Rabi{
	double areaIrrigatedBySurfaceWater;
	double areaIrrigatedByMITank;
	double areaIrrigatedByGroundWater;
	Rabi(double areaIrrigatedBySurfaceWater,double areaIrrigatedByMITank,double areaIrrigatedByGroundWater){
		this.areaIrrigatedBySurfaceWater=areaIrrigatedBySurfaceWater;
		this.areaIrrigatedByMITank=areaIrrigatedByMITank;
		this.areaIrrigatedByGroundWater=areaIrrigatedByGroundWater;
	}
}

