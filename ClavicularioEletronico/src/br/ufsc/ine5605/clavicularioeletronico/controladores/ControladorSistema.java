package br.ufsc.ine5605.clavicularioeletronico.controladores;

import br.ufsc.ine5605.clavicularioeletronico.telas.TelaSistema;

/**
 * Controlador principal do sistema, controla o menu principal e 
 * chama os demais controladores
 * @author Flávio
 */
public class ControladorSistema {

    private static ControladorSistema instance;
    
    private TelaSistema tela;
    
    private ControladorSistema() {
        tela = new TelaSistema();
    }

    public static ControladorSistema getInstance() {
        if (instance == null) {
            instance = new ControladorSistema();
        }
        return instance;
    }

    public void inicia() {
        abreCadastroFuncionario();
        //this.exibeMenuInicial();
    }

    public void exibeMenuInicial() {
        tela.exibeMenu();
    }

    public void abreCadastroVeiculo() {
        ControladorVeiculo.getInstance().inicia();
    }

    public void abreCadastroFuncionario() {
        ControladorFuncionarioNew.getInstance().inicia();
    }

    public void abreClaviculario() {
        ControladorClaviculario.getInstance().inicia();
    }

}