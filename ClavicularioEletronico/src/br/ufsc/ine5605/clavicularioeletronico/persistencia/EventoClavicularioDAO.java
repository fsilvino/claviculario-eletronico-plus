package br.ufsc.ine5605.clavicularioeletronico.persistencia;

import br.ufsc.ine5605.clavicularioeletronico.entidades.EventoClaviculario;

/**
 * 
 * @author Flávio
 */
public class EventoClavicularioDAO extends BaseDAO<String, EventoClaviculario> {
    
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
    
}