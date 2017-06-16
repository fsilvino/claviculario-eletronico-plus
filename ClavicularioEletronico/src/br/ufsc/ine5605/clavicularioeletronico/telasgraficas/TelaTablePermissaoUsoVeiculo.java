package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosPermissaoUsoVeiculo;
import java.awt.Dimension;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Flávio
 */
public class TelaTablePermissaoUsoVeiculo extends TelaBaseTable<DadosPermissaoUsoVeiculo> {
    
    private Integer matriculaFuncionario;
    private String nomeFuncionario;

    public TelaTablePermissaoUsoVeiculo() {
        super("Permissões de Uso de Veículos pelo Funcionário");
    }
    
    public void setFuncionario(Integer matriculaFuncionario, String nomeFuncionario) {
        this.matriculaFuncionario = matriculaFuncionario;
        this.nomeFuncionario = nomeFuncionario;
    }
    
    public DadosPermissaoUsoVeiculo getDadosComFuncionarioParaIncluir() {
        DadosPermissaoUsoVeiculo dados = new DadosPermissaoUsoVeiculo();
        dados.matriculaFuncionario = this.matriculaFuncionario;
        dados.nomeFuncionario = this.nomeFuncionario;
        return dados;
    }
    
    @Override
    protected void init() {
        super.init();
        
        this.btAltera.setVisible(false);
    }
    
    @Override
    protected Dimension getTamanhoTela() {
        return new Dimension(600, 350);
    }

    @Override
    protected TableModel getTableModel() {
        DefaultTableModel tbModel = new DefaultTableModel();
        tbModel.addColumn("Matrícula");
        tbModel.addColumn("Nome");
        tbModel.addColumn("Telefone");
        tbModel.addColumn("Placa");
        tbModel.addColumn("Modelo");
        tbModel.addColumn("Marca");
        tbModel.addColumn("Ano");
        
        for (DadosPermissaoUsoVeiculo permissao : this.lista) {
            tbModel.addRow(new Object[] { permissao.matriculaFuncionario, permissao.nomeFuncionario, permissao.telefoneFuncionario,
                                          permissao.placaVeiculo, permissao.modeloVeiculo, permissao.marcaVeiculo, permissao.anoVeiculo });
        }
        
        return tbModel;
    }
    
    
    
}
