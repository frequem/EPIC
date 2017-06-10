/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.maths.term;

import com.sachsenschnitzel.epic.maths.parser.SumParser;
import com.sachsenschnitzel.epic.maths.parser.ConstantParser;
import com.frequem.epic.iface.Cursorable;
import com.frequem.epic.iface.Textable;
import com.frequem.epic.mathematics.parser.*;
import com.sachsenschnitzel.epic.maths.AssignedValues;
import com.sachsenschnitzel.epic.maths.Equation;
import com.sachsenschnitzel.epic.maths.MathObject;
import com.sachsenschnitzel.epic.maths.MathObjectWrapper;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


/**
 * This is semantically actually not a Term, but it acts like one.
 * The UnfinishedTerm is used for parsing and providing user-interface on the Term-Level.
 *
 * @author frequem
 */
public class UnfinishedTerm extends Term implements Cursorable, Textable{
    private String data;
    
    private int cursorIndex, selectionLength;
    
    /*public UnfinishedTerm(Term parent, String stringRep){
        super(parent);
        this.data = stringRep;
    }*/
    
    public UnfinishedTerm(String stringRep){
        this.data = stringRep;
    }
    
    @Override
    public void print() {
        System.out.print("["+this.data+"(unfinished)]");
    }
    
    @Override
    public void parseContent(int offset){
        if(this.parent == null)
            return;
        
        MathObject mo = Parser.tryParse(data, offset);
        if(mo == null)
            return;
        
        MathObject.transferProps(this, mo);
        if(mo instanceof Equation && !(parent instanceof MathObjectWrapper)){
            MathObject p = parent;
            while(!(p.getParent() instanceof MathObjectWrapper)) //get "root"-mo
                p = p.getParent();
            if(!(p instanceof Equation)){ //parse everything anew (easiest way without messing with MOs)
                UnfinishedTerm uft = new UnfinishedTerm(p.toInputForm());
                MathObject.transferProps(p, uft);
                p.getParent().changeSubObj(p, uft);
                uft.parseContent(1);
            }
        }else{
            this.parent.changeSubObj(this, mo);
        }
    }
    
    @Override
    public String toInputForm(){
        return data;
    }
    
    @Override
    public void changeSubObj(MathObject o, MathObject n) {}

    @Override
    public Term calc(AssignedValues avs) {
        return null;
    }

    @Override
    public Term derive(String var) {
        //same here
        return null;
    }

    @Override
    public void simplify() {}

    @Override
    public void optSize(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        setWidth(fm.stringWidth(data));
        setHeight(fm.getHeight());
    }

    @Override
    public void optSubPos(Graphics g) {}
    
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
        g.drawString(data, getX(), getY()+getHeight()/*-g.getFontMetrics(font).getDescent()*/);
        
        g.setFont(f);
        g.setColor(c);
    }
    
    @Override
    public int getCursorSide(){
        if(data.length() == 0)
            return 2;
        if(selectionLength != 0)
            return 0;
        if(cursorIndex == 0)
            return -1;
        if(cursorIndex == data.length())
            return 1;
        else
            return 0;
    }
    
    public void setCursor(int index){
        cursorIndex = index;
        selectionLength = 0;
        //System.out.println("cursor: " + cursorIndex);
    }
    
    @Override
    public void setCursor(int x, int y) {
        x -= this.getX();
        y -= this.getY();
        selectionLength = 0;
        cursorIndex = findCursorSnapPos(x, y);
        //System.out.println("cursor: " + cursorIndex);
    }
    
    private int findCursorSnapPos(int x, int y){
        if(data.length() == 0)
            return 0;
        x = Math.max(0, Math.min(x, this.getWidth()-1));
        Canvas c = new Canvas();
        FontMetrics fm = c.getFontMetrics(font);
        char[] atext = data.toCharArray();
        
        int l = 0; 
        int h = atext.length;
        int m, w;
        do{
            m = (l+h)/2;
            w = fm.charsWidth(atext, 0, m);
            if(Math.abs(x-w) <= fm.charWidth(atext[x<w?m-1:m])/2) break;
            else if(x < w) h = m-1;
            else if(x > w) l = m+1;
        }while(true);
        return m;
    }

    @Override
    public void cursorDragged(int x, int y) {
        x -= this.getX();
        y -= this.getY();
        int p = findCursorSnapPos(x, y);
        this.selectionLength = p - cursorIndex;
    }
    
    @Override
    public void putText(String s) {
        if(this.selectionLength != 0)
            removeSelection();
        
        StringBuilder sb = new StringBuilder(data);
        sb.insert(cursorIndex, s);
        data = sb.toString();
        
        this.cursorIndex += s.length();
        this.parseContent(1);
        //System.out.println("done");
    }

    @Override
    public void backspace() {
        if(this.selectionLength != 0){
            this.removeSelection();
            return;
        }
        if(this.cursorIndex > 0){
            StringBuilder sb = new StringBuilder(data);
            sb.deleteCharAt(--cursorIndex);
            data = sb.toString();
        }
    }

    @Override
    public void deleteText() {
        if(this.selectionLength != 0){
            this.removeSelection();
            return;
        }
        if(this.cursorIndex < data.length()){
            StringBuilder sb = new StringBuilder(data);
            sb.deleteCharAt(cursorIndex);
            data = sb.toString();
        }
    }
    
    private void removeSelection(){
        StringBuilder sb = new StringBuilder(data);
        sb.delete(Math.min(cursorIndex, cursorIndex + selectionLength), Math.max(cursorIndex, cursorIndex + selectionLength));
        data = sb.toString();
        
        if(selectionLength < 0)
            cursorIndex += selectionLength;
        selectionLength = 0;
    }
    
    @Override
    public void moveCursor(int direction) {}

    @Override
    public void paintCursor(Graphics g) {
        Color c = g.getColor();
        
        g.setColor(Color.BLUE);
        FontMetrics fm = g.getFontMetrics();
        int cursorX = fm.stringWidth(data.substring(0, cursorIndex));
        int selX = fm.stringWidth(data.substring(0, cursorIndex + selectionLength));
        g.fillRect(getX() + Math.min(cursorX, selX), getY(), Math.max(cursorX - selX, selX - cursorX) + 1, getHeight());
        
        g.setColor(c);
    }
}
