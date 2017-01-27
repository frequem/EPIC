/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.sprite;

import com.frequem.epic.iface.Sprite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

/**
 *
 * @author user
 */
public class Line{
    public int xStart, yStart, xEnd, yEnd;
    
    public Line(int xStart, int yStart, int xEnd, int yEnd){
        this.xStart = xStart;
        this.yStart = yStart;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }
    
    public static void paint(Graphics g, int xStart, int yStart, int xEnd, int yEnd) {
        Graphics2D g2d = (Graphics2D) g;
        final Line2D l = new Line2D.Float(xStart, yStart, xEnd, yEnd);
        g2d.draw(l);
        //g.drawLine(xStart, yStart, xEnd, yEnd);
    }
    
    public void paint(Graphics g) {
        Line.paint(g, xStart, yStart, xEnd, yEnd);
    }
    

    public void paintOffset(Graphics g, int xO, int yO){
        Line.paint(g, xStart-xO, yStart-yO, xEnd-xO, yEnd-yO);
    }


}
