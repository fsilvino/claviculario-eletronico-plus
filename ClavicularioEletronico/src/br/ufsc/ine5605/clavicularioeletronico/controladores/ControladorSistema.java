package br.ufsc.ine5605.clavicularioeletronico.controladores;

import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaSistemaNew;

/**
 * Controlador principal do sistema, controla o menu principal e 
 * chama os demais controladores
 * @author Flávio
 */
public class ControladorSistema {

    private static ControladorSistema instance;
    
    private TelaSistemaNew tela;
    
    private ControladorSistema() {
        tela = new TelaSistemaNew();
    }

    public static ControladorSistema getInstance() {
        if (instance == null) {
            instance = new ControladorSistema();
        }
        return instance;
    }

    public void inicia() {
        this.exibeMenuInicial();
    }

    public void exibeMenuInicial() {
        tela.setVisible(true);
    }

    public void abreCadastroVeiculo() {
        ControladorVeiculo.getInstance().inicia();
    }

    public void abreCadastroFuncionario() {
        ControladorFuncionario.getInstance().inicia();
    }

    public void abreRetirarChaveClaviculario() {
        ControladorClaviculario.getInstance().abreTelaRetirarChave();
    }
    
    public void abreDevolverChaveClaviculario() {
        ControladorClaviculario.getInstance().abreTelaDevolverChave();
    }

    public void abreRelatorioCompleto() {
        ControladorClaviculario.getInstance().abreTelaRelatorioCompleto();
    }
    
    public void abreRelatorioEvento() {
        ControladorClaviculario.getInstance().abreTelaRelatorioEvento();
    }

    public void abreRelatorioFuncionario() {
        ControladorClaviculario.getInstance().abreTelaRelatorioFuncionario();
    }

    public void abreRelatorioVeiculo() {
        ControladorClaviculario.getInstance().abreTelaRelatorioVeiculo();
    }

}