package GUI;


public class Verifier {

    // static final variables used to display warning messages to the user
    // in case of poorly formatted or mathematically invalid input while interacting with the first tab

    public static final String WARNING = "INVALID INPUT!!";
    public static final String DOUBLE_WARNING = "Please make sure that fields x0, y0 and X are numerical values";
    public  static final String INTEGER_WARNING = "Please make sure that the step N and the field n0 are whole numbers";
    public static final String VALID_INPUT = "OK";

    private static final String X0_NON_POSITIVE = "Please make sure that the x0 field is strictly greater than 0";
    private static final String Y0_LESS_THAN_XO = "Please make sure that the y0 field is greater or equal to x0";
    private static final String X_LESS_THAN_X0 = "Please make sure that the X field is greater than x0";
    private static final String N_NON_POSITIVE = "Please make sure that the N field is greater than 0";

    // static final variables used to display warning messages to the user
    // in case of poorly formatted or mathematically invalid input while interacting with the second tab

    private static final String N0_NON_POSITIVE = "Please make sure that the n0 field is strictly greater than 0";
    private static final String N_LESS_THAN_N0 = "Please make sure that N is greater than n0";

    // this method is used to verify the format of the passed arguments
    // this method does not take into consideration the mathematical restrictions
    public static String validateFormat(String x0,String y0,String X,String N,String n0){
        try{
            Double.parseDouble(x0);
            Double.parseDouble(y0);
            Double.parseDouble(X);
        }catch (Exception e){
            return DOUBLE_WARNING;
        }

        try{
            Integer.parseInt(N);
            Integer.parseInt(n0);
        }catch(Exception e){
            return INTEGER_WARNING;
        }
        return VALID_INPUT;
    }

    // this method is used to verify the mathematical constraints of the input in the first tab
    // it should not be called before validateFormatTab1()
    public static String validateMath(String x0Field,String y0Field,String XField,String NField,String n0Field) {
        double x0,y0,X;
        int N,n0;
        try{
            x0 = Double.parseDouble(x0Field);
            y0 = Double.parseDouble(y0Field);
            X = Double.parseDouble(XField);
            N = Integer.parseInt(NField);
            n0 = Integer.parseInt(n0Field);
        }
        catch(Exception e){
            return validateFormat(x0Field, y0Field, XField, NField, n0Field);
        }
        // the right hand side function accepts only positive arguments
        if (x0 <= 0) return X0_NON_POSITIVE;

        // the image of a number is larger or equal to it. x0 and y0 are no exception
        if (y0 < x0) return Y0_LESS_THAN_XO;

        // X is xMax it should be at least equal to x0
        if (X < x0) return X_LESS_THAN_X0;

        // we cannot have a negative number of iterations
        if(n0 <= 0) return N_NON_POSITIVE;

        // we cannot have the final number of iterations less than the initial one
        if (n0 >= N) return N_LESS_THAN_N0;

        return VALID_INPUT;
    }
}
