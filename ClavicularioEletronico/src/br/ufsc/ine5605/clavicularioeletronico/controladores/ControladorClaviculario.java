
package br.ufsc.ine5605.clavicularioeletronico.controladores;

import br.ufsc.ine5605.clavicularioeletronico.entidades.EventoClaviculario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Funcionario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.SaidaVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Veiculo;
import br.ufsc.ine5605.clavicularioeletronico.enums.Cargo;
import br.ufsc.ine5605.clavicularioeletronico.enums.Evento;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.MatriculaNaoCadastradaException;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.PlacaNaoCadastradaException;
import br.ufsc.ine5605.clavicularioeletronico.persistencia.EventoClavicularioDAO;
import br.ufsc.ine5605.clavicularioeletronico.persistencia.SaidaVeiculoDAO;
import br.ufsc.ine5605.clavicularioeletronico.persistencia.VeiculoDAO;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaDevolverChave;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaRetirarChave;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaTableChegadaVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaTableSaidaVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosEventoClaviculario;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosVeiculo;
//import br.ufsc.ine5605.clavicularioeletronico.transferencias.ItemListaCadastro;
//import br.ufsc.ine5605.clavicularioeletronico.transferencias.Listavel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Implementa a funcionalidade do Claviculario propriamente dita:
 * a liberação e a recepção das chaves conforme as configurações
 * do sistema e salva o log de eventos
 * @author Flávio
 * **/
 
public class ControladorClaviculario {

    private static ControladorClaviculario instance;
    private TelaRetirarChave telaRetirar;
    private TelaDevolverChave telaDevolver;
    private TelaTableSaidaVeiculo tbRetirar;
    private TelaTableChegadaVeiculo tbDevolver;
    
    private ControladorClaviculario() {
        telaRetirar = new TelaRetirarChave();
        tbDevolver = new TelaTableChegadaVeiculo();
        telaDevolver = new TelaDevolverChave();
        tbRetirar = new TelaTableSaidaVeiculo();
    }

    public static ControladorClaviculario getInstance() {
        if (instance == null) {
            instance = new ControladorClaviculario();
        }
        return instance;
    }
    
    public void abreTelaRetirarChave() {
        this.telaRetirar.setVisible(true);
    }
    
    public void abreTelaDevolverChave() {
        this.telaDevolver.setVisible(true);
    }
        
    public int validaMatricula(String input) throws Exception {
        if (!input.matches("^[1-9][0-9]*")) {
            throw new Exception("O numero de matricula deve ser preenchido com um numero maior do que zero!");
        }
        
        int matricula = Integer.parseInt(input);
        if (!ControladorFuncionario.getInstance().funcionarioExiste(matricula)) {
            throw new MatriculaNaoCadastradaException(matricula);
        }
        return matricula;
    }
    
    private void atualizaListaRetirarChave() throws Exception {
        int matricula = validaMatricula(telaRetirar.getInput());
        List<DadosVeiculo> dadosVeiculo = ControladorFuncionario.getInstance().getListaPermissoes(matricula);
        if (dadosVeiculo.isEmpty()) {
            throw new Exception ("Voce nao tem permissao para retirar a chave de nenhum veiculo!");
        }
        this.tbRetirar.setLista(dadosVeiculo);
    }
    
    private void atualizaListaDevolverChave() throws Exception {
        int matricula = validaMatricula(telaDevolver.getInput());
        List<DadosVeiculo> dadosVeiculo = new ArrayList<>();
        for (SaidaVeiculo saidaVeiculo : SaidaVeiculoDAO.getInstance().getList()) {
            if (saidaVeiculo.getFuncionario().getMatricula() == matricula) {
                dadosVeiculo.add(saidaVeiculo.getVeiculo().getDTO());
            }
        }
        if (dadosVeiculo.isEmpty()) {
            throw new Exception ("Nenhuma chave foi retirada!");
        }
        this.tbDevolver.setLista(dadosVeiculo);
    }
    
    public void abreTabelaRetirarChave() throws Exception {
        atualizaListaRetirarChave();
        tbRetirar.setVisible(true);        
    }
    
    public void abreTabelaDevolverChave() throws Exception{
        atualizaListaDevolverChave();
        tbDevolver.setVisible(true);
    }
        
    public Veiculo retirarChave() throws Exception {
        Funcionario funcionario = ControladorFuncionario.getInstance().getFuncionarioPelaMatricula(validaMatricula(telaRetirar.getInput()));
        if (funcionario.isBloqueado()) {
            this.novoEvento(Evento.USUARIO_BLOQUEADO, funcionario.getMatricula(), "");
            throw new Exception("Funcionario encontra-se bloqueado!");
        }
        Veiculo veiculo = ControladorVeiculo.getInstance().getVeiculoPelaPlaca(tbRetirar.getPlacaSelecionada());
                
        if (this.veiculoDisponivel(veiculo.getPlaca())) {
            if (funcionario.getCargo() != Cargo.DIRETORIA) {
                 if (!funcionario.podeAcessarVeiculo(veiculo)) {
                    funcionario.incrementaNumeroTentativasSemPermissao();
                    this.novoEvento(Evento.PERMISSAO_INSUFICIENTE, funcionario.getMatricula(), "");
                    if (funcionario.getNumeroTentativasSemPermissao() > 3) {
                        funcionario.setBloqueado(true);
                        this.novoEvento(Evento.ACESSO_BLOQUEADO, funcionario.getMatricula(), veiculo.getPlaca());
                        throw new Exception("Usuario bloqueado por excesso de tentativas sem permissao!");
                    }
                    throw new Exception("Voce nao possui permissoes de acesso a este veiculo! (" + funcionario.getNumeroTentativasSemPermissao() + "/3)");
                }
            }
            funcionario.resetNumeroTentativasSemPermissao();
            this.novaSaida(veiculo, funcionario);
            this.novoEvento(Evento.ACESSO_PERMITIDO, funcionario.getMatricula(), veiculo.getPlaca());            
            return veiculo;
        } else {
            this.novoEvento(Evento.VEICULO_INDISPONIVEL, funcionario.getMatricula(), veiculo.getPlaca());
            throw new Exception(String.format("O veiculo %s %s %s, placa %s, esta indisponivel no momento",
                                              veiculo.getMarca(), veiculo.getModelo(), veiculo.getAno(), 
                                              veiculo.getPlaca()));
        }
    }
    
