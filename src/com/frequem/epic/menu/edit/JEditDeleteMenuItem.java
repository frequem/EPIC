/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.edit;

import com.frequem.epic.JMenuItem;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.action.SpriteDeleteAction;
import com.frequem.epic.iface.Sprite;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;

/**
 *
 * @author user
 */
public class JEditDeleteMenuItem extends JMenuItem{
    
    public JEditDeleteMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
    }
    
    
    @Override
    protected String getLabelText(){
        return "Delete";
    }
    
    @Override
    protected ImageIcon getIcon(){
        return new ImageIcon("img" + File.separator + "trash.png");
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        Sprite[] sa = this.getSpritePanel().getSprites().stream().peek(s->s.isSelected()).toArray(Sprite[]::new);
        this.getSpritePanel().doAction(new SpriteDeleteAction(this.getSpritePanel(), sa));
        this.getSpritePanel().repaint();
    }
    
}
