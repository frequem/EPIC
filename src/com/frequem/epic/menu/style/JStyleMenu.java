package com.frequem.epic.menu.style;

import com.frequem.epic.JBarMenu;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.iface.Menuable;

public class JStyleMenu extends JBarMenu{
    public JStyleMenu(JSpritePanel spritePanel){
        super(spritePanel);
    }

    @Override
    public void fillMenu(Menuable menu) {
        menu.addMenuItem(new JStyleColorMenuItem(getSpritePanel()));
        menu.addMenuItem(new JStyleStrokeMenuItem(getSpritePanel()));
    }
}
