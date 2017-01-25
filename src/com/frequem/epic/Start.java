package com.frequem.epic;

import java.awt.Container;
import javax.swing.JFrame;

public class Start {
    public static void main(String[] args){
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = f.getContentPane();
        JCalculator calc = new JCalculator();
        c.add(calc);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        f.setUndecorated(true);
        f.setVisible(true);
    }
}
