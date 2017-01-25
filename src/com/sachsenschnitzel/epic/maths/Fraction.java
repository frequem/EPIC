package com.sachsenschnitzel.epic.maths;

public class Fraction extends Term{
	private Term numerator;
	private Term denom; //-inator
	
	public Fraction(Term numerator, Term denominator){
		this.numerator = numerator;
		denom = denominator;
	}

	@Override
	public double calc(AssignedValues avs){
		double denomResult = denom.calc();
		if(denomResult != 0)
			return numerator.calc()/denom.calc();
		else
			return 0; //should be null in final version...
	}
	
	@Override
	public Term derive(String var){
		//(u/v)' = (u'*v-u*v')/v²
		return new Fraction(
				new Sum(
						new Product(numerator.derive(var), denom),
						new Product(new Constant(-1), numerator, denom.derive(var))),
				new Product(denom, denom));
	}

	@Override
	public void simplify(){
		while(numerator instanceof Fraction || denom instanceof Fraction){
			if(numerator instanceof Fraction){
				//num.num -> num*num.num; num.den -> den*num.den
				numerator = new Product(numerator, ((Fraction)numerator).numerator);
				denom = new Product(denom, ((Fraction)numerator).denom);
			}
			if(denom instanceof Fraction){
				numerator = new Product(numerator, ((Fraction)denom).denom);
				denom = new Product(denom, ((Fraction)denom).numerator);
			}
		}
		
		numerator.simplify();
		denom.simplify();
	}
	
	@Override
	public String toString(){
		return "(" + numerator + "/" + denom + ")";
	}
}
