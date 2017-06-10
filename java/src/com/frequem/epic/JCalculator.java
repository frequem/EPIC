package com.frequem.epic;

import com.frequem.epic.mode.DrawMode;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class JCalculator extends JPanel implements AdjustmentListener, MouseListener{
    
    private JSpritePanel panel;
    private JMenuBar bar;
    private JScrollPane panelScrollPane;
    private boolean scrollEndReached = false;
    
    public JCalculator(){
       this.setLayout(new BorderLayout());
        
       this.panel = new JSpritePanel();
       this.panel.setPreferredSize(new Dimension(2000, 2000));
       this.panelScrollPane = new JScrollPane(this.panel);
       this.panelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       this.panelScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       
       this.panelScrollPane.getVerticalScrollBar().addAdjustmentListener(this);
       this.panelScrollPane.getVerticalScrollBar().addMouseListener(this);
       
       this.add(this.panelScrollPane, BorderLayout.CENTER);
       
       this.bar = new JMenuBar(this.panel);
       final JScrollPane menuScroll = new JScrollPane(this.bar);
       menuScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
       menuScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
       this.add(menuScroll, BorderLayout.NORTH);
       
       this.panel.setMode(new DrawMode(this.panel));
       
       this.repaint();
       
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent ae) {
        if(ae.getValueIsAdjusting()){
            int extent = this.panelScrollPane.getVerticalScrollBar().getModel().getExtent();
            if(this.panelScrollPane.getVerticalScrollBar().getValue() + extent >= this.panelScrollPane.getVerticalScrollBar().getMaximum()){
                this.scrollEndReached = true;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {
        if(this.scrollEndReached){
            this.panel.setPreferredSize(new Dimension(this.panel.getWidth(), this.panel.getHeight() + this.panel.getVisibleRect().height));
            this.panel.revalidate();
            this.scrollEndReached = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
}
