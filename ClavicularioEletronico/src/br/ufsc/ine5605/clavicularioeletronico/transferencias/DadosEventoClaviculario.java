package br.ufsc.ine5605.clavicularioeletronico.transferencias;

import br.ufsc.ine5605.clavicularioeletronico.enums.Evento;
import java.util.Calendar;

/**
 *
 * @author Flávio
 */
public class DadosEventoClaviculario {
    
    public Evento evento;
    public Calendar dataHora;
    public int matricula;
    public String placa;

    public DadosEventoClaviculario(Evento evento, Calendar dataHora, int matricula, String placa) {
        this.evento = evento;
        this.dataHora = dataHora;
        this.matricula = matricula;
        this.placa = placa;
    }
    
}
