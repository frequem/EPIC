/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.mode;

import com.frequem.epic.JSpritePanel;
import com.frequem.epic.Mode;
import com.frequem.epic.action.SpriteAddAction;
import com.frequem.epic.sprite.Text;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.NoSuchElementException;

/**
 *
 * @author user
 */
public class TextMode extends Mode{
    
    Text recent = null;
    
    public TextMode(JSpritePanel spritePanel) {
        super(spritePanel);
    }
    
    @Override
    public void mousePressed(MouseEvent me) {
        //find and set MathsObject(Wrapper) every time LMB is down...
        try{
            recent = (Text)this.getSpritePanel().getSprites().stream().peek(s->s.setSelected(false)).filter(s->s.getBounds().contains(me.getPoint())).filter(s -> s instanceof Text).findFirst().get();
            recent.setCursor(me.getX(), me.getY());
            recent.setSelected(true);
        }catch(NoSuchElementException e){
            recent = null;
        }
        this.getSpritePanel().repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        //...if there was nothing there, create a new one
        if(recent == null){
            recent = new Text();
            recent.setX(me.getX());
            recent.setY(me.getY());
            this.getSpritePanel().doAction(new SpriteAddAction(getSpritePanel(), recent));
            recent.setSelected(true);
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
        this.getSpritePanel().repaint();
    }
    
    @Override
    public void paint(Graphics g){
        if(recent != null) recent.paintCursor(g);
    }
}
