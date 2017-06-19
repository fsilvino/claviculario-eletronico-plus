package br.ufsc.ine5605.clavicularioeletronico.transferencias;

/**
 *
 * @author Flávio
 */
public class DadosPermissaoUsoVeiculo {
    
    public Integer matriculaFuncionario;
    public String nomeFuncionario;
    public String telefoneFuncionario;
    public String placaVeiculo;
    public String modeloVeiculo;
    public String marcaVeiculo;
    public int anoVeiculo;

    public DadosPermissaoUsoVeiculo() {
        
    }
    
    public DadosPermissaoUsoVeiculo(Integer matriculaFuncionario, String nomeFuncionario, String telefoneFuncionario, String placaVeiculo, String modeloVeiculo, String marcaVeiculo, int anoVeiculo) {
        this.matriculaFuncionario = matriculaFuncionario;
        this.nomeFuncionario = nomeFuncionario;
        this.telefoneFuncionario = telefoneFuncionario;
        this.placaVeiculo = placaVeiculo;
        this.modeloVeiculo = modeloVeiculo;
        this.marcaVeiculo = marcaVeiculo;
        this.anoVeiculo = anoVeiculo;
    }
    
}
