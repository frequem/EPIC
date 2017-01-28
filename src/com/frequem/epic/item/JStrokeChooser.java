/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.item;

import com.frequem.epic.JSpritePanel;
import com.frequem.epic.JSpritePanelComponent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author user
 */
public class JStrokeChooser extends JSpritePanelComponent{
    
    private JComboBox cb;
    
    public JStrokeChooser(JSpritePanel spritePanel) {
        super(spritePanel);
        
        //TODO: set width to parent
        this.setLayout(new BorderLayout());
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        
        cb = new JComboBox();
        
        this.add(cb);
        
    }
    
    
    
    private class ComboBoxStrokeRenderer implements ListCellRenderer{

        public ComboBoxStrokeRenderer(){
        }
        
        @Override
        public Component getListCellRendererComponent(JList jlist, Object e, int i, boolean bln, boolean bln1) {
            return new JStrokePanel(JStrokeChooser.this.getSpritePanel().getStroke());
        }
        
    }
    
    private class JStrokePanel extends JPanel{
        private final static int HEIGHT = 16;
        private final static int WIDTH = 100;
        private Shape s;
        public JStrokePanel(Stroke str){
            this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            s = str.createStrokedShape(new Line2D.Float(0, HEIGHT/2, WIDTH, HEIGHT/2));
        }
        
        @Override
        protected void paintComponent(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            g2d.fill(s);
        }
    }
    
    
}
