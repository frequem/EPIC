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
public class Parenthesis extends Encapsulator implements Textable{
    //only used for drawing in sprite
    public static final int PAR_SPRITE_MARGIN_SIDE = 1;
    
    private Term c; //child / content
    
    private int cursorStart, cursorEnd; //0 = before '(', 1 = in subterm, 2 = after ')'

    public Parenthesis(Term content){
        super();
        this.c = content;
        c.setParent(this);
    }
    
    @Override
    public void print(){
        System.out.print("#(");
        c.print();
        System.out.print("#)");
    }
    
    @Override
    public void parseContent(int offset){
        //TODO
        if(offset == 0)
            c.parseContent(0);
    }
    
    @Override
    public String toInputForm(){
        return "(" + c.toInputForm() + ")";
    }
    
    @Override
    public void changeSubterm(Term o, Term n){
        if(o.equals(c)){
            MathObject.transferProps(o, n);
            c = n;
        }
    }
    
    @Override
    public double calc(AssignedValues avs){
        return c.calc(avs);
    }

    @Override
    public Term derive(String var){
        //(c)' = (c')          ;)
        return new Parenthesis(c.derive(var));
    }

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
            font = g.getFont().deriveFont(h);
    }

    @Override
    public void optSize(Graphics g){
        c.optSize(g);
        h = c.getHeight();
        optFont(g);
        FontMetrics fm = g.getFontMetrics(font);
        w = c.getWidth() + fm.stringWidth("()") + 2*PAR_SPRITE_MARGIN_SIDE;
    }
    
    @Override
    public void optSubPos(Graphics g){
        c.setX(this.x + g.getFontMetrics(font).stringWidth("(")
                + PAR_SPRITE_MARGIN_SIDE);
        c.setY(y);
        c.optSubPos(g);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        optFont(g);
        
        //int desc = g.getFontMetrics(font).getDescent();
        g.drawString("(", x, y+h);
        c.paint(g);
        g.drawString(")",
                x + w/2 + c.getWidth()/2 + PAR_SPRITE_MARGIN_SIDE,
                y+h);
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
    public void setCursor(int x, int y) {
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
            case 0: xs = x;              break;
            case 1: xs = this.c.getX();  break;
            case 2: xs = x+w;            break;
        }
        switch(c2){
            case 0: xe = x;                                  break;
            case 1: xe = this.c.getX() + this.c.getWidth();  break;
            case 2: xe = x+w;                                break;
        }
        g.fillRect(xs, y, xe-xs, h);
        
        g.setColor(c);
    }
    
    @Override
    public void putText(String s){
        if(cursorStart != cursorEnd)
            removeOwnSelection();
        
        else if(cursorStart == 1 && c instanceof Textable)
            ((Textable)c).putText(s);
        //TODO: cursor at 0, 2
        else{//cursorStart == cursorEnd = true
            String uftContent = "(" + c.toInputForm() + ")";
            UnfinishedTerm uft = new UnfinishedTerm(uftContent);
            MathObject.transferProps(this, uft); //not 100% correct, because of color and fonts
            if(cursorStart == 2)
                uft.setCursor(uftContent.length());
            uft.setFont(c.getFont());
            parent.changeSubterm(this, uft);
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
                    parent.changeSubterm(this, uft);
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
                parent.changeSubterm(this, uft);
            }
        }else
            removeOwnSelection();
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
                    parent.changeSubterm(this, uft);
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
                parent.changeSubterm(this, uft);
            }
        }else
            removeOwnSelection();
    }
    
    private void removeOwnSelection(){
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
            return;
        
        MathObject.transferProps(this, uft);
        parent.changeSubterm(this, uft);
    }
}
