package br.ufsc.ine5605.clavicularioeletronico.entidades;

import java.security.InvalidParameterException;

/**
 * Define a permissão de uso de um veículo por um funcionário
 * @author Flávio
 */
public class PermissaoUsoVeiculo {
    
    private Funcionario funcionario;
    private Veiculo veiculo;
    
    public PermissaoUsoVeiculo(Funcionario funcionario, Veiculo veiculo) {
        
        if (funcionario == null) {
            throw new InvalidParameterException("Parametro funcionario nao pode ser nulo!");
        }
        
        if (veiculo == null) {
            throw new InvalidParameterException("Parametro veiculo nao pode ser nulo!");
        }
        
        this.funcionario = funcionario;
        this.veiculo = veiculo;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }
    
}