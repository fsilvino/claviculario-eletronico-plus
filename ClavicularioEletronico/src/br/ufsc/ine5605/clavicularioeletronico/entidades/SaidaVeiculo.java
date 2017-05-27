package br.ufsc.ine5605.clavicularioeletronico.entidades;

import java.util.Calendar;

/**
 * Representa o veículo que está fora da empresa e com quem ele
 * está.
 * @author Flávio
 */
public class SaidaVeiculo {
    
    private Veiculo veiculo;
    private Funcionario funcionario;
    private Calendar dataHora;

    public SaidaVeiculo(Veiculo veiculo, Funcionario funcionario, Calendar dataHora) {
        this.veiculo = veiculo;
        this.funcionario = funcionario;
        this.dataHora = dataHora;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Calendar getDataHora() {
        return dataHora;
    }

    public void setDataHora(Calendar dataHora) {
        this.dataHora = dataHora;
    }
}
