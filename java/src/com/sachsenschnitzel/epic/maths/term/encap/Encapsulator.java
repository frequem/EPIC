/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.maths.term.encap;

import com.frequem.epic.iface.Textable;
import com.sachsenschnitzel.epic.maths.AssignedValues;
import com.sachsenschnitzel.epic.maths.MathObject;
import com.sachsenschnitzel.epic.maths.term.Sum;
import com.sachsenschnitzel.epic.maths.term.Term;
import static com.sachsenschnitzel.epic.maths.term.Term.removeElements;
import com.sachsenschnitzel.epic.maths.term.UnfinishedTerm;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author schnitzel
 */
public abstract class Encapsulator extends Term implements Textable{
    //only used for drawing in sprite
    public static final int PAR_SPRITE_MARGIN_SIDE = 1;
    
    protected Term c; //child / content
    protected String l, r; //left and right indicators i.e. "(", ")"
    
    protected int cursorStart, cursorEnd; //0 = before '(', 1 = in subterm, 2 = after ')'

    public Encapsulator(Term content){
        super();
        this.c = content;
        if(c != null)
            c.setParent(this);
    }
    
    /**
     * Provisionary solution for EncapsulatorParser.
     * @param content
     * @return 
     */
    public abstract Encapsulator getInstance(Term content);
    
    public String getL(){ return l; }
    public String getR(){ return r; }
    
    @Override
    public void print(){
        System.out.print("#" + l);
        c.print();
        System.out.print("#" + r);
    }
    
    @Override
    public void parseContent(int offset){
        //TODO
        if(offset == 0)
            c.parseContent(0);
    }
    
    @Override
    public String toInputForm(){
        return l + c.toInputForm() + r;
    }
    
    @Override
    public void changeSubObj(MathObject o, MathObject n){
        if(!(o instanceof Term && n instanceof Term))
            return;
        if(o.equals(c)){
            MathObject.transferProps(o, n);
            c = (Term)n;
        }
    }
    
    @Override
    public Term calc(AssignedValues avs){
        return c.calc(avs);
    }

    /*@Override
    public Term derive(String var){
        //(c)' = (c')          ;)
        return new Parenthesis(c.derive(var));
    }*/

    @Override
    public void simplify(){
        /*while(c instanceof Parenthesis)
            c = ((Parenthesis)c).c;
        c.setParent(this);*/
        c.simplify();
        //cursorStart = cursorEnd = cursor;
    }
    
    private void optFont(Graphics g){
        if(font == null)
            font = g.getFont().deriveFont(getHeight());
    }

    @Override
    public void optSize(Graphics g){
        c.optSize(g);
        setHeight(c.getHeight());
        optFont(g);
        setWidth(c.getWidth() + g.getFontMetrics(font).stringWidth(l+r) + 2*PAR_SPRITE_MARGIN_SIDE);
    }
    
    @Override
    public void optSubPos(Graphics g){
        c.setX(this.getX() + g.getFontMetrics(font).stringWidth(l)
                + PAR_SPRITE_MARGIN_SIDE);
        c.setY(getY());
        c.optSubPos(g);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        optFont(g);
        
        if(font == null)
            font = g.getFont();
        
        int fh = g.getFontMetrics(font).getHeight();
        g.drawString(l, getX(), getY()+getHeight()-(getHeight()-fh)/2);
        c.paint(g);
        g.drawString(r,
                c.getX() + c.getWidth() + PAR_SPRITE_MARGIN_SIDE,
                getY()+getHeight()-(getHeight()-fh)/2);
    }
    
    @Override
    public int getCursorSide(){
        if(cursorStart != cursorEnd)
            return 0;
        return cursorStart-1;
    }
    
    public void setCursor(int index){
        cursorStart = cursorEnd = index;
    }
    
    @Override
    public void setCursor(int x, int y) { //TODO: turn into unfinished
        if(x < c.getX()-PAR_SPRITE_MARGIN_SIDE)
            cursorStart = cursorEnd = 0;
        else if(x > c.getX()+c.getWidth()+PAR_SPRITE_MARGIN_SIDE)
            cursorStart = cursorEnd = 2;
        else{
            cursorStart = cursorEnd = 1;
            c.setCursor(x, y);
        }
    }

    @Override
    public void cursorDragged(int x, int y) {
        if(x < c.getX()-PAR_SPRITE_MARGIN_SIDE/2)
            cursorEnd = 0;
        else if(x > c.getX()+c.getWidth()+PAR_SPRITE_MARGIN_SIDE/2)
            cursorEnd = 2;
        else
            cursorEnd = 1;
        
        if(cursorStart == cursorEnd && cursorEnd == 1)
            c.cursorDragged(x, y);
    }

    @Override
    public void moveCursor(int direction) {}

