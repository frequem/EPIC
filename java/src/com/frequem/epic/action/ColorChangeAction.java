package com.frequem.epic.action;

import com.frequem.epic.iface.Action;
import com.frequem.epic.iface.Colorable;
import java.awt.Color;

public class ColorChangeAction implements Action{
    
    private final Colorable c;
    
    private final Color oldColor;
    private final Color newColor;
    
    public ColorChangeAction(Colorable c, Color newColor){
        this.c = c;
        this.oldColor = c.getColor();
        this.newColor = newColor;
    }

    @Override
    public void act() {
        c.setColor(newColor);
    }
    
    @Override
    public void revert() {
        c.setColor(oldColor);
    }
    
}
