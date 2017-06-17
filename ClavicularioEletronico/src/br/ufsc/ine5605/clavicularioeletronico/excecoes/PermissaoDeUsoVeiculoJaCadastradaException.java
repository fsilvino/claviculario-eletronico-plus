package br.ufsc.ine5605.clavicularioeletronico.excecoes;

import br.ufsc.ine5605.clavicularioeletronico.entidades.Funcionario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Veiculo;

/**
 *
 * @author Flávio
 */
public class PermissaoDeUsoVeiculoJaCadastradaException extends Exception {
    
    public PermissaoDeUsoVeiculoJaCadastradaException(Funcionario funcionario, Veiculo veiculo) {
        super(String.format("Esta permissão já está cadastrada!\nFuncionário: %s\nVeículo: %s", funcionario.getNome(), veiculo.getPlaca()));
    }
    
}
