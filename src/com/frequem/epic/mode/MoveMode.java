package com.frequem.epic.mode;

import com.frequem.epic.JSpritePanel;
import com.frequem.epic.Mode;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class MoveMode extends Mode{
    
    private int oldX, oldY, curX, curY;
    
    public MoveMode(JSpritePanel spritePanel) {
        super(spritePanel);
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        this.getSpritePanel().getSprites().stream().forEach(s->s.setSelected(s.getBounds().contains(me.getX(), me.getY())));
        this.getSpritePanel().repaint();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        oldX = curX = me.getX();
        oldY = curY = me.getY();
        
        this.getSpritePanel().getSprites().stream().filter(s->s.getBounds().contains(me.getX(), me.getY())).peek(s->s.setSelected(true)).count();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        this.mouseDragged(me);
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        oldX = curX;
        oldY = curY;
        curX = me.getX();
        curY = me.getY();
        
        boolean newSelect = false;
        this.getSpritePanel().getSprites().stream().filter(s->s.isSelected()).forEach(s->s.moveBy(oldX-curX, oldY-curY));
        this.getSpritePanel().repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }
}
