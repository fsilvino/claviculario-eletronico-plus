/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.controladores.ControladorClaviculario;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Luke
 */
public class TelaRetirarChave extends TelaClaviculario {
    
    public TelaRetirarChave() {
        super("Retirar chave");
        init();
    }
    
    @Override
    protected void init() {        
        super.init();
        this.btAcao.setActionCommand(AcoesClaviculario.ACAO_RETIRA);
        this.btAcao.addActionListener(actManager);
    }

    @Override
    public String getInput() {
        return this.tfMatricula.getText();
    }
    
}
