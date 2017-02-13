package com.sachsenschnitzel.epic.maths;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

public class Sum extends Term{
    public static final int SUM_SPRITE_MARGIN_SIDE = 2;
    
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

    @Override
    protected void optSize(Graphics g){
        w = (summands.length-1)*
                (2*SUM_SPRITE_MARGIN_SIDE + g.getFontMetrics().stringWidth("+"));
        h = 0;
        
        for(Term summand : summands){
            summand.optSize(g);
            w += summand.getWidth();
            if (summand.getHeight() > h)
                h = summand.getHeight();
        }
    }
    
    @Override
    protected void optSubPos(Graphics g){
        int plusWidth = g.getFontMetrics().stringWidth("+");
        
        int xCounter = x;
        for(Term summand : summands){
            summand.setX(xCounter);
            summand.setY(y + (h-summand.getHeight())/2);
            summand.optSubPos(g);
            xCounter += summand.getWidth() + 2*SUM_SPRITE_MARGIN_SIDE + plusWidth;
        }
    }

    @Override
    public void paint(Graphics g){
        /*Color c = g.getColor();

        g.setColor(Color.ORANGE);
        g.drawRect(x, y, w, h);

        g.setColor(c);*/
        
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        
        int plusWidth = g.getFontMetrics().stringWidth("+");
        int plusHeight = g.getFontMetrics().getHeight();
        summands[0].paint(g);
        for(int i = 1; i < summands.length; i++){
            int posX = summands[i].getX()-SUM_SPRITE_MARGIN_SIDE - plusWidth;
            g.drawString("+", posX, y + (h+plusHeight)/2);
            summands[i].paint(g);
        }
    }
}
