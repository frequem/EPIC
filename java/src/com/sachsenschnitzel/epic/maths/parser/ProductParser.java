/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.maths.parser;

import com.frequem.epic.mathematics.parser.Parser;
import com.sachsenschnitzel.epic.maths.term.Product;
import com.sachsenschnitzel.epic.maths.term.Sum;
import com.sachsenschnitzel.epic.maths.term.Term;
import com.sachsenschnitzel.epic.maths.term.UnfinishedTerm;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class ProductParser extends Parser{
    
    @Override
    public Term tryParse(String data){
        Term[] elements = Parser.separateToUnfinishedBy(data, '*');
        
        Product p = null;
        if(elements.length > 1){
            p = new Product(elements);
            p.setCursor(elements.length-1);
        }
        return p;
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
