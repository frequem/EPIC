package com.frequem.epic;

import com.frequem.epic.menu.edit.JEditMenu;
import com.frequem.epic.menu.file.JFileMenu;
import com.frequem.epic.menu.history.JHistoryMenu;
import com.frequem.epic.menu.mode.JModeMenu;
import com.frequem.epic.menu.style.JStyleMenu;
import java.awt.*;

public class JMenuBar extends JSpritePanelComponent{
    public JMenuBar(JSpritePanel spritePanel){
        super(spritePanel);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        this.add(new JFileMenu(spritePanel));
        this.add(new JHistoryMenu(spritePanel));
        this.add(new JModeMenu(spritePanel));
        this.add(new JEditMenu(spritePanel));
        this.add(new JStyleMenu(spritePanel));
    }
}
