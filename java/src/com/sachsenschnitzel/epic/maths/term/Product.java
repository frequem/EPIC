package com.sachsenschnitzel.epic.maths.term;

import com.frequem.epic.iface.Textable;
import com.sachsenschnitzel.epic.maths.AssignedValues;
import com.sachsenschnitzel.epic.maths.MathObject;
import static com.sachsenschnitzel.epic.maths.term.Term.removeElements;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

public class Product extends Term{
    public static final int PRODUCT_SPRITE_MARGIN_SIDE = 1;

    private Term[] factors;
    
    private int cursorStart, cursorEnd; //index in factors[]

    public Product(Term... factors){
        super();
        this.factors = factors;
        for(Term factor : this.factors)
            factor.setParent(this);
    }
    
    @Override
    public void print(){
        factors[0].print();
        System.out.print("P{");
        for(int i = 1; i < factors.length; i++){
            System.out.print(" • ");
            factors[i].print();
        }
        System.out.print("}");
    }
    
    @Override
    public void changeSubObj(MathObject o, MathObject n){
        if(!(o instanceof Term && n instanceof Term))
            return;
        for(int i = 0; i < factors.length; i++)
            if(o == factors[i]){
                MathObject.transferProps(o, n);
                factors[i] = (Term)n;
            }
    }

    @Override
    public Term calc(AssignedValues avs){
        double prod = 1;
        for(Term factor : factors){
            Term factResult = factor.calc();
            if(factResult == null || !(factResult instanceof Constant))
                return null;
            prod *= ((Constant)factResult).getValue();
        }
        return new Constant(prod);
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
        
        int cursor = 0; //counts where the cursor should be afterwards

        //scroll through elements
        for(int i = 0; i < elements.size(); i++)
            //sub-prod?
            if(elements.get(i) instanceof Product){
                //sub-prod! (break it down...)
                Product subprod = (Product)elements.get(i);
                for(int j = 0; j < subprod.factors.length; j++)
                    elements.add(i+j+1, subprod.factors[j]);
                elements.remove(i);
                i--;//cancels out the i++
                cursor += subprod.factors.length-1;
            }else{
                if(i < cursorStart) cursor++;
            }
        //System.out.println("elements: " + elements.size());
        cursorStart = cursorEnd = cursor;

        factors = elements.toArray(new Term[0]);

        for(Term factor : factors){ //do it for the rest
            factor.setParent(this);
            factor.simplify();
        }
        
        clean();
    }

    @Override
    public String toString(){
        String s = "";
        for(int i = 0; i < factors.length-1; i++)
            s += factors[i] + " * ";
        return "(" + s + factors[factors.length-1] + ")";
    }
    
    @Override
    public void optSize(Graphics g){
        setWidth((factors.length-1)*
                (2*PRODUCT_SPRITE_MARGIN_SIDE + g.getFontMetrics().stringWidth("•")));
        setHeight(0);
        
        for(Term factor : factors){
            factor.optSize(g);
            setWidth(getWidth() + factor.getWidth());
            if (factor.getHeight() > getHeight())
                setHeight(factor.getHeight());
        }
    }
    
    @Override
    public void optSubPos(Graphics g){
        int dotWidth = g.getFontMetrics().stringWidth("•");
        
        int xCounter = getX();
        for(Term factor : factors){
            factor.setX(xCounter);
            factor.setY(getY() + (getHeight()-factor.getHeight())/2);
            factor.optSubPos(g);
            xCounter += factor.getWidth() + 2*PRODUCT_SPRITE_MARGIN_SIDE + dotWidth;
        }
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        Color c = g.getColor();
        
        if(color == null)
            color = c;
        else
            g.setColor(color);
        
        int dotWidth = g.getFontMetrics().stringWidth("•");
        int dotHeight = g.getFontMetrics().getHeight();
        factors[0].paint(g);
        for(int i = 1; i < factors.length; i++){
            int posX = factors[i].getX()-PRODUCT_SPRITE_MARGIN_SIDE - dotWidth;
            g.drawString("•", posX, getY() + (getHeight()+dotHeight)/2);
            factors[i].paint(g);
        }
        
        g.setColor(c);
    }

    @Override
    public void parseContent(int offset){
        for(int i = 0; i < factors.length-offset; i++)
            factors[i].parseContent(0);
    }
    
    @Override
    public String toInputForm(){
        if(factors.length == 0)
            return "";
        String s = factors[0].toInputForm();
        for(int i = 1; i < factors.length; i++)
            s += "*" + factors[i].toInputForm();
        return s;
    }

    @Override
    public int getCursorSide(){
        if(cursorStart != cursorEnd)
            return 0;
        
        int innerCursor = factors[cursorStart].getCursorSide();
        if(cursorStart == 0 && (innerCursor == -1 || innerCursor == 2)) //at the start?
            return -1;
        if(cursorStart == factors.length-1 && innerCursor >= 1) //at the end?
            return 1;
        else
            return 0;
    }
    
