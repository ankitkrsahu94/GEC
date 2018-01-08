

public class format {

    // use this updated function
    public static String removeQuotes(String inputStr) {
        String result = inputStr.trim();
        int firstQuote = inputStr.indexOf('\"');
        int lastQuote = result.lastIndexOf('\"');
        int strLength = inputStr.length();
        
        if (firstQuote == 0 && lastQuote == strLength - 1) {
            result = result.substring(1, strLength - 1);
        }
        return result.trim();
    }

    public static String removeHash(String inputStr) {
        String result = inputStr.trim();
        int firstQuote = inputStr.indexOf('#');
        int lastQuote = result.lastIndexOf('#');
        int strLength = inputStr.length();
        
        if (firstQuote == 0 && lastQuote == strLength - 1) {
            result = result.substring(1, strLength - 1);
        }
        return result.trim();
    }
    
    public static int convert(String a) {    
        a = a.toLowerCase();    
        int ans = -1;
        if(a.length() == 1) {
            ans = a.charAt(0) - 'a';
        } else if(a.length() == 2) {
            
            ans = (a.charAt(0) - 'a' + 1)*26 + (a.charAt(1) - 'a');
        }
        return ans;
    }
    
}
