package br.ufsc.ine5605.clavicularioeletronico.entidades;

import java.util.Date;
import br.ufsc.ine5605.clavicularioeletronico.enums.Cargo;

public class Funcionario {

    private int matricula;
    private String nome;
    private Date nascimento;
    private String telefone;
    private boolean bloqueado;
    private Cargo cargo;
    private int numeroTentativasSemPermissao;

    public void incrementaNumeroTentativasSemPermissao() {
        this.numeroTentativasSemPermissao++;
    }

    public void resetNumeroTentativasSemPermissao() {
        this.numeroTentativasSemPermissao = 0;
    }

    public int getNumeroTentativasSemPermissao() {
        return numeroTentativasSemPermissao;
    }
    
    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
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

}