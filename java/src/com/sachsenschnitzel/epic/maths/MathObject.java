package com.sachsenschnitzel.epic.maths;

import com.frequem.epic.iface.*;
import com.frequem.epic.sprite.BasicSprite;
import com.sachsenschnitzel.epic.maths.term.Term;
import com.sachsenschnitzel.epic.maths.term.UnfinishedTerm;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author schnitzel
 */
public abstract class MathObject extends BasicSprite implements Cursorable, Fontable, Colorable{
    protected MathObject parent;
    protected Font font;
    protected Color color;
    
    public MathObject(MathObject parent){
        this.parent = parent;
    }
    
    public MathObject(){
        parent = null;
    }
    
    public MathObject getParent(){
        return parent;
    }
    
    public void setParent(MathObject parent){
        this.parent = parent;
    }
    
    public abstract void print();
    
    /**
     * This is not where the actual parsing happens but rather
     * what is used to distribute and delegate parsing tasks.
     * The offset is there for practical reasons, while typing,
     * one would never want everything to be parsed.
     * 
     * @param offset starting right, tells how many parseable sections should be ignored
     */
    public abstract void parseContent(int offset);
    
    public abstract String toInputForm();
    
    /**
     * This method can be used by parent MathsObjects to determine how to use the cursor
     * in case of a "backspace" or "del" for certain Textables.
     * -1 is returned, if the cursor is located at what the MO considers the very beginning.
     * 0 is returned, if the cursor is somewhere in the middle.
     * 1 is returned, if the cursor is located at the end.
     * 2 is returned, if the length of the object is 0 and therefore all is true
     */
    public abstract int getCursorSide();
    
    /**
     * optimizes size and positions of all possible subterms
     * @param g 
     */
    public abstract void optStructure(Graphics g);
    
    public abstract void simplify();
    
    /**
     * 
     * @param o original Term
     * @param n new Term
     */
    public abstract void changeSubterm(Term o, Term n);
    
    public static void transferProps(Term o, Term n){
        n.setParent(o.getParent());
        n.setFont(o.getFont());
        n.setColor(o.getColor());
        n.setX(o.getX());
        n.setY(o.getY());
        n.setWidth(o.getWidth());
        n.setHeight(o.getHeight());
        n.setSelected(o.isSelected());
    }
    
    @Override
    public void setFont(Font f){ font = f; }
    @Override
    public Font getFont(){ return font; }
    @Override
    public void setColor(Color c){ color = c; }
    @Override
    public Color getColor(){ return color; }
    
    public void paint(Graphics g){
        optStructure(g);
    }
}
