package com.sachsenschnitzel.epic.maths;

import static com.sachsenschnitzel.epic.maths.MathObject.transferProps;
import com.sachsenschnitzel.epic.maths.term.Term;
import com.sachsenschnitzel.epic.maths.term.UnfinishedTerm;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Equation extends MathObject{
    public static final int EQUATION_SPRITE_MARGIN_SIDE = 2;
    
    private Term left;
    private Term right;
    
    private int cursorStart, cursorEnd;
    private boolean isCalcable;
    private boolean isCalculation; //determines if the equation is a statement of its own or a calculation
    
    //for drawing
    Ellipse2D.Double calcBtn;
    
    public Equation(Term leftSide, Term rightSide){
            left = leftSide;
            if(left != null)
                left.setParent(this);
            right = rightSide;
            if(right != null)
                right.setParent(this);
            isCalculation = false;
            isCalcable = true;
    }

    @Override
    public void print() {
        left.print();
        System.out.print("=");
        right.print();
    }

    @Override
    public void parseContent(int offset) {
        if(offset <= 1){
            if(offset <= 0)
                right.parseContent(0);
            left.parseContent(0);
        }
    }

    @Override
    public String toInputForm() {
        return left.toInputForm() + "=" + right.toInputForm();
    }

    @Override
    public int getCursorSide() {
        if(cursorStart != cursorEnd)
            return 0;
        
        int innerCursor = ((cursorStart==0)?left:right).getCursorSide();
        if(cursorStart == 0 && (innerCursor == -1 || innerCursor == 2)) //at the start?
            return -1;
        if(cursorStart == 1 && innerCursor >= 1) //at the end?
            return 1;
        else
            return 0;
    }

    @Override
    public void optSize(Graphics g) {
        left.optSize(g);
        right.optSize(g);
        
        setWidth(2*EQUATION_SPRITE_MARGIN_SIDE + left.getWidth() +
                g.getFontMetrics().stringWidth("=") + right.getWidth());
        setHeight( (left.getHeight()>right.getHeight()) ? left.getHeight() : right.getHeight() );
    }

    @Override
    public void optSubPos(Graphics g) {
        left.setX(getX());
        left.setY(getY() + (getHeight()-left.getHeight())/2);
        left.optSubPos(g);
        
        right.setX(getX() + getWidth() - right.getWidth());
        right.setY(getY() + (getHeight()-right.getHeight())/2);
        right.optSubPos(g);
        
        calcBtn = new Ellipse2D.Double(left.getX() + left.getWidth(),
                    getY() + getHeight()/2 - 3 - EQUATION_SPRITE_MARGIN_SIDE,
                    g.getFontMetrics().stringWidth("=") + 2*EQUATION_SPRITE_MARGIN_SIDE,
                    g.getFontMetrics().stringWidth("=") + 2*EQUATION_SPRITE_MARGIN_SIDE);
    }

    @Override
    public void simplify() {
        //throw new UnsupportedOperationException("Not supported yet."); //change: Tools | Templates.
    }

    @Override
    public void changeSubObj(MathObject o, MathObject n) {
        if(!(o instanceof Term && n instanceof Term))
            return;
        if(left == o){
            left = (Term)n;
            return;
        }
        if(right == o)
            right = (Term)n;
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        left.paint(g);
        
        Color c = g.getColor();
        Font f = g.getFont();
        
        if(color == null)
            color = c;
        if(font == null)
            font = f;
        else
            g.setFont(font);
        
        if(isCalcable && left.calc() != null){
            g.setColor(Color.GREEN);
            ((Graphics2D)g).fill(calcBtn);
            g.setColor(color);
            ((Graphics2D)g).draw(calcBtn);
        }
        
        g.setColor(color);
        g.drawString("=", left.getX() + left.getWidth() + EQUATION_SPRITE_MARGIN_SIDE,
                        getY() + (getHeight()+g.getFontMetrics(font).getHeight())/2);
        
        g.setColor(c);
        g.setFont(f);
        
        right.paint(g);
    }
    
    public void setCursor(int index){
        cursorStart = cursorEnd = index;
    }

    @Override
    public void setCursor(int x, int y) {
        Term lResult = left.calc();
        if(calcBtn.contains(x, y) && isCalcable && lResult != null){
            System.out.println("in");
            right = lResult;
            right.setParent(this);
            isCalcable = false;
            return;
        }
        
        if(x > (right.getX() + left.getX() + left.getWidth()) / 2){ //x > middle between left & right
            right.setCursor(x, y);
            cursorStart = cursorEnd = 1;
        }else{
            left.setCursor(x, y);
            cursorStart = cursorEnd = 0;
        }
    }

    @Override
    public void cursorDragged(int x, int y) {
        if(x > (right.getX() + left.getX() + left.getWidth()) / 2){ //x > middle between left & right
            if(cursorStart == 1)
                right.setCursor(x, y);
            cursorEnd = 1;
        }else{
            if(cursorStart == 0)
                left.setCursor(x, y);
            cursorEnd = 0;
        }
    }

    @Override
    public void moveCursor(int direction) {}

    @Override
    public void paintCursor(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.BLUE);
        
        if(cursorStart == cursorEnd)
            ((cursorStart == 0)?left:right).paintCursor(g);
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
        
        ((cursorStart==0)?left:right).putText(s);
        
        isCalcable = true;
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
            left.backspace();
        }else{//denominator
            if(right.getCursorSide() == -1 || right.getCursorSide() == 2){//start of denominator
                UnfinishedTerm uft = new UnfinishedTerm(left.toInputForm() + right.toInputForm());
                transferProps(this, uft);
                uft.setCursor(left.toInputForm().length());
                parent.changeSubObj(this, uft);
            }else
                right.backspace();
        }
        
        isCalcable = true;
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
            if(left.getCursorSide() > 0){//end of numerator
                UnfinishedTerm uft = new UnfinishedTerm(left.toInputForm() + right.toInputForm());
                transferProps(this, uft);
                uft.setCursor(left.toInputForm().length());
                parent.changeSubObj(this, uft);
            }else{
                left.deleteText();
            }
        }else{//denominator
            right.deleteText();
        }
        
        isCalcable = true;
    }
	
	
}
