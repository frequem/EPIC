/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.menu.file;

import com.frequem.epic.JMenuItem;
import com.frequem.epic.JSpritePanel;
import com.frequem.epic.action.SpriteDeleteAction;
import com.frequem.epic.iface.Sprite;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 *
 * @author user
 */
public class JFileOpenMenuItem extends JMenuItem{
    
    public JFileOpenMenuItem(JSpritePanel panel) {
        super(panel);
    }
    
    @Override
    protected String getLabelText(){
        return "Open File";
    }
    
    @Override
    protected ImageIcon getIcon(){
        return new ImageIcon("img" + File.separator + "open.png");
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
        final JFileChooser fc = new JFileChooser();
        
        int returnVal = fc.showSaveDialog(this.getSpritePanel());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("ok");
            openFromFile(fc.getSelectedFile());
        }
    }
    
    private void openFromFile(File f){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        
        Sprite[] sa = this.getSpritePanel().getSprites().stream().toArray(Sprite[]::new);
        this.getSpritePanel().doAction(new SpriteDeleteAction(this.getSpritePanel(), sa));
        
        try{
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            
            getSpritePanel().setPreferredSize((Dimension)ois.readObject());
            
            int size = ois.readInt();
            for(int i = 0; i < size; i++){
                getSpritePanel().addSprite((Sprite)ois.readObject());
            }
            
            ois.close();
            fis.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        getSpritePanel().repaint();
    }
}
