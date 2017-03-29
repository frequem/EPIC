/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.maths.term.encap;

import com.sachsenschnitzel.epic.maths.term.Term;

/**
 * An Encapsulator is a Term that uses two operators, one for starting and one for ending an encapsulation.
 * For example Parenthesis is an Encapsulator. This class is necessary, because with it,
 * Parsers can take notice of which content to leave untouched.
 *
 * @author schnitzel
 */
public abstract class Encapsulator extends Term{
    //should be 1 for "(c", 0 for "(c)", -1 for "c)"
    protected int completion;
    
    public int countEncaps(){
        return completion;
    }
}
