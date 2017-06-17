package br.ufsc.ine5605.clavicularioeletronico.enums;

import java.io.Serializable;

/**
 * Eventos registrados pelo claviculário
 * @author Flávio
 */
public enum Evento implements Serializable {

    ACESSO_PERMITIDO,
    MATRICULA_INVALIDA,
    PERMISSAO_INSUFICIENTE,
    VEICULO_INDISPONIVEL,
    ACESSO_BLOQUEADO,
    USUARIO_BLOQUEADO,
    VEICULO_DEVOLVIDO;
    
    private static final long serialVersionUID = 1L;

}