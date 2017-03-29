package com.sachsenschnitzel.epic.maths.term;

import com.frequem.epic.iface.Textable;
import com.sachsenschnitzel.epic.maths.AssignedValues;
import com.sachsenschnitzel.epic.maths.MathObject;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

public class Sum extends Term implements Textable{
    //only used for drawing in sprite
    public static final int SUM_SPRITE_MARGIN_SIDE = 2;
    
    private Term[] summands;
    
    private int cursorStart, cursorEnd; //this refers to the index in summands[]

    public Sum(Term... summands){
        super();
        this.summands = summands;
        for(Term summand : this.summands)
            summand.setParent(this);
    }
    
    @Override
    public void print(){
        if(summands.length == 0)
            return;
        System.out.print("S{");
        summands[0].print();
        for(int i = 1; i < summands.length; i++){
            System.out.print(" + ");
            summands[i].print();
        }
        System.out.print("}");
    }
    
    @Override
    public void parseContent(int offset){
        //System.out.println("parsing inside sum for: " + (summands.length-offset));
        for(int i = 0; i < summands.length-offset; i++)
            summands[i].parseContent(0);
    }
    
    @Override
    public String toInputForm(){
        if(summands.length == 0)
            return "";
        String s = summands[0].toInputForm();
        for(int i = 1; i < summands.length; i++)
            s += "+" + summands[i].toInputForm();
        return s;
    }
    
    private String toInputForm(int from, int to){
        if(to-from > summands.length || to > summands.length-1 || from < 0)
            return "";
        String s = summands[from].toInputForm();
        for(int i = from+1; i <= to; i++)
            s += "+" + summands[i].toInputForm();
        return s;
    }
    
    @Override
    public void changeSubterm(Term o, Term n){
        for(int i = 0; i < summands.length; i++)
            if(o.equals(summands[i])){
                MathObject.transferProps(o, n);
                summands[i] = n;
            }
    }
    
    @Override
    public double calc(AssignedValues avs){
        double sm = 0;
        for(Term summand : summands)
            sm += summand.calc(avs);
        return sm;
    }
    
    @Override
    public int countEncaps(){
        int sum = 0; // ;P
        for(Term summand : summands){
            sum += summand.countEncaps();
        }//TODO...
        return sum;
    }

    @Override
    public Term derive(String var){
        //(f+g)' = f'+g'
        Term[] derivedSummands = new Term[summands.length];
        for(int i = 0; i < summands.length; i++)
            derivedSummands[i] = summands[i].derive(var);
        return new Sum(derivedSummands);
    }

    @Override
    public void simplify(){
        ArrayList<Term> elements = new ArrayList<Term>();
        elements.addAll(Arrays.asList(summands));
        
        int cursor = 0; //counts where the cursor should be afterwards

        //scroll through elements
        for(int i = 0; i < elements.size(); i++)
            //sub-sum?
            if(elements.get(i) instanceof Sum){
                //sub-sum! (break it down...)
                Sum subsum = (Sum)elements.get(i);
                for(int j = 0; j < subsum.summands.length; j++)
                    elements.add(i+j+1, subsum.summands[j]);
                elements.remove(i);
                i--;//cancels out the i++
                cursor += subsum.summands.length-1;
            }else{
                if(i < cursorStart) cursor++;
            }
        //System.out.println("elements: " + elements.size());
        cursorStart = cursorEnd = cursor;

        summands = elements.toArray(new Term[0]);

        for(Term summand : summands){ //do it for the rest
            summand.setParent(this);
            summand.simplify();
        }
        
        clean();
    }
    
    private void clean(){
        if(summands.length == 1){ //only one left?
            parent.changeSubterm(this, summands[0]); //no sum anymore
        }
    }

    @Override
    public String toString(){
        String s = "";
        for(int i = 0; i < summands.length-1; i++)
            s += summands[i] + " + ";
        return "(" + s + summands[summands.length-1] + ")";
    }

    @Override
    public void optSize(Graphics g){
        w = (summands.length-1)*
                (2*SUM_SPRITE_MARGIN_SIDE + g.getFontMetrics().stringWidth("+"));
        h = 0;
        
        for(Term summand : summands){
            summand.optSize(g);
            w += summand.getWidth();
            if (summand.getHeight() > h)
                h = summand.getHeight();
        }
    }
    
