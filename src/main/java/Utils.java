
public class Utils {

	static double parseDouble(String s){
		if(s.isEmpty())	return 0.0;
		else return Double.parseDouble(s);
	}
	
	static int parseInt(String s){
		if(s.isEmpty())	return 0;
		else return Integer.parseInt(s);
	}
}
