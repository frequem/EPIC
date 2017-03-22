/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.style;

import com.frequem.epic.item.JColorPicker;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.JDialogMenuItem;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;

public class JStyleColorMenuItem extends JDialogMenuItem implements PropertyChangeListener{
    
    public JStyleColorMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
        this.getSpritePanel().addPropertyChangeListener(this);
        
        this.setIcon(getColorIcon(this.getSpritePanel().getColor()));
    }
    
    private static ImageIcon getColorIcon(Color c){
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(c);
        g2d.fillRect(0, 0, 1, 1);
        return new ImageIcon(img);
    }
    
    @Override
    protected String getLabelText(){
        return "Set Color";
    }
    
    @Override
    protected Container getContainer() {
        return new JColorPicker(getSpritePanel());
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if(pce.getPropertyName().equals("color")){
            this.setIcon(getColorIcon((Color) pce.getNewValue()));
        }
    }
    
    
}
