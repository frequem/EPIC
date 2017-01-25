package com.sachsenschnitzel.epic.maths;

public class Constant extends Term{
	private double val;
	
	public Constant(double value){
		val = value;
	}
	
	@Override
	public double calc(AssignedValues avs){
		return val;
	}
	
	@Override
	public Term derive(String var){
		return new Constant(0);
	}
	
	@Override
	public void simplify(){}

	@Override
	public String toString(){
		return String.valueOf(val);
	}
}
