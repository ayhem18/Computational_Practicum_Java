package EquationSolver;


import Equations.FirstOrderDE;
import javafx.scene.chart.XYChart;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RungeKuttaMethod extends NumericalMethod {
    public static final String METHOD_NAME = "RUNGE-KUTTA";
    // the number of digits after the decimal in division operations
    private static final int SCALE = 15;
    // the value 0 as Big Decimal
    private static final BigDecimal ZERO = new BigDecimal("0");
    // the value 2 as BigDecimal
    private static final BigDecimal TWO = new BigDecimal("2");
    // the value 6 as BigDecimal
    private static final BigDecimal SIX = new BigDecimal("6");

    public RungeKuttaMethod(){
        super();
    }

    @Override
    public void makeCalculations(FirstOrderDE equation) {
        // initialize the maxLocalError
        maxLocalError = ZERO;
        // initialize the series
        approximations = new XYChart.Series<>();
        localErrors = new XYChart.Series<>();
        
        // the intermediateValue
        BigDecimal currentX = equation.x0;

        // the approximation of the intermediate step
        BigDecimal currentY = equation.y0;

        // the difference between two consecutive intermediate values
        BigDecimal step = (equation.xMax.subtract(equation.x0)).divide(new BigDecimal(equation.N),SCALE, RoundingMode.CEILING);

        BigDecimal localError,exactImage;

        // while currentX is less than xMax
        while(currentX.compareTo(equation.xMax)<=0){
            // get the image of the intermediate value
            exactImage = equation.getExactValue(currentX);

            // calculate the local error
            localError = (currentY.subtract(exactImage)).abs();

            //updating the value of maxLocalError
            maxLocalError = maxLocalError.max(localError);

            // add it to the corresponding series
            localErrors.getData().add(new XYChart.Data<>(currentX,localError));

            // add the pair of intermediate value and approximation to the series
            approximations.getData().add(new XYChart.Data<>(currentX,currentY));

            // k1 = f(xi,yi)
            BigDecimal k1 = equation.getRightHandSideValue(currentX,currentY);

            // halfStep
            BigDecimal halfStep = step.divide(TWO,SCALE,RoundingMode.CEILING);

            //k2 = f(xi + h/2,  yi + h * f(xi,yi)/2) = f(xi + h/2, yi+ h*k1/2)
            BigDecimal k2 = equation.getRightHandSideValue( currentX.add(halfStep),currentY.add(halfStep.multiply(k1)) );

            //k3 = f(xi + h/2, yi + h*k2/2)
            BigDecimal k3 = equation.getRightHandSideValue( currentX.add(halfStep),currentY.add(halfStep.multiply(k2)) );

            //k4 = f(xi + h, yi+h*k3)
            BigDecimal k4 = equation.getRightHandSideValue( currentX.add(step),currentY.add(step.multiply(k3)) );

            // sixthStep = h/6
            BigDecimal sixthStep = step.divide(SIX,SCALE,RoundingMode.CEILING);
            // weightedSum = (k1 + 2k2 + 2k3 + k4)
            BigDecimal weightedSum = k1.add(k2.multiply(TWO)).add(k3.multiply(TWO)).add(k4);
            // assign the value of currentY accordingly yi+1 = yi + h*(k1 + 2k2 + 2k3 + k4)/6
            currentY = currentY.add(weightedSum.multiply(sixthStep));

            // increment the intermediate value
            currentX = currentX.add(step);
        }

    }

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }
}
