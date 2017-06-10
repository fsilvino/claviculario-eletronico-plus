package br.ufsc.ine5605.clavicularioeletronico.controladores;

import br.ufsc.ine5605.clavicularioeletronico.entidades.Funcionario;
import br.ufsc.ine5605.clavicularioeletronico.persistencia.FuncionarioDAO;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaFuncionarioNew;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaTableFuncionario;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosFuncionario;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Flávio
 */
public class ControladorFuncionarioNew extends ControladorCadastroNew<TelaTableFuncionario, TelaFuncionarioNew, FuncionarioDAO, Integer, Funcionario, DadosFuncionario> {

    private static ControladorFuncionarioNew instance;
    
    private ControladorFuncionarioNew() {
        
    }

    @Override
    protected FuncionarioDAO getDao() {
        return FuncionarioDAO.getInstance();
    }
    
    public static ControladorFuncionarioNew getInstance() {
        if (instance == null) {
            instance = new ControladorFuncionarioNew();
        }
        return instance;
    }
    
    @Override
    protected TelaTableFuncionario instanciaTelaTable() {
        return new TelaTableFuncionario();
    }

    @Override
    protected TelaFuncionarioNew instanciaTelaCadastro() {
        return new TelaFuncionarioNew();
    }

    @Override
    protected boolean valida(DadosFuncionario item) throws Exception {
        if (item.nome == null || item.nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe o nome do funcionário!");
            return false;
        }
        return true;
    }

    @Override
    protected void salvaInclusao(DadosFuncionario item) {
        Funcionario funcionario = new Funcionario();
        funcionario.setMatricula(item.matricula);
        funcionario.setNome(item.nome);
        this.getDao().put(funcionario.getMatricula(), funcionario);
        atualizaListaTela();
    }

    @Override
    protected void salvaAlteracao(DadosFuncionario item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<DadosFuncionario> getListaDTO() {
        ArrayList<DadosFuncionario> listaDTO = new ArrayList<>();
        
        for (Funcionario funcionario : this.getDao().getList()) {
            DadosFuncionario dto = new DadosFuncionario();
            dto.matricula = funcionario.getMatricula();
            dto.nome = funcionario.getNome();
            listaDTO.add(dto);
        }
        
        return listaDTO;
    }
    
    
    
}