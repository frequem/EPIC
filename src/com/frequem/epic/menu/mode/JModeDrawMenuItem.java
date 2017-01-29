package com.frequem.epic.menu.mode;

import com.frequem.epic.JSpritePanel;
import com.frequem.epic.Mode;
import com.frequem.epic.mode.DrawMode;
import java.io.File;
import javax.swing.ImageIcon;

public class JModeDrawMenuItem extends JModeMenuItem{
    
    public JModeDrawMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
    }
    
    @Override
    protected String getLabelText(){
        return "Draw Mode";
    }
    
    @Override
    protected ImageIcon getIcon(){
        return new ImageIcon("img" + File.separator + "pencil.png");
    }
    
    @Override
    protected Mode getMode() {
        return new DrawMode(this.getSpritePanel());
    }
    
}
