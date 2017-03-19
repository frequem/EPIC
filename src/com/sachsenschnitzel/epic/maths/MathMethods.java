package com.sachsenschnitzel.epic.maths;

import com.sachsenschnitzel.epic.maths.AssignedValues.AssignedValue;
import java.util.ArrayList;

public class MathsMethods{
    public static int factorial(int n){
        int r = 1;
        for(int i = 2; i <= n; i++)
            r *= i;
        return r;
    }
    
    //WIP (no powers as term yet...)
    /**
     * This algorithm can turn any function term approximately into a polynomial term.
     * It makes use of the Taylor series:
     * sigma n=0->inf ((d^n*f(0))/(dx^n * n!) * x^n)
     * ...or look it up in wikipedia if you want ;)
     * 
     * @param original original function term
     * @param approx the grade of approximation = the number of polynomial terms
     * @return 
     */
    public static Term polynomify(Term original, int approx){
        AssignedValues avs = new AssignedValues(new AssignedValue("x", 0));
        
        ArrayList<Term> taylor = new ArrayList<Term>();
        taylor.add(new Constant(original.calc(avs)));
        
        Term d = original.derive("x");
        for(int i = 1; i < approx; i++){
            //taylor.add(new Product( ...derivative/factorial * x-power... ))
        }
        
        return new Sum((Term[])taylor.toArray());
    }
    
    /**
     * This method solves any linear equation system with the given coefficients
     * 
     * @param coeff outer array contains equations, inner is for coefficients in an equation
     * @return
     */
    public static double[] solveEquSys(double[][] coeffs){
        //as many equations as unknowns?
        if(coeffs.length == coeffs[0].length-1){
            // bring to diagonal form
            for(int i = 0; i < coeffs.length-1; i++){
                for(int j = i+1; j < coeffs.length; j++){
                    double factor = coeffs[i][i]/coeffs[j][i];
                    for(int k = i; k < coeffs[j].length; k++)
                        //perform gaussian elimination
                        coeffs[j][k] = coeffs[i][k] - coeffs[j][k]*factor;
                }
            }

            //next solve one after the other
            double[] solution = new double[coeffs.length];
            double right; //right side of the equation
            for(int i = coeffs.length-1; i >= 0; i--){
                right = coeffs[i][coeffs.length];//2nd index should be: coeffs[0].length-1, but performance
                for(int j = i+1; j < coeffs.length; j++)
                    right -= coeffs[i][j] * solution[j];

                solution[i] = right/coeffs[i][i];
            }

            return solution;
        }else return null;
    }
}
