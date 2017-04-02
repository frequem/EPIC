package com.sachsenschnitzel.epic.maths.term;

import com.sachsenschnitzel.epic.maths.AssignedValues;
import java.awt.Color;
import java.awt.Graphics;

public class Fraction extends Term{
    public static final int FRACTION_SPRITE_MARGIN_SIDES = 2;
    public static final int FRACTION_SPRITE_MARGIN_TOP_BOTTOM = 2;

    private Term numerator;
    private Term denom; //-inator

    public Fraction(Term numerator, Term denominator){
        this.numerator = numerator;
        denom = denominator;
    }

    public Fraction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void print(){
        numerator.print();
        System.out.print("/");
        denom.print();
    }

    @Override
    public void changeSubterm(Term o, Term n) {
        System.out.println("Not supported yet.");
    }

    @Override
    public double calc(AssignedValues avs){
        double denomResult = denom.calc();
        if(denomResult != 0)
            return numerator.calc()/denom.calc();
        else
            return 0; //should be null in final version...
    }
    
    @Override
    public int countEncaps(){
        return numerator.countEncaps() + denom.countEncaps();
    }

    @Override
    public Term derive(String var){
        //(u/v)' = (u'*v-u*v')/v²
        return new Fraction(
                    new Sum(
                            new Product(numerator.derive(var), denom),
                            new Product(new Constant(-1), numerator, denom.derive(var))),
                    new Product(denom, denom));
    }

    @Override
    public void simplify(){
        while(numerator instanceof Fraction || denom instanceof Fraction){
            if(numerator instanceof Fraction){
                //num.num -> num*num.num; num.den -> den*num.den
                numerator = new Product(numerator, ((Fraction)numerator).numerator);
                denom = new Product(denom, ((Fraction)numerator).denom);
            }
            if(denom instanceof Fraction){
                numerator = new Product(numerator, ((Fraction)denom).denom);
                denom = new Product(denom, ((Fraction)denom).numerator);
            }
        }

        numerator.simplify();
        denom.simplify();
    }

    @Override
    public String toString(){
        return "(" + numerator + "/" + denom + ")";
    }
    
    @Override
    public void optSize(Graphics g){
        numerator.optSize(g);
        denom.optSize(g);
        
        if(numerator.getWidth() > denom.getWidth())
            w = numerator.getWidth();
        else
            w = denom.getWidth();
        h = numerator.getHeight() + denom.getHeight() + 2*FRACTION_SPRITE_MARGIN_TOP_BOTTOM;
    }
    
    @Override
    public void optSubPos(Graphics g){
        numerator.setX(x + (w-numerator.getWidth())/2);
        numerator.setY(y);
        denom.setX(x + (w-denom.getWidth())/2);
        denom.setY(y + numerator.getHeight() + 2*FRACTION_SPRITE_MARGIN_TOP_BOTTOM);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        numerator.paint(g);
        g.fillRect(x,
                denom.getY()-(int)(FRACTION_SPRITE_MARGIN_TOP_BOTTOM*1.5),
                w,
                FRACTION_SPRITE_MARGIN_TOP_BOTTOM);
        denom.paint(g);
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