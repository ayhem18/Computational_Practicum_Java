package EquationSolver;


import Equations.FirstOrderDE;
import javafx.scene.chart.XYChart;

import java.math.BigDecimal;

public abstract class NumericalMethod {
    protected  XYChart.Series<BigDecimal,BigDecimal> approximations;
    protected  XYChart.Series<BigDecimal,BigDecimal> localErrors;
    protected  XYChart.Series<BigDecimal,BigDecimal> globalErrors;
    protected BigDecimal maxLocalError;

    public NumericalMethod(){
        approximations = new XYChart.Series<>();
        localErrors = new XYChart.Series<>();
        globalErrors = new XYChart.Series<>();
        maxLocalError = new BigDecimal("0");
    }

    public XYChart.Series<BigDecimal, BigDecimal> getApproximations(FirstOrderDE equation) {
        makeCalculations(equation);
        approximations.setName(getMethodName());
        return approximations;
    }

    public XYChart.Series<BigDecimal, BigDecimal> getLocalErrors(FirstOrderDE equation) {
        makeCalculations(equation);
        localErrors.setName(getMethodName());
        return localErrors;
    }

    public XYChart.Series<BigDecimal, BigDecimal> getGlobalErrors(FirstOrderDE equation, String n0, String N) {

        // convert the string parameters to integers
        int N0 = Integer.parseInt(n0);
        int NF = Integer.parseInt(N);
        // initialize the series
        globalErrors = new XYChart.Series<>();
        // for each step store the pair: grid size, the largest local error
        for(int i = N0; i <= NF;i++) {
            // set the grid size to the i
            equation.updateGridSize(String.valueOf(i));
            // make calculations
            makeCalculations(equation);

            // makeCalculations errors will store the largest local error in the maxLocalError field
            // the latter will be added to the globalErrors series
            globalErrors.getData().add(new XYChart.Data<>(new BigDecimal(i),maxLocalError));
        }
        // set the name to be displayed on the user interface
        globalErrors.setName(getMethodName());
        return globalErrors;
    }

    public abstract void makeCalculations(FirstOrderDE equation);

    public abstract String getMethodName();

}