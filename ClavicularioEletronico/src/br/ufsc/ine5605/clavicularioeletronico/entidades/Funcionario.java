package br.ufsc.ine5605.clavicularioeletronico.entidades;

import java.util.Date;
import br.ufsc.ine5605.clavicularioeletronico.enums.Cargo;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.ParametroNuloException;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.PermissaoDeUsoVeiculoJaCadastradaException;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosFuncionario;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosVeiculo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Funcionario implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Integer matricula;
    private String nome;
    private Date nascimento;
    private String telefone;
    private boolean bloqueado;
    private Cargo cargo;
    private int numeroTentativasSemPermissao;
    private final HashMap<String, Veiculo> veiculos;
    
    public Funcionario() {
        this.veiculos = new HashMap<>();
    }

    public void incrementaNumeroTentativasSemPermissao() {
        this.numeroTentativasSemPermissao++;
    }

    public void resetNumeroTentativasSemPermissao() {
        this.numeroTentativasSemPermissao = 0;
    }

    public int getNumeroTentativasSemPermissao() {
        return numeroTentativasSemPermissao;
    }
    
    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
    public boolean podeAcessarVeiculo(Veiculo veiculo) {
        if (veiculo != null) {
            return podeAcessarVeiculo(veiculo.getPlaca());
        }
        throw new ParametroNuloException("Veículo");
    }
    
    public boolean podeAcessarVeiculo(String placa) {
        return this.veiculos.get(placa) != null;
    }
    
    public void putVeiculo(Veiculo veiculo) throws PermissaoDeUsoVeiculoJaCadastradaException {
        if (veiculo != null) {
            if (!podeAcessarVeiculo(veiculo.getPlaca())) {
                this.veiculos.put(veiculo.getPlaca(), veiculo);
            } else {
                throw new PermissaoDeUsoVeiculoJaCadastradaException(this, veiculo);
            }
        } else {
            throw new ParametroNuloException("Veículo");
        }
    }
    
    public void removeVeiculo(Veiculo veiculo) {
        if (veiculo != null) {
            removeVeiculo(veiculo.getPlaca());
        } else {
            throw new ParametroNuloException("Veículo");
        }
    }
    
    public void removeVeiculo(String placa) {
        this.veiculos.remove(placa);
    }
    
    public ArrayList<Veiculo> getVeiculos() {
        return new ArrayList<>(this.veiculos.values());
    }
    
    public void limpaVeiculos() {
        this.veiculos.clear();
    }
    
    public DadosFuncionario getDTO() {
        HashMap<String, DadosVeiculo> listaVeiculos = new HashMap<>();
        for (Veiculo veiculo : getVeiculos()) {
            DadosVeiculo dadosVeiculo = veiculo.getDTO();
            listaVeiculos.put(dadosVeiculo.placa, dadosVeiculo);
        }
        return new DadosFuncionario(getMatricula(), getNome(), getNascimento(), getTelefone(), getCargo(), isBloqueado(), listaVeiculos);
    }
    
}