package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.controladores.ControladorFuncionario;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosFuncionario;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * 
 * @author Flávio
 */
public class TelaTableFuncionario extends TelaBaseTable<DadosFuncionario> {
    
    private JButton btPermissoes;
    private ActionManagerFuncionario actManagerFunc;
    
    
    public TelaTableFuncionario() {
        super("Cadastro de Funcionários");
    }
    
    @Override
    protected void init() {
        super.init();
        
        actManagerFunc = new ActionManagerFuncionario();
        
        Container container = getContentPane();
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.CENTER;
        
        this.btPermissoes = new JButton("Permissões");
        this.btPermissoes.setActionCommand(AcoesCadastroFuncionario.ACAO_PERMISSOES_FUNCIONARIO);
        this.btPermissoes.addActionListener(actManagerFunc);
        constraint.gridwidth = 1;
        constraint.gridheight = 1;
        constraint.gridx = 3;
        constraint.gridy = 4;
        container.add(this.btPermissoes, constraint);
    }
    
    @Override
    protected Dimension getTamanhoTela() {
        return new Dimension(500, 350);
    }

    @Override
    protected TableModel getTableModel() {
        DefaultTableModel tbModel = new DefaultTableModel();
        tbModel.addColumn("Matrícula");
        tbModel.addColumn("Nome");
        tbModel.addColumn("Telefone");
        
        for (DadosFuncionario funcionario : this.lista) {
            tbModel.addRow(new Object[] { funcionario.matricula, funcionario.nome, funcionario.telefone });
        }
        
        return tbModel;
    }
    
    private void abrePermissoesFuncionario() {
        DadosFuncionario dadosFuncionario = this.getItemSelecionado();
        if (dadosFuncionario != null) {
            ControladorFuncionario.getInstance().abreCadastroPermissaoUsoVeiculo(dadosFuncionario.matricula);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado!");
        }
    }
    
    private class ActionManagerFuncionario implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (AcoesCadastroFuncionario.ACAO_PERMISSOES_FUNCIONARIO.equals(e.getActionCommand())) {
                abrePermissoesFuncionario();
            }
        }
        
    }
    
    private class AcoesCadastroFuncionario {
        
        public static final String ACAO_PERMISSOES_FUNCIONARIO = "ACAO_PERMISSOES_FUNCIONARIO";
                
    }
    
}