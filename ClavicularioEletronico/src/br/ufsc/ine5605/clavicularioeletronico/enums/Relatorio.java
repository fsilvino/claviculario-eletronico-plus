package br.ufsc.ine5605.clavicularioeletronico.enums;

/**
 *
 * @author Flávio
 */
public enum Relatorio {
    
    COMPLETO("Relatório de Eventos Completo"),
    POR_FUNCIONARIO("Relatório de Eventos por Funcionário"),
    POR_VEICULO("Relatório de Eventos por Veículo"),
    POR_EVENTO("Relatório de Eventos por Evento");
    
    public String titulo;
    
    Relatorio(String titulo) {
        this.titulo = titulo;
    }
    
}
