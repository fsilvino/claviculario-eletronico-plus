package br.ufsc.ine5605.clavicularioeletronico.persistencia;

import br.ufsc.ine5605.clavicularioeletronico.entidades.SaidaVeiculo;

/**
 *
 * @author Flávio
 */
public class SaidaVeiculoDAO extends BaseDAO<String, SaidaVeiculo> {
    
    private static SaidaVeiculoDAO instance;
    
    public static SaidaVeiculoDAO getInstance() {
        if (instance == null) {
            instance = new SaidaVeiculoDAO();
        }
        return instance;
    }
    
    private SaidaVeiculoDAO() {
        super();
    }

    @Override
    protected String getFileName() {
        return "saidaveiculo.cla";
    }
    
}