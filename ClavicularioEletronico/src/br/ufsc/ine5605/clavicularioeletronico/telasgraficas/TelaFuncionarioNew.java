package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.enums.Cargo;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosFuncionario;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Flávio
 */
public class TelaFuncionarioNew extends TelaBaseCadastro<DadosFuncionario> {

    private JLabel lbMatricula;
    private JTextField tfMatricula;
    private JLabel lbNome;
    private JTextField tfNome;
    private JLabel lbCargo;
    private JComboBox cbCargo;
    private JLabel lbNascimento;
    private JTextField tfNascimento;
    private JLabel lbTelefone;
    private JTextField tfTelefone;
    
    public TelaFuncionarioNew() {
        super("Cadastro Funcionário");
        init();
    }
    
    @Override
    protected void init() {
        super.init();
        
        Container container = getContentPane();
        
        GridBagConstraints constraint = new GridBagConstraints();
        
        this.lbMatricula = new JLabel("Matrícula: ");
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 0;
        container.add(this.lbMatricula, constraint);
        
        this.tfMatricula = new JTextField();
        this.tfMatricula.setPreferredSize(new Dimension(130, 20));
        constraint.gridheight = 1;
        constraint.gridwidth = 2;
        constraint.gridx = 1;
        constraint.gridy = 0;
        container.add(this.tfMatricula, constraint);
        
        this.lbNome = new JLabel("Nome: ");
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 1;
        container.add(this.lbNome, constraint);
        
        this.tfNome = new JTextField();
        this.tfNome.setPreferredSize(new Dimension(130, 20));
        constraint.gridheight = 1;
        constraint.gridwidth = 2;
        constraint.gridx = 1;
        constraint.gridy = 1;
        container.add(this.tfNome, constraint);
        
        this.lbCargo = new JLabel("Cargo: ");
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 2;
        container.add(this.lbCargo, constraint);
        
        this.cbCargo = new JComboBox(new String[]{ Cargo.MOTORISTA.descricao, Cargo.DIRETORIA.descricao });
        this.cbCargo.setSelectedIndex(-1);
        constraint.gridheight = 1;
        constraint.gridwidth = 2;
        constraint.gridx = 1;
        constraint.gridy = 2;
        container.add(this.cbCargo, constraint);
        
        this.lbNascimento = new JLabel("Nascimento: ");
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 3;
        container.add(this.lbNascimento, constraint);
        
        this.tfNascimento = new JTextField();
        this.tfNascimento.setPreferredSize(new Dimension(130, 20));
        constraint.gridheight = 1;
        constraint.gridwidth = 2;
        constraint.gridx = 1;
        constraint.gridy = 3;
        container.add(this.tfNascimento, constraint);
        
        this.lbTelefone = new JLabel("Telefone: ");
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = 0;
        constraint.gridy = 4;
        container.add(this.lbTelefone, constraint);
        
        this.tfTelefone = new JTextField();
        this.tfTelefone.setPreferredSize(new Dimension(130, 20));
        constraint.gridheight = 1;
        constraint.gridwidth = 2;
        constraint.gridx = 1;
        constraint.gridy = 4;
        container.add(this.tfTelefone, constraint);
    }

    @Override
    protected Dimension getTamanhoJanela() {
        return new Dimension(300, 200);
    }
    
    @Override
    protected void reset() {
        this.tfMatricula.setText("");
        this.tfNome.setText("");
        this.cbCargo.setSelectedIndex(-1);
        this.tfNascimento.setText("");
        this.tfTelefone.setText("");
    }

    @Override
    protected void setDados(DadosFuncionario dadosFuncionario) {
        this.tfMatricula.setText(String.valueOf(dadosFuncionario.matricula));
        this.tfNome.setText(dadosFuncionario.nome);
        if (dadosFuncionario.cargo != null) {
            this.cbCargo.setSelectedIndex(dadosFuncionario.cargo.ordinal());
        } else {
            this.cbCargo.setSelectedIndex(-1);
        }
        if (dadosFuncionario.nascimento != null) {
            this.tfNascimento.setText(new SimpleDateFormat("dd/MM/yyyy").format(dadosFuncionario.nascimento));
        } else {
            this.tfNascimento.setText("");
        }
        this.tfTelefone.setText(dadosFuncionario.telefone);
    }

    @Override
    public DadosFuncionario getDados() {
        DadosFuncionario funcionario = new DadosFuncionario();
        funcionario.matricula = Integer.valueOf(this.tfMatricula.getText());
        funcionario.nome = this.tfNome.getText();
        if (this.cbCargo.getSelectedIndex() > -1) {
            funcionario.cargo = Cargo.values()[this.cbCargo.getSelectedIndex()];
        }
        try {
            funcionario.nascimento = new SimpleDateFormat("dd/MM/yyyy").parse(this.tfNascimento.getText());
        } catch (ParseException e) {            
        }
        funcionario.telefone = this.tfTelefone.getText();
        return funcionario;
    }

    @Override
    protected int getColunaBtOk() {
        return 0;
    }

    @Override
    protected int getLinhaBtOk() {
        return 5;
    }
    
    
    
}
