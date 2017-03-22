/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.file;

import com.frequem.epic.JBarMenu;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.iface.Menuable;
/**
 *
 * @author user
 */
public class JFileMenu extends JBarMenu{

    public JFileMenu(JSpritePanel panel){
        super(panel);
    }
    
    @Override
    public void fillMenu(Menuable menu) {
        menu.addMenuItem(new JFileSaveMenuItem(getSpritePanel()));
        menu.addMenuItem(new JFileNewMenuItem(getSpritePanel()));
        menu.addMenuItem(new JFileOpenMenuItem(getSpritePanel()));
    }
    
}
