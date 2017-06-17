/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.ine5605.clavicularioeletronico.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Flávio
 */
public class Conversao {
    
    private static Conversao instance;
    
    public static Conversao getInstance() {
        if (instance == null) {
            instance = new Conversao();
        }
        return instance;
    }
    
    private Conversao() {
        
    }
    
    public String dateToStr(Date data) {
        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }
    
    public Date strToDate(String data) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(data);
    }
    
}
