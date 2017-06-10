package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosFuncionario;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * 
 * @author Flávio
 */
public class TelaTableFuncionario extends TelaBaseTable<DadosFuncionario> {
    
    
    public TelaTableFuncionario() {
        super("Cadastro de Funcionários");
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
            tbModel.addRow(new Object[] { funcionario.getMatricula(), funcionario.getNome(), funcionario.getTelefone() });
        }
        
        return tbModel;
    }
    
}