/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.maths.parser;

import com.frequem.epic.mathematics.parser.Parser;
import com.sachsenschnitzel.epic.maths.term.Term;
import com.sachsenschnitzel.epic.maths.term.UnfinishedTerm;
import com.sachsenschnitzel.epic.maths.term.encap.Encapsulator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author schnitzel
 */
public class EncapsulatorParser<T extends Encapsulator> extends Parser{
    private String opL, opR;
    private T t;
    
    public EncapsulatorParser(T enc){
        opL = enc.getL();
        opR = enc.getR();
        t = enc;
    }
    
    public String getLeftOp(){ return opL; }
    public String getRightOp(){ return opR; }
    
    @Override
    public Term tryParse(String data){
        if(data.length() > 0 && data.startsWith(opL) && data.endsWith(opR))
                return (T) t.getInstance(new UnfinishedTerm(
                        data.substring(opL.length(), data.length()-opR.length())));
        return null;
    }
}
