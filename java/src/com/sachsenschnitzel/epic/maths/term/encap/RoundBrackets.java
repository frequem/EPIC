/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.maths.term.encap;

import com.sachsenschnitzel.epic.maths.term.Term;

/**
 *
 * @author schnitzel
 */
public class RoundBrackets extends Encapsulator{
    public RoundBrackets(Term content){
        super(content);
        l = "(";
        r = ")";
    }
    
    @Override
    public RoundBrackets getInstance(Term content){
        return new RoundBrackets(content);
    }
    
    @Override
    public RoundBrackets derive(String var){
        //(c)' = (c')          ;)
        return new RoundBrackets(c.derive(var));
    }
}
