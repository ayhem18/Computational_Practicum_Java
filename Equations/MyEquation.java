package Equations;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MyEquation extends FirstOrderDE{
    // the precision used to calculate the square roots
    private static final MathContext PRECISION = new MathContext(15);
    // the value of 2 as BigDecimal
    private static final BigDecimal TWO = new BigDecimal("2");

    // the expression of the right hand side : in this variant :
    // f(x,y) = sqrt(y-x)/sqrt(x) +1
    private static final TwoVarFunction RIGHT_HAND_SIDE = (x, y) -> {
        // sqrt(y-x)
        BigDecimal numerator = (y.subtract(x)).sqrt(PRECISION);
        // sqrt(x)
        BigDecimal denominator = x.sqrt(PRECISION);
        // sqrt(y-x)/sqrt(x)
        BigDecimal ratio =numerator.divide(denominator,15, RoundingMode.CEILING);
        return ratio.add(new BigDecimal("1"));
    };

    /*
     this method will calculate and return the constant determined
     by the parameters of the IVP
     in this variant it is : sqrt(yo-xo) - sqrt(x0)
     */
    @Override
    protected BigDecimal getIntegrationConstant(String x0, String y0){
        // convert the string parameters into BigDecimals
        BigDecimal X = new BigDecimal(x0);
        BigDecimal Y = new BigDecimal(y0);

        // sqrt(yo-xo)
        BigDecimal firstPart = (Y.subtract(X)).sqrt(PRECISION);

        return firstPart.subtract(X.sqrt(PRECISION));
    }

    @Override
    // this method will return the exact solution after determining the integration constant
    // in this variant: 2*x + cte*cte + 2*cte*sqrt(x)
    protected OneVarFunction getIVPSolution(String x0, String y0){
        // cte = integration constant
        BigDecimal cte = getIntegrationConstant(x0,y0);

        // return the exact solution
        return (x) -> {
            // firstPart = 2*x + cte*cte
            BigDecimal firstPart = (x.multiply(TWO)).add(cte.multiply(cte)) ;
            // secondPart = 2*cte*sqrt(x)
            BigDecimal secondPart = cte.multiply(TWO).multiply(x.sqrt(PRECISION));

            return firstPart.add(secondPart);
        };
    }

    public MyEquation(){
        super(RIGHT_HAND_SIDE);
    }


}
