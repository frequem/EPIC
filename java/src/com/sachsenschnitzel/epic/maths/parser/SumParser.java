/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.maths.parser;

import com.frequem.epic.mathematics.parser.Parser;
import com.sachsenschnitzel.epic.maths.MathObject;
import com.sachsenschnitzel.epic.maths.term.Sum;
import com.sachsenschnitzel.epic.maths.term.Term;
import com.sachsenschnitzel.epic.maths.term.UnfinishedTerm;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class SumParser extends Parser{
    /*public SumParser(){
        op = "+";
    }*/
    
    @Override
    public Sum tryParse(String data){
        /*int offsetDone = offset, end; //keeps track of what to leave untouched
        
        for(end = data.length()-1; end >= 0; end--){ //cut off offset
            if(data.charAt(end) == '+')
                offsetDone--;
            if(offsetDone == 0)
                break;
        }*/
        
        ArrayList<UnfinishedTerm> auft = new ArrayList<UnfinishedTerm>();
        int i, last;
        for(i = 0, last = 0; i < data.length(); i++) //collect ufts
            if(data.charAt(i) == '+' && i-last > 0){
                auft.add(new UnfinishedTerm(data.substring(last, i)));
                //System.out.println("found smth: " + data.substring(last, i));
                last = ++i;
            }
        
        if(data.length() > 0 && data.charAt(data.length()-1) == '+')
            auft.add(new UnfinishedTerm(""));
        else
            auft.add(new UnfinishedTerm(data.substring(last, i)));
        
        Sum s = null;
        if(auft.size() > 1){
            s = new Sum(auft.toArray(new Term[0]));
            s.setCursor(auft.size()-1);
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
