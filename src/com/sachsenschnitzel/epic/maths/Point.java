package com.sachsenschnitzel.epic.maths;

public class Point{
	double[] coords; //can be any number of coordinates (=dimension)
	
	public Point(double... coordinates){
		coords = coordinates;
	}
	
	public int getDimension(){
		return coords.length;
	}

	@Override
	public String toString(){
		String s = "";
		for(int i = 0; i < coords.length-1; i++)
			s += coords[i] + "/";
		return "(" + s + coords[coords.length-1] + ")";
	}
}
