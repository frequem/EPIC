package com.sachsenschnitzel.epic.maths.term;

import com.sachsenschnitzel.epic.maths.AssignedValues;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Variable extends Term{
    private String name;

    public Variable(String name){
        this.name = name;
    }

    @Override
    public void print(){
        System.out.print(name);
    }

    @Override
    public void changeSubterm(Term o, Term n){

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
    public void optSize(Graphics g){
        FontMetrics fm = g.getFontMetrics();
        w = fm.stringWidth(name);
        h = fm.getHeight();
    }

    @Override
    public void optSubPos(Graphics g){}

    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        if(font == null)
            font = g.getFont();
        if(color == null)
            color = g.getColor();
        
        g.drawString(name, x, y+h);
    }

    @Override
    public void parseContent(int offset) {
        System.out.println("Not supported yet.");
    }

    @Override
    public String toInputForm() {
        System.out.println("Not supported yet.");
        return null;
    }

    @Override
    public int getCursorSide() {
        System.out.println("Not supported yet.");
        return 0;
    }

    @Override
    public void setCursor(int x, int y) {
        System.out.println("Not supported yet.");
    }

    @Override
    public void cursorDragged(int x, int y) {
        System.out.println("Not supported yet.");
    }

    @Override
    public void moveCursor(int direction) {
        System.out.println("Not supported yet.");
    }

    @Override
    public void paintCursor(Graphics g) {
        System.out.println("Not supported yet.");
    }
}