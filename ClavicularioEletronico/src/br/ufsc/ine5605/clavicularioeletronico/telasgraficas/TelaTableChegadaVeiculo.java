/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.controladores.ControladorClaviculario;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosVeiculo;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Luke
 */
public class TelaTableChegadaVeiculo extends JFrame {
    
    private List<DadosVeiculo> lista;
    private JTable table;
    private JScrollPane spTable;
    private ActionManager actManager;
    private JButton btDevolve;
    private JLabel lbQuilometragem;
    private JTextField tfQuilometragem;
    
    public TelaTableChegadaVeiculo() {
        super("Devolver Chave");
        this.lista = new ArrayList<>();
        init();
    }
    
    protected void init() {
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());
        
        this.actManager = new ActionManager();
        
        Dimension tamanhoTela = new Dimension(600, 350);
        
        this.table = new JTable();
        this.table.setPreferredScrollableViewportSize(new Dimension(tamanhoTela.width - 100, tamanhoTela.height - 200));
        this.table.setDefaultEditor(Object.class, null);

        this.table.setFillsViewportHeight(true);
        GridBagConstraints constraint = new GridBagConstraints();
        
        int y = 0;
        
        constraint.fill = GridBagConstraints.CENTER;
        constraint.gridwidth = 4;
        constraint.gridheight = 3;
        constraint.gridx = 0;
        constraint.gridy = y++;
        y += constraint.gridheight - 1;
        
        this.spTable = new JScrollPane(this.table);
        container.add(this.spTable, constraint);
        
        this.lbQuilometragem = new JLabel("Quilometragem atual: ");
        constraint.gridwidth = 1;
        constraint.gridheight = 1;
        constraint.gridx = 0;
        constraint.gridy = y;
        container.add(this.lbQuilometragem, constraint);
        
        this.tfQuilometragem = new JTextField("");
        constraint.gridwidth = 1;
        constraint.gridheight = 1;
        constraint.gridx = 1;
        constraint.gridy = y;
        this.tfQuilometragem.setPreferredSize(new Dimension(130, 20));
        container.add(this.tfQuilometragem, constraint);
        y++;
        
        this.btDevolve = new JButton("Devolver chave");
        this.btDevolve.setActionCommand(AcoesClaviculario.ACAO_OK);
        this.btDevolve.addActionListener(actManager);
        constraint.gridwidth = 1;
        constraint.gridheight = 1;
        constraint.gridx = 0;
        constraint.gridy = y;
        container.add(this.btDevolve, constraint);
        this.setSize(tamanhoTela);
        setLocationRelativeTo(null);
    }
    
    private void atualizaDados() {
        this.table.setModel(this.getTableModel());
        this.repaint();
    }
    
    private TableModel getTableModel() {
        DefaultTableModel tbModel = new DefaultTableModel();
        tbModel.addColumn("Placa");
        tbModel.addColumn("Marca");
        tbModel.addColumn("Modelo");
        tbModel.addColumn("Ano");
        tbModel.addColumn("KM");
        
        for (DadosVeiculo veiculo : this.lista) {
            tbModel.addRow(new Object[] { veiculo.placa, veiculo.marca, veiculo.modelo, veiculo.ano, veiculo.quilometragemAtual });
        }
        
        return tbModel;
    }
    
    public void setLista(List<DadosVeiculo> lista) {
        this.lista = lista;
        this.atualizaDados();
    }
    
    private DadosVeiculo getItemSelecionado() {
        int row = this.table.getSelectedRow();
        if (row > -1) {
            return lista.get(row);
        }
        return null;
    }
    
    public String getPlacaSelecionada() {
        return getItemSelecionado().placa;
    }
    
    public String getQuilometragemAtual() {
        return this.tfQuilometragem.getText();
    }
    
    private static class ActionManager implements ActionListener{

        public ActionManager() {
            
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (AcoesClaviculario.ACAO_OK.equals(e.getActionCommand())) {
                try {
                    ControladorClaviculario.getInstance().devolverVeiculo();
                    JOptionPane.showMessageDialog(null, "Chave devolvida com sucesso!");                  
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }
    }
    
}