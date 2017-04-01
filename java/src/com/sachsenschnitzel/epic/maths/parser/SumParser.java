/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.maths.parser;

import com.frequem.epic.mathematics.parser.Parser;
import com.sachsenschnitzel.epic.maths.term.Sum;
import com.sachsenschnitzel.epic.maths.term.Term;
import com.sachsenschnitzel.epic.maths.term.UnfinishedTerm;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class SumParser extends Parser{
    
    @Override
    public Sum tryParse(String data){
        Term[] elements = Parser.separateToUnfinishedBy(data, '+');
        
        Sum s = null;
        if(elements.length > 1){
            s = new Sum(elements);
            s.setCursor(elements.length-1);
        }
        return s;
    }
    
    /*@Override
    public Sum tryParse(Term origin, String op, Term left, Term right){
        if("+".equals(op)){
            Sum s = new Sum(/*origin.getParent(), /left, right);
            left.setParent(s);
            right.setParent(s);
            return s;
        }
        return null;
    }

    @Override
    public Term tryParse(Term origin, String dt) {
        return null;
    }*/
}
