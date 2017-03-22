package com.frequem.epic.menu.history;

import com.frequem.epic.JMenuItem;
import com.frequem.epic.JSpritePanel;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;

public class JHistoryBackMenuItem extends JMenuItem{
    
    public JHistoryBackMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
    }
    
    @Override
    protected String getLabelText(){
        return "Back";
    }
    
    @Override
    protected ImageIcon getIcon(){
        return new ImageIcon("img" + File.separator + "back.png");
    }
    
    
    @Override
    public void mouseClicked(MouseEvent me) {
        this.getSpritePanel().revertAction();
        this.getSpritePanel().repaint();
    }
    
}
