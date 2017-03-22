/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.file;

import com.frequem.epic.JMenuItem;
import com.frequem.epic.JSpritePanel;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

/**
 *
 * @author user
 */
public class JFileSaveMenuItem extends JMenuItem{
    
    public JFileSaveMenuItem(JSpritePanel panel) {
        super(panel);
    }
    
    @Override
    protected String getLabelText(){
        return "Save File";
    }
    
    @Override
    protected ImageIcon getIcon(){
        return new ImageIcon("img" + File.separator + "floppy.png");
    }
    
    
    @Override
    public void mouseClicked(MouseEvent me) {
        System.out.println("click 1 (save)");
    }

}
