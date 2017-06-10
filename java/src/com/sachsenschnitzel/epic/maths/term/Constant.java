package com.sachsenschnitzel.epic.maths.term;

import com.frequem.epic.iface.Colorable;
import com.frequem.epic.iface.Cursorable;
import com.frequem.epic.iface.Fontable;
import com.frequem.epic.iface.Textable;
import com.sachsenschnitzel.epic.maths.AssignedValues;
import com.sachsenschnitzel.epic.maths.MathObject;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 *  This should represent a simple number.
 * 
 * @author schnitzel
 */
public class Constant extends Term{
    private double val;

    private int cursorStart, cursorEnd; //indices for start and end pos of cursor

    public Constant(double value){
        super();
        val = value;
    }
    
    public double getValue(){ return val; }
    
    @Override
    public void parseContent(int offset) {}
    
    @Override
    public String toInputForm(){
        return String.valueOf(val);
    }
    
    @Override
    public void print(){
        System.out.print("[" + val + "]");
    }
    
    @Override
    public void changeSubObj(MathObject o, MathObject n) {}

    @Override
    public Term calc(AssignedValues avs){
        return new Constant(val);
    }
    
    /*@Override
    public int countEncaps(){
        return 0;
    }*/

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
    public void optSize(Graphics g){
        FontMetrics fm = g.getFontMetrics();
        setWidth(fm.stringWidth(String.valueOf(val)));
        setHeight(fm.getHeight());
    }

    @Override
    public void optSubPos(Graphics g){}

    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        Font f = g.getFont();
        Color c = g.getColor();
        
        if(font == null)
            font = f;
        else
            g.setFont(font);
        
        
        if(color == null)
            color = c;
        else
            g.setColor(color);
        g.drawString(String.valueOf(val), getX(), getY()+getHeight());
        
        g.setFont(f);
        g.setColor(c);
    }
    
    @Override
    public int getCursorSide(){
        return 0;
    }
    
    @Override
    public void setCursor(int x, int y) {
        UnfinishedTerm uft = new UnfinishedTerm(this.toInputForm());
        this.parent.changeSubObj(this, uft);
        MathObject.transferProps(this, uft);
        uft.setCursor(x, y);
        //System.out.println("Constant -> UnfinishedT: " + val);
    }

    @Override
    public void cursorDragged(int x, int y) {}

    @Override
    public void moveCursor(int direction) {}

    @Override
    public void paintCursor(Graphics g) {}

    @Override
    public void putText(String s) {}

    @Override
    public void backspace() {}

    @Override
    public void deleteText() {}
}
