package br.ufsc.ine5605.clavicularioeletronico.controladores;

import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosFuncionario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Funcionario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Veiculo;
import br.ufsc.ine5605.clavicularioeletronico.enums.Cargo;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.CadastroInvalidoPermissaoUsoVeiculoDiretoria;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.MatriculaJaCadastradaException;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.MatriculaNaoCadastradaException;
import br.ufsc.ine5605.clavicularioeletronico.persistencia.FuncionarioDAO;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.AcoesCadastro;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.ITelaBaseCadastroObserver;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.ITelaBaseTableObserver;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaFuncionarioNew;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaPermissaoUsoVeiculoNew;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaTableFuncionario;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaTablePermissaoUsoVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosPermissaoUsoVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosVeiculo;
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
    private final TelaTablePermissaoUsoVeiculo telaTablePermissoes;
    private final TelaPermissaoUsoVeiculoNew telaPermissao;
    private final CadastroPermissoesObserver observerPermissoes;

    private ControladorFuncionario() {
        super();
        this.observerPermissoes = new CadastroPermissoesObserver();
        
        this.telaTablePermissoes = new TelaTablePermissaoUsoVeiculo();
        this.telaTablePermissoes.addObserver(this.observerPermissoes);
        
        this.telaPermissao = new TelaPermissaoUsoVeiculoNew();
        this.telaPermissao.addObserver(this.observerPermissoes);
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
            lista.add(funcionario.getDTO());
        }
        return lista;
    }

    @Override
    protected FuncionarioDAO getDao() {
        return FuncionarioDAO.getInstance();
    }

    @Override
    protected String getMensagemConfirmacaoExclusao(DadosFuncionario dadosFuncionario) {
        return super.getMensagemConfirmacaoExclusao(dadosFuncionario) + 
               "\nMatrícula: " + dadosFuncionario.matricula + 
               "\nNome: " + dadosFuncionario.nome;
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
            funcionario.limpaVeiculos();
            getDao().remove(funcionario.getMatricula());
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
        try {
            Funcionario funcionario = getFuncionarioPelaMatricula(matricula);
            if (funcionario.getCargo() == Cargo.DIRETORIA) {
                throw new CadastroInvalidoPermissaoUsoVeiculoDiretoria();
            }
            this.telaTablePermissoes.setDadosFuncionario(funcionario.getDTO());
            this.telaTablePermissoes.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    /**** Permissões de Uso de Veículos ****/
    
    /**
     * Inclui a permissão de uso a um veículo para um funcionário pela 
     * matrícula e placa
     * @param matricula Matrícula do funcionário que terá permissão de uso
     * @param placa Placa do veículo que será permitido
     * @throws Exception Caso os dados sejam inválidos ou segundo a regra de negócio
     * não seja possível incluir esta permissão
     */
    public void incluiPermissao(Integer matricula, String placa) throws Exception {
        Funcionario funcionario = getFuncionarioPelaMatricula(matricula);
        if (funcionario.getCargo() == Cargo.DIRETORIA) {
            throw new CadastroInvalidoPermissaoUsoVeiculoDiretoria();
        }
        
        Veiculo veiculo = ControladorVeiculo.getInstance().getVeiculoPelaPlaca(placa);
        funcionario.putVeiculo(veiculo);
        getDao().persist();
    }
    
    /**
     * Remove a permissão de uso de um veículo de um funcionário pela matrícula e placa
     * @param matricula Matrícula do funcionário que terá a permissão removida
     * @param placa Placa do veículo que terá o uso negado ao funcionário
     * @throws Exception Caso os parâmtros sejam inválidos ou não exista a permissão
     */
    public void excluiPermissao(Integer matricula, String placa) throws Exception {
        Funcionario funcionario = getFuncionarioPelaMatricula(matricula);
        if (funcionario.getCargo() == Cargo.DIRETORIA) {
            throw new CadastroInvalidoPermissaoUsoVeiculoDiretoria();
        }
        funcionario.removeVeiculo(placa);
        getDao().persist();
    }
    
    public List<Veiculo> getPermissoesFuncionario(Integer matricula) throws Exception {
        Funcionario funcionario = getFuncionarioPelaMatricula(matricula);
        
        if (funcionario.getCargo() == Cargo.DIRETORIA) {
            throw new CadastroInvalidoPermissaoUsoVeiculoDiretoria();
        }
        
        return funcionario.getVeiculos();
    }
    
    public List<DadosVeiculo> getListaPermissoes(Integer matricula) throws Exception {
        Funcionario funcionario = getFuncionarioPelaMatricula(matricula);
        List<DadosVeiculo> lista = new ArrayList<>();
        for (Veiculo veiculo : funcionario.getVeiculos()) {
            lista.add(veiculo.getDTO());
        }
        return lista;
    }
    
    public void excluirPermissoesVeiculo(Veiculo veiculo) {
        for (Funcionario funcionario : getDao().getList()) {
            funcionario.removeVeiculo(veiculo);
        }
    }
    
    private class CadastroPermissoesObserver implements ITelaBaseTableObserver<DadosVeiculo>, ITelaBaseCadastroObserver {

        private DadosPermissaoUsoVeiculo getDadosPermissao(DadosFuncionario dadosFuncionario, DadosVeiculo dadosVeiculo) {
            return new DadosPermissaoUsoVeiculo(dadosFuncionario.matricula, dadosFuncionario.nome, dadosFuncionario.telefone,
                                                dadosVeiculo.placa, dadosVeiculo.modelo, dadosVeiculo.marca, dadosVeiculo.ano);
        }
        
        @Override
        public void inclui() {
            telaPermissao.setPlacas(ControladorVeiculo.getInstance().getPlacas());
            telaPermissao.abreInclusao(getDadosPermissao(telaTablePermissoes.getDadosFuncionario(), new DadosVeiculo()));
        }

        @Override
        public void altera(DadosVeiculo dadosVeiculo) {
            telaPermissao.abreAlteracao(getDadosPermissao(telaTablePermissoes.getDadosFuncionario(), dadosVeiculo));
        }

        @Override
        public void exclui(DadosVeiculo dadosVeiculo) {
            DadosFuncionario dadosFuncionario = telaTablePermissoes.getDadosFuncionario();
            if (JOptionPane.showConfirmDialog(null, "Deseja realmente excluir a permissão?\n" +
                                                    "Funcionário: " + dadosFuncionario.nome + "\n" +
                                                    "Veículo: " + dadosVeiculo.placa, "Confirmação", 
                                              JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    Integer matricula = dadosFuncionario.matricula;
                    excluiPermissao(matricula, dadosVeiculo.placa);
                    telaTablePermissoes.setDadosFuncionario(getFuncionarioPelaMatricula(matricula).getDTO());
                    //atualizaListaTelaPermissoes(item.matriculaFuncionario);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }

        @Override
        public void salvaCadastro(String acao) {
            try {
                DadosPermissaoUsoVeiculo item = telaPermissao.getDados();
                if (AcoesCadastro.ACAO_INCLUI.equals(acao)) {
                    incluiPermissao(item.matriculaFuncionario, item.placaVeiculo);
                }
                telaTablePermissoes.setDadosFuncionario(getFuncionarioPelaMatricula(item.matriculaFuncionario).getDTO());
                //atualizaListaTelaPermissoes(item.matriculaFuncionario);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        
    }
    
    /**** FIM Permissões de Uso de Veículos ****/
    
}
