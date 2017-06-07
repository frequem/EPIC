/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.sprite;

import com.frequem.epic.iface.Sprite;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 *
 * @author user
 */
public abstract class BasicSprite implements Sprite, Serializable{
    private int x, y, w, h;
    private boolean selected = false;

    @Override
    public void setX(int x){
        this.x = x;
    }
    
    @Override
    public int getX(){
        return this.x;
    }
    
    @Override
    public void setY(int y){
        this.y = y;
    }
    
    @Override
    public int getY(){
        return this.y;
    }
    
    @Override
    public int getWidth(){
        return this.w;
    }
    
    @Override
    public void setWidth(int w){
        this.w = w;
    }
    
    @Override
    public int getHeight(){
        return this.h;
    }
    
    @Override
    public void setHeight(int h){
        this.h = h;
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    @Override
    public void setSelected(boolean s) {
        this.selected = s;
    }

    @Override
    public boolean isSelected() {
        return this.selected;
    }
    
    @Override
    public void moveBy(int x, int y){
        this.x -= x;
        this.y -= y;
    }
    
}
