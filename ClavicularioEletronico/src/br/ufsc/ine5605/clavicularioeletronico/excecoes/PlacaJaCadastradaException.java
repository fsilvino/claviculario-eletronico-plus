/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.ine5605.clavicularioeletronico.excecoes;

import java.security.InvalidParameterException;

/**
 * Exceção gerada ao tentar incluir uma placa já cadastrada
 * @author Gabriel
 */
public class PlacaJaCadastradaException extends InvalidParameterException{

    public PlacaJaCadastradaException(String placa) {
        super("Ja existe veiculo cadastrado com a placa: " + placa);
    }
}