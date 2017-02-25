/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.mode;

import com.frequem.epic.JSpritePanel;
import com.frequem.epic.Mode;
import com.frequem.epic.mode.SelectMode;
import com.frequem.epic.mode.TextMode;
import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author user
 */
public class JModeTextMenuItem extends JModeMenuItem{

    public JModeTextMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
    }
    
    @Override
    protected String getLabelText(){
        return "Text Mode";
    }
    
    @Override
    protected ImageIcon getIcon(){
        return null;
    }

    @Override
    protected Mode getMode() {
        return new TextMode(this.getSpritePanel());
    }
}
