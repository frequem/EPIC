/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.maths.parser;

import com.frequem.epic.mathematics.parser.Parser;
import com.sachsenschnitzel.epic.maths.Equation;
import com.sachsenschnitzel.epic.maths.MathObject;
import com.sachsenschnitzel.epic.maths.term.Constant;
import com.sachsenschnitzel.epic.maths.term.Fraction;
import com.sachsenschnitzel.epic.maths.term.Term;

/**
 *
 * @author user
 */
public class EquationParser extends Parser{
    @Override
    public Equation tryParse(String data){
        Term[] elements = Parser.separateToUnfinishedBy(data, '=');
        
        Equation e = null;
        if(elements.length == 2){
            e = new Equation(elements[0], elements[1]);
        
            e.setCursor(1);
        }
        return e;
    }
}
