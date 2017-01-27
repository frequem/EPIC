package com.frequem.epic.dialog;

import com.frequem.epic.JSpritePanel;
import com.frequem.epic.JSpritePanelComponent;
import com.frequem.epic.action.ColorChangeAction;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JPanel;

public class JColorPicker extends JSpritePanelComponent implements MouseListener, MouseMotionListener, ActionListener{
    private final static int PICKERSIZE = 200;
    private final static int PICKERRINGSIZE = 10;
    private final static int SLIDERWIDTH = 30;
    
    private final JPicker picker;
    private final JSlider slider;
    private final JButton btnOk;
    
    public JColorPicker(JSpritePanel spritePanel){
        super(spritePanel);
        
        this.picker = new JPicker();
        this.slider = new JSlider();
        this.btnOk = new JButton("OK");
        
        this.setLayout(new BorderLayout());
        this.add(picker, BorderLayout.CENTER);
        this.add(slider, BorderLayout.EAST);
        this.add(btnOk, BorderLayout.SOUTH);
        
        btnOk.addActionListener(this);
    }
    
    public Color getColor(){
        return picker.getHSBColor().getColor();
    }
    
    public static Color getContrastColor(Color color) {
        double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
        return y >= 128 ? Color.black : Color.white;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        this.mouseDragged(me);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        this.mouseDragged(me);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        this.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        if(me.getSource().equals(picker)){
            picker.setPickerLocation(me.getX(), me.getY());
            this.repaint();
        } else if(me.getSource().equals(slider)){
            slider.setYSlider(me.getY());
            picker.setSaturation(1-(float)slider.getYSlider()/PICKERSIZE);
            ((JPanel)me.getSource()).repaint();
        }
        this.btnOk.setEnabled(true);
    }

    @Override
    public void mouseMoved(MouseEvent me) {
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        this.btnOk.setEnabled(false);
        this.getSpritePanel().doAction(new ColorChangeAction(this.getSpritePanel(), this.getColor()));
    }
    
    private class JPicker extends JPanel{
        
        private float saturation;
        
        private int xPicker, yPicker;
        
        private BufferedImage buffer;
        private boolean needsRedraw;
        
        public JPicker(){
            this.setPreferredSize(new Dimension(PICKERSIZE, PICKERSIZE));
            this.saturation = 1f;
            buffer = new BufferedImage(PICKERSIZE, PICKERSIZE, BufferedImage.TYPE_INT_ARGB);
            needsRedraw = true;
            this.addMouseListener(JColorPicker.this);
            this.addMouseMotionListener(JColorPicker.this);
        }
        
        public void setSaturation(float s){
            this.needsRedraw = true;
            this.saturation = s;
        }
        
        public float getSaturation(){
            return this.saturation;
        }
        
        public void setPickerLocation(int x, int y){
            this.xPicker = Math.max(0, Math.min(x, PICKERSIZE));
            this.yPicker = Math.max(0, Math.min(y, PICKERSIZE));
        }
        
        public HSBColor getHSBColor(){
            return new HSBColor((float)xPicker/PICKERSIZE, this.saturation, 1-(float)yPicker/PICKERSIZE);
        }
         
        @Override
        protected void paintComponent(Graphics g){
           super.paintComponent(g);
           
           if(needsRedraw){
               needsRedraw = false;
               Graphics2D g2dbuf = this.buffer.createGraphics();
               final Color[] colors = new Color[2];
               GradientPaint gp;
               Shape rect;
               
               for(int i=0; i<360;i++){
                   colors[0] = Color.getHSBColor((float)i/360, this.saturation, 1f);
                   colors[1] = Color.getHSBColor((float)i/360, this.saturation, 0f);
                   
                   gp = new GradientPaint(0, 0, colors[0], 0, PICKERSIZE, colors[1]);
                   g2dbuf.setPaint(gp);
                   
                   rect = new Rectangle2D.Float(i*(float)PICKERSIZE/360, 0f, (float)PICKERSIZE/360, (float)PICKERSIZE);
                   g2dbuf.draw(rect);
               }
           }
            
           
           Color c = g.getColor();
           
           g.drawImage(buffer, 0, 0, null);
           g.setColor(getContrastColor(getHSBColor().getColor()));
           g.drawOval(-PICKERRINGSIZE/2+xPicker, -PICKERRINGSIZE/2+yPicker, PICKERRINGSIZE, PICKERRINGSIZE);
           
           g.setColor(c);
            
        }
    }
    
    private class JSlider extends JPanel{
        private int ySlider;
        
        
        public JSlider(){
            this.setPreferredSize(new Dimension(SLIDERWIDTH, PICKERSIZE));
            this.ySlider = 0;
            this.addMouseListener(JColorPicker.this);
            this.addMouseMotionListener(JColorPicker.this);
        }
        
        public void setYSlider(int y){
            this.ySlider = Math.max(0, Math.min(y, PICKERSIZE));
        }
        
        public int getYSlider(){
            return this.ySlider;
        }
        
        @Override
        protected void paintComponent(Graphics g){
           super.paintComponent(g);
           Graphics2D g2d = (Graphics2D) g;
           
           Color c = g2d.getColor();
           Paint p = g2d.getPaint();
           
           HSBColor hsb = picker.getHSBColor();
           hsb.sat = 1f;
           
           final Color[] colors = new Color[2];
           
           colors[0] = hsb.getColor();
           colors[1] = Color.WHITE;
           
           GradientPaint gp = new GradientPaint(0, 0, colors[0], 0, PICKERSIZE, colors[1]);
           
           g2d.setPaint(gp);
           g2d.fillRect(0, 0, SLIDERWIDTH, PICKERSIZE);
           
           g2d.setPaint(p);
           hsb = picker.getHSBColor();
           hsb.bri = (float)ySlider/PICKERSIZE;
           g2d.setColor(getContrastColor(hsb.getColor()));
           
           g2d.drawLine(0, ySlider, SLIDERWIDTH, ySlider);
           
           g2d.setColor(c);
        }
    }
    
    private static class HSBColor{
        public float hue, sat, bri;
        
        public HSBColor(float hue, float sat, float bri){
            this.hue = hue;
            this.sat = sat;
            this.bri = bri;
        }
        
        public Color getColor(){
            return Color.getHSBColor(hue, sat, bri);
        }
    }
}
