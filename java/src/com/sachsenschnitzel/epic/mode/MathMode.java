/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.mode;

import com.frequem.epic.JSpritePanel;
import com.frequem.epic.Mode;
import com.frequem.epic.action.SpriteAddAction;
import com.sachsenschnitzel.epic.maths.MathObject;
import com.sachsenschnitzel.epic.maths.MathObjectWrapper;
import com.sachsenschnitzel.epic.maths.term.Constant;
import com.sachsenschnitzel.epic.maths.term.Sum;
import com.sachsenschnitzel.epic.maths.term.UnfinishedTerm;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.NoSuchElementException;

/**
 * This is a mode for putting down MathsObjects and modifying their content.
 * ...Copied from frequem's package and modified ^^'
 *
 * @author sachsenschnitzel
 */
public class MathMode extends Mode{
    
    MathObjectWrapper recent = null;
    
    public MathMode(JSpritePanel spritePanel) {
        super(spritePanel);
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        if(recent == null){
            //parent in constant gets overwritten immediately by MOW:
            /*Constant c1 = new Constant(1);
            Constant c2 = new Constant(2);
            Constant c3 = new Constant(3);
            Sum s = new Sum(c1, c2, c3);
            recent = new MathObjectWrapper(s);*/
            recent = new MathObjectWrapper(new UnfinishedTerm(""));
            recent.setX(me.getX());
            recent.setY(me.getY());
            this.getSpritePanel().doAction(new SpriteAddAction(getSpritePanel(), recent));
            recent.setSelected(true);
        }
        this.getSpritePanel().repaint();
    }
    
    @Override
    public void mousePressed(MouseEvent me) {
        try{
            if(recent != null){
                recent.print();
                recent.parseContent(0);
            }
            recent = (MathObjectWrapper)this.getSpritePanel().getSprites().stream().peek(s->s.setSelected(false)).filter(s->s.getBounds().contains(me.getPoint())).filter(s -> s instanceof MathObject).findFirst().get();
            //System.out.println("found");
            recent.setCursor(me.getX(), me.getY());
            recent.setSelected(true);
        }catch(NoSuchElementException e){
            recent = null;
        }
        this.getSpritePanel().repaint();
    }
    
    @Override
    public void mouseDragged(MouseEvent me) {
        if(recent != null)
            recent.cursorDragged(me.getX(), me.getY());
        this.getSpritePanel().repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        if(ke.getKeyChar() > 31 && ke.getKeyChar() != 127) recent.putText(""+ke.getKeyChar());
        if(recent != null){
            if(ke.getKeyChar() == 8) recent.backspace(); //backspace
            if(ke.getKeyChar() == 127) recent.deleteText(); //del
        }
        recent.simplify();
        this.getSpritePanel().repaint();
    }
    
    @Override
    public void paint(Graphics g){
        if(recent != null) recent.paintCursor(g);
    }
}
