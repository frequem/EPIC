package com.frequem.epic;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;

public abstract class JContentMenuItem extends JMenuItem{
    
    public JContentMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
        final FlowLayout f = new FlowLayout(); //needed for simulating gap
        this.setLayout(new BorderLayout());
        this.add(this.getComponent(), BorderLayout.SOUTH);
        this.setBorder(BorderFactory.createEmptyBorder(f.getHgap(), f.getVgap(), f.getHgap(), f.getVgap()));
    }
    
    @Override
    public void mouseClicked(MouseEvent me){}
    
    protected abstract Component getComponent();
}
