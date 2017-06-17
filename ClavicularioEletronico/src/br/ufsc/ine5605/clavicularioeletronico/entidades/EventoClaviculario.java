package br.ufsc.ine5605.clavicularioeletronico.entidades;

import br.ufsc.ine5605.clavicularioeletronico.enums.Evento;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosEventoClaviculario;
import java.io.Serializable;
import java.util.Calendar;

public class EventoClaviculario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Evento evento;
    private Calendar dataHora;
    private Integer matricula;
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

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public DadosEventoClaviculario getDTO() {
        return new DadosEventoClaviculario(getEvento(), getDataHora(), getMatricula(), getPlaca());
    }
    
}