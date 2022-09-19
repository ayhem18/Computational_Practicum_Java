# Numerical Differential Equation Solver
## Project Description
This project provides a GUI desktop tool to solve a given first order Differential equation numerically and 
compare it to the exact analytical solution if provided.

## Project's Structure
The Project's can be divided into 3 main packages:
1. Equations:
includes The main class FirstOrderDE short of FirstOrder differential equation. 
This class should be inherited by a class representing the actual equation: in this case: MyEquation. Any First order De can be modeled
by adding a new class implementing (hard-coding for the moment):
    * RIGHT-HAND-Function: two-variable function
    * getIntegrationConstant: given intial condition
    * getIVP solution: given initial condition
2. EquationSolver:
This package offers the tools to solve the given equation. Depending on the child class a solution is computed and stored
in a data stucture that is later displayed and used by the GUI components. Currently, the project supports main 3 methods (in addition to the exact solution given by the user):
    * Euler Method
    * Improved Euler Method
    * RungeKutta method
3. GUI: 
This package includes the components responsible for verifying the validity of the user input as well as controlling The
GUI.

### Libaries
In addition to Core Java, this project makes usage of the [JAVAFX](https://openjfx.io/openjfx-docs/#install-javafx) library.  

More Details concerning the function considered and its analytical solution are provided in the report.