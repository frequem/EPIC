package com.frequem.epic.menu.mode;

import com.frequem.epic.JBarMenu;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.iface.Menuable;
import com.sachsenschnitzel.epic.menu.mode.JModeMathMenuItem;

public class JModeMenu extends JBarMenu{

    public JModeMenu(JSpritePanel panel) {
        super(panel);
    }

    @Override
    public void fillMenu(Menuable menu) {
        menu.addMenuItem(new JModeDrawMenuItem(getSpritePanel()));
        menu.addMenuItem(new JModeTextMenuItem(getSpritePanel()));
        menu.addMenuItem(new JModeMathMenuItem(getSpritePanel())); //originally: Text
        menu.addMenuItem(new JModeSelectMenuItem(getSpritePanel()));
        menu.addMenuItem(new JModeMoveMenuItem(getSpritePanel()));
    }
    
}
