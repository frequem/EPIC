/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.mode;

import com.frequem.epic.JSpritePanel;
import com.frequem.epic.Mode;
import com.frequem.epic.mode.MoveMode;
import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author user
 */
public class JModeMoveMenuItem extends JModeMenuItem{

    public JModeMoveMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
    }
    
    @Override
    protected String getLabelText(){
        return "Move Mode";
    }
    
    @Override
    protected ImageIcon getIcon(){
        return new ImageIcon("img" + File.separator + "move.png");
    }

    @Override
    protected Mode getMode() {
        return new MoveMode(this.getSpritePanel());
    }
    
}
