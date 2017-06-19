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
public abstract class TelaClaviculario extends JFrame {
    
    protected JLabel lbMatricula;
    protected JTextField tfMatricula;
    protected JButton btAcao;
    protected JButton btCancela;
    protected ActionManager actManager;
    
    public TelaClaviculario(String titulo) {
        super(titulo);
    }
    
    protected void init() {
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());
        this.actManager = new ActionManager();
        GridBagConstraints constraint = new GridBagConstraints();
        this.lbMatricula = new JLabel("Matrícula: ");
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 0;
        container.add(this.lbMatricula, constraint);
        
        this.tfMatricula = new JTextField();
        this.tfMatricula.setPreferredSize(new Dimension(133, 20));
        constraint.gridheight = 1;
        constraint.gridwidth = 2;
        constraint.gridx = 1;
        constraint.gridy = 0;
        container.add(this.tfMatricula, constraint);
        
        this.btAcao = new JButton("OK");
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = 1;
        constraint.gridy = 1;
        container.add(this.btAcao, constraint);
        
        this.btCancela = new JButton("Cancela");
        this.btCancela.setActionCommand(AcoesClaviculario.ACAO_CANCELA);
        this.btCancela.addActionListener(actManager);
        constraint.gridx++;
        container.add(this.btCancela, constraint);
        
        setSize(new Dimension(300, 150));
        setLocationRelativeTo(null);
    }
    
    public void fechaTela() {
        setVisible(false);
    }
    
    public abstract String getInput();

    private class ActionManager implements ActionListener{

        public ActionManager() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (AcoesClaviculario.ACAO_DEVOLVE.equals(e.getActionCommand())) {
                try {                  
                    ControladorClaviculario.getInstance().abreTabelaDevolverChave();
                    fechaTela();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else if (AcoesClaviculario.ACAO_RETIRA.equals(e.getActionCommand())) {
                try {                  
                    ControladorClaviculario.getInstance().abreTabelaRetirarChave();
                    fechaTela();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } else if (AcoesCadastro.ACAO_CANCELA.equals(e.getActionCommand())) {
               fechaTela();
            }
        }
    }  
}
