package LastRefactoration;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class FirstOrderDE {
    // in the equation y' = f(x,y)
    // this variable represents f
    private final TwoVarFunction rightHandSide;
    // this is the exact solution of the DE
    protected OneVarFunction exactSolution;

    public BigDecimal x0,y0,xMax;
    public BigInteger N;

    // constructor
    public FirstOrderDE(TwoVarFunction rightHandSide) {
        this.rightHandSide = rightHandSide;
    }

    // return the image of the passed value by the exact function
    public BigDecimal getExactValue(BigDecimal x){
        return exactSolution.function(x);
    }

    // returns the image of the passed arguments by the right hand side function
    public BigDecimal getRightHandSideValue(BigDecimal x, BigDecimal y){
        return rightHandSide.function(x,y);
    }

    // this method is used to update the parameters whenever they are changed by the user
    // which changes the value of the exact solution
    protected void updateIVP( String x0, String y0, String xMax, String N){
        this.exactSolution = getIVPSolution(x0,y0);
        this.x0 =  new BigDecimal(x0);
        this.y0 = new BigDecimal(y0);
        this.xMax = new BigDecimal(xMax);
        this.N = new BigInteger(N);
    }


    public void updateGridSize(String N){
        this.N = new BigInteger(N);
    }

    /*
     this method will calculate and return the constant determined
     by the parameters of the IVP
     in this variant it is : sqrt(yo-xo) - sqrt(x0)
     */
    protected abstract BigDecimal getIntegrationConstant(String x0, String y0);


    // this method will return the exact solution after determining the integration constant
    // in this variant: 2*x + cte*cte + 2*cte*sqrt(x)

    protected abstract OneVarFunction getIVPSolution(String x0, String y0);
}
