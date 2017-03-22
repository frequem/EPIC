/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.action;

import com.frequem.epic.iface.Action;
import com.frequem.epic.iface.Sprite;
import com.frequem.epic.iface.SpriteContainable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author user
 */
public class SpriteDeleteAction implements Action{

    private final SpriteContainable con;
    private final Sprite[] sprites;
    
    public SpriteDeleteAction(SpriteContainable con, Sprite[] sprites){
        this.con = con;
        this.sprites = sprites;
    }
    
    public SpriteDeleteAction(SpriteContainable con, Sprite sprite){
        this.con = con;
        this.sprites = new Sprite[1];
        sprites[0] = sprite;
    }
    
    @Override
    public void act() {
        for(int i=0; i<sprites.length;i++)
            con.removeSprite(sprites[i]);
    }

    @Override
    public void revert() {
        for(Sprite s:sprites)
            con.addSprite(s);
    }
    
}
