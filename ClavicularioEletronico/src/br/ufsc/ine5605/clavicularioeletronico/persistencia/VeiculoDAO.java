package br.ufsc.ine5605.clavicularioeletronico.persistencia;

import br.ufsc.ine5605.clavicularioeletronico.entidades.Veiculo;

/**
 *
 * @author Flávio
 */
public class VeiculoDAO extends BaseDAO<String, Veiculo> {
    
    private static VeiculoDAO instance;
    
    public static VeiculoDAO getInstance() {
        if (instance == null) {
            instance = new VeiculoDAO();
        }
        return instance;
    }

    private VeiculoDAO() {
        super();
    }

    @Override
    protected String getFileName() {
        return "veiculos.cla";
    }
    
}
