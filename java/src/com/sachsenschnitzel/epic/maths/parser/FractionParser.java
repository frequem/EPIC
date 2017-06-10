/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.maths.parser;

import com.frequem.epic.mathematics.parser.Parser;
import com.sachsenschnitzel.epic.maths.term.Fraction;
import com.sachsenschnitzel.epic.maths.term.Product;
import com.sachsenschnitzel.epic.maths.term.Sum;
import com.sachsenschnitzel.epic.maths.term.Term;
import com.sachsenschnitzel.epic.maths.term.UnfinishedTerm;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class FractionParser extends Parser{
    @Override
    public Term tryParse(String data){
        Term[] elements = Parser.separateToUnfinishedBy(data, '/');
        //System.out.println(elements.length);
        Fraction f = null;
        if(elements.length > 1){
            f = new Fraction(elements[0], elements[1]);
        
            for(int i = 2; i < elements.length; i++)
                f = new Fraction(f, elements[i]);
            
            f.setCursor(1);
        }
        return f;
    }
}
