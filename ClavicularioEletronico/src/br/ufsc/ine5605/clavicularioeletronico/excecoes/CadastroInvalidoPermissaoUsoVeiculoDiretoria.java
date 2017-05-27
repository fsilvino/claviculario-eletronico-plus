package br.ufsc.ine5605.clavicularioeletronico.excecoes;

/**
 * Exceção gerada ao tentar incluir/excluir uma permissão de uso de um veículo a
 * um funcionário que tem cargo de diretoria
 * @author Flávio
 */
public class CadastroInvalidoPermissaoUsoVeiculoDiretoria extends Exception {

    public CadastroInvalidoPermissaoUsoVeiculoDiretoria() {
        this(null);
    }

    public CadastroInvalidoPermissaoUsoVeiculoDiretoria(String msg) {
        super("Este funcionario tem acesso a todos os veiculos pois seu cargo eh de diretoria." + (msg == null || msg.trim().isEmpty() ? "" : "\n" + msg));
    }

}
