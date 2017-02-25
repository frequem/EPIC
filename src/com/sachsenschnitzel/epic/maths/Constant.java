package com.sachsenschnitzel.epic.maths;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

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
        
        @Override
        protected void optSize(Graphics g){
            FontMetrics fm = g.getFontMetrics();
            w = fm.stringWidth(String.valueOf(val));
            h = fm.getHeight();
        }
        
        @Override
        protected void optSubPos(Graphics g){}
        
        @Override
        public void paint(Graphics g){
            super.paint(g);
            /*Color c = g.getColor();

            g.setColor(Color.BLUE);
            g.fillRect(x, y, w, h);

            g.setColor(c);*/
            g.drawString(String.valueOf(val), x, y+h);
        }
}
