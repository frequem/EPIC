/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.menu.mode;

import com.frequem.epic.menu.mode.*;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.Mode;
import com.frequem.epic.mode.SelectMode;
import com.frequem.epic.mode.TextMode;
import com.sachsenschnitzel.epic.mode.MathMode;
import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author user
 */
public class JModeMathMenuItem extends JModeMenuItem{

    public JModeMathMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
    }
    
    @Override
    protected String getLabelText(){
        return "Math Mode";
    }
    
    @Override
    protected ImageIcon getIcon(){
        return null;
    }

    @Override
    protected Mode getMode() {
        return new MathMode(this.getSpritePanel());
    }
}
