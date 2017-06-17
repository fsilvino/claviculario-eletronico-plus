/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.ine5605.clavicularioeletronico.excecoes;

/**
 *
 * @author Flávio
 */
public class ParametroNuloException extends IllegalArgumentException {
    
    public ParametroNuloException(String nomeParametro) {
        super("Parâmetro " + nomeParametro + " não pode ser nulo!");
    }
    
}
