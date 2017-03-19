/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.mathematics.parser;

import com.sachsenschnitzel.epic.maths.MathObject;
import com.sachsenschnitzel.epic.maths.term.Term;

/**
 *
 * @author frequem
 */
public abstract class Parser {
    /**
     * Try to parse a certain String.
     * 
     * @param data String to be parsed
     * @return null if String does not represent the suggested Type
     */
    public abstract Term tryParse(String data);
}
