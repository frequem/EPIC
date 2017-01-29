/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.item;

import com.frequem.epic.JSpritePanel;
import com.frequem.epic.JSpritePanelComponent;
import com.frequem.epic.action.StrokeChangeAction;
import java.awt.BasicStroke;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Line2D;
import java.util.Map;
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
public class JStrokeChooser extends JSpritePanelComponent implements ItemListener{
    
    private final static Stroke[] STROKES;
    
    static {
        STROKES = new Stroke[5];
        STROKES[0] = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        STROKES[1] = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        STROKES[2] = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        STROKES[3] = new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        STROKES[4] = new BasicStroke(15, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    }
    
    private JComboBox cb;
    
    public JStrokeChooser(JSpritePanel spritePanel) {
        super(spritePanel);
        
        this.setLayout(new BorderLayout());
        
        cb = new JComboBox();
        
        cb.setRenderer(new ListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList jlist, Object e, int i, boolean bln, boolean bln1) {
                return new JStroke(i<0?JStrokeChooser.this.getSpritePanel().getStroke():(Stroke) e);
            }
        });
        
        for(Stroke s : STROKES)
            cb.addItem(s);
        
        cb.addItemListener(this);
        
        this.add(cb);
        
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        this.getSpritePanel().doAction(new StrokeChangeAction(this.getSpritePanel(), (Stroke) ie.getItem()));
    }
    
    private static class JStroke extends JPanel{
        private static final int HEIGHT = 20;
        private Stroke s;
        
        public JStroke(Stroke s){
            this.s = s;
            this.setLayout(new BorderLayout());
            this.setPreferredSize(new Dimension(this.getWidth(), HEIGHT));
        }
        
        @Override
        protected void paintComponent(Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            Stroke str = g2d.getStroke();
            
            g2d.setStroke(s);
            g2d.drawLine(0, HEIGHT/2, this.getWidth(), HEIGHT/2);
            
            g2d.setStroke(str);
        }
    }
    
}
