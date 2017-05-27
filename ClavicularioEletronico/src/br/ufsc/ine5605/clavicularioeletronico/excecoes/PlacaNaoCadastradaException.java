package br.ufsc.ine5605.clavicularioeletronico.excecoes;

import java.security.InvalidParameterException;

/**
 * Exceção gerada ao pesquisar/utilizar uma placa que não está cadastrada
 * @author Flávio
 */
public class PlacaNaoCadastradaException extends InvalidParameterException {

    public PlacaNaoCadastradaException(String placa) {
        super("Nenhum veiculo cadastrado com a placa: " + placa);
    }

}
