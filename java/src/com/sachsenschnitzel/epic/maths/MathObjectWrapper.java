/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.maths;

import com.frequem.epic.iface.Textable;
import com.sachsenschnitzel.epic.maths.term.Term;
import com.sachsenschnitzel.epic.maths.term.UnfinishedTerm;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * This is a wrapper-class, which has only one private MathObject and delegates everything to it.
 * It should be used as the first object in a chain of MathObject's,
 so that whatever comes after it has a parent and can overwrite itself.
 *
 * @author schnitzel
 */
public class MathObjectWrapper extends MathObject{
    private MathObject mo;
    
    public MathObjectWrapper(MathObject mo){
        this.mo = mo;
        mo.setParent(this);
    }
    
    @Override
    public void print(){
        mo.print();
        System.out.println();
    }
    
    @Override
    public void parseContent(int offset){ mo.parseContent(offset); }
    
    @Override
    public String toInputForm(){ return mo.toInputForm(); }
    
    @Override
    public void optSize(Graphics g){ mo.optSize(g); }
    
    @Override
    public void optSubPos(Graphics g){ mo.optSubPos(g); }
    
    @Override
    public void simplify(){ mo.simplify(); }
    
    @Override
    public void changeSubObj(MathObject o, MathObject n){
        if(mo == o){
            MathObject.transferProps(o, n);
            mo = n;
        }
    }

    @Override
    public void paint(Graphics g) { mo.paint(g); }

    @Override
    public Rectangle getBounds() { return mo.getBounds(); }

    @Override
    public void setSelected(boolean s) { mo.setSelected(s); }

    @Override
    public boolean isSelected() { return mo.isSelected(); }

    @Override
    public void setX(int x) { mo.setX(x); }

    @Override
    public int getX() { return mo.getX(); }

    @Override
    public void setY(int y) { mo.setY(y); }

    @Override
    public int getY() { return mo.getY(); }

    @Override
    public void setWidth(int w) { mo.setWidth(w); }

    @Override
    public int getWidth() { return mo.getWidth(); }

    @Override
    public void setHeight(int h) { mo.setHeight(h); }

    @Override
    public int getHeight() { return mo.getHeight(); }

    @Override
    public void moveBy(int x, int y) { mo.moveBy(x, y); }

    @Override
    public int getCursorSide(){ return mo.getCursorSide(); }
    
    @Override
    public void setCursor(int x, int y) { mo.setCursor(x, y); }

    @Override
    public void cursorDragged(int x, int y) { mo.cursorDragged(x, y); }

    @Override
    public void moveCursor(int direction) { mo.moveCursor(direction); }

    @Override
    public void paintCursor(Graphics g) { mo.paintCursor(g); }
    
    @Override
    public void putText(String s){ mo.putText(s); }
    
    @Override
    public void backspace(){ mo.backspace(); }
    
    @Override
    public void deleteText(){ mo.deleteText(); }
}
