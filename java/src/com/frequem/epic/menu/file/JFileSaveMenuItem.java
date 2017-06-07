/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.file;

import com.frequem.epic.JMenuItem;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.iface.Sprite;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

/**
 *
 * @author user
 */
public class JFileSaveMenuItem extends JMenuItem{
    
    public JFileSaveMenuItem(JSpritePanel panel) {
        super(panel);
    }
    
    @Override
    protected String getLabelText(){
        return "Save File";
    }
    
    @Override
    protected ImageIcon getIcon(){
        return new ImageIcon("img" + File.separator + "floppy.png");
    }
    
    
    @Override
    public void mouseClicked(MouseEvent me) {
        final JFileChooser fc = new JFileChooser();
        
        int returnVal = fc.showSaveDialog(this.getSpritePanel());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            saveToFile(fc.getSelectedFile());
        }
    }
    
    private void saveToFile(File f){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        
        try{
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            
            oos.writeObject(getSpritePanel().getPreferredSize());
            
            ArrayList<Sprite> sprites = getSpritePanel().getSprites();
            oos.writeInt(sprites.size());
            for(Sprite s : sprites){
                oos.writeObject(s);
            }
            
            fos.close();
            oos.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
