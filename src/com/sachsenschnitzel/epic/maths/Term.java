package com.sachsenschnitzel.epic.maths;

import java.util.ArrayList;
import java.util.List;

import com.frequem.epic.iface.Sprite;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Term implements Sprite{
    public abstract double calc(AssignedValues avs);
    public abstract Term derive(String var);
    public abstract void simplify();
    public double calc(){ return calc(null); }

    //spritey stuff...
    protected int x, y, w, h;
    protected boolean selected;
    
    protected abstract void optSize(Graphics g);
    protected abstract void optSubPos(Graphics g);
    /**
     * optimizes size and positions of all possible subterms
     * @param g 
     */
    public void optStructure(Graphics g){
        optSize(g);
        optSubPos(g);
    }

    public abstract void paint(Graphics g);
    public Rectangle getBounds(){ return new Rectangle(x, y, w, h); }
    public void setSelected(boolean s){ selected = s; }
    public boolean isSelected(){ return selected; }

    public void setX(int x){ this.x = x; }
    public int getX(){ return x; }
    public void setY(int y){ this.y = y; }
    public int getY(){ return y; }
    public void setWidth(int w){ this.w = w; }
    public int getWidth(){ return w; }
    public void setHeight(int h){ this.h = h; }
    public int getHeight(){ return h; }
    public void moveBy(int x, int y){
        this.x -= x;
        this.y -= y;
    }
}
