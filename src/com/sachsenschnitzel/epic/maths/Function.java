package com.sachsenschnitzel.epic.maths;

import com.sachsenschnitzel.epic.maths.AssignedValues.AssignedValue;

/**
 * This is only for test of some functionality, not the final structure.
 * 
 * @author schnitzel
 *
 */
public class Function{
	Term funcTerm;
	String depVar; //dependent variable
	String[] indVars; //independent variables
	//-> depVar(indVar1, indVar2, ...) = funcTerm
	
	public Function(Term funcTerm, String dependentVar, String... independentVars){
		this.funcTerm = funcTerm;
		depVar = dependentVar;
		indVars = independentVars;
	}
	
	/**
	 * calculates all the points in a function for the specified range.
	 * 
	 * @param startVal range starts here
	 * @param endVal range ends here
	 * @param inc increment = step size
	 * @return
	 */
	public Point[] calcThrough(double startVal, double endVal, double inc){
		AssignedValues avs = new AssignedValues(new AssignedValue(indVars[0], startVal));
		Point[] points = new Point[(int)((endVal-startVal)/inc)];
		
		for(int i = 0; i < points.length-1; i++){
			double x = startVal+i*inc;
			avs.assignNewValue(indVars[0], x);
			points[i] = new Point(x, funcTerm.calc(avs));
		}
		
		avs.assignNewValue(indVars[0], endVal);
		points[points.length-1] = new Point(endVal, funcTerm.calc(avs));
		
		return points;
	}
	
	/**
	 * returns the nearest root from an initial value.
	 * 
	 * @param initVal
	 * @param accuracy deviation from actual 0 as y-value
	 * @return
	 */
	public double getRoot(double initVal, double accuracy){
		Term derivative = funcTerm.derive(indVars[0]);
		AssignedValues avs = new AssignedValues(new AssignedValue(indVars[0], initVal));
		
		double result;
		while((result = Math.abs(funcTerm.calc(avs))) > accuracy){
			/* Newton
			 * x_(n+1) = x_n - f(x_n)/f'(x_n)
			 */
			avs.assignNewValue(
					indVars[0],
					avs.getValue(indVars[0]) - result/derivative.calc(avs));
		}
		
		return avs.getValue(indVars[0]);
	}
	
	@Override
	public String toString(){
		return depVar + "("+indVars[0]+") = " + funcTerm;
	}
}
