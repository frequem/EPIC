/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.edit;

import com.frequem.epic.JBarMenu;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.iface.Menuable;
import java.awt.event.MouseEvent;

/**
 *
 * @author user
 */
public class JEditMenu extends JBarMenu{

    public JEditMenu(JSpritePanel spritePanel) {
        super(spritePanel);
    }

    @Override
    public void fillMenu(Menuable menu) {
        menu.addMenuItem(new JEditDeleteMenuItem(this.getSpritePanel()));
    }
    
    
}
