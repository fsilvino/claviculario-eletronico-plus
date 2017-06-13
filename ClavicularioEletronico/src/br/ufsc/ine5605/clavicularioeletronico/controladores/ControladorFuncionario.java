package br.ufsc.ine5605.clavicularioeletronico.controladores;

import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosFuncionario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Funcionario;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.MatriculaJaCadastradaException;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.MatriculaNaoCadastradaException;
import br.ufsc.ine5605.clavicularioeletronico.persistencia.FuncionarioDAO;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaFuncionarioNew;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaTableFuncionario;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Responsável pelo controle de cadastros dos funcionários
 * @author Flávio
 */
public class ControladorFuncionario extends ControladorCadastroNew<TelaTableFuncionario, TelaFuncionarioNew, FuncionarioDAO, Integer, Funcionario, DadosFuncionario> {

    private static ControladorFuncionario instance;

    private ControladorFuncionario() {
        super();
    }

    public static ControladorFuncionario getInstance() {
        if (instance == null) {
            instance = new ControladorFuncionario();
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
    protected boolean valida(DadosFuncionario dadosFuncionario) throws Exception {
        if (dadosFuncionario == null) {
            throw new InvalidParameterException("Dados invalidos. Parametro nulo.");
        }
        
        if (dadosFuncionario.matricula == null || dadosFuncionario.matricula <= 0) {
            throw new Exception("O numero de matricula deve ser preenchido com um numero maior do que zero!");
        }
        
        if (dadosFuncionario.nome == null || dadosFuncionario.nome.trim().isEmpty()) {
            throw new Exception("O nome do funcionario eh obrigatorio!");
        }
        
        if (dadosFuncionario.cargo == null) {
            throw new Exception("O cargo do funcionario eh obrigatorio!");
        }
        
        return true;
    }

    @Override
    protected void salvaInclusao(DadosFuncionario dadosFuncionario) {
        try {
            if (valida(dadosFuncionario)) {
                if (findFuncionarioPelaMatricula(dadosFuncionario.matricula) == null) {
                    Funcionario funcionario = new Funcionario();
                    copiaDadosParaFuncionario(dadosFuncionario, funcionario);
                    getDao().put(funcionario.getMatricula(), funcionario);
                } else {
                    throw new MatriculaJaCadastradaException(dadosFuncionario.matricula);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(telaCad, e.getMessage());
        }
    }

    @Override
    protected void salvaAlteracao(DadosFuncionario dadosFuncionario) {
        if (!funcionarioExiste(dadosFuncionario.matricula)) {
            throw new MatriculaNaoCadastradaException(dadosFuncionario.matricula);
        }
        Funcionario funcionario = findFuncionarioPelaMatricula(dadosFuncionario.matricula);
        copiaDadosParaFuncionario(dadosFuncionario, funcionario);
        getDao().put(funcionario.getMatricula(), funcionario);
    }

    @Override
    protected List<DadosFuncionario> getListaDTO() {
        ArrayList<DadosFuncionario> lista = new ArrayList<>();
        for (Funcionario funcionario : getDao().getList()) {
            lista.add(new DadosFuncionario(funcionario.getMatricula(), 
                                           funcionario.getNome(), 
                                           funcionario.getNascimento(),
                                           funcionario.getTelefone(), 
                                           funcionario.getCargo(), 
                                           funcionario.isBloqueado()));
        }
        return lista;
    }

    @Override
    protected FuncionarioDAO getDao() {
        return FuncionarioDAO.getInstance();
    }

    @Override
    protected void executaExclusao(DadosFuncionario dadosFuncionario) {
        try {
            Funcionario funcionario = findFuncionarioPelaMatricula(dadosFuncionario.matricula);
            if (funcionario == null) {
                throw new MatriculaNaoCadastradaException(dadosFuncionario.matricula);
            }

            if (ControladorClaviculario.getInstance().funcionarioEstaUtilizandoAlgumVeiculo(dadosFuncionario.matricula)) {
                throw new Exception("Este funcionario possui chave(s) a ser(em) devolvida(s).\n" +
                                    "Nao sera possivel excluir ate que todas sejam devolvidas.");
            }
            ControladorPermissaoUsoVeiculo.getInstance().excluirPermissoesFuncionario(funcionario);
            getDao().remove(dadosFuncionario.matricula);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**
     * Pesquisa o funcionário pela matrícula
     * @param matricula Matrícula a ser pesquisada
     * @return Objeto do funcionário encontrado ou null caso não exista a matrícula
     */
    private Funcionario findFuncionarioPelaMatricula(Integer matricula) {
        return getDao().get(matricula);
    }

    /**
     * Copia os dados do funcionário fornecidos pela tela para o objeto funcionário que será salvo
     * @param dadosFuncionario Dados do funcionário passados pela tela
     * @param funcionario Objeto funcionário que será salvo
     */
    private void copiaDadosParaFuncionario(DadosFuncionario dadosFuncionario, Funcionario funcionario) {
        funcionario.setMatricula(dadosFuncionario.matricula);
        funcionario.setNome(dadosFuncionario.nome);
        funcionario.setNascimento(dadosFuncionario.nascimento);
        funcionario.setTelefone(dadosFuncionario.telefone);
        funcionario.setBloqueado(dadosFuncionario.bloqueado);
        funcionario.setCargo(dadosFuncionario.cargo);
    }

    /**
     * Pesquisa o funcionário pela matrícula
     * @param matricula Matrícula a ser pesquisada
     * @return Retorna o objeto do funcionário se encontrar pela matrículaa
     * @throws MatriculaNaoCadastradaException Caso não exista a matrícula cadastrada
     */
    public Funcionario getFuncionarioPelaMatricula(Integer matricula) throws MatriculaNaoCadastradaException {
        Funcionario funcionario = findFuncionarioPelaMatricula(matricula);
        if (funcionario == null) {
            throw new MatriculaNaoCadastradaException(matricula);
        }
        return funcionario;
    }
    
     /**
     * Verifica se o funcionário existe
     * @param matricula Matrícula a ser pesquisada
     * @return true se o funcionário existe, false se não foi encontrado
     */
    public boolean funcionarioExiste(Integer matricula) {
        return findFuncionarioPelaMatricula(matricula) != null;
    }

    public void abreCadastroPermissaoUsoVeiculo(Integer matricula) {
        ControladorPermissaoUsoVeiculo.getInstance().inicia();
    }
    
}
