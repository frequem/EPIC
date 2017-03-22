package com.sachsenschnitzel.epic.maths;

import com.sachsenschnitzel.epic.maths.term.Constant;
import com.sachsenschnitzel.epic.maths.term.Variable;
import com.sachsenschnitzel.epic.maths.term.Term;
import com.sachsenschnitzel.epic.maths.term.Product;
import com.sachsenschnitzel.epic.maths.term.Sum;
import java.util.Arrays;

public class Test{
	public static void main(String args[]){
		/*Term functionTerm = new Sum(
				new Product(
						new Constant(5),
						new Variable("x")),
				new Constant(-3));
		Function f = new Function(functionTerm, "f", "x");
		Term deriv = functionTerm.derive("x");
		deriv.simplify();
		Point[] ps = f.calcThrough(-3, 5, 1);
		
		double root = f.getRoot(1, 0.0001);
		
		System.out.println(functionTerm);
		System.out.println(deriv);
		System.out.println(Arrays.toString(ps));
		System.out.println(root);*/
		
		
		double[][] equns = {{23, 18, 99},
							{6272, 78, 88}};
		System.out.println(Arrays.toString(MathMethods.solveEquSys(equns)));
		
		/*Term t = new Sum(
				new Product(
						new Constant(4),
						new Variable("x")),
				new Fraction(
						new Fraction(
								new Variable("x"),
								new Constant(4)),
						new Constant(-3)));
		AssignedValues avs = new AssignedValues(new AssignedValue[]{new AssignedValue("x", 2)});
		System.out.println(t.calc(avs));
		t.simplify();*/
	}
}
