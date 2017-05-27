package br.ufsc.ine5605.clavicularioeletronico.telas;

import br.ufsc.ine5605.clavicularioeletronico.controladores.ControladorVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.excecoes.PlacaJaCadastradaException;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosVeiculo;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.ItemListaCadastro;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.Listavel;

public class TelaVeiculo extends TelaCadastro {

    public TelaVeiculo() {
        super();
    }
    
    @Override
    public void exibeMenu() {
        System.out.println("----------------Menu Veiculo-----------------");
        super.exibeMenu();
    }
    
    @Override
    public void exibeTelaInclui() {
        try {
            DadosVeiculo novoVeiculo = entradaDadosVeiculo();
            ControladorVeiculo.getInstance().inclui(novoVeiculo);
            System.out.println("Veiculo cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        solicitaEnterParaContinuar();
    }

    @Override
    public void exibeTelaAltera() {
        try {
            ControladorVeiculo.getInstance().altera(inputPlaca());
            System.out.println("Veiculo alterado com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        
        solicitaEnterParaContinuar();
    }

    @Override
    public void exibeTelaExclui() {
        try {
            ControladorVeiculo.getInstance().exclui(inputPlaca());
            System.out.println("Veiculo excluido com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        solicitaEnterParaContinuar();
    }
    
    @Override
    public void exibeLista() {
        System.out.println("--------------Veiculos cadastrados---------------");
        for (Listavel item: ControladorVeiculo.getInstance().getListaItensCadastro()) {
            System.out.println(item.getDescricao());
        }
        System.out.println("-------------------------------------------------");
        solicitaEnterParaContinuar();
    }
    
    private DadosVeiculo entradaDadosVeiculo() throws Exception {
        String placa = inputPlaca();
        if (ControladorVeiculo.getInstance().veiculoExiste(placa)) {
            throw new PlacaJaCadastradaException(placa);
        }
        return new DadosVeiculo(placa, inputModelo(), inputMarca(), inputAno(), inputQuilometragemAtual());
    }
    
    public boolean pedeConfirmacaoExclusao(String modelo, String placa) {
        System.out.println("Deseja mesmo excluir o veiculo:");
        System.out.println("Modelo: "+modelo+"\tPlaca: "+placa);
        return pedeConfirmacao();
    }
        
    public boolean alteraModelo() {
        System.out.println("Deseja alterar modelo?");
        return pedeConfirmacao();
    }
    
    public boolean alteraMarca() {
        System.out.println("Deseja alterar marca?");
        return pedeConfirmacao();
    }
    
    public boolean alteraAno() {
        System.out.println("Deseja alterar ano?");
        return pedeConfirmacao();
    }
    
    public boolean alteraQuilometragem() {
        System.out.println("Deseja alterar quilometragem?");
        return pedeConfirmacao();
    }
   
    
}