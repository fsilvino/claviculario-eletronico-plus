package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosFuncionario;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
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
    }

    @Override
    protected Dimension getTamanhoJanela() {
        return new Dimension(300, 200);
    }
    
    @Override
    protected void reset() {
        this.tfMatricula.setText("");
        this.tfNome.setText("");
    }

    @Override
    protected void setDados(DadosFuncionario item) {
        this.tfMatricula.setText(String.valueOf(item.matricula));
        this.tfNome.setText(item.nome);
    }

    @Override
    public DadosFuncionario getDados() {
        DadosFuncionario funcionario = new DadosFuncionario();
        funcionario.matricula = Integer.valueOf(this.tfMatricula.getText());
        funcionario.nome = this.tfNome.getText();
        return funcionario;
    }

    @Override
    protected int getColunaBtOk() {
        return 0;
    }

    @Override
    protected int getLinhaBtOk() {
        return 2;
    }
    
    
    
}
