/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosPermissaoUsoVeiculo;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Flávio
 */
public class TelaPermissaoUsoVeiculoNew  extends TelaBaseCadastro<DadosPermissaoUsoVeiculo> {
    
    private JLabel lbFuncionario;
    private JTextField tfFuncionario;
    private JLabel lbVeiculo;
    private JComboBox cbVeiculos;
    private String[] placas;
    private DadosPermissaoUsoVeiculo permissao;
    
    public TelaPermissaoUsoVeiculoNew() {
        super("Permissão de Uso de Veículo");
        this.placas = new String[0];
        init();
    }
    
    public void setPlacas(String[] placas) {
        this.placas = placas;
        if (this.cbVeiculos != null) {
            this.cbVeiculos.removeAllItems();
            if (placas != null) {
                for (String placa : placas) {
                    this.cbVeiculos.addItem(placa);
                }
            }
            this.cbVeiculos.setSelectedIndex(-1);
            repaint();
        }
    }
    
    public void abreInclusao(DadosPermissaoUsoVeiculo item) {
        this.acao = AcoesCadastro.ACAO_INCLUI;
        setDados(item);
        setVisible(true);
    }

    @Override
    protected void init() {
        super.init();
        
        Container container = getContentPane();
        
        GridBagConstraints constraint = new GridBagConstraints();
        
        this.lbFuncionario = new JLabel("Funcionário: ");
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 0;
        container.add(this.lbFuncionario, constraint);
        
        this.tfFuncionario = new JTextField();
        this.tfFuncionario.setPreferredSize(new Dimension(130, 20));
        this.tfFuncionario.setEnabled(false);
        constraint.gridheight = 1;
        constraint.gridwidth = 2;
        constraint.gridx = 1;
        constraint.gridy = 0;
        container.add(this.tfFuncionario, constraint);
        
        this.lbVeiculo = new JLabel("Veículo: ");
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 1;
        container.add(this.lbVeiculo, constraint);
        
        this.cbVeiculos = new JComboBox(placas);
        this.cbVeiculos.setSelectedIndex(-1);
        constraint.gridheight = 1;
        constraint.gridwidth = 2;
        constraint.gridx = 1;
        constraint.gridy = 1;
        container.add(this.cbVeiculos, constraint);
    }
    
    @Override
    protected int getColunaBtOk() {
        return 0;
    }

    @Override
    protected int getLinhaBtOk() {
        return 2;
    }

    @Override
    protected Dimension getTamanhoJanela() {
        return new Dimension(500, 400);
    }

    @Override
    protected void reset() {
        this.cbVeiculos.setSelectedIndex(-1);
    }

    @Override
    protected void setDados(DadosPermissaoUsoVeiculo item) {
        this.permissao = item;
        this.tfFuncionario.setText(item.nomeFuncionario);
        if (item.placaVeiculo != null) {
            this.cbVeiculos.setSelectedIndex(Arrays.binarySearch(placas, item.placaVeiculo));
        } else {
            this.cbVeiculos.setSelectedIndex(-1);
        }
    }

    @Override
    public DadosPermissaoUsoVeiculo getDados() {
        if (this.cbVeiculos.getSelectedIndex() > -1) {
            permissao.placaVeiculo = this.placas[this.cbVeiculos.getSelectedIndex()];
        }
        return permissao;
    }
    
}
