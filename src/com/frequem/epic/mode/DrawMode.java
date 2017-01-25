/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.mode;

import com.frequem.epic.JSpritePanel;
import com.frequem.epic.Mode;
import com.frequem.epic.action.SpriteAddAction;
import com.frequem.epic.sprite.Drawing;
import com.frequem.epic.sprite.Line;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

/**
 *
 * @author user
 */
public class DrawMode extends Mode{
    
    private Drawing d;
    private int oldX, oldY, curX, curY;
    
    public DrawMode(JSpritePanel spritePanel) {
        super(spritePanel);
        d = new Drawing();
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        curX = me.getX();
        curY = me.getY();
        oldX = curX;
        oldY = curY;
    }

    @Override
    public void mousePressed(MouseEvent me) {
        curX = me.getX();
        curY = me.getY();
        oldX = curX;
        oldY = curY;
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        curX = me.getX();
        curY = me.getY();
        d.scheduleBuffer();
        this.getSpritePanel().doAction(new SpriteAddAction(getSpritePanel(), d));
        d = new Drawing();
        this.getSpritePanel().repaint();
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
        d.addLine(new Line(oldX, oldY, curX, curY));
        this.getSpritePanel().repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }
    
    @Override
    public void paint(Graphics g){
        d.paint(g);
    }
    
}
