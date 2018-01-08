

public class checker {

    
    public static boolean isInteger(String s) {

        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
        //    System.out.println(e);
            return false; 
        } catch(NullPointerException e) {
        //    System.out.println(e);
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static boolean isDouble(String s) {

        try { 
            Double.parseDouble(s); 
        } catch(NumberFormatException e) { 
        //    System.out.println(e);
            return false; 
        } catch(NullPointerException e) {
        //    System.out.println(e);
            return false;
        }
        // only got here if we didn't return false
        return true;
    }    
    
}