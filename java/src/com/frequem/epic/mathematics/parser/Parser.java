/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.mathematics.parser;

import com.sachsenschnitzel.epic.maths.MathObject;
import com.sachsenschnitzel.epic.maths.parser.ConstantParser;
import com.sachsenschnitzel.epic.maths.parser.EncapsulatorParser;
import com.sachsenschnitzel.epic.maths.parser.ParenthesisParser;
import com.sachsenschnitzel.epic.maths.parser.SumParser;
import com.sachsenschnitzel.epic.maths.term.Term;
import com.sachsenschnitzel.epic.maths.term.UnfinishedTerm;
import java.util.ArrayList;

/**
 *
 * @author frequem
 */
public abstract class Parser {
    public static final Parser[] OP_PARSERS = { new SumParser() };
    public static final Parser[] DT_PARSERS = { new ConstantParser() };
    public static final EncapsulatorParser[] EC_PARSERS = { new ParenthesisParser() };
    
    /**
     * Try to parse a certain String.
     * 
     * @param data String to be parsed
     * @return null if String does not represent the suggested Type
     */
    public abstract Term tryParse(String data);
    
    public static String[] separateBy(String data, char op){
        ArrayList<String> strings = new ArrayList<>();
        int i, last;
        for(i = 0, last = 0; i < data.length(); i++) //collect ufts
            if(data.charAt(i) == op && i-last > 0){
                strings.add(data.substring(last, i));
                last = ++i;
            }
        
        if(data.length() > 0 && data.charAt(data.length()-1) == op)
            strings.add("");
        else
            strings.add(data.substring(last, i));
        
        return strings.toArray(new String[0]);
    }
    
    public static Term[] separateToUnfinishedBy(String data, char op){
        ArrayList<Term> ufts = new ArrayList<>();
        int i, last, parCount = 0;
        char c;
        for(i = 0, last = 0; i < data.length(); i++){ //collect ufts
            c = data.charAt(i);
            for(EncapsulatorParser pe : EC_PARSERS){
                if(c == pe.getLeftOp())
                    parCount++;
                else if(c == pe.getRightOp())
                    parCount--;
            }
            if(parCount == 0 && c == op && i-last > 0){
                ufts.add(new UnfinishedTerm(data.substring(last, i)));
                last = ++i;
            }
        }
        
        if(data.length() > 0 && data.charAt(data.length()-1) == op)
            ufts.add(new UnfinishedTerm(""));
        else
            ufts.add(new UnfinishedTerm(data.substring(last, i)));
        
        return ufts.toArray(new Term[0]);
    }
}
