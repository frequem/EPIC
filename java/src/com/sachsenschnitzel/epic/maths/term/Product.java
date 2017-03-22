package com.sachsenschnitzel.epic.maths.term;

import com.sachsenschnitzel.epic.maths.AssignedValues;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

public class Product extends Term{
    public static final int PRODUCT_SPRITE_MARGIN_SIDE = 1;

    private Term[] factors;

    public Product(Term... factors){
        this.factors = factors;
    }
    
    @Override
    public void print(){
        factors[0].print();
        for(int i = 1; i < factors.length; i++){
            System.out.print(" • ");
            factors[i].print();
        }
    }
    
    @Override
    public void changeSubterm(Term o, Term n){
        
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
    
    @Override
    protected void optSize(Graphics g){
        w = (factors.length-1)*
                (2*PRODUCT_SPRITE_MARGIN_SIDE + g.getFontMetrics().stringWidth("•"));
        h = 0;
        
        for(Term factor : factors){
            factor.optSize(g);
            w += factor.getWidth();
            if (factor.getHeight() > h)
                h = factor.getHeight();
        }
    }
    
    @Override
    protected void optSubPos(Graphics g){
        int dotWidth = g.getFontMetrics().stringWidth("•");
        
        int xCounter = x;
        for(Term factor : factors){
            factor.setX(xCounter);
            factor.setY(y + (h-factor.getHeight())/2);
            factor.optSubPos(g);
            xCounter += factor.getWidth() + 2*PRODUCT_SPRITE_MARGIN_SIDE + dotWidth;
        }
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        int dotWidth = g.getFontMetrics().stringWidth("•");
        int dotHeight = g.getFontMetrics().getHeight();
        factors[0].paint(g);
        for(int i = 1; i < factors.length; i++){
            int posX = factors[i].getX()-PRODUCT_SPRITE_MARGIN_SIDE - dotWidth;
            g.drawString("•", posX, y + (h+dotHeight)/2);
            factors[i].paint(g);
        }
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
