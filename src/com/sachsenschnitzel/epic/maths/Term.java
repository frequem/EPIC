package com.sachsenschnitzel.epic.maths;

import java.util.ArrayList;
import java.util.List;

public abstract class Term{
	public abstract double calc(AssignedValues avs);
	public abstract Term derive(String var);
	public abstract void simplify();
	public double calc(){ return calc(null); }
	
	
	/*public static final Term parseString(String text){
		return parseStringR(simplify(text));
	}
	
	private static final String simplify(String query){
		String simple = query;
		int i = 0;
		if(query.charAt(0) == '-'){//TODO either clear "-" here or down there...
			
		}
	}*/
	
	/**
	 * runs recursively, needs a simplifier to work, that's why it's private.
	 * @param text
	 * @return
	 */
	/*private static final Term parseStringR(String text){
		//is string number?
		try{
			double number = Double.parseDouble(text);
			return new Constant(number);
		}catch(NumberFormatException e) {}
		
		int par = 0; //parenthesis count
		int startPos = 0; //marker where current term begins
		List<Term> subterms = new ArrayList<Term>();
		
		//is it a sum?
		for(int i = 0; i < text.length(); i++){
			char c = text.charAt(i); //current char
			if(c == '(')
				par++;
			else if(c == ')')
				par--;
			else if(par == 0){
				if(c == '+'){
					subterms.add(Term.parseString(text.substring(startPos, i)));
					startPos = i+1;
				}
				else if(c == '-')
					subterms.add(Term.parseString(text.substring(startPos, i)));
					startPos = i;
			}
		}
	}*/
}
