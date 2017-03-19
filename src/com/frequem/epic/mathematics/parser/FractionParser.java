/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.mathematics.parser;

import com.sachsenschnitzel.epic.maths.term.Fraction;
import com.sachsenschnitzel.epic.maths.term.Term;

/**
 *
 * @author user
 */
public class FractionParser extends Parser{
    @Override
    public Fraction tryParse(String data){
        return null;
    }
    
    /*@Override
    public Fraction tryParse(Term origin, String op, Term left, Term right){
        if("/".equals(op)){
            Fraction d = new Fraction(origin.getParent());
            left.setParent(d);
            right.setParent(d);
            
            d.setT1(left);
            d.setT2(right);
            return d;
        }
        return null;
    }

    @Override
    public Term tryParse(Term origin, String dt) {
        return null;
    }*/
}
