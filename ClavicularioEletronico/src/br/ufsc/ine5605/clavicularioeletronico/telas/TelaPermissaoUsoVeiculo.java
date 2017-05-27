package br.ufsc.ine5605.clavicularioeletronico.telas;

import br.ufsc.ine5605.clavicularioeletronico.controladores.ControladorPermissaoUsoVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.Listavel;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class TelaPermissaoUsoVeiculo extends TelaCadastro {
    
    @Override
    public void exibeMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("----------------Menu Permissoes-----------------");
            System.out.println("1 - Incluir Permissao");       
            System.out.println("2 - Excluir Permissao");
            System.out.println("3 - Listar Veiculos que o funcionario pode utilizar");
            System.out.println("0 - Voltar");
            opcao = inputOpcao();
            if (opcao != 0) {
                switch (opcao) {
                    case 1:
                        exibeTelaInclui();
                        break;
                    case 2:
                        exibeTelaExclui();
                        break;    
                    case 3:
                        exibeLista();
                        break;
                    default:
                        System.out.println("Informe uma opcao valida!");
                }
            }
        }             
    }
    
    @Override
    public void exibeTelaInclui() {
        try {
            ControladorPermissaoUsoVeiculo.getInstance().inclui();
            System.out.println("Permissao cadastrada com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        solicitaEnterParaContinuar();
    }

    @Override
    public void exibeTelaAltera() {
        throw new UnsupportedOperationException("Esta opcao nao deve ser utilizada nesta tela!");
    }

    @Override
    public void exibeTelaExclui() {
        try {
            ControladorPermissaoUsoVeiculo.getInstance().exclui();
            System.out.println("Permissao excluida com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        solicitaEnterParaContinuar();
    }

    /**
     * Método usado para solicitar a matrícula do funcionário para imprimir a
     * lista
     */

    @Override
    public void exibeLista() {
        try {
            System.out.println("-------------------Permissoes de Uso dos Veiculos---------------------");
            List<Listavel> lista = ControladorPermissaoUsoVeiculo.getInstance().getListaPermissoes(inputMatricula());
            if (lista.size() > 0) {
                for (Listavel item: lista) {
                    System.out.println(item.getDescricao());
                }
            } else {
                System.out.println("Nenhum veiculo cadastrado para uso deste funcionario.");
            }
            System.out.println("--------------------------------------------------");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        solicitaEnterParaContinuar();
    }
    
    public boolean pedeConfirmacaoExclusao(int matricula, String placa) {
        System.out.println("Deseja mesmo excluir a permissão:");
        System.out.println("Matricula: "+matricula+"\tPlaca: "+placa);
        return pedeConfirmacao();
    }
}
