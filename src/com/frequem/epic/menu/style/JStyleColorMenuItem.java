/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.style;

import com.frequem.epic.JColorPicker;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.JWindowMenuItem;
import java.awt.Container;

/**
 *
 * @author user
 */
public class JStyleColorMenuItem extends JWindowMenuItem{
    
    public JStyleColorMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
    }
    
    @Override
    protected String getLabelText(){
        return "Set Color";
    }

    @Override
    protected Container getContentPane() {
        return new JColorPicker();
    }
    
    
}
