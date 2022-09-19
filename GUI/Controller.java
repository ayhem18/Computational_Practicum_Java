package GUI;


import EquationSolver.*;
import Equations.FirstOrderDE;
import Equations.MyEquation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // these values are determined out of the variant description
    // they are displayed initially when the application is launched
    // static final variables are preferred over literals
    private static final double INITIAL_X0 = 1, INITIAL_Y0 = 10, INITIAL_X = 15;
    private static final int INITIAL_N = 12,INITIAL_N0=1;
    @FXML
    private TextField x0Field, y0Field,xMaxField,NField,n0Field;

    // these check boxes enable user to determine which methods to apply
    @FXML
    private CheckBox displayExact,displayEuler,displayImprovedEuler,displayRungeKutta;

    // used to display the different data
    @FXML
    private LineChart<BigDecimal, BigDecimal> solutionGraph, localErrorGraph, globalErrorGraph;

    // the numerical methods used in the applications
    private NumericalMethod euler, improvedEuler,runge_Kutta,exactSolution;


    private FirstOrderDE ourEquation ;

    private String x0 , y0, X , N ,n0 ;

    private void showAlert(String warningMsg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(Verifier.WARNING);
        alert.setContentText(warningMsg);

        while((alert.showAndWait().get())!= ButtonType.OK);
    }

    private boolean validateAndSetFields(){
        x0 = x0Field.getText(); y0 = y0Field.getText();
        X = xMaxField.getText(); N = NField.getText(); n0 = n0Field.getText();

        //verify the format of the input
        String format = Verifier.validateFormat(x0, y0, X, N, n0);
        if (!format.equals(Verifier.VALID_INPUT)) {
            showAlert(format);
            return false;
        }
        //verify the mathematical constraints
        String math = Verifier.validateMath(x0, y0, X, N, n0);
        if(!math.equals(Verifier.VALID_INPUT)){
            showAlert(math);
            return false;
        }
        return true;
    }

    private void display(LineChart<BigDecimal, BigDecimal> graph){
        if(!validateAndSetFields())
            return;

        // set the IVP parameters
        ourEquation.updateIVP(x0,y0,X,N);
        graph.getData().clear();
        //display selected graphs
        if(displayExact.isSelected()){
            graph.getData().add(exactSolution.getApproximations(ourEquation));

        }

        if(displayEuler.isSelected()){
            graph.getData().add(euler.getApproximations(ourEquation));
        }

        if(displayImprovedEuler.isSelected()){
            graph.getData().add(improvedEuler.getApproximations(ourEquation));

        }
        if(displayRungeKutta.isSelected()){
            graph.getData().add(runge_Kutta.getApproximations(ourEquation));
        }
    }

    @FXML
    private void displaySolution(ActionEvent e){
        if(!validateAndSetFields())
            return;

        // set the IVP parameters


        ourEquation.updateIVP(x0,y0,X,N);

       solutionGraph.getData().clear();


        //display selected graphs
        if(displayExact.isSelected()){
            solutionGraph.getData().add(exactSolution.getApproximations(ourEquation));
        }

        if(displayEuler.isSelected()){
            solutionGraph.getData().add(euler.getApproximations(ourEquation));
        }

        if(displayImprovedEuler.isSelected()){
            solutionGraph.getData().add(improvedEuler.getApproximations(ourEquation));

        }
        if(displayRungeKutta.isSelected()){
            solutionGraph.getData().add(runge_Kutta.getApproximations(ourEquation));
        }

    }

    @FXML
    private void displayLocalErrors(ActionEvent e){

        if(!validateAndSetFields())
            return;

        // set the IVP parameters
        //ourEquation.setEquation(x0,y0,X,N);

        ourEquation.updateIVP(x0,y0,X,N);

        //clear previous data
        localErrorGraph.getData().clear();

        //display selected graphs

        if(displayExact.isSelected()){
            localErrorGraph.getData().add(exactSolution.getLocalErrors(ourEquation));
        }
        if(displayEuler.isSelected()){
            localErrorGraph.getData().add(euler.getLocalErrors(ourEquation));

        }
        if(displayImprovedEuler.isSelected()){
            localErrorGraph.getData().add(improvedEuler.getLocalErrors(ourEquation));
        }
        if(displayRungeKutta.isSelected()){
            localErrorGraph.getData().add(runge_Kutta.getLocalErrors(ourEquation));
        }

    }

    @FXML
    private void displayGlobalErrors(ActionEvent e){
        if(!validateAndSetFields())
            return;

        // set the IVP parameters
        //ourEquation.setEquation(x0,y0,X,n0);

        ourEquation.updateIVP(x0,y0,X,N);

        globalErrorGraph.getData().clear();

        //display selected graphs
        if(displayExact.isSelected()){
            //ourEquation.populateGlobalErrors(this.n0,this.N);
            //globalErrorGraph.getData().add(ourEquation.getZerosGlobalErrorsSeries());
            globalErrorGraph.getData().add(exactSolution.getGlobalErrors(ourEquation,n0,N));
        }

        if(displayEuler.isSelected()){
            //globalErrorGraph.getData().add(euler.getGlobalErrors(ourEquation.getEquation(),n0,N));
            globalErrorGraph.getData().add(euler.getGlobalErrors(ourEquation,n0,N));

        }
        if(displayImprovedEuler.isSelected()){
            //globalErrorGraph.getData().add(improvedEuler.getGlobalErrors(ourEquation.getEquation(),n0,N));
            globalErrorGraph.getData().add(improvedEuler.getGlobalErrors(ourEquation,n0,N));
        }
        if(displayRungeKutta.isSelected()){
            globalErrorGraph.getData().add(runge_Kutta.getGlobalErrors(ourEquation,n0,N));
           // globalErrorGraph.getData().add(runge_Kutta.getGlobalErrors(ourEquation.getEquation(),n0,N));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFields();
        initializeObjects();
    }

    // set the initial values of text fields
    public void setFields(){
        x0Field.setText(String.valueOf(INITIAL_X0));
        y0Field.setText(String.valueOf(INITIAL_Y0));
        xMaxField.setText(String.valueOf(INITIAL_X));
        NField.setText(String.valueOf(INITIAL_N));
        n0Field.setText(String.valueOf(INITIAL_N0));
    }

    public void initializeObjects(){
        exactSolution =  new ExactSolution();
        euler = new EulerMethod();
        improvedEuler = new ImprovedEulerMethod();
        runge_Kutta = new RungeKuttaMethod();
        //ourEquation = new OurEquation();
        ourEquation = new MyEquation();
    }


}