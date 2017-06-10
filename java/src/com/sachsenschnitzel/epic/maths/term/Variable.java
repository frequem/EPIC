package com.sachsenschnitzel.epic.maths.term;

import com.sachsenschnitzel.epic.maths.AssignedValues;
import com.sachsenschnitzel.epic.maths.MathObject;
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
    public void changeSubObj(MathObject o, MathObject n){

    }

    @Override
    public Term calc(AssignedValues avs){
        Double assignedVal = avs.getValue(name);
        if(assignedVal != null)
                return new Constant(assignedVal);

        return null;
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
        setWidth(fm.stringWidth(name));
        setHeight(fm.getHeight());
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
        
        g.drawString(name, getX(), getY()+getHeight());
    }

    @Override
    public void parseContent(int offset) {
        System.out.println("Not supported yet.");
    }

    @Override
    public String toInputForm() {
        return name;
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
    public void moveCursor(int direction) {}

    @Override
    public void paintCursor(Graphics g) {
        System.out.println("Not supported yet.");
    }

    @Override
    public void putText(String s) {}

    @Override
    public void backspace() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteText() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}