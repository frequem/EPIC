package com.frequem.epic.menu.style;

import com.frequem.epic.JDialogMenuItem;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.item.JFontPicker;
import java.awt.Container;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;

public class JStyleFontMenuItem extends JDialogMenuItem implements PropertyChangeListener{

    public JStyleFontMenuItem(JSpritePanel spritePanel) {
        super(spritePanel);
    }

    private static ImageIcon getFontIcon(Font f){
        return null;
    }
    
    @Override
    protected Container getContainer() {
        return new JFontPicker(this.getSpritePanel());
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if(pce.getPropertyName().equals("font")){
            this.setIcon(getFontIcon((Font) pce.getNewValue()));
        }
    }
    
}
