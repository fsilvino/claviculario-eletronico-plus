package br.ufsc.ine5605.clavicularioeletronico.transferencias;

/**
 * Classe que encapsula os dados a serem enviados pela tela ao controlador
 * (Data Transfer Object)
 * @author Flávio
 */
public class DadosVeiculo {

    private String placa;
    private String modelo;
    private String marca;
    private int ano;
    private int quilometragemAtual;

    public DadosVeiculo() {

    }

    public DadosVeiculo(String placa, String modelo, String marca, int ano, int quilometragemAtual) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.quilometragemAtual = quilometragemAtual;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getQuilometragemAtual() {
        return quilometragemAtual;
    }

    public void setQuilometragemAtual(int quilometragemAtual) {
        this.quilometragemAtual = quilometragemAtual;
    }

}
