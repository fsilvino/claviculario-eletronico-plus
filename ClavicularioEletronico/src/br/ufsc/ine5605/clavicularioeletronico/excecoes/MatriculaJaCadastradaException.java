/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.ine5605.clavicularioeletronico.excecoes;

import java.security.InvalidParameterException;

/**
 * Exceção gerada ao tentar incluir uma matrícula que já está cadastrada
 * @author Gabriel
 */
public class MatriculaJaCadastradaException extends InvalidParameterException {

    public MatriculaJaCadastradaException(int matricula) {
       super("Ja existe funcionario cadastrado com a matricula: " + matricula);
    }
}
