/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.style;

import com.frequem.epic.JMenuItem;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.action.ColorChangeAction;
import java.awt.Color;
import java.awt.event.MouseEvent;

/**
 *
 * @author user
 */
public class JStyleColorMenuItem extends JMenuItem{
    
    public JStyleColorMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
    }
    
    @Override
    protected String getLabelText(){
        return "Set Color";
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        getSpritePanel().doAction(new ColorChangeAction(getSpritePanel(), Color.GREEN));
    }
    
    
}
