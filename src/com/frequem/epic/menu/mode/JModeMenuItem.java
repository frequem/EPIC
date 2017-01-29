/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.mode;

import com.frequem.epic.JMenuItem;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.Mode;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.UIManager;

/**
 *
 * @author user
 */
public abstract class JModeMenuItem extends JMenuItem implements PropertyChangeListener{
    
    public JModeMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
        this.getSpritePanel().addPropertyChangeListener(this);
    }

    protected abstract Mode getMode();    
    
    @Override
    public void mouseClicked(MouseEvent me) {
        getSpritePanel().setMode(this.getMode());
    }
    
    @Override
    public void mousePressed(MouseEvent me) {}
    
    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if(pce.getPropertyName().equals("mode")){
            if(this.getMode().getClass().isInstance(pce.getNewValue())){
                this.setBackground(UIManager.getColor("Panel.background").darker());
            }else{
                this.setBackground(UIManager.getColor("Panel.background"));
            }
        }
    }
    
}