    public void devolverVeiculo() throws Exception {
        int matricula = validaMatricula(this.telaDevolver.getInput());
        if (!ControladorFuncionario.getInstance().funcionarioExiste(matricula)) {
            throw new MatriculaNaoCadastradaException(matricula);
        }
        Veiculo veiculo = ControladorVeiculo.getInstance().getVeiculoPelaPlaca(tbDevolver.getPlacaSelecionada());
        if (!ControladorVeiculo.getInstance().veiculoExiste(veiculo.getPlaca())) {
            throw new PlacaNaoCadastradaException(veiculo.getPlaca());
        }
        SaidaVeiculo veiculoFora = SaidaVeiculoDAO.getInstance().get(veiculo.getPlaca());
        if(veiculoFora != null) {
            if (veiculoFora.getFuncionario().getMatricula() == matricula) {
                if (!this.tbDevolver.getQuilometragemAtual().matches("^[1-9][0-9]*")) {
                    throw new Exception ("Você deve informar a quilometragem atual, sendo um numero positivo!");
                }              
                veiculoFora.getVeiculo().setQuilometragemAtual(Integer.parseInt(this.tbDevolver.getQuilometragemAtual()));
                VeiculoDAO.getInstance().put(veiculo.getPlaca(), veiculo);
                SaidaVeiculoDAO.getInstance().remove(veiculo.getPlaca());
                this.novoEvento(Evento.VEICULO_DEVOLVIDO, matricula, veiculo.getPlaca());
            } else {
                throw new Exception("O veiculo com placa " + veiculo.getPlaca() + " nao foi retirado com a matricula " + matricula + ".");
            }
        }
        else {
            throw new Exception("Veiculo encontra-se na garagem");
        }      
    }
        
    public boolean veiculoDisponivel(String placa) {
        for (SaidaVeiculo veiculoFora : SaidaVeiculoDAO.getInstance().getList()) {
            if ((veiculoFora.getVeiculo().getPlaca().equals(placa))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean funcionarioEstaUtilizandoAlgumVeiculo(int matricula) throws Exception {
        Funcionario funcionario = ControladorFuncionario.getInstance().getFuncionarioPelaMatricula(matricula);
        for (SaidaVeiculo veiculoFora : SaidaVeiculoDAO.getInstance().getList()) {
            if (veiculoFora.getFuncionario().getMatricula().equals(funcionario.getMatricula())) {
                return true;
            }
        }
        return false;
    }
    
    private void novoEvento(Evento evento, int matricula, String placa) {
        Calendar dataHora = Calendar.getInstance();           //verificar como pegar a hora
        EventoClaviculario novoEvento = new EventoClaviculario(evento,dataHora, matricula, placa);
        EventoClavicularioDAO.getInstance().put(placa, novoEvento);      
    }
    
    private void novaSaida(Veiculo veiculo, Funcionario funcionario) {
        Calendar dataHora = Calendar.getInstance();         //verificar como pegar a hora
        SaidaVeiculo novaSaida = new SaidaVeiculo(veiculo, funcionario, dataHora);
        SaidaVeiculoDAO.getInstance().put(veiculo.getPlaca(), novaSaida);
    }
    
    private List<DadosEventoClaviculario> getListaRelatorio(ArrayList<EventoClaviculario> eventos) {
        List<DadosEventoClaviculario> relatorio = new ArrayList<>();
        for (EventoClaviculario item : eventos) {
             relatorio.add(item.getDTO());
         }
         return relatorio;
    }
    
    //relatório de acessos a veículos, onde seja possível pesquisar/filtrar por:
    //motivo de negação/permissão, pela matrícula do funcionário ou pela placa do veículo.
    public List<DadosEventoClaviculario> geraRelatorioPorEvento (Evento evento) {
        return getListaRelatorio(EventoClavicularioDAO.getInstance().getListByEvento(evento));
    }
     
    public List<DadosEventoClaviculario> geraRelatorioPorMatricula(Integer matricula) {
        return getListaRelatorio(EventoClavicularioDAO.getInstance().getListByMatricula(matricula));
    }
     
    public List<DadosEventoClaviculario> geraRelatorioPorVeiculo(String placa) {
        return getListaRelatorio(EventoClavicularioDAO.getInstance().getListByPlaca(placa));
    }
     
     public List<DadosEventoClaviculario> geraRelatorioCompleto() {
         return getListaRelatorio(new ArrayList<>(EventoClavicularioDAO.getInstance().getList()));
    }


    
}
