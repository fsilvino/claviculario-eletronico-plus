package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosVeiculo;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Flávio
 */
public class TelaVeiculoNew extends TelaBaseCadastro<DadosVeiculo> {

    private JLabel lbPlaca;
    private JFormattedTextField tfPlaca;
    private JLabel lbModelo;
    private JTextField tfModelo;
    private JLabel lbMarca;
    private JTextField tfMarca;
    private JLabel lbAno;
    private JFormattedTextField tfAno;
    private JLabel lbQuilometragemAtual;
    private JTextField tfQuilometragemAtual;
    
    public TelaVeiculoNew() {
        super("Cadastro Veículo");
        init();
    }
    
    @Override
    protected void init() {
        super.init();
        
        Container container = getContentPane();
        GridBagConstraints constraint = new GridBagConstraints();
        
        
        MaskFormatter mascaraPlaca = null;
        MaskFormatter mascaraAno = null;
        try{
                
            mascaraPlaca = new MaskFormatter("UUU-####");
            mascaraAno = new MaskFormatter("####");


        } catch(ParseException excp) {
                System.err.println("Erro na formatação da mascara: " + excp.getMessage());
        }
        
        this.lbPlaca = new JLabel("Placa: ");
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 0;
        container.add(this.lbPlaca, constraint);
        
        this.tfPlaca = new JFormattedTextField(mascaraPlaca);
        this.tfPlaca.setPreferredSize(new Dimension(130, 20));
        constraint.gridheight = 1;
        constraint.gridwidth = 2;
        constraint.gridx = 1;
        constraint.gridy = 0;
        container.add(this.tfPlaca, constraint);
        
        this.lbModelo = new JLabel("Modelo: ");
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 1;
        container.add(this.lbModelo, constraint);
        
        this.tfModelo = new JTextField();
        this.tfModelo.setPreferredSize(new Dimension(130, 20));
        constraint.gridheight = 1;
        constraint.gridwidth = 2;
        constraint.gridx = 1;
        constraint.gridy = 1;
        container.add(this.tfModelo, constraint);
        
        this.lbMarca = new JLabel("Marca: ");
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 2;
        container.add(this.lbMarca, constraint);
        
        this.tfMarca = new JTextField();
        this.tfMarca.setPreferredSize(new Dimension(130, 20));
        constraint.gridheight = 1;
        constraint.gridwidth = 2;
        constraint.gridx = 1;
        constraint.gridy = 2;
        container.add(this.tfMarca, constraint);
        
        this.lbAno = new JLabel("Ano: ");
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 3;
        container.add(this.lbAno, constraint);
        
        this.tfAno = new JFormattedTextField(mascaraAno);
        this.tfAno.setPreferredSize(new Dimension(130, 20));
        constraint.gridheight = 1;
        constraint.gridwidth = 2;
        constraint.gridx = 1;
        constraint.gridy = 3;
        container.add(this.tfAno, constraint);
        
        this.lbQuilometragemAtual = new JLabel("Quilometragem Atual: ");
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 4;
        container.add(this.lbQuilometragemAtual, constraint);
        
        this.tfQuilometragemAtual = new JTextField();
        this.tfQuilometragemAtual.setPreferredSize(new Dimension(130, 20));
        constraint.gridheight = 1;
        constraint.gridwidth = 2;
        constraint.gridx = 1;
        constraint.gridy = 4;
        container.add(this.tfQuilometragemAtual, constraint);
    }

    @Override
    protected Dimension getTamanhoJanela() {
        return new Dimension(300, 200);
    }
    
    @Override
    protected void reset() {
        this.tfPlaca.setText("");
        this.tfModelo.setText("");
        this.tfMarca.setText("");
        this.tfAno.setText("");
        this.tfQuilometragemAtual.setText("");
    }

    @Override
    protected void setDados(DadosVeiculo dadosVeiculo) {
        this.tfPlaca.setText(dadosVeiculo.placa);
        this.tfModelo.setText(dadosVeiculo.modelo);
        this.tfMarca.setText(dadosVeiculo.marca);
        this.tfAno.setText(String.valueOf(dadosVeiculo.ano));
        this.tfQuilometragemAtual.setText(String.valueOf(dadosVeiculo.quilometragemAtual));
    }

    @Override
    public DadosVeiculo getDados() {
        int ano = 0;
        try {
            ano = Integer.parseInt(this.tfAno.getText());
        } catch (Exception e) {
        }
        int quilometragemAtual = 0;
        try {
            quilometragemAtual = Integer.parseInt(this.tfQuilometragemAtual.getText());
        } catch (Exception e) {
        }
        return new DadosVeiculo(this.tfPlaca.getText(), this.tfModelo.getText(), this.tfMarca.getText(), ano, quilometragemAtual);
    }

    @Override
    protected int getColunaBtOk() {
        return 0;
    }

    @Override
    protected int getLinhaBtOk() {
        return 5;
    }

    @Override
    public void abreAlteracao(DadosVeiculo item) {
        this.tfPlaca.setEnabled(false);
        this.tfQuilometragemAtual.setEnabled(false);
        super.abreAlteracao(item);
    }

    @Override
    public void abreInclusao() {
        this.tfPlaca.setEnabled(true);
        this.tfQuilometragemAtual.setEnabled(true);
        super.abreInclusao();
    }
    
}