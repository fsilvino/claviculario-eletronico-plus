package br.ufsc.ine5605.clavicularioeletronico.telas;

import br.ufsc.ine5605.clavicularioeletronico.controladores.ControladorClaviculario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Veiculo;
import br.ufsc.ine5605.clavicularioeletronico.enums.Evento;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.Listavel;
import java.util.List;

public class TelaClaviculario extends TelaBase {
        
    public TelaClaviculario() {
        super();
    }
    
    public void exibeMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("----------------Claviculario Eletronico-----------------");
            System.out.println("1 - Retirar Chave");
            System.out.println("2 - Devolver Chave");
            System.out.println("3 - Relatorios");
            System.out.println("0 - Voltar");
        
            opcao = inputOpcao();
            if (opcao != 0) {
                switch (opcao) {
                    case 1:
                        retirarChave();
                        break;
                    case 2:
                        devolverChave();
                        break;
                    case 3:
                        exibeMenuRelatorios();
                        break;
                    default:
                        System.out.println("Informe uma opcao valida!");
                }
            }
        }
    }
    
    private void exibeMenuRelatorios() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("----------------Relatorios-----------------");
            System.out.println("1 - Exibir relatorio completo");
            System.out.println("2 - Pesquisar por funcionario");
            System.out.println("3 - Pesquisar por veiculo ");
            System.out.println("4 - Pesquisar por evento");
            System.out.println("0 - Voltar");           
            opcao = inputOpcao();
            
            if (opcao != 0) {
                switch (opcao) {
                    case 1:
                        this.exibeRelatorios(ControladorClaviculario.getInstance().geraRelatorioCompleto());
                        break;
                    case 2:
                        try {
                            this.exibeRelatorios(ControladorClaviculario.getInstance().geraRelatorioPorMatricula(inputMatricula()));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        try {
                            this.exibeRelatorios(ControladorClaviculario.getInstance().geraRelatorioPorVeiculo(inputPlaca()));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        exibeMenuEventos();
                        break;
                    default:
                        System.out.println("Informe uma opcao valida!");
                }
            }
        }
    }
    
    private void exibeMenuEventos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("-----------Selecione um evento---------------");
            System.out.println("1 - Acesso permitido");
            System.out.println("2 - Acesso bloqueado");
            System.out.println("3 - Usuario bloqueado");
            System.out.println("4 - Matricula invalida");
            System.out.println("5 - Permissao insuficiente");
            System.out.println("6 - Veiculo indisponivel");
            System.out.println("7 - Veiculo devolvido");
            System.out.println("0 - Voltar");
            
            opcao = inputOpcao();
        
            if (opcao != 0) {
                Evento evento = null;
                switch (opcao) {
                    case 1:
                        evento = Evento.ACESSO_PERMITIDO;
                        break;
                    case 2:
                        evento = Evento.ACESSO_BLOQUEADO;
                        break;
                    case 3:
                       evento = Evento.USUARIO_BLOQUEADO;
                        break;
                    case 4:
                        evento = Evento.MATRICULA_INVALIDA;
                        break;
                    case 5:
                        evento = Evento.PERMISSAO_INSUFICIENTE;
                        break;
                    case 6:
                        evento = Evento.VEICULO_INDISPONIVEL;
                        break;
                    case 7:
                        evento = Evento.VEICULO_DEVOLVIDO;
                        break;
                    default:
                        System.out.println("Informe uma opcao valida!");
                        break;
                }   
                if (evento != null) {
                    List<Listavel> relatorio = ControladorClaviculario.getInstance().geraRelatorioPorEvento(evento);
                    exibeRelatorios(relatorio);
                }
            }
        }
    }
    
    private void retirarChave() {
        try {
            Veiculo veiculo = ControladorClaviculario.getInstance().retirarChave(inputMatricula());
            if (veiculo != null) {
                System.out.println(String.format("Chave liberada com sucesso!\n" +
                                                 "Veiculo: %s %s %s\n" +
                                                 "Placa: %s",
                                                 veiculo.getMarca(), veiculo.getModelo(), veiculo.getAno(), veiculo.getPlaca()));
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        solicitaEnterParaContinuar();
    }
    
    private void devolverChave() {
        try {
            ControladorClaviculario.getInstance().devolverVeiculo();
            System.out.println("Chave devolvida com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        solicitaEnterParaContinuar();
    }
    
    private void exibeRelatorios (List<Listavel> relatorio) {
        System.out.println("---------------------------------Relatorios-----------------------------");

        if (relatorio.isEmpty()) {
            System.out.println("Sem correspondencias na pesquisa");
            System.out.println("------------------------------------------------------------------------");
        } else {
            System.out.println("--------------Data--------------Evento--------------Matricula----Veiculo");
            for (Listavel evento : relatorio) {
                System.out.println(evento.getDescricao());    
            }
            System.out.println("------------------------------------------------------------------------");
        }
        solicitaEnterParaContinuar();
    }
}