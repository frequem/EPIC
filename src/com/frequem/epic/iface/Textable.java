/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.iface;

/**
 *
 * @author user
 */
public interface Textable {
    public void putText(String s);
    public void backspace(); //deletes the last character, or whatever is selected
    public void deleteText(); //deletes whatever is selected
}
