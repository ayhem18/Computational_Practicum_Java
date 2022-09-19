package EquationSolver;

import Equations.FirstOrderDE;
import javafx.scene.chart.XYChart;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EulerMethod extends NumericalMethod {
    // the name of the method
    private static final String METHOD_NAME = "EULER";
    // the number of digits after the decimal in division operations
    private static final int SCALE = 15;
    // the value 0 as Big Decimal
    private static final BigDecimal ZERO = new BigDecimal("0");

    public EulerMethod() {
        super();
    }

    @Override
    public void makeCalculations(FirstOrderDE equation) {
        //initialize the maxLocalError field to 0
        maxLocalError = ZERO;

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

            //updating the value of the maximum local error
            maxLocalError = maxLocalError.max(localError);

            // add it to the corresponding series
            localErrors.getData().add(new XYChart.Data<>(currentX,localError));

            // add the pair of intermediate value and approximation to the series
            approximations.getData().add(new XYChart.Data<>(currentX,currentY));

            // temp1  =  f(xi,yi)
            BigDecimal temp1 = equation.getRightHandSideValue(currentX,currentY);

            // assign currentY according to : y(i+1) = y(i) + (step) h * f(xi,yi)
            currentY = currentY.add(step.multiply(temp1));

            // increment intermediate value
            currentX = currentX.add(step);
        }
    }

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }
}
