/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.sprite;

import com.frequem.epic.iface.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Drawing extends BasicSprite{

    private final ArrayList<Line> lines;
    private BufferedImage image;
    
    private boolean buffer = false;
    
    public Drawing(){
        lines = new ArrayList<>();
    }
    
    public void addLine(Line l){
        this.lines.add(l);
    }
    
    @Override
    public Rectangle getBounds(){
        if(lines.size() > 0){
            Line l = lines.get(0);
            int xMin = Math.min(l.xStart, l.xEnd);
            int yMin = Math.min(l.yStart, l.yEnd);
            int xMax = Math.max(l.xStart, l.xEnd);
            int yMax = Math.max(l.yStart, l.yEnd);
            
            for(int i = 1; i < lines.size(); i++){
                l = lines.get(i);
                xMin = Math.min(xMin, Math.min(l.xStart, l.xEnd));
                yMin = Math.min(yMin, Math.min(l.yStart, l.yEnd));
                xMax = Math.max(xMax, Math.max(l.xStart, l.xEnd));
                yMax = Math.max(yMax, Math.max(l.yStart, l.yEnd));
            }
            
            if(image != null){
                xMin = Math.min(xMin, getX());
                yMin = Math.min(yMin, getY());
                xMax = Math.max(xMax, getX() + getWidth());
                yMax = Math.max(yMax, getY() + getHeight());
            }
            return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
        }
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    public void scheduleBuffer(){
        this.buffer = true;
    }
    
    @Override
    public void paint(Graphics g) {
        Rectangle r = getBounds();
        if(buffer){
            int xImg = getX() - r.x;
            int yImg = getY() - r.y ;
            
            setX(r.x);
            setY(r.y);
            setWidth(r.width);
            setHeight(r.height);
            
            BufferedImage buf = new BufferedImage(getWidth() + 1, getHeight() + 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = buf.createGraphics();
            g2d.setColor(g.getColor());
            g2d.drawImage(this.image, xImg, yImg, null);
            lines.forEach((li)->li.paintOffset(g2d, getX(), getY()));
            
            this.image = buf;
            lines.clear();
            buffer = false;
        }
        
        g.drawImage(image, getX(), getY(), null);
        lines.forEach((l)->l.paint(g));
    }


    
}
