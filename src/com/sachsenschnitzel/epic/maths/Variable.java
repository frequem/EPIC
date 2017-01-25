package com.sachsenschnitzel.epic.maths;

public class Variable extends Term{
	private String name;
	
	public Variable(String name){
		this.name = name;
	}

	@Override
	public double calc(AssignedValues avs){
		Double assignedVal = avs.getValue(name);
		if(assignedVal != null)
			return assignedVal;
		
		//throw exception
		return 0;
	}
	
	@Override
	public Term derive(String var){
		if(name.equals(var))
			return new Constant(1);
		else
			return new Constant(0);
	}

	@Override
	public void simplify(){}
	
	@Override
	public String toString(){
		return name;
	}
}