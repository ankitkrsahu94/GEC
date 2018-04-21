import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {
	static double parseDouble(String s){
		try{
			if(s.isEmpty())	return 0.0;
			else return round(Double.parseDouble(s), Constants.DECIMAL);
		}catch(Exception e){
			return 0;
		}
		
	}
	
	static double validateValue(Double d){
		return (d==null)?0.0:round(d, Constants.DECIMAL);
	}
	
	static int parseInt(String s){
		Long val = Math.round(parseDouble(s));
	
		return val.intValue();
	}
	
	static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
