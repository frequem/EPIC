/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.maths.parser;

import com.frequem.epic.mathematics.parser.Parser;
import com.sachsenschnitzel.epic.maths.term.UnfinishedTerm;
import com.sachsenschnitzel.epic.maths.term.encap.Parenthesis;

/**
 *
 * @author schnitzel
 */
public class ParenthesisParser extends EncapsulatorParser{
    public ParenthesisParser(){
        opL = '(';
        opR = ')';
    }
    
    @Override
    public Parenthesis tryParse(String data){
        if(data.length() > 0 && data.charAt(0) == opL && data.charAt(data.length()-1) == opR)
            return new Parenthesis(new UnfinishedTerm(data.substring(1, data.length()-1)));
        System.out.println("");
        return null;
    }
}
