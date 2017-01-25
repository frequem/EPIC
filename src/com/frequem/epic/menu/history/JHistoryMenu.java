package com.frequem.epic.menu.history;

import com.frequem.epic.JBarMenu;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.iface.Menuable;

public class JHistoryMenu extends JBarMenu{

    public JHistoryMenu(JSpritePanel spritePanel) {
        super(spritePanel);
    }

    @Override
    public void fillMenu(Menuable menu) {
        menu.addMenuItem(new JHistoryBackMenuItem(getSpritePanel()));
        menu.addMenuItem(new JHistoryForwardMenuItem(getSpritePanel()));
    }
    
}
