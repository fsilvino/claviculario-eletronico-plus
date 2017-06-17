package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosFuncionario;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosVeiculo;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Flávio
 */
public class TelaTablePermissaoUsoVeiculo extends TelaBaseTable<DadosVeiculo> {
    
    private DadosFuncionario dadosFuncionario;
    private JLabel lbFuncionario;

    public TelaTablePermissaoUsoVeiculo() {
        super("Permissões de Uso de Veículos pelo Funcionário");
    }
    
    public void setDadosFuncionario(DadosFuncionario dadosFuncionario) {
        this.dadosFuncionario = dadosFuncionario;
        this.lbFuncionario.setText("Permissões do Funcionário: " + dadosFuncionario.nome);
        setLista(new ArrayList<>(dadosFuncionario.veiculos.values()));
    }
    
    public DadosFuncionario getDadosFuncionario() {
        return dadosFuncionario;
    }
    
    @Override
    protected void init() {
        super.init();
        
        Container container = getContentPane();
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.LINE_START;
        
        this.lbFuncionario = new JLabel();
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.gridwidth = 5;
        constraint.gridheight = 1;
        container.add(this.lbFuncionario, constraint);
        
        this.btAltera.setVisible(false);
    }
    
    @Override
    protected Dimension getTamanhoTela() {
        return new Dimension(500, 300);
    }
    
    @Override
    protected int getTableGridY() {
        return 1;
    }

    @Override
    protected TableModel getTableModel() {
        DefaultTableModel tbModel = new DefaultTableModel();
        tbModel.addColumn("Placa");
        tbModel.addColumn("Marca");
        tbModel.addColumn("Modelo");
        tbModel.addColumn("Ano");
        
        for (DadosVeiculo veiculo : this.lista) {
            tbModel.addRow(new Object[] { veiculo.placa, veiculo.marca, veiculo.modelo, veiculo.ano });
        }
        
        return tbModel;
    }
    
    
    
}
