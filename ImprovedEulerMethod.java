package LastRefactoration;


import javafx.scene.chart.XYChart;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ImprovedEulerMethod extends NumericalMethod {
    // the name of the method
    private static final String METHOD_NAME = "IMPROVED_EULER";
    // the number of digits after the decimal in division operations
    private static final int SCALE = 15;
    // the value 0 as Big Decimal
    private static final BigDecimal ZERO = new BigDecimal("0");
    // the value 2 as BigDecimal
    private static final BigDecimal TWO = new BigDecimal("2");



    public ImprovedEulerMethod() {
        super();
    }
    @Override
    public void makeCalculations(FirstOrderDE equation) {
        maxLocalError = ZERO;
        approximations = new XYChart.Series<>();
        localErrors = new XYChart.Series<>();

        // the intermediateValue
        BigDecimal currentX = equation.x0;

        // the approximation of the intermediate step
        BigDecimal currentY = equation.y0;

        BigDecimal N = new BigDecimal(equation.N);
        // the difference between two consecutive intermediate values
        BigDecimal step = (equation.xMax.subtract(equation.x0)).divide(N,SCALE, RoundingMode.CEILING);

        BigDecimal localError,exactImage;

        // while currentX is less than xMax
        while(currentX.compareTo(equation.xMax)<=0){
            // get the image of the intermediate value
            exactImage = equation.getExactValue(currentX);

            // calculate the local error
            localError = (currentY.subtract(exactImage)).abs();

            //update the value of the maxLocalError
            maxLocalError = maxLocalError.max(localError);

            // add it to the corresponding series
            localErrors.getData().add(new XYChart.Data<>(currentX,localError));

            // add the pair of intermediate value and approximation to the series
            approximations.getData().add(new XYChart.Data<>(currentX,currentY));

            // temp1  =  f(xi,yi)
            BigDecimal temp1 = equation.getRightHandSideValue(currentX,currentY);

            // temp2 is an approximation of f(xi,y(x(i+1))) which is  f(xi,yi+ h*f(xi,yi) ) = f(xi, yi+ h *temp1)
            BigDecimal temp2 =  equation.getRightHandSideValue(currentX.add(step),currentY.add(step.multiply(temp1)));


            // assign currentY according to the relation : y(i+1) = y(i) + temp1 * temp2 * h /2
            BigDecimal t = (temp1.add(temp2)).multiply(step).divide(TWO,SCALE,RoundingMode.CEILING);
            currentY = currentY.add(t);

            // increment the intermediate value by the step
            currentX = currentX.add(step);
        }
    }

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }
}
