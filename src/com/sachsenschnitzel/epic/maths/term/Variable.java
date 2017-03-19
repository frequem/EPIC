package com.sachsenschnitzel.epic.maths;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

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
        
        @Override
        protected void optSize(Graphics g){
            FontMetrics fm = g.getFontMetrics();
            w = fm.stringWidth(name);
            h = fm.getHeight();
        }
        
        @Override
        protected void optSubPos(Graphics g){}
        
        @Override
        public void paint(Graphics g){
            super.paint(g);
            /*Color c = g.getColor();

            g.setColor(Color.RED);
            g.fillRect(x, y, w, h);

            g.setColor(c);*/
            g.drawString(name, x, y+h);
        }
}