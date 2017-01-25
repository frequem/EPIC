package com.frequem.epic.mode;

import com.frequem.epic.JSpritePanel;
import com.frequem.epic.Mode;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

/**
 *
 * @author user
 */
public class SelectMode extends Mode{

    private Rectangle rect;
    private Point pressPoint;
    
    public SelectMode(JSpritePanel panel){
        super(panel);
        this.rect = new Rectangle(0, 0, 0, 0);
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        this.rect.setLocation(me.getX(), me.getY());
        this.rect.setSize(0, 0);
        this.getSpritePanel().getSprites().stream().forEach(s->s.setSelected(s.getBounds().contains(me.getX(), me.getY())));
        this.getSpritePanel().repaint();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        this.pressPoint = me.getPoint();
        this.rect.setLocation(me.getX(), me.getY());
        this.rect.setSize(0, 0);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        this.mouseDragged(me);
        this.getSpritePanel().getSprites().stream().forEach(s->s.setSelected(this.rect.contains(s.getBounds())));
        this.rect.setSize(0, 0);
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        Point dragPoint = me.getPoint();
        int x = Math.min(pressPoint.x, dragPoint.x);
        int y = Math.min(pressPoint.y, dragPoint.y);
        int width = Math.max(pressPoint.x - dragPoint.x, dragPoint.x - pressPoint.x);
        int height = Math.max(pressPoint.y - dragPoint.y, dragPoint.y - pressPoint.y);
        
        this.rect.setLocation(x, y);
        this.rect.setSize(width, height);
        
        this.getSpritePanel().repaint();
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        Color c = g2.getColor();
        Stroke s = g2.getStroke();
        
        g2.setColor(this.getSpritePanel().getSelectionColor());
        g2.setStroke(this.getSpritePanel().getSelectionStroke());
        g2.drawRect(rect.x, rect.y, rect.width, rect.height);
        
        g2.setStroke(s);
        g2.setColor(c);
    }
}
