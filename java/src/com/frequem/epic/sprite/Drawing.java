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
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author user
 */
public class Drawing extends BasicSprite implements Serializable{

    private final ArrayList<Line> lines;
    private transient BufferedImage image;
    
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
        Graphics2D g2d = (Graphics2D) g;
        
        final Rectangle re = g2d.getStroke().createStrokedShape(new RoundRectangle2D.Float()).getBounds();
        final int s = Math.max(re.width, re.height);
        
        Rectangle r = getBounds();
        r.setSize(r.width + s, r.height + s);
        r.setLocation(r.x - s/2, r.y - s/2);
        
        if(buffer){
            int xImg = getX() - r.x;
            int yImg = getY() - r.y ;
            
            setX(r.x);
            setY(r.y);
            setWidth(r.width);
            setHeight(r.height);
            
            BufferedImage buf = new BufferedImage(getWidth() + 1, getHeight() + 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2dbuf = buf.createGraphics();
            g2dbuf.setColor(g2d.getColor());
            g2dbuf.setStroke(g2d.getStroke());
            g2dbuf.drawImage(this.image, xImg, yImg, null);
            lines.forEach((li)->li.paintOffset(g2dbuf, getX(), getY()));
            
            this.image = buf;
            lines.clear();
            buffer = false;
        }
        
        g2d.drawImage(image, getX(), getY(), null);
        lines.forEach((l)->l.paint(g));
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        ImageIO.write(this.image, "png", out); // png is lossless
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.image = ImageIO.read(in);
    }

    
}
