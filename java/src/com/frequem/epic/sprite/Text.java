/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.sprite;

import com.frequem.epic.iface.Colorable;
import com.frequem.epic.iface.Cursorable;
import com.frequem.epic.iface.Fontable;
import com.frequem.epic.iface.Textable;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.io.Serializable;

/**
 *
 * @author user
 */
public class Text extends BasicSprite implements Serializable, Textable, Cursorable, Fontable, Colorable{
    
    private String text = null;
    private Font font;
    private Color color;
    
    private int cursorIndex, selectionLength;
    
    public Text(){
        text = "";
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        Font f = g.getFont();
        Color c = g.getColor();
        Stroke s = g2d.getStroke();
        
        if(font == null)
            font = f;
        else
            g.setFont(font);
        
        
        if(color == null)
            color = c;
        else
            g.setColor(color);
        
        this.setHeight(g.getFontMetrics().getHeight());
        
        if(text != null){
            this.setWidth(g.getFontMetrics().stringWidth(text));
            g.drawString(text, getX(), getY() + getHeight());
        }
        
        g.setColor(c);
        g.setFont(f);
        g2d.setStroke(s);
        
    }

    @Override
    public void putText(String s) {
        if(this.selectionLength != 0)
            removeSelection();
        
        StringBuilder sb = new StringBuilder(text);
        sb.insert(cursorIndex, s);
        text = sb.toString();
        
        this.cursorIndex += s.length();
    }

    @Override
    public void backspace() {
        if(this.selectionLength != 0){
            this.removeSelection();
            return;
        }
        if(this.cursorIndex > 0){
            StringBuilder sb = new StringBuilder(text);
            sb.deleteCharAt(--cursorIndex);
            text = sb.toString();
        }
    }

    @Override
    public void deleteText() {
        if(this.selectionLength != 0){
            this.removeSelection();
            return;
        }
        if(this.cursorIndex < text.length()){
            StringBuilder sb = new StringBuilder(text);
            sb.deleteCharAt(cursorIndex);
            text = sb.toString();
        }
    }
    
    private void removeSelection(){
        StringBuilder sb = new StringBuilder(text);
        sb.delete(Math.min(cursorIndex, cursorIndex + selectionLength), Math.max(cursorIndex, cursorIndex + selectionLength));
        text = sb.toString();
        
        if(selectionLength < 0)
            cursorIndex += selectionLength;
        selectionLength = 0;
    }
    
    @Override
    public void setCursor(int x, int y) {
        x -= this.getX();
        y -= this.getY();
        selectionLength = 0;
        cursorIndex = findCursorSnapPos(x, y);
    }
    
    private int findCursorSnapPos(int x, int y){
        x = Math.max(0, Math.min(x, this.getWidth()-1));
        Canvas c = new Canvas();
        FontMetrics fm = c.getFontMetrics(font);
        char[] atext = text.toCharArray();
        
        int l = 0; 
        int h = atext.length;
        int m, w;
        do{
            m = (l+h)/2;
            w = fm.charsWidth(atext, 0, m);
            if(Math.abs(x-w) <= fm.charWidth(atext[x<w?m-1:m])/2) break;
            else if(x < w) h = m-1;
            else if(x > w) l = m+1;
        }while(true);
        return m;
    }

    @Override
    public void cursorDragged(int x, int y) {
        x -= this.getX();
        y -= this.getY();
        int p = findCursorSnapPos(x, y);
        this.selectionLength = p - cursorIndex;
    }
    
    @Override
    public void setFont(Font f) {
        this.font = f;
    }

    @Override
    public Font getFont() {
        return this.font;
    }

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void moveCursor(int direction) {}

    @Override
    public void paintCursor(Graphics g) {
        Color c = g.getColor();
        
        if(font == null)
            font = g.getFont();
        else
            g.setFont(font);
        
        g.setColor(Color.BLUE);
        FontMetrics fm = g.getFontMetrics(font);
        int cursorX = fm.stringWidth(text.substring(0, cursorIndex));
        int selX = fm.stringWidth(text.substring(0, cursorIndex + selectionLength));
        g.fillRect(getX() + Math.min(cursorX, selX), getY(), Math.max(cursorX - selX, selX - cursorX) + 1, getHeight());
        
        g.setColor(c);
    }

}
