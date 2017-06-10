package com.sachsenschnitzel.epic.maths.term;

import java.util.ArrayList;
import java.util.List;

import com.frequem.epic.iface.Sprite;
import com.sachsenschnitzel.epic.maths.AssignedValues;
import com.sachsenschnitzel.epic.maths.MathObject;
import com.sachsenschnitzel.epic.maths.MathObjectWrapper;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Term extends MathObject{
    public Term(MathObject parent){
        super(parent);
    }
    
    public Term(){
        super();
    }
    
    public abstract void print();
    
    //mathematical stuff...
    public abstract Term calc(AssignedValues avs);
    public abstract Term derive(String var);
    //public abstract void simplify();
    public Term calc(){ return calc(null); }
    /**
     * 
     * @return +1 for open encaps, -1 for close
     */
    //public abstract int countEncaps();

    //spritey stuff...
    
    public void paint(Graphics g){
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        this.optSize(g);
        this.optSubPos(g);
    }
    
    /*public Rectangle getBounds(){ return new Rectangle(x, y, w, h); }
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
    }*/
    
    /**
     * 
     * @param o
     * @param from (inclusive)
     * @param to (exclusive)
     * @return 
     */
    public static Term[] removeElements(Term[] o, int from, int to){
        Term[] n = new Term[o.length-(to-from)];
        for(int i = 0, j = 0; i < o.length; i++, j++){
            if(i == from)
                i = to;
            n[j] = o[i];
        }
        return n;
    }
}
