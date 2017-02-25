/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.iface;

import java.awt.Graphics;

/**
 * 
 * @author frequem
 */
public interface Cursorable {
    public final static int DIRECTION_RIGHT = 0;
    public final static int DIRECTION_LEFT = 1;
    
    public void setCursor(int x, int y);
    public void cursorDragged(int x, int y);
    public void moveCursor(int direction);
    
    public void paintCursor(Graphics g);
}
