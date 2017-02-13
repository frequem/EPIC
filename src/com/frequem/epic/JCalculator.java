package com.frequem.epic;

import com.frequem.epic.mode.DrawMode;
import com.sachsenschnitzel.epic.maths.Constant;
import com.sachsenschnitzel.epic.maths.Fraction;
import com.sachsenschnitzel.epic.maths.Product;
import com.sachsenschnitzel.epic.maths.Sum;
import com.sachsenschnitzel.epic.maths.Term;
import com.sachsenschnitzel.epic.maths.Variable;
import java.awt.*;
import javax.swing.*;

public class JCalculator extends JPanel{
    
    private JSpritePanel panel;
    private JMenuBar bar;
    
    //ss:
    //private Term functionTerm;
    
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
       
       /*//sachsenschnitzel:
       functionTerm = new Sum(
				new Product(
						new Fraction(
                                                        new Constant(-5),
                                                        new Constant(2)),
						new Variable("x")),
				new Constant(3));
       panel.addSprite(functionTerm);
       this.repaint();*/
    }
    
    //sachsenschnitzel:
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        /*g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        functionTerm.optStructure(g);*/
    }
}
