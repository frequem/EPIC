package com.sachsenschnitzel.epic.maths;

import java.util.ArrayList;
import java.util.Arrays;

public class Product extends Term{
	private Term[] factors;
	
	public Product(Term... factors){
		this.factors = factors;
	}

	@Override
	public double calc(AssignedValues avs){
		double prod = 1;
		for(int i = 0; i < factors.length; i++)
			prod *= factors[i].calc(avs);
		return prod;
	}
	
	@Override
	public Term derive(String var){
		//(f*g)' = f'*g + f*g'
		if(factors.length == 1)
			return factors[0].derive(var);
		else if(factors.length == 2){
			return new Sum(
					new Product(factors[0].derive(var), factors[1]),
					new Product(factors[0], factors[1].derive(var)));
		}else if(factors.length > 2){
			Product f = new Product(Arrays.copyOfRange(factors, 0, factors.length/2));
			Product g = new Product(Arrays.copyOfRange(factors, factors.length/2, factors.length));
			return new Sum(
					new Product(f.derive(var), g),
					new Product(f, g.derive(var)));
		}else
			return null; //...?
	}

	@Override
	public void simplify(){
		ArrayList<Term> elements = new ArrayList<Term>();
		elements.addAll(Arrays.asList(factors));
		
		//scroll through elements
		for(int i = 0; i < elements.size(); i++)
			//sub-sum?
			if(elements.get(i) instanceof Product){
				//sub-sum! (break it down...)
				for(int j = 0; j < ((Product)elements.get(i)).factors.length; j++)
					elements.add(((Product)elements.get(i)).factors[j]);
				elements.remove(i);
				i--;//cancels out the i++
			}
		
		factors = elements.toArray(new Term[0]);
		
		//do it for the rest
		for(int i = 0; i < factors.length; i++)
			factors[i].simplify();
	}
	
	@Override
	public String toString(){
		String s = "";
		for(int i = 0; i < factors.length-1; i++)
			s += factors[i] + " * ";
		return "(" + s + factors[factors.length-1] + ")";
	}
}