    @Override
    public void optSubPos(Graphics g){
        int plusWidth = g.getFontMetrics().stringWidth("+");
        
        int xCounter = x;
        for(Term summand : summands){
            summand.setX(xCounter);
            summand.setY(y + (h-summand.getHeight())/2);
            summand.optSubPos(g);
            xCounter += summand.getWidth() + 2*SUM_SPRITE_MARGIN_SIDE + plusWidth;
        }
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        if(summands.length < 1)
            return;
        
        if(font == null)
            font = g.getFont();
        
        int plusWidth = g.getFontMetrics(font).stringWidth("+");
        int plusHeight = g.getFontMetrics(font).getHeight();
        
        summands[0].paint(g);
        for(int i = 1; i < summands.length; i++){
            int posX = summands[i].getX()-SUM_SPRITE_MARGIN_SIDE - plusWidth;
            g.drawString("+", posX, y + (h+plusHeight)/2);
            summands[i].paint(g);
        }
    }
    
    @Override
    public int getCursorSide(){
        if(cursorStart != cursorEnd)
            return 0;
        if(cursorStart*cursorEnd == 0) //at the start?
            return -1;
        if((summands.length-1-cursorStart)*(summands.length-1-cursorEnd) == 0) //at the end?
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
        summands[cursorStart].setCursor(x, y);
        //System.out.println("set cursor to " + cursorStart);
    }
    
    /**
     * Without this functionality, Encapsulators wouldn't work.
     * They have to be in one String. This method ensures
     * that all Unfinished[ sub]Terms are connected.
     * @param index starting point for concatenation
     */
    /*private void concatSubterms(int index){
        int i1, i2;
        for(i1 = index; i1 >= 0; i1--)
            if(!(summands[i1] instanceof UnfinishedTerm))
                break;
        for(i2 = index; i2 < summands.length; i2++)
            if(!(summands[i2] instanceof UnfinishedTerm))
                break;
        if(i2-i1 > 0){
            i1++;i2--;
            summands[i1] = new UnfinishedTerm
            removeElements(summands, i1, i2);
        }
    }*/
    
    private int findCursorSnapPos(int x, int y){
        //check every interspace
        for(int i = 0; i < summands.length-1; i++){
            if(x < (summands[i+1].x+summands[i].x+summands[i].w)/2)
                return i; //if x is to the left of the center of the interspace
        }
        return summands.length-1;
    }

    @Override
    public void cursorDragged(int x, int y) {
        cursorEnd = findCursorSnapPos(x, y);
        if(cursorStart == cursorEnd){
            //System.out.println("dragging cursor in summand " + cursorStart);
            summands[cursorStart].cursorDragged(x, y);
        }//else
            //System.out.println("( " + cursorStart + "-" + cursorEnd + " )");
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
                if(maxSubHeight < summands[i].h)
                    maxSubHeight = summands[i].h;//q&d possible
            
            g.fillRect(summands[c1].x,
                    y+(h-maxSubHeight)/2,
                    summands[c2].x+summands[c2].w-summands[c1].x,
                    maxSubHeight);
            //System.out.println("left 2 right");
        }else{
            summands[cursorStart].paintCursor(g);
        }
        
        g.setColor(c);
    }
    
    @Override
    public void putText(String s){
        if(cursorStart != cursorEnd)
            removeOwnSelection();
        
        if(summands[cursorStart] instanceof Textable)
            ((Textable)summands[cursorStart]).putText(s);
        else
            System.out.println("cannot put text on " + summands[cursorStart].getClass().getSimpleName());
        
        clean();
    }
    
    @Override
    public void backspace(){
        if(cursorStart == cursorEnd){
            int cs = summands[cursorStart].getCursorSide();
            if(cs == -1 || cs == 2){ //if we need to erase smth
                deleteAtCursor(-1);
            }else{ // if not, delegate...
                if(summands[cursorStart] instanceof Textable)
                    ((Textable)summands[cursorStart]).backspace();
                else
                    System.out.println("cannot apply backspace on " + summands[cursorStart].getClass().getSimpleName());
            }
        }else
            removeOwnSelection();
        
        clean();
    }
    
    @Override
    public void deleteText(){
        if(cursorStart == cursorEnd){
            int cs = summands[cursorStart].getCursorSide();
            if(cs == 1 || cs == 2){ //if we need to erase smth
                deleteAtCursor(1);// System.out.println("hi");
            }else{ // if not, delegate...
                if(summands[cursorStart] instanceof Textable)
                    ((Textable)summands[cursorStart]).deleteText();
                else
                    System.out.println("cannot apply deleteText on " + summands[cursorStart].getClass().getSimpleName());
            }
        }else
            removeOwnSelection();
        
        clean();
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

        summands = removeElements(summands, c1, c2);
        summands[c1] = genUnfinished("");
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
        
        if(cursorStart == (direction<0 ? 0 : summands.length-1))
            return;
        String combined = summands[posp].toInputForm();
        int index = combined.length();
        combined += summands[posh].toInputForm();
        summands = removeElements(summands, posp, posh);
        UnfinishedTerm uft = genUnfinished(combined);
        uft.setCursor(index);
        summands[posp] = uft;
        if(direction < 0)
            cursorEnd = --cursorStart;
    }
}
