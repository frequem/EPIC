/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic;

import com.frequem.epic.iface.SpritePanelable;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public class JSpritePanelComponent extends JPanel implements SpritePanelable{
    
    private JSpritePanel spritePanel;
    
    public JSpritePanelComponent(JSpritePanel spritePanel){
        this.spritePanel = spritePanel;
        this.setFocusable(false);
    }
    
    @Override
    public JSpritePanel getSpritePanel(){
        return this.spritePanel;
    }
    
}
