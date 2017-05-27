package br.ufsc.ine5605.clavicularioeletronico.telas;

import br.ufsc.ine5605.clavicularioeletronico.transferencias.ItemListaCadastro;

public abstract class TelaCadastro extends TelaBase {
    
    public TelaCadastro() {
        super();
    }

    public abstract void exibeTelaInclui();

    public abstract void exibeTelaAltera();

    public abstract void exibeTelaExclui();
    
    public abstract void exibeLista();

    protected boolean executaOpcaoMenu(int opcao) {
        switch (opcao) {
            case 1:
                exibeTelaInclui();
                break;
            case 2:
                exibeTelaAltera();
                break;
            case 3:
                exibeTelaExclui();
                break;
            case 4:
                exibeLista();
                break;
            default:
                return false;
        }
        return true;
    }
    
    protected void exibeOpcoesMenu() {
        System.out.println("1 - Incluir");
        System.out.println("2 - Alterar");
        System.out.println("3 - Excluir");
        System.out.println("4 - Listagem");
        System.out.println("0 - Voltar");
    }
    
    public void exibeMenu() {
        int opcao = -1;
        while (opcao != 0) {
            exibeOpcoesMenu();
            opcao = inputOpcao();
            if (opcao != 0) {
                if (!executaOpcaoMenu(opcao)) {
                    System.out.println("Informe uma opcao valida!");
                }
            }
        }             
    }
    
    public void exibeCadastro(ItemListaCadastro item) {
       System.out.println("----------------Dados Cadastrados----------------");
       System.out.println(item.getDescricao());
       System.out.println("-------------------------------------------------");
    }
    
}