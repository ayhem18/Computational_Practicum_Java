package LastRefactoration;//package Computational_Practicum;

import javafx.scene.chart.XYChart;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExactSolution extends NumericalMethod{

    // the number of digits after the decimal in division operations
    protected final int SCALE = 15;
    // the value 0 as Big Decimal
    protected  final BigDecimal ZERO = new BigDecimal("0");
    //name given to the series
    protected  final String NAME = "EXACT";

    @Override
    public void makeCalculations(FirstOrderDE equation) {
        approximations = new XYChart.Series<>();
        localErrors = new XYChart.Series<>();

        // the intermediateValue
        BigDecimal currentX = equation.x0;

        // the difference between two consecutive intermediate values
        BigDecimal step = (equation.xMax.subtract(equation.x0)).divide(new BigDecimal(equation.N),SCALE, RoundingMode.CEILING);

        while(currentX.compareTo(equation.xMax)<=0){
            // add the intermediate value and its image to the list
            approximations.getData().add(new XYChart.Data<>(currentX,equation.getExactValue(currentX)));
            localErrors.getData().add( new XYChart.Data<>(currentX,ZERO));
            // increment the intermediate value
            currentX = currentX.add(step);
        }
    }

    @Override
    public String getMethodName() {
        return NAME;
    }

}
