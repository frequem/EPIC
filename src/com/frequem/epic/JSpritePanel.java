package com.frequem.epic;

import com.frequem.epic.iface.Action;
import com.frequem.epic.iface.SpriteContainable;
import com.frequem.epic.iface.Modeable;
import com.frequem.epic.iface.Sprite;
import com.frequem.epic.iface.Actionable;
import com.frequem.epic.iface.Colorable;
import com.frequem.epic.iface.Fontable;
import com.frequem.epic.iface.Strokeable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class JSpritePanel extends JPanel implements Colorable, Strokeable, Fontable, SpriteContainable, Modeable, Actionable{
    
    private Mode mode;
    private ArrayList<Sprite> sprites;
    private Color color;
    private Stroke stroke;
    private Font font;
    
    private ArrayList<Action> actions;
    private int actionIndex = 0;
    
    public JSpritePanel(){
        this.setBackground(Color.WHITE);
        this.sprites = new ArrayList<>();
        this.actions = new ArrayList<>();
        this.color = Color.BLACK;
        this.stroke = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        this.font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
        
        this.addFocusListener(new FocusAdapter(){

            @Override
            public void focusLost(FocusEvent fe) {
                JSpritePanel.this.requestFocusInWindow();
            }
        });
        
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Color c = g2.getColor();
        Stroke str = g2.getStroke();
        
        g2.setColor(this.getColor());
        g2.setStroke(this.getStroke());
        
        if(this.mode != null)
            this.mode.paint(g);
        
        sprites.forEach((s)->s.paint(g2));
        
        
        g2.setColor(this.getSelectionColor());
        g2.setStroke(this.getSelectionStroke());
        
        sprites.stream().filter(s->s.isSelected()).forEach(s->{
            Rectangle r = s.getBounds();
            g.drawRect(r.x, r.y, r.width, r.height);
        });
        
        g2.setStroke(str);
        g2.setColor(c);
        
    }
    
    public Color getSelectionColor(){
        return Color.BLUE;
    }
    
    public Stroke getSelectionStroke(){
        return new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
    }
    
    @Override
    public Color getColor(){
        return this.color;
    }
    
    @Override
    public void setColor(Color c){
        this.firePropertyChange("color", this.color, c);
        this.color = c;
    }
    
    
    @Override
    public void setStroke(Stroke s) {
        this.firePropertyChange("stroke", this.stroke, s);
        this.stroke = s;
    }

    @Override
    public Stroke getStroke() {
        return this.stroke;
    }
    
    @Override
    public void setFont(Font f){
        this.firePropertyChange("font", this.font, f);
        this.font = f;
    }
    
    @Override
    public Font getFont(){
        return this.font;
    }
    
    @Override
    public void addSprite(Sprite s){
        this.sprites.add(s);
    }
    
    @Override
    public void removeSprite(Sprite s){
        this.sprites.remove(s);
    }
    
    @Override
    public ArrayList<Sprite> getSprites(){
        return this.sprites;
    }
    
    @Override
    public Mode getMode(){
        return this.mode;
    }
    
    @Override
    public void setMode(Mode mode){
        this.firePropertyChange("mode", this.mode, mode);
        
        this.removeMouseListener(this.mode);
        this.removeMouseMotionListener(this.mode);
        this.removeKeyListener(this.mode);
        
        this.mode = mode;
        
        this.addMouseListener(this.mode);
        this.addMouseMotionListener(this.mode);
        this.addKeyListener(this.mode);
        this.requestFocusInWindow();
        this.repaint();
    }
    
    @Override
    public void doAction(Action a){
        a.act();
        if(this.actions.size() > actionIndex+1)
            this.actions.subList(actionIndex+1, this.actions.size()).clear();
        
        this.actionIndex = this.actions.size();
        this.actions.add(a);
    }
    
    @Override
    public void revertAction(){
        if(actionIndex >= 0 && this.actions.size() >= actionIndex)
            this.actions.get(actionIndex--).revert();
    }
    
    @Override
    public void advanceAction(){
        if(this.actions.size() > actionIndex+1)
            this.actions.get(++actionIndex).act();
    }
    
}
