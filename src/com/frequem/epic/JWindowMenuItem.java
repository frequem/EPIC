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
import javax.swing.JFrame;

/**
 *
 * @author user
 */
public abstract class JWindowMenuItem extends JMenuItem{
    
    private boolean frameOpened = false;
    
    public JWindowMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        System.out.println(frameOpened);
        if(!frameOpened){
            this.frameOpened = true;
            JFrame f = new JFrame();
            f.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent we) {
                    frameOpened = false;
                }
            });
            f.setContentPane(this.getContentPane());
            f.pack();
            f.setVisible(true);
        }
    }
    
    protected abstract Container getContentPane();
    
}
