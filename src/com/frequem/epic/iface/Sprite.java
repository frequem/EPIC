package com.frequem.epic.iface;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface Sprite {
    public void paint(Graphics g);
    public Rectangle getBounds();
    public void setSelected(boolean s);
    public boolean isSelected();
    
    public void setX(int x);
    public int getX();
    public void setY(int y);
    public int getY();
    public void setWidth(int w);
    public int getWidth();
    public void setHeight(int h);
    public int getHeight();
    public void moveBy(int x, int y);
}
