package br.ufsc.ine5605.clavicularioeletronico.enums;

import java.io.Serializable;

/**
 * Cargos dos funcionários
 * @author Flávio
 */
public enum Cargo implements Serializable {
    
    MOTORISTA("Motorista"),
    DIRETORIA("Diretoria");
    
    public String descricao;
    
    Cargo(String descricao) {
        this.descricao = descricao;
    }

}