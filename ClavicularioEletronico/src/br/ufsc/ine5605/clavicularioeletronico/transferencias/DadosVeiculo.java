package br.ufsc.ine5605.clavicularioeletronico.transferencias;

import br.ufsc.ine5605.clavicularioeletronico.entidades.Veiculo;

/**
 * Classe que encapsula os dados a serem enviados pela tela ao controlador
 * (Data Transfer Object)
 * @author Flávio
 */
public class DadosVeiculo {

    public String placa;
    public String modelo;
    public String marca;
    public int ano;
    public int quilometragemAtual;
    
    public DadosVeiculo() {

    }

    public DadosVeiculo(String placa, String modelo, String marca, int ano, int quilometragemAtual) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.quilometragemAtual = quilometragemAtual;
    }
    
}
