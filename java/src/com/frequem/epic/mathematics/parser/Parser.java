/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frequem.epic.mathematics.parser;

import com.sachsenschnitzel.epic.maths.MathObject;
import com.sachsenschnitzel.epic.maths.parser.ConstantParser;
import com.sachsenschnitzel.epic.maths.parser.EncapsulatorParser;
import com.sachsenschnitzel.epic.maths.parser.EquationParser;
import com.sachsenschnitzel.epic.maths.parser.ProductParser;
//import com.sachsenschnitzel.epic.maths.parser.ParenthesisParser;
import com.sachsenschnitzel.epic.maths.parser.SumParser;
import com.sachsenschnitzel.epic.maths.parser.FractionParser;
import com.sachsenschnitzel.epic.maths.term.Term;
import com.sachsenschnitzel.epic.maths.term.UnfinishedTerm;
import com.sachsenschnitzel.epic.maths.term.encap.RoundBrackets;
import java.util.ArrayList;

/**
 *
 * @author frequem
 */
public abstract class Parser {
    public static final Parser[] OP_PARSERS = { new EquationParser(), new SumParser(), new ProductParser(),
                                                new FractionParser() };
    public static final Parser[] DT_PARSERS = { new ConstantParser() };
    public static final EncapsulatorParser[] EC_PARSERS = { new EncapsulatorParser<RoundBrackets>(new RoundBrackets(null)) };
    
    /**
     * Try to parse a certain String.
     * 
     * @param data String to be parsed
     * @return null if String does not represent the suggested Type
     */
    public abstract MathObject tryParse(String data);
    
    public static MathObject tryParse(String data, int offset){
        MathObject mo;
        
        for(Parser po : Parser.OP_PARSERS){
            mo = po.tryParse(data);
            if(mo != null){
                mo.parseContent(offset);
                return mo;
            }
        }
        if(offset == 0){
            for(Parser pe : Parser.EC_PARSERS){
                mo = pe.tryParse(data);
                if(mo != null){
                    mo.parseContent(0);
                    return mo;
                }
            }
            for(Parser pd : Parser.DT_PARSERS){
                mo = pd.tryParse(data);
                if(mo != null){
                    return mo;
                }
            }
        }
        
        return null;
    }
    
    public static Term[] separateToUnfinishedBy(String data, char op){
        ArrayList<Term> ufts = new ArrayList<>();
        int i, last, parCount = 0;
        String sub;
        for(i = 0, last = 0; i < data.length(); i++){ //collect ufts
            sub = data.substring(i);
            for(EncapsulatorParser pe : EC_PARSERS){
                if(sub.startsWith(pe.getLeftOp())){
                    parCount++;
                    i += pe.getLeftOp().length()-1;
                }else if(sub.startsWith(pe.getRightOp())){
                    parCount--;
                    i += pe.getRightOp().length()-1;
                }
            }
            if(parCount == 0 && data.charAt(i) == op && i-last > 0){
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
