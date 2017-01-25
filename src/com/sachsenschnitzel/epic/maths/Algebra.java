package com.sachsenschnitzel.epic.maths;

public class Algebra{
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
