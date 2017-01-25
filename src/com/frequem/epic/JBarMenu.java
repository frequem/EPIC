/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic;

import com.frequem.epic.iface.Menuable;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author user
 */
public abstract class JBarMenu extends JSpritePanelComponent implements Menuable, ActionListener{
    private final static int MAX_ITEMS = 3;
    
    private final JMenu expandMenu;
    private final JPanel itemPanel;
    private final JButton btnExpand;
    
    private boolean expMenuOpened = false;
    
    public JBarMenu(JSpritePanel spritePanel) {
        super(spritePanel);
        this.expandMenu = new JMenu(spritePanel);
        
        this.setLayout(new BorderLayout());
        this.itemPanel = new JPanel();
        this.itemPanel.setLayout(new BoxLayout(this.itemPanel, BoxLayout.PAGE_AXIS));
        this.add(itemPanel, BorderLayout.CENTER);
        
        this.btnExpand = new JButton("more");
        this.btnExpand.setVisible(false);
        this.add(btnExpand, BorderLayout.SOUTH);
        
        btnExpand.addActionListener(this);
        
        this.fillMenu(expandMenu);
        this.fillMenu(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(!expMenuOpened){
            this.expMenuOpened = true;
            JFrame f = new JFrame();
            f.setContentPane(this.expandMenu);
            f.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent we) {
                    JBarMenu.this.expMenuOpened = false;
                }
            });
            f.pack();
            f.setVisible(true); 
        }
    }
    
    @Override
    public void addMenuItem(JMenuItem i){
        int c = this.itemPanel.getComponentCount();
        if(c < MAX_ITEMS){
            this.itemPanel.add(i);
        }
        if(c >= MAX_ITEMS)
            this.btnExpand.setVisible(true);
    }
    
}
