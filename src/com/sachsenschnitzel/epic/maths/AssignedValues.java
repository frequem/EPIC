package com.sachsenschnitzel.epic.maths;

public class AssignedValues{
	private AssignedValue[] avs;
	
	public AssignedValues(AssignedValue... assValues){
		avs = assValues;
	}
	
	public Double getValue(String varName){
		for(int i = 0; i < avs.length; i++)
			if(varName.equals(avs[i].varName))
				return avs[i].value;
		return null;
	}
	
	public void assignNewValue(String var, double val){
		for(AssignedValue av: avs)
			if(av.varName == var)
				av.value = val;
	}
	
	public static class AssignedValue{
		String varName;
		double value;
		
		public AssignedValue(String variableName, double value){
			varName = variableName;
			this.value = value;
		}
		
		public Double getValue(String varName){
			if(varName.equals(this.varName))
				return value;
			else
				return null;
		}
	}
}
