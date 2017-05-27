package br.ufsc.ine5605.clavicularioeletronico.entidades;

import br.ufsc.ine5605.clavicularioeletronico.enums.Evento;
import java.util.Calendar;

public class EventoClaviculario {

    private Evento evento;
    private Calendar dataHora;
    private int matricula;
    private String placa;

    public EventoClaviculario(Evento evento, Calendar dataHora, int matricula, String placa) {
        this.evento = evento;
        this.dataHora = dataHora;
        this.matricula = matricula;
        this.placa = placa;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Calendar getDataHora() {
        return dataHora;
    }

    public void setDataHora(Calendar dataHora) {
        this.dataHora = dataHora;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
}