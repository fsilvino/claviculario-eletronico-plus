package br.ufsc.ine5605.clavicularioeletronico.controladores;

import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Veiculo;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.PlacaJaCadastradaException;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.PlacaNaoCadastradaException;
import br.ufsc.ine5605.clavicularioeletronico.persistencia.VeiculoDAO;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaTableVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaVeiculoNew;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Responsável pelo controle de cadastro dos veículos
 * @author Flávio
 */
public class ControladorVeiculo extends ControladorCadastroNew<TelaTableVeiculo, TelaVeiculoNew, VeiculoDAO, String, Veiculo, DadosVeiculo> {

    private static ControladorVeiculo instance;

    private ControladorVeiculo() {
        super();
    }

    public static ControladorVeiculo getInstance() {
        if (instance == null) {
            instance = new ControladorVeiculo();
        }
        return instance;
    }

    @Override
    protected TelaTableVeiculo instanciaTelaTable() {
        return new TelaTableVeiculo();
    }

    @Override
    protected TelaVeiculoNew instanciaTelaCadastro() {
        return new TelaVeiculoNew();
    }

    @Override
    protected boolean valida(DadosVeiculo dadosVeiculo) throws Exception {
        if (dadosVeiculo == null) {
            throw new InvalidParameterException("Dados invalidos! Parametro nulo.");
        }
        
        if (dadosVeiculo.placa == null || dadosVeiculo.placa.trim().isEmpty()) {
            throw new Exception("Informe a placa do veiculo!");
        }
        
        if (!dadosVeiculo.placa.matches("[A-Z]{3}-[0-9]{4}")) {
            throw new Exception("Informe a placa com 3 letras maiusculas e 4 numeros, separados por um traco. Ex: AAA-9999.");
        }
        
        return true;
    }

    @Override
    protected void salvaInclusao(DadosVeiculo dadosVeiculo) {
        try {
            if (valida(dadosVeiculo)) {
                if (findVeiculoPelaPlaca(dadosVeiculo.placa) == null) {
                    Veiculo veiculo = new Veiculo();
                    copiaDadosParaVeiculo(dadosVeiculo, veiculo);
                    getDao().put(veiculo.getPlaca(), veiculo);
                } else {
                    throw new PlacaJaCadastradaException(dadosVeiculo.placa);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(telaCad, e.getMessage());
        }
    }

    @Override
    protected void salvaAlteracao(DadosVeiculo dadosVeiculo) {
        try {
            Veiculo veiculo = findVeiculoPelaPlaca(dadosVeiculo.placa);
            if (veiculo != null) {
                if (valida(dadosVeiculo)) {
                    copiaDadosParaVeiculo(dadosVeiculo, veiculo); 
                    getDao().put(veiculo.getPlaca(), veiculo);
                }
            } else {
                throw new PlacaNaoCadastradaException(dadosVeiculo.placa);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(telaCad, e.getMessage());
        }
    }

    @Override
    protected String getMensagemConfirmacaoExclusao(DadosVeiculo dadosVeiculo) {
        return super.getMensagemConfirmacaoExclusao(dadosVeiculo) +
               "\nPlaca: " + dadosVeiculo.placa + 
               "\nModelo: " + dadosVeiculo.modelo + 
               "\nMarca: " + dadosVeiculo.marca;
    }

    
    
    @Override
    protected void executaExclusao(DadosVeiculo dadosVeiculo) {
        try {
            if (dadosVeiculo.placa == null || dadosVeiculo.placa.isEmpty()) {
                throw new InvalidParameterException("Falha ao excluir o veiculo! Placa nao informada.");
            }

            Veiculo veiculo = findVeiculoPelaPlaca(dadosVeiculo.placa);
            if (veiculo == null) {
                throw new PlacaNaoCadastradaException(dadosVeiculo.placa);
            }

            if (!ControladorClaviculario.getInstance().veiculoDisponivel(veiculo.getPlaca())) {
                throw new Exception("Este veiculo esta sendo utilizado! Para excluir ele deve ser devolvido primeiro.");
            }

            ControladorFuncionario.getInstance().excluirPermissoesVeiculo(veiculo);
            getDao().remove(veiculo.getPlaca());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(telaTb, e.getMessage());
        }
    }

    @Override
    protected List<DadosVeiculo> getListaDTO() {
        ArrayList<DadosVeiculo> lista = new ArrayList<>();
        for (Veiculo veiculo : getDao().getList()) {
            lista.add(new DadosVeiculo(veiculo.getPlaca(), 
                                       veiculo.getModelo(), 
                                       veiculo.getMarca(), 
                                       veiculo.getAno(),
                                       veiculo.getQuilometragemAtual()));
        }
        return lista;
    }

    @Override
    protected VeiculoDAO getDao() {
        return VeiculoDAO.getInstance();
    }

    /**
     * Pesquisa o veiculo pela placa
     * @param placa Placa a ser pesquisada
     * @return Veiculo encontrado pela placa ou nulo se nao encontrar
     */
    private Veiculo findVeiculoPelaPlaca(String placa) {
        return getDao().get(placa);
    }
    
    /**
     * Copia os dados da tela para uma instancia de veiculo
     * @param dadosVeiculo Dados passados pela tela
     * @param veiculo Objeto veiculo que ira receber os dados
     */
    private void copiaDadosParaVeiculo(DadosVeiculo dadosVeiculo, Veiculo veiculo) {
        veiculo.setPlaca(dadosVeiculo.placa);
        veiculo.setModelo(dadosVeiculo.modelo);
        veiculo.setMarca(dadosVeiculo.marca);
        veiculo.setAno(dadosVeiculo.ano);
        veiculo.setQuilometragemAtual(dadosVeiculo.quilometragemAtual);
    }

    /**
     * Pesquisa o veículo pela placa e o retorna
     * @param placa Placa do veiculo no formato AAA-9999
     * @return Veiculo cadastrado com a placa informada
     * @throws PlacaNaoCadastradaException Caso nao seja encontrado o veiculo
     */
    public Veiculo getVeiculoPelaPlaca(String placa) throws PlacaNaoCadastradaException {
        Veiculo veiculo = findVeiculoPelaPlaca(placa);
        if (veiculo == null) {
            throw new PlacaNaoCadastradaException(placa);
        }
        return veiculo;
    }
    
     /**
     * Pesquisa o veículo pela placa e o retorna
     * @param placa Placa do veiculo no formato AAA-9999
     * @return True se o veiculo existe na lista
     */ 
    public boolean veiculoExiste(String placa) {
        return findVeiculoPelaPlaca(placa) != null;
    }
    
    /**
     * Retorna o veículo quando existe apenas um cadastrado
     * @return Retorna o veículo caso seja o único cadastrado, caso contrário retorna null
     */
    public Veiculo getVeiculoQuandoUnico() {
        if (this.getDao().getList().size() == 1) {
            return (Veiculo)this.getDao().getList().toArray()[0];
        }
        return null;
    }

    public String[] getPlacas() {
        Collection<Veiculo> veiculos = this.getDao().getList();
        String[] placas = new String[veiculos.size()];
        int i = 0;
        for (Veiculo veiculo : veiculos) {
            placas[i] = veiculo.getPlaca();
            i++;
        }
        return placas;
    }
    
}