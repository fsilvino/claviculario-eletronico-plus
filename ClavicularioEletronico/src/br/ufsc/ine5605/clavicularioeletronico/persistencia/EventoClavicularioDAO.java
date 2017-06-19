package br.ufsc.ine5605.clavicularioeletronico.persistencia;

import br.ufsc.ine5605.clavicularioeletronico.entidades.EventoClaviculario;
import br.ufsc.ine5605.clavicularioeletronico.enums.Evento;
import java.util.ArrayList;

/**
 * 
 * @author Flávio
 */
public class EventoClavicularioDAO extends BaseDAO<Integer, EventoClaviculario> {
    
    private static EventoClavicularioDAO instance;
    
    public static EventoClavicularioDAO getInstance() {
        if (instance == null) {
            instance = new EventoClavicularioDAO();
        }
        return instance;
    }
    
    private EventoClavicularioDAO() {
        super();
    }

    @Override
    protected String getFileName() {
        return "eventoclaviculario.cla";
    }
    
    public void put(EventoClaviculario evento) {
        int key = 10000 - this.cache.size();
        this.cache.put(key, evento);
        persist();
    }
    
    public ArrayList<EventoClaviculario> getListByMatricula(Integer matricula) {
        ArrayList<EventoClaviculario> lista = new ArrayList<>();
        for (EventoClaviculario eventoClaviculario : getList()) {
            if (eventoClaviculario.getMatricula().equals(matricula)) {
                lista.add(eventoClaviculario);
            }
        }
        return lista;
    }
    
    public ArrayList<EventoClaviculario> getListByPlaca(String placa) {
        ArrayList<EventoClaviculario> lista = new ArrayList<>();
        for (EventoClaviculario eventoClaviculario : getList()) {
            if (eventoClaviculario.getPlaca().equals(placa)) {
                lista.add(eventoClaviculario);
            }
        }
        return lista;
    }
    
    public ArrayList<EventoClaviculario> getListByEvento(Evento evento) {
        ArrayList<EventoClaviculario> lista = new ArrayList<>();
        for (EventoClaviculario eventoClaviculario : getList()) {
            if (eventoClaviculario.getEvento() == evento) {
                lista.add(eventoClaviculario);
            }
        }
        return lista;
    }
    
}