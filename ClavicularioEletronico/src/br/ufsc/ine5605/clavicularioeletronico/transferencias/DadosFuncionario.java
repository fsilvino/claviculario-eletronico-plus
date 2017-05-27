package br.ufsc.ine5605.clavicularioeletronico.transferencias;

import br.ufsc.ine5605.clavicularioeletronico.enums.Cargo;
import java.util.Date;

/**
 * Classe que encapsula os dados a serem enviados pela tela ao controlador
 * (Data Transfer Object)
 * @author Flávio
 */
public class DadosFuncionario {

    private int matricula;
    private String nome;
    private Date nascimento;
    private String telefone;
    private Cargo cargo;
    private boolean bloqueado;

    public DadosFuncionario() {
        
    }
    
    public DadosFuncionario(int matricula, String nome, Date nascimento, String telefone, Cargo cargo, boolean bloqueado) {
        this.matricula = matricula;
        this.nome = nome;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.cargo = cargo;
        this.bloqueado = bloqueado;
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

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

}