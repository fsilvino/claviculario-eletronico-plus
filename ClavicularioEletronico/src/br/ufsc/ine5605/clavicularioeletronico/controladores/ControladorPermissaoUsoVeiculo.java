package br.ufsc.ine5605.clavicularioeletronico.controladores;

import br.ufsc.ine5605.clavicularioeletronico.entidades.Funcionario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.PermissaoUsoVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Veiculo;
import br.ufsc.ine5605.clavicularioeletronico.enums.Cargo;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.CadastroInvalidoPermissaoUsoVeiculoDiretoria;
import br.ufsc.ine5605.clavicularioeletronico.telas.TelaPermissaoUsoVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.ItemListaCadastro;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.Listavel;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsável pelo controle do cadastrado das permissões de uso dos veículos
 * pelos funcionários
 * @author Flávio
 */
public class ControladorPermissaoUsoVeiculo extends ControladorCadastro<TelaPermissaoUsoVeiculo, PermissaoUsoVeiculo> {
    
    private static ControladorPermissaoUsoVeiculo instancia;
    
    private ControladorPermissaoUsoVeiculo() {
        super();
        tela = new TelaPermissaoUsoVeiculo();
    }
    
    public static ControladorPermissaoUsoVeiculo getInstance() {
        if (instancia == null) {
            instancia = new ControladorPermissaoUsoVeiculo();
        }
        return instancia;
    }

    @Override
    public List<Listavel> getListaItensCadastro() {
        throw new RuntimeException("Use exibeListaPermissoes!");
    }
    
    /**
     * Inclui a permissão de uso a um veículo para um funcionário pela 
     * matrícula e placa
     * @param matricula Matrícula do funcionário que terá permissão de uso
     * @param placa Placa do veículo que será permitido
     * @throws Exception Caso os dados sejam inválidos ou segundo a regra de negócio
     * não seja possível incluir esta permissão
     */
    public void inclui(int matricula, String placa) throws Exception {
        Funcionario funcionario = ControladorFuncionario.getInstance().getFuncionarioPelaMatricula(matricula);
        if (funcionario.getCargo() == Cargo.DIRETORIA) {
            throw new CadastroInvalidoPermissaoUsoVeiculoDiretoria();
        }
        
        Veiculo veiculo = ControladorVeiculo.getInstance().getVeiculoPelaPlaca(placa);
        
        if (permissaoExiste(funcionario, veiculo)) {
            throw new Exception(String.format("O funcionario %s - %s ja possui permissao para acessar o veiculo %s.", funcionario.getMatricula(), funcionario.getNome(), veiculo.getPlaca()));
        }
        
        itens.add(new PermissaoUsoVeiculo(funcionario, veiculo));
    }
    
     /**
     * Inclui a permissão de uso a um veículo para um funcionário
     * @throws Exception Caso a matricula não exista
     */
    public void inclui() throws Exception {
        int matricula = this.tela.inputMatricula();
        Funcionario funcionario = ControladorFuncionario.getInstance().getFuncionarioPelaMatricula(matricula);
        if (funcionario.getCargo() == Cargo.DIRETORIA) {
            throw new CadastroInvalidoPermissaoUsoVeiculoDiretoria();
        }
        String placa = this.tela.inputPlaca();
        inclui(matricula, placa);
    }
    
    /**
     * Remove a permissão de uso de um veículo de um funcionário pela matrícula e placa
     * @param matricula Matrícula do funcionário que terá a permissão removida
     * @param placa Placa do veículo que terá o uso negado ao funcionário
     * @throws Exception Caso os parâmtros sejam inválidos ou não exista a permissão
     */
    public void exclui(int matricula, String placa) throws Exception {
        PermissaoUsoVeiculo permissao = findPermissaoUsoVeiculo(matricula, placa);
        if (permissao == null) {
            throw new Exception(String.format("O funcionario de matricula %s nao possui permissao de uso para o veiculo de placa %s", matricula, placa));
        }
        if (permissao.getFuncionario().getCargo() == Cargo.DIRETORIA) {
            throw new CadastroInvalidoPermissaoUsoVeiculoDiretoria();
        }
        if (this.tela.pedeConfirmacaoExclusao(matricula, placa)) {
            itens.remove(permissao);   
        }
    }
    
    /**
     * Remove a permissão de uso de um veículo de um funcionário pela matrícula e placa
     * @throws Exception Caso a matricula nao exista
     */
    public void exclui() throws Exception {
        int matricula = this.tela.inputMatricula();
        Funcionario funcionario = ControladorFuncionario.getInstance().getFuncionarioPelaMatricula(matricula);
        if (funcionario.getCargo() == Cargo.DIRETORIA) {
            throw new CadastroInvalidoPermissaoUsoVeiculoDiretoria();
        }
        String placa = this.tela.inputPlaca();
        exclui(matricula, placa);
    }
    
    public List<PermissaoUsoVeiculo> getPermissoesFuncionario(int matricula) throws Exception {
        Funcionario funcionario = ControladorFuncionario.getInstance().getFuncionarioPelaMatricula(matricula);
        
        if (funcionario.getCargo() == Cargo.DIRETORIA) {
            throw new CadastroInvalidoPermissaoUsoVeiculoDiretoria();
        }
        
        List<PermissaoUsoVeiculo> lista = new ArrayList<>();
        for (PermissaoUsoVeiculo permissao : itens) {
            if (permissao.getFuncionario().getMatricula() == matricula) {
                lista.add(permissao);
            }
        }
        return lista;
    }
    
    public List<Listavel> getListaPermissoes(int matricula) throws Exception {
        List<Listavel> lista = new ArrayList<>();
        for (PermissaoUsoVeiculo permissao : getPermissoesFuncionario(matricula)) {
            lista.add(new ItemListaCadastro(permissao.getVeiculo().getPlaca()+"\t"+permissao.getVeiculo().getModelo()));
        }
        return lista;
    }
    
    private PermissaoUsoVeiculo findPermissaoUsoVeiculo(Funcionario funcionario, Veiculo veiculo) {
        for (PermissaoUsoVeiculo permissao : itens) {
            if (permissao.getFuncionario().equals(funcionario) && permissao.getVeiculo().equals(veiculo)) {
                return permissao;
            }
        }
        return null;
    }
    
    private PermissaoUsoVeiculo findPermissaoUsoVeiculo(int matricula, String placa) throws Exception {
        Funcionario funcionario = ControladorFuncionario.getInstance().getFuncionarioPelaMatricula(matricula);
        
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
        if (funcionario.getCargo() == Cargo.MOTORISTA) {
            for (PermissaoUsoVeiculo permissao : getPermissoesFuncionario(funcionario.getMatricula())) {
                itens.remove(permissao);
            }
        }
    }
    
    public void excluirPermissoesVeiculo(Veiculo veiculo) {
        List<PermissaoUsoVeiculo> listaPermissao = new ArrayList<>(itens);
        for (PermissaoUsoVeiculo permissao: listaPermissao) {
            if (permissao.getVeiculo().getPlaca().equals(veiculo.getPlaca())) {
                itens.remove(permissao);
            }
        }
    }
    
}