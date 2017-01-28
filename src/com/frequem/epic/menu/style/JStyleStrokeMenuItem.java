/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.style;

import com.frequem.epic.JContentMenuItem;
import com.frequem.epic.JDialogMenuItem;
import com.frequem.epic.JMenuItem;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.item.JStrokeChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;

/**
 *
 * @author user
 */
public class JStyleStrokeMenuItem extends JContentMenuItem implements PropertyChangeListener{

    public JStyleStrokeMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
        this.getSpritePanel().addPropertyChangeListener(this);
    }

    
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if(pce.getPropertyName().equals("stroke")){
            this.repaint();
        }
    }

    @Override
    protected Component getComponent() {
        return new JStrokeChooser(getSpritePanel());
    }
    
}
