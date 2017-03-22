/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.iface;

import com.frequem.epic.JMenuItem;

/**
 *
 * @author user
 */
public interface Menuable {
    public void addMenuItem(JMenuItem i);
    public void fillMenu(Menuable menu);
}
