/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.maths.parser;

import com.frequem.epic.mathematics.parser.Parser;
import com.sachsenschnitzel.epic.maths.MathObject;
import com.sachsenschnitzel.epic.maths.term.Constant;
import com.sachsenschnitzel.epic.maths.term.Term;

/**
 *
 * @author user
 */
public class ConstantParser extends Parser{
    @Override
    public Constant tryParse(String data){
        //System.out.println("constantinopolis");
        try{
            System.out.println("UnfinishedT -> Constant: " + data);
            return new Constant(Double.parseDouble(data));
        }catch(NumberFormatException nfe){
            return null;
        }
    }

    /*@Override
    public Term tryParse(Term origin, String op, Term left, Term right) { return null; }

    @Override
    public Constant tryParse(Term origin, String dt) {
        double val;
        
        try{
            val = Double.valueOf(dt);
        }catch(NumberFormatException nfe){
            return null;
        }
        
        return new Constant(/*origin.getParent(), /val);
    }*/
}
