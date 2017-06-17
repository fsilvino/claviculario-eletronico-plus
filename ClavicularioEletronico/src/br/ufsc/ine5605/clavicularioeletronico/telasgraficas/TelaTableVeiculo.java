package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosVeiculo;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * 
 * @author Flávio
 */
public class TelaTableVeiculo extends TelaBaseTable<DadosVeiculo> {
    
    
    public TelaTableVeiculo() {
        super("Cadastro de Veículos");
    }
    
    @Override
    protected Dimension getTamanhoTela() {
        return new Dimension(600, 350);
    }

    @Override
    protected int getTableGridY() {
        return 0;
    }
    
    @Override
    protected TableModel getTableModel() {
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
    
}