package br.ufsc.ine5605.clavicularioeletronico.telas;

import br.ufsc.ine5605.clavicularioeletronico.controladores.ControladorSistema;

/**
 * 
 * @author Gabriel
 */
public class TelaSistema extends TelaBase {

    public TelaSistema() {
        super();
    }
      
    public void exibeMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("-----------------Menu Principal------------------");
            System.out.println("1 - Funcionarios");
            System.out.println("2 - Veiculos");
            System.out.println("3 - Claviculario Eletronico");
            System.out.println("0 - Sair do sistema");
            
            opcao = inputOpcao();
            if (opcao != 0) {
                switch (opcao) {
                    case 1:
                        ControladorSistema.getInstance().abreCadastroFuncionario();
                        break;
                    case 2:
                        ControladorSistema.getInstance().abreCadastroVeiculo();
                        break;
                    case 3:
                        ControladorSistema.getInstance().abreClaviculario();
                        break;
                    default:
                        System.out.println("Informe uma opcao valida!");
                }
            }
        }
    }

}