    public void setCursor(int index){
        cursorStart = cursorEnd = index;
    }

    @Override
    public void setCursor(int x, int y) {
        cursorStart = cursorEnd = findCursorSnapPos(x, y);
        factors[cursorStart].setCursor(x, y);
        //System.out.println("set cursor to " + cursorStart);
    }
    
    private int findCursorSnapPos(int x, int y){
        //check every interspace
        for(int i = 0; i < factors.length-1; i++){
            if(x < (factors[i+1].getX()+factors[i].getX()+factors[i].getWidth())/2)
                return i; //if x is to the left of the center of the interspace
        }
        return factors.length-1;
    }

    @Override
    public void cursorDragged(int x, int y) {
        cursorEnd = findCursorSnapPos(x, y);
        if(cursorStart == cursorEnd)
            factors[cursorStart].cursorDragged(x, y);
    }

    @Override
    public void moveCursor(int direction) {}

    @Override
    public void paintCursor(Graphics g) {
        Color c = g.getColor();
        
        g.setColor(Color.BLUE);
        
        int c1 = Math.min(cursorStart, cursorEnd);
        int c2 = (c1==cursorStart)?cursorEnd:cursorStart;
        
        if(c1 != c2){
            int maxSubHeight = 0;
            for(int i = c1; i <= c2; i++)
                if(maxSubHeight < factors[i].getHeight())
                    maxSubHeight = factors[i].getHeight();//q&d possible
            
            g.fillRect(factors[c1].getX(),
                    getY()+(getHeight()-maxSubHeight)/2,
                    factors[c2].getX()+factors[c2].getWidth()-factors[c1].getX(),
                    maxSubHeight);
            //System.out.println("left 2 right");
        }else{
            factors[cursorStart].paintCursor(g);
        }
        
        g.setColor(c);
    }
    
    @Override
    public void putText(String s){
        if(cursorStart != cursorEnd)
            removeOwnSelection();
        
        if(factors[cursorStart] instanceof Textable)
            ((Textable)factors[cursorStart]).putText(s);
        else
            System.out.println("cannot put text on " + factors[cursorStart].getClass().getSimpleName());
        
        clean();
    }
    
    @Override
    public void backspace(){
        if(cursorStart == cursorEnd){
            int cs = factors[cursorStart].getCursorSide();
            if(cs == -1 || cs == 2){ //if we need to erase smth
                deleteAtCursor(-1);
            }else{ // if not, delegate...
                if(factors[cursorStart] instanceof Textable)
                    ((Textable)factors[cursorStart]).backspace();
                else
                    System.out.println("cannot apply backspace on " + factors[cursorStart].getClass().getSimpleName());
            }
        }else
            removeOwnSelection();
        
        clean();
    }
    
    @Override
    public void deleteText(){
        if(cursorStart == cursorEnd){
            int cs = factors[cursorStart].getCursorSide();
            if(cs == 1 || cs == 2){ //if we need to erase smth
                deleteAtCursor(1);// System.out.println("hi");
            }else{ // if not, delegate...
                if(factors[cursorStart] instanceof Textable)
                    ((Textable)factors[cursorStart]).deleteText();
                else
                    System.out.println("cannot apply deleteText on " + factors[cursorStart].getClass().getSimpleName());
            }
        }else
            removeOwnSelection();
        
        clean();
    }
    
    private void clean(){
        if(factors.length == 1){ //only one left?
            parent.changeSubObj(this, factors[0]); //no product anymore
        }
    }
    
    /**
     * This is just a shortcut, so I don't have to code this every time.
     * 
     * @return 
     */
    private UnfinishedTerm genUnfinished(String text){
        UnfinishedTerm uft = new UnfinishedTerm(text);
        MathObject.transferProps(this, uft);
        return uft;
    }
    
    private void removeOwnSelection(){
        int c1 = Math.min(cursorStart, cursorEnd);
        int c2 = c1==cursorStart ? cursorEnd : cursorStart;

        factors = removeElements(factors, c1, c2);
        factors[c1] = genUnfinished("");
        cursorStart = cursorEnd = c1;
        //System.out.println("weep");
    }
    
    /**
     * Again only a helper method, which is used in backspace() and deleteText()
     * because they're basically the same except for the direction
     * @param direction -1=left, 1=right
     */
    private void deleteAtCursor(int direction){
        int posp = cursorStart + (direction-1)/2; //primary pos
        int posh = cursorStart + (direction+1)/2; //helper pos
        
        if(cursorStart == (direction<0 ? 0 : factors.length-1))
            return;
        String combined = factors[posp].toInputForm();
        int index = combined.length();
        combined += factors[posh].toInputForm();
        factors = removeElements(factors, posp, posh);
        UnfinishedTerm uft = genUnfinished(combined);
        uft.setCursor(index);
        factors[posp] = uft;
        if(direction < 0)
            cursorEnd = --cursorStart;
    }
}
