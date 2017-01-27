/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.style;

import com.frequem.epic.JDialogMenuItem;
import com.frequem.epic.JSpritePanel;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author user
 */
public class JStyleStrokeMenuItem extends JDialogMenuItem implements PropertyChangeListener{

    public JStyleStrokeMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
    }

    @Override
    protected Container getContentPane() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
