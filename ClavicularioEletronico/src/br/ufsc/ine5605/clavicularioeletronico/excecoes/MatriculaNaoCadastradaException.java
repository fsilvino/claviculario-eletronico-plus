package br.ufsc.ine5605.clavicularioeletronico.excecoes;

import java.security.InvalidParameterException;

/**
 * Exceção gerada ao pesquisar/utilizar uma matrícula que não está cadastrada
 * @author Flávio
 */
public class MatriculaNaoCadastradaException extends InvalidParameterException {

    public MatriculaNaoCadastradaException(int matricula) {
        this("Nenhum funcionario cadastrado com a matricula: " + matricula);
    }

    public MatriculaNaoCadastradaException(String msg) {
        super(msg);
    }

}
