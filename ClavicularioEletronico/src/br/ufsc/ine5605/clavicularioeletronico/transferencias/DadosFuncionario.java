package br.ufsc.ine5605.clavicularioeletronico.transferencias;

import br.ufsc.ine5605.clavicularioeletronico.entidades.Funcionario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Veiculo;
import br.ufsc.ine5605.clavicularioeletronico.enums.Cargo;
import java.util.Date;
import java.util.HashMap;

/**
 * Classe que encapsula os dados a serem enviados pela tela ao controlador
 * (Data Transfer Object)
 * @author Flávio
 */
public class DadosFuncionario {

    public Integer matricula;
    public String nome;
    public Date nascimento;
    public String telefone;
    public Cargo cargo;
    public boolean bloqueado;
    public HashMap<String, DadosVeiculo> veiculos;
    
    public static DadosFuncionario from(Funcionario funcionario) {
        HashMap<String, DadosVeiculo> veiculos = new HashMap<>();
        for (Veiculo veiculo : funcionario.getVeiculos()) {
            DadosVeiculo dadosVeiculo = DadosVeiculo.from(veiculo);
            veiculos.put(dadosVeiculo.placa, dadosVeiculo);
        }
        return new DadosFuncionario(funcionario.getMatricula(), funcionario.getNome(), funcionario.getNascimento(), funcionario.getTelefone(), funcionario.getCargo(), funcionario.isBloqueado(), veiculos);
    }

    public DadosFuncionario() {
        this(null, null, null, null, null, false, null);
    }
    
    public DadosFuncionario(Integer matricula, String nome, Date nascimento, String telefone, Cargo cargo, boolean bloqueado) {
        this(matricula, nome, nascimento, telefone, cargo, bloqueado, null);
    }
    
    public DadosFuncionario(Integer matricula, String nome, Date nascimento, String telefone, Cargo cargo, boolean bloqueado, HashMap<String, DadosVeiculo> veiculos) {
        this.matricula = matricula;
        this.nome = nome;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.cargo = cargo;
        this.bloqueado = bloqueado;
        this.veiculos = veiculos;
    }
    
}