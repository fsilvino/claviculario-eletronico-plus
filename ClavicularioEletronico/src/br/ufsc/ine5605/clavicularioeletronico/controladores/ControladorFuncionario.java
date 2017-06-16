package br.ufsc.ine5605.clavicularioeletronico.controladores;

import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosFuncionario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Funcionario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.PermissaoUsoVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Veiculo;
import br.ufsc.ine5605.clavicularioeletronico.enums.Cargo;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.CadastroInvalidoPermissaoUsoVeiculoDiretoria;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.MatriculaJaCadastradaException;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.MatriculaNaoCadastradaException;
import br.ufsc.ine5605.clavicularioeletronico.persistencia.FuncionarioDAO;
import br.ufsc.ine5605.clavicularioeletronico.persistencia.PermissaoUsoVeiculoDAO;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.AcoesCadastro;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.ITelaBaseCadastroObserver;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.ITelaBaseTableObserver;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaFuncionarioNew;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaPermissaoUsoVeiculoNew;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaTableFuncionario;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaTablePermissaoUsoVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosPermissaoUsoVeiculo;
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
            excluirPermissoesFuncionario(funcionario);
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
        try {
            this.telaTablePermissoes.setFuncionario(matricula, getFuncionarioPelaMatricula(matricula).getNome());
            this.atualizaListaTelaPermissoes(matricula);
            this.telaTablePermissoes.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    protected void atualizaListaTelaPermissoes(Integer matricula) throws Exception {
        this.telaTablePermissoes.setLista(getListaPermissoes(matricula));
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
        
        if (permissaoExiste(funcionario, veiculo)) {
            throw new Exception(String.format("O funcionario %s - %s ja possui permissao para acessar o veiculo %s.", funcionario.getMatricula(), funcionario.getNome(), veiculo.getPlaca()));
        }
        
        PermissaoUsoVeiculoDAO.getInstance().add(new PermissaoUsoVeiculo(funcionario, veiculo));
    }
    
    /**
     * Remove a permissão de uso de um veículo de um funcionário pela matrícula e placa
     * @param matricula Matrícula do funcionário que terá a permissão removida
     * @param placa Placa do veículo que terá o uso negado ao funcionário
     * @throws Exception Caso os parâmtros sejam inválidos ou não exista a permissão
     */
    public void excluiPermissao(Integer matricula, String placa) throws Exception {
        PermissaoUsoVeiculo permissao = findPermissaoUsoVeiculo(matricula, placa);
        if (permissao == null) {
            throw new Exception(String.format("O funcionario de matricula %s nao possui permissao de uso para o veiculo de placa %s", matricula, placa));
        }
        if (permissao.getFuncionario().getCargo() == Cargo.DIRETORIA) {
            throw new CadastroInvalidoPermissaoUsoVeiculoDiretoria();
        }
        PermissaoUsoVeiculoDAO.getInstance().remove(permissao);
    }
    
    public List<PermissaoUsoVeiculo> getPermissoesFuncionario(Integer matricula) throws Exception {
        Funcionario funcionario = getFuncionarioPelaMatricula(matricula);
        
        if (funcionario.getCargo() == Cargo.DIRETORIA) {
            throw new CadastroInvalidoPermissaoUsoVeiculoDiretoria();
        }
        
        List<PermissaoUsoVeiculo> lista = new ArrayList<>();
        for (PermissaoUsoVeiculo permissao : PermissaoUsoVeiculoDAO.getInstance().getList()) {
            if (permissao.getFuncionario().getMatricula().equals(matricula)) {
                lista.add(permissao);
            }
        }
        return lista;
    }
    
    public List<PermissaoUsoVeiculo> getPermissoesVeiculo(String placa) {
        List<PermissaoUsoVeiculo> lista = new ArrayList<>();
        for (PermissaoUsoVeiculo permissao : PermissaoUsoVeiculoDAO.getInstance().getList()) {
            if (permissao.getVeiculo().getPlaca().equals(placa)) {
                lista.add(permissao);
            }
        }
        return lista;
    }
    
    public List<DadosPermissaoUsoVeiculo> getListaPermissoes(Integer matricula) throws Exception {
        List<DadosPermissaoUsoVeiculo> lista = new ArrayList<>();
        for (PermissaoUsoVeiculo permissao : getPermissoesFuncionario(matricula)) {
            Funcionario funcionario = permissao.getFuncionario();
            Veiculo veiculo = permissao.getVeiculo();
            lista.add(new DadosPermissaoUsoVeiculo(funcionario.getMatricula(), funcionario.getNome(), funcionario.getTelefone(), 
                                                   veiculo.getPlaca(), veiculo.getModelo(), veiculo.getMarca(), veiculo.getAno()));
        }
        return lista;
    }
    
    private PermissaoUsoVeiculo findPermissaoUsoVeiculo(Funcionario funcionario, Veiculo veiculo) {
        for (PermissaoUsoVeiculo permissao : PermissaoUsoVeiculoDAO.getInstance().getList()) {
            if (permissao.getFuncionario().getMatricula().equals(funcionario.getMatricula()) && permissao.getVeiculo().getPlaca().equals(veiculo.getPlaca())) {
                return permissao;
            }
        }
        return null;
    }
    
    private PermissaoUsoVeiculo findPermissaoUsoVeiculo(Integer matricula, String placa) throws Exception {
        Funcionario funcionario = getFuncionarioPelaMatricula(matricula);
        
        if (funcionario.getCargo() == Cargo.DIRETORIA) {
            throw new CadastroInvalidoPermissaoUsoVeiculoDiretoria();
        }
        
        Veiculo veiculo = ControladorVeiculo.getInstance().getVeiculoPelaPlaca(placa);
        
        return findPermissaoUsoVeiculo(funcionario, veiculo);
    }
    
    public boolean permissaoExiste(Funcionario funcionario, Veiculo veiculo) throws Exception {
        return findPermissaoUsoVeiculo(funcionario, veiculo) != null;
    }
    
    public void excluirPermissoesFuncionario(Funcionario funcionario) throws Exception {
        if (funcionario.getCargo() != Cargo.DIRETORIA) {
            for (PermissaoUsoVeiculo permissao : getPermissoesFuncionario(funcionario.getMatricula())) {
                PermissaoUsoVeiculoDAO.getInstance().getList().remove(permissao);
            }
        }
    }
    
    public void excluirPermissoesVeiculo(Veiculo veiculo) {
        for (PermissaoUsoVeiculo permissao : getPermissoesVeiculo(veiculo.getPlaca())) {
            PermissaoUsoVeiculoDAO.getInstance().remove(permissao);
        }
    }
    
    private class CadastroPermissoesObserver implements ITelaBaseTableObserver<DadosPermissaoUsoVeiculo>, ITelaBaseCadastroObserver {

        @Override
        public void inclui() {
            telaPermissao.setPlacas(ControladorVeiculo.getInstance().getPlacas());
            telaPermissao.abreInclusao(telaTablePermissoes.getDadosComFuncionarioParaIncluir());
        }

        @Override
        public void altera(DadosPermissaoUsoVeiculo item) {
            telaPermissao.abreAlteracao(item);
        }

        @Override
        public void exclui(DadosPermissaoUsoVeiculo item) {
            if (JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "Confirmação", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    excluiPermissao(item.matriculaFuncionario, item.placaVeiculo);
                    atualizaListaTelaPermissoes(item.matriculaFuncionario);
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
                atualizaListaTelaPermissoes(item.matriculaFuncionario);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        
    }
    
    /**** FIM Permissões de Uso de Veículos ****/
    
}
