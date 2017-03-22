package com.frequem.epic.menu.history;

import com.frequem.epic.JMenuItem;
import com.frequem.epic.JSpritePanel;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;

public class JHistoryForwardMenuItem extends JMenuItem{
    
    public JHistoryForwardMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
    }
    
    @Override
    protected String getLabelText(){
        return "Forward";
    }
    
    @Override
    protected ImageIcon getIcon(){
        return new ImageIcon("img" + File.separator + "forward.png");
    }
    
    
    @Override
    public void mouseClicked(MouseEvent me) {
        this.getSpritePanel().advanceAction();
        this.getSpritePanel().repaint();
    }
}
