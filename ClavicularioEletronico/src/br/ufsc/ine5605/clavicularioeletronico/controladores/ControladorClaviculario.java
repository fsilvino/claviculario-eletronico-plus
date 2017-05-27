
package br.ufsc.ine5605.clavicularioeletronico.controladores;

import br.ufsc.ine5605.clavicularioeletronico.entidades.EventoClaviculario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Funcionario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.PermissaoUsoVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.entidades.SaidaVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Veiculo;
import br.ufsc.ine5605.clavicularioeletronico.enums.Cargo;
import br.ufsc.ine5605.clavicularioeletronico.enums.Evento;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.MatriculaNaoCadastradaException;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.PlacaNaoCadastradaException;
import br.ufsc.ine5605.clavicularioeletronico.telas.TelaClaviculario;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.ItemListaCadastro;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.Listavel;
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
    private List<EventoClaviculario> log;
    private List<SaidaVeiculo> veiculosFora;
    private TelaClaviculario tela;
    
    private ControladorClaviculario() {
        log = new ArrayList<>();
        veiculosFora = new ArrayList();
        tela = new TelaClaviculario();
    }

    public static ControladorClaviculario getInstance() {
        if (instance == null) {
            instance = new ControladorClaviculario();
        }
        return instance;
    }

    public void inicia() {
        tela.exibeMenu();
    }
    
    public Veiculo retirarChave(int matricula) throws Exception {
        
        Funcionario funcionario = null;
        
        try {
            funcionario = ControladorFuncionario.getInstance().getFuncionarioPelaMatricula(matricula);
        } catch (MatriculaNaoCadastradaException e) {
            this.novoEvento(Evento.MATRICULA_INVALIDA, matricula, "");
            throw e;
        }
               
        if (funcionario.isBloqueado()) {
            this.novoEvento(Evento.USUARIO_BLOQUEADO, matricula, "");
            throw new Exception("Funcionario encontra-se bloqueado!");
        }
 
        Veiculo veiculo = null;
        if (funcionario.getCargo() == Cargo.DIRETORIA) {
            veiculo = ControladorVeiculo.getInstance().getVeiculoQuandoUnico();
        } else {
            List<PermissaoUsoVeiculo> permissoes = ControladorPermissaoUsoVeiculo.getInstance().getPermissoesFuncionario(funcionario.getMatricula());
            if (permissoes.size() == 1) {
                veiculo = permissoes.get(0).getVeiculo();
            }
        }
        
        if (veiculo == null) {
            String placa = this.tela.inputPlaca();
            veiculo = ControladorVeiculo.getInstance().getVeiculoPelaPlaca(placa);
        }
        
        if (this.veiculoDisponivel(veiculo.getPlaca())) {
            if (funcionario.getCargo() != Cargo.DIRETORIA) {   
                 if (!ControladorPermissaoUsoVeiculo.getInstance().permissaoExiste(funcionario, veiculo)) {
                    funcionario.incrementaNumeroTentativasSemPermissao();
                    this.novoEvento(Evento.PERMISSAO_INSUFICIENTE, matricula, "");
                    if (funcionario.getNumeroTentativasSemPermissao() > 3) {
                        funcionario.setBloqueado(true);
                        this.novoEvento(Evento.ACESSO_BLOQUEADO, matricula, veiculo.getPlaca());
                        throw new Exception("Usuario bloqueado por excesso de tentativas sem permissao!");
                    }
                    throw new Exception("Voce nao possui permissoes de acesso a este veiculo! (" + funcionario.getNumeroTentativasSemPermissao() + "/3)");
                }
            }
            funcionario.resetNumeroTentativasSemPermissao();
            this.novaSaida(veiculo, funcionario);
            return veiculo;
        } else {
            this.novoEvento(Evento.VEICULO_INDISPONIVEL, matricula, veiculo.getPlaca());
            throw new Exception(String.format("O veiculo %s %s %s, placa %s, esta indisponivel no momento",
                                              veiculo.getMarca(), veiculo.getModelo(), veiculo.getAno(), 
                                              veiculo.getPlaca()));
        }
    }
    
    public void devolverVeiculo() throws Exception {
        int matricula = this.tela.inputMatricula();
        if (!ControladorFuncionario.getInstance().funcionarioExiste(matricula)) {
            throw new MatriculaNaoCadastradaException(matricula);
        }
        String placa = this.tela.inputPlaca();
        if (!ControladorVeiculo.getInstance().veiculoExiste(placa)) {
            throw new PlacaNaoCadastradaException(placa);
        }
        boolean veiculoEncontrado = false;
        for (SaidaVeiculo veiculoFora : veiculosFora) {
            if((veiculoFora.getVeiculo().getPlaca().equals(placa))) {
                if (veiculoFora.getFuncionario().getMatricula() == matricula) {
                    veiculoFora.getVeiculo().setQuilometragemAtual(this.tela.inputQuilometragemAtual());
                    this.veiculosFora.remove(veiculoFora);
                    this.novoEvento(Evento.VEICULO_DEVOLVIDO, matricula, placa);
                    veiculoEncontrado = true;
                    break;
                } else {
                    throw new Exception("O veiculo com placa " + placa + " nao foi retirado com a matricula " + matricula + ".");
                }
            }
        }
        if (!veiculoEncontrado) {
            throw new Exception("Veiculo encontra-se na garagem");
        }
    }
        
    public boolean veiculoDisponivel(String placa) {
        
        for (SaidaVeiculo veiculoFora : veiculosFora) {
            if ((veiculoFora.getVeiculo().getPlaca().equals(placa))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean funcionarioEstaUtilizandoAlgumVeiculo(int matricula) throws Exception {
        Funcionario funcionario = ControladorFuncionario.getInstance().getFuncionarioPelaMatricula(matricula);
        for (SaidaVeiculo veiculoFora : veiculosFora) {
            if (veiculoFora.getFuncionario().getMatricula() == funcionario.getMatricula()) {
                return true;
            }
        }
        return false;
    }
    
    private void novoEvento(Evento evento, int matricula, String placa) {
        Calendar dataHora = Calendar.getInstance();           //verificar como pegar a hora
        EventoClaviculario novoEvento = new EventoClaviculario(evento,dataHora, matricula, placa);
        this.log.add(novoEvento);
        
    }
    
    private void novaSaida(Veiculo veiculo, Funcionario funcionario) {
        Calendar dataHora = Calendar.getInstance();         //verificar como pegar a hora
        SaidaVeiculo novaSaida = new SaidaVeiculo(veiculo, funcionario, dataHora);
        this.veiculosFora.add(novaSaida);
        this.novoEvento(Evento.ACESSO_PERMITIDO, funcionario.getMatricula(), veiculo.getPlaca());
    } 
    
     //relatório de acessos a veículos, onde seja possível pesquisar/filtrar por:
     //motivo de negação/permissão, pela matrícula do funcionário ou pela placa do veículo.
     public List<Listavel> geraRelatorioPorEvento (Evento evento) {
         
         List<Listavel> relatorio = new ArrayList();
         for (EventoClaviculario item : log) {
             if (item.getEvento().equals(evento)) {
                 String descricao = String.format("%s\t%s\t%s\t%s",                       
                         item.getDataHora().getTime().toString(),
                         item.getEvento(),
                         item.getMatricula(),
                         item.getPlaca());
                 relatorio.add(new ItemListaCadastro(descricao));
             }
         }
         return relatorio;
     }
     
    public List<Listavel> geraRelatorioPorMatricula(int matricula) {
         
         List<Listavel> relatorio = new ArrayList();
         for (EventoClaviculario item : log) {
             if (item.getMatricula() == matricula) {
                 String descricao = String.format("%s\t%s\t%s\t%s",                       
                         item.getDataHora().getTime().toString(),
                         item.getEvento(),
                         item.getMatricula(),
                         item.getPlaca());
                 relatorio.add(new ItemListaCadastro(descricao));
             }
         }
         return relatorio;
     }
     
     public List<Listavel> geraRelatorioPorVeiculo(String placa) {
         
         List<Listavel> relatorio = new ArrayList();
         for (EventoClaviculario item : log) {
             if (item.getPlaca().equals(placa)) {
                 String descricao = String.format("%s\t%s\t%s\t%s",                       
                         item.getDataHora().getTime().toString(),
                         item.getEvento(),
                         item.getMatricula(),
                         item.getPlaca());
                 relatorio.add(new ItemListaCadastro(descricao));
             }
         }
         return relatorio;
     }
     
     public List<Listavel> geraRelatorioCompleto() {
         
        List<Listavel> relatorio = new ArrayList();
        for (EventoClaviculario item : log) {
            String descricao = String.format("%s\t%s\t%s\t%s",                       
                    item.getDataHora().getTime().toString(),
                    item.getEvento(),
                    item.getMatricula(),
                    item.getPlaca());
                relatorio.add(new ItemListaCadastro(descricao));
   
        }
        return relatorio;
    }
    
}
