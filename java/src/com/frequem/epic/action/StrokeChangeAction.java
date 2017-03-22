/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.action;

import com.frequem.epic.iface.Action;
import com.frequem.epic.iface.Strokeable;
import java.awt.Stroke;

/**
 *
 * @author user
 */
public class StrokeChangeAction implements Action{

    Strokeable sa;
    Stroke oldStroke;
    Stroke newStroke;
    
    public StrokeChangeAction(Strokeable sa, Stroke s){
        this.sa = sa;
        this.oldStroke = sa.getStroke();
        this.newStroke = s;
    }
    
    @Override
    public void act() {
        sa.setStroke(newStroke);
    }

    @Override
    public void revert() {
        sa.setStroke(oldStroke);
    }
    
}
