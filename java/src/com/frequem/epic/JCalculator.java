package com.frequem.epic;

import com.frequem.epic.mode.DrawMode;
import com.sachsenschnitzel.epic.maths.Equation;
import com.sachsenschnitzel.epic.maths.term.*;
import com.sachsenschnitzel.epic.maths.term.encap.Parenthesis;
import java.awt.*;
import javax.swing.*;

public class JCalculator extends JPanel{
    
    private JSpritePanel panel;
    private JMenuBar bar;
    
    public JCalculator(){
       this.setLayout(new BorderLayout());
        
       JScrollPane scroll;
       this.panel = new JSpritePanel();
       scroll = new JScrollPane(this.panel);
       scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       this.add(scroll, BorderLayout.CENTER);
       
       this.bar = new JMenuBar(this.panel);
       scroll = new JScrollPane(this.bar);
       scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
       scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
       this.add(scroll, BorderLayout.NORTH);
       
       this.panel.setMode(new DrawMode(this.panel));
       
       this.repaint();
       
    }
}
