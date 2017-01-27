/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic;

import com.frequem.epic.action.ColorChangeAction;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author user
 */
public abstract class JDialogMenuItem extends JMenuItem{
    
    private boolean dialogOpened = false;
    
    public JDialogMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        if(!dialogOpened){
            this.dialogOpened = true;
            JDialog d = new JDialog();
            d.setAlwaysOnTop(true);
            d.setResizable(false);
            d.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent we) {
                    dialogOpened = false;
                }
            });
            d.setContentPane(this.getContentPane());
            d.pack();
            d.setVisible(true);
        }
    }
    
    protected abstract Container getContentPane();
    
}
