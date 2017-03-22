/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sachsenschnitzel.epic.maths.parser;

import com.frequem.epic.mathematics.parser.Parser;

/**
 *
 * @author schnitzel
 */
public abstract class EncapsulatorParser extends Parser{
    protected char opL;
    protected char opR;
    
    public char getLeftOp(){
        return opL;
    }
    
    public char getRightOp(){
        return opR;
    }
}
