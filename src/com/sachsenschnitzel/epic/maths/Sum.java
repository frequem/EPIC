package com.sachsenschnitzel.epic.maths;

import java.util.ArrayList;
import java.util.Arrays;

public class Sum extends Term{
	private Term[] summands;
	
	public Sum(Term... summands){
		this.summands = summands;
	}
	
	@Override
	public double calc(AssignedValues avs){
		double sm = 0;
		for(int i = 0; i < summands.length; i++)
			sm += summands[i].calc(avs);
		return sm;
	}
	
	@Override
	public Term derive(String var){
		//(f+g)' = f'+g'
		Term[] derivedSummands = new Term[summands.length];
		for(int i = 0; i < summands.length; i++)
			derivedSummands[i] = summands[i].derive(var);
		return new Sum(derivedSummands);
	}
	
	@Override
	public void simplify(){
		ArrayList<Term> elements = new ArrayList<Term>();
		elements.addAll(Arrays.asList(summands));
		
		//scroll through elements
		for(int i = 0; i < elements.size(); i++)
			//sub-sum?
			if(elements.get(i) instanceof Sum){
				//sub-sum! (break it down...)
				for(int j = 0; j < ((Sum)elements.get(i)).summands.length; j++)
					elements.add(((Sum)elements.get(i)).summands[j]);
				elements.remove(i);
				i--;//cancels out the i++
			}
		
		summands = elements.toArray(new Term[0]);
		
		//do it for the rest
		for(int i = 0; i < summands.length; i++)
			summands[i].simplify();
	}
	
	@Override
	public String toString(){
		String s = "";
		for(int i = 0; i < summands.length-1; i++)
			s += summands[i] + " + ";
		return "(" + s + summands[summands.length-1] + ")";
	}
}
