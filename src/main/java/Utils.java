import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

	static double parseDouble(String s){
		try{
			if(s.isEmpty())	return 0.0;
			else return Double.parseDouble(s);
		}catch(Exception e){
			return 0;
		}
		
	}
	
	static int parseInt(String s){
		Long val = Math.round(parseDouble(s));
	
		return val.intValue();
	}
	
}
