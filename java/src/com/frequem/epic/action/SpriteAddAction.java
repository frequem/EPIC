package com.frequem.epic.action;

import com.frequem.epic.iface.Action;
import com.frequem.epic.iface.Sprite;
import com.frequem.epic.iface.SpriteContainable;

public class SpriteAddAction implements Action{

    private final SpriteContainable con;
    private final Sprite s;
    
    public SpriteAddAction(SpriteContainable con, Sprite s){
        this.con = con;
        this.s = s;
    }
    
    @Override
    public void act() {
        con.addSprite(s);
    }

    @Override
    public void revert() {
        con.removeSprite(s);
    }
    
}
