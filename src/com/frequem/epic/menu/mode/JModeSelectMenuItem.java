package com.frequem.epic.menu.mode;

import com.frequem.epic.JSpritePanel;
import com.frequem.epic.mode.SelectMode;
import java.io.File;
import javax.swing.ImageIcon;

public class JModeSelectMenuItem extends JModeMenuItem{
    
    public JModeSelectMenuItem(JSpritePanel panel) {
        super(panel);
    }
    
    @Override
    protected String getLabelText(){
        return "Selection Mode";
    }
    
    @Override
    protected ImageIcon getIcon(){
        return new ImageIcon("img" + File.separator + "select.png");
    }

    @Override
    protected Class getModeClass() {
        return SelectMode.class;
    }

}
