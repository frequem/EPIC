package com.frequem.epic;

import com.frequem.epic.iface.Menuable;
import java.awt.Component;
import javax.swing.*;

public class JMenu extends JSpritePanelComponent implements Menuable{
    
    public JMenu(JSpritePanel spritePanel){
        super(spritePanel);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        this.fillMenu(this);
    }
    
    @Override
    public void addMenuItem(JMenuItem i){
        this.add(i);
    }

    @Override
    public void fillMenu(Menuable menu) {
    }
    
    
}