    @Override
    public void paintCursor(Graphics g) {
        Color c = g.getColor();
        
        g.setColor(Color.BLUE);
        
        if(cursorStart == 1 && cursorEnd == 1){
            this.c.paintCursor(g);
            g.setColor(c);
            return;
        }
        
        int c1 = Math.min(cursorStart, cursorEnd);
        int c2 = (c1==cursorStart)?cursorEnd:cursorStart;
        
        int xs = 0, xe = 0; //start, end for rectangle
        switch(c1){
            case 0: xs = getX();              break;
            case 1: xs = this.c.getX();  break;
            case 2: xs = getX()+getWidth();            break;
        }
        switch(c2){
            case 0: xe = getX();                                  break;
            case 1: xe = this.c.getX() + this.c.getWidth();  break;
            case 2: xe = getX()+getWidth();                                break;
        }
        g.fillRect(xs, getY(), xe-xs+1, getHeight());
        
        g.setColor(c);
    }
    
    @Override
    public void putText(String s){
        if(cursorStart != cursorEnd){
            UnfinishedTerm subs = removeOwnSelection(); //substitute
            subs.putText(s);
            parent.changeSubObj(this, subs);
        }
        
        else if(cursorStart == 1 && c instanceof Textable)
            ((Textable)c).putText(s);
        //TODO: cursor at 0, 2
        else{//cursorStart == cursorEnd
            String uftContent = "(" + c.toInputForm() + ")";
            UnfinishedTerm uft = new UnfinishedTerm(uftContent);
            MathObject.transferProps(this, uft); //not 100% correct, because of color and fonts
            if(cursorStart == 2)
                uft.setCursor(uftContent.length());
            uft.setFont(c.getFont());
            uft.putText(s);
            parent.changeSubObj(this, uft);
        }
    }
    
    @Override
    public void backspace(){
        if(cursorStart == cursorEnd){
            if(cursorStart == 1){
                if(c.getCursorSide() == -1){ //if we need to erase smth ourselves
                    UnfinishedTerm uft = new UnfinishedTerm(c.toInputForm() + ")");
                    MathObject.transferProps(this, uft); //not 100% correct, because of color and fonts
                    uft.setFont(c.getFont());
                    parent.changeSubObj(this, uft);
                }else{ // if not, delegate...
                    if(c instanceof Textable)
                        ((Textable)c).backspace();
                    else
                        System.out.println("cannot apply backspace on " + c.getClass().getSimpleName());
                }
            }else{ //cursorStart must be 2, because if it was 0, parent would have done smth
                String uftContent = "(" + c.toInputForm();
                UnfinishedTerm uft = new UnfinishedTerm(uftContent);
                MathObject.transferProps(this, uft); //not 100% correct, because of color and fonts
                uft.setCursor(uftContent.length());
                uft.setFont(c.getFont());
                parent.changeSubObj(this, uft);
            }
        }else
            parent.changeSubObj(this, removeOwnSelection());
    }
    
    @Override
    public void deleteText(){
        if(cursorStart == cursorEnd){
            if(cursorStart == 1){
                if(c.getCursorSide() == 1){ //if we need to erase smth ourselves
                    String uftContent = "(" + c.toInputForm();
                    UnfinishedTerm uft = new UnfinishedTerm(uftContent);
                    uft.setCursor(uftContent.length());
                    MathObject.transferProps(this, uft); //not 100% correct, because of color and fonts
                    parent.changeSubObj(this, uft);
                }else{ // if not, delegate...
                    if(c instanceof Textable)
                        ((Textable)c).deleteText();
                    else
                        System.out.println("cannot apply backspace on " + c.getClass().getSimpleName());
                }
            }else{ //cursorStart must be 0, because if it was 2, parent would have done smth
                UnfinishedTerm uft = new UnfinishedTerm(c.toInputForm() + ")");
                MathObject.transferProps(this, uft); //not 100% correct, because of color and fonts
                uft.setFont(c.getFont());
                parent.changeSubObj(this, uft);
            }
        }else
            parent.changeSubObj(this, removeOwnSelection());
    }
    
    private UnfinishedTerm removeOwnSelection(){
        int c1 = Math.min(cursorStart, cursorEnd);
        int c2 = c1==cursorStart ? cursorEnd : cursorStart;
        
        UnfinishedTerm uft = null;
        
        if(c1 == 0 && c2 == 1){
            uft = new UnfinishedTerm(")");
        }else if(c1 == 1 && c2 == 2){
            uft = new UnfinishedTerm("(");
            uft.setCursor(1);
        }else if(c2-c1 == 2){
            uft = new UnfinishedTerm("");
        }else
            return null;
        
        MathObject.transferProps(this, uft);
        return uft;
    }
}
