/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.file;

import com.frequem.epic.JMenuItem;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.action.SpriteDeleteAction;
import com.frequem.epic.iface.Sprite;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author user
 */
public class JFileNewMenuItem extends JMenuItem{
    
    public JFileNewMenuItem(JSpritePanel panel) {
        super(panel);
    }
    
    @Override
    protected String getLabelText(){
        return "Create new File";
    }
    
    @Override
    protected ImageIcon getIcon(){
        return new ImageIcon("img" + File.separator + "new.png");
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        Sprite[] sa = this.getSpritePanel().getSprites().stream().toArray(Sprite[]::new);
        this.getSpritePanel().doAction(new SpriteDeleteAction(this.getSpritePanel(), sa));
        this.getSpritePanel().repaint();
    }
}
