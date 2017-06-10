package com.sachsenschnitzel.epic.maths.term;

import com.sachsenschnitzel.epic.maths.AssignedValues;
import com.sachsenschnitzel.epic.maths.MathObject;
import java.awt.Color;
import java.awt.Graphics;

public class Fraction extends Term{
    public static final int FRACTION_SPRITE_MARGIN_SIDES = 2;
    public static final int FRACTION_SPRITE_MARGIN_TOP_BOTTOM = 2;

    private Term numerator;
    private Term denom; //-inator
    
    private int cursorStart, cursorEnd; //0 = numerator, 1 = denom

    public Fraction(Term numerator, Term denominator){
        this.numerator = numerator;
        if(numerator != null)
            numerator.setParent(this);
        denom = denominator;
        if(denominator != null)
            denominator.setParent(this);
    }
    
    @Override
    public void print(){
        numerator.print();
        System.out.print("/");
        denom.print();
    }

    @Override
    public void changeSubObj(MathObject o, MathObject n) {
        if(!(o instanceof Term && n instanceof Term))
            return;
        if(numerator == o){
            numerator = (Term)n;
            return;
        }
        if(denom == o)
            denom = (Term)n;
    }

    @Override
    public Term calc(AssignedValues avs){
        Term numResult = numerator.calc();
        Term denomResult = denom.calc();
        if(numResult == null || denomResult == null ||
            !(numResult instanceof Constant && denomResult instanceof Constant))
            return null;
        if(((Constant)denomResult).getValue() != 0)
            return new Constant(((Constant)numResult).getValue()/((Constant)denomResult).getValue());
        else
            return null;
    }
    
    /*@Override
    public int countEncaps(){
        return numerator.countEncaps() + denom.countEncaps();
    }*/

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
        /*while(numerator instanceof Fraction || denom instanceof Fraction){
            if(numerator instanceof Fraction){
                //num.num -> num*num.num; num.den -> den*num.den
                numerator = new Product(numerator, ((Fraction)numerator).numerator);
                denom = new Product(denom, ((Fraction)numerator).denom);
            }
            if(denom instanceof Fraction){
                numerator = new Product(numerator, ((Fraction)denom).denom);
                denom = new Product(denom, ((Fraction)denom).numerator);
            }
        }*/

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
            setWidth(numerator.getWidth() + 2*FRACTION_SPRITE_MARGIN_SIDES);
        else
            setWidth(denom.getWidth() + 2*FRACTION_SPRITE_MARGIN_SIDES);
        setHeight(numerator.getHeight() + denom.getHeight() + 2*FRACTION_SPRITE_MARGIN_TOP_BOTTOM);
    }
    
    @Override
    public void optSubPos(Graphics g){
        numerator.setX(getX() + (getWidth()-numerator.getWidth())/2);
        numerator.setY(getY());
        denom.setX(getX() + (getWidth()-denom.getWidth())/2);
        denom.setY(getY() + numerator.getHeight() + 2*FRACTION_SPRITE_MARGIN_TOP_BOTTOM);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        numerator.paint(g);
        
        Color c = g.getColor();
        
        if(color == null)
            color = c;
        else
            g.setColor(color);
        
        g.fillRect(getX(),
                denom.getY()-(int)(FRACTION_SPRITE_MARGIN_TOP_BOTTOM*1.5),
                getWidth(),
                FRACTION_SPRITE_MARGIN_TOP_BOTTOM);
        
        g.setColor(c);
        
        denom.paint(g);
    }

    @Override
    public void parseContent(int offset) {
        if(offset <= 1){
            if(offset <= 0)
                denom.parseContent(0);
            numerator.parseContent(0);
        }
    }

    @Override
    public String toInputForm() {
        return numerator.toInputForm() + "/" + denom.toInputForm();
    }

    @Override
    public int getCursorSide() {
        if(cursorStart != cursorEnd)
            return 0;
        
        int innerCursor = ((cursorStart==0)?numerator:denom).getCursorSide();
        if(cursorStart == 0 && (innerCursor == -1 || innerCursor == 2)) //at the start?
            return -1;
        if(cursorStart == 1 && innerCursor >= 1) //at the end?
            return 1;
        else
            return 0;
    }
    
    /**
     * Set cursor directly per index.
     * @param index 0 = numerator, 1 = denominator
     */
    public void setCursor(int index) {
        cursorStart = cursorEnd = index;
    }

    @Override
    public void setCursor(int x, int y) {
        if(y < denom.getY()-FRACTION_SPRITE_MARGIN_TOP_BOTTOM){
            cursorStart = cursorEnd = 0;
            numerator.setCursor(x, y);
        }else{
            cursorStart = cursorEnd = 1;
            denom.setCursor(x, y);
        }
    }

    @Override
    public void cursorDragged(int x, int y) {
        if(y < denom.getY()-FRACTION_SPRITE_MARGIN_TOP_BOTTOM){
            cursorEnd = 0;
            numerator.cursorDragged(x, y);
        }else{
            cursorEnd = 1;
            denom.cursorDragged(x, y);
        }
        
        if(cursorStart == cursorEnd)
            ((cursorStart==0)?numerator:denom).cursorDragged(x, y);
    }

    @Override
    public void moveCursor(int direction) {}

    @Override
    public void paintCursor(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLUE);
        
        if(cursorStart == cursorEnd)
            if(cursorStart == 0)
                numerator.paintCursor(g);
            else
                denom.paintCursor(g);
        else
            g.fillRect(getX(), getY(), getWidth(), getHeight());
        
        g.setColor(c);
    }

    @Override
    public void putText(String s) {
        if(cursorStart != cursorEnd){
            UnfinishedTerm uft = new UnfinishedTerm(s);
            transferProps(this, uft);
            parent.changeSubObj(this, uft);
        }
        
        ((cursorStart==0)?numerator:denom).putText(s);
    }

    @Override
    public void backspace() {
        if(cursorStart != cursorEnd){
            UnfinishedTerm uft = new UnfinishedTerm("");
            transferProps(this, uft);
            parent.changeSubObj(this, uft);
            return;
        }
        
        if(cursorStart == 0){//numerator
            numerator.backspace();
        }else{//denominator
            if(denom.getCursorSide() == -1 || denom.getCursorSide() == 2){//start of denominator
                UnfinishedTerm uft = new UnfinishedTerm(numerator.toInputForm() + denom.toInputForm());
                transferProps(this, uft);
                uft.setCursor(numerator.toInputForm().length());
                parent.changeSubObj(this, uft);
            }else{
                denom.backspace();
            }
        }
    }

    @Override
    public void deleteText() {
        if(cursorStart != cursorEnd){
            UnfinishedTerm uft = new UnfinishedTerm("");
            transferProps(this, uft);
            parent.changeSubObj(this, uft);
            return;
        }
        
        if(cursorStart == 0){//numerator
            if(numerator.getCursorSide() > 0){//end of numerator
                UnfinishedTerm uft = new UnfinishedTerm(numerator.toInputForm() + denom.toInputForm());
                transferProps(this, uft);
                uft.setCursor(numerator.toInputForm().length());
                parent.changeSubObj(this, uft);
            }else{
                numerator.deleteText();
            }
        }else{//denominator
            denom.deleteText();
        }
    }
}