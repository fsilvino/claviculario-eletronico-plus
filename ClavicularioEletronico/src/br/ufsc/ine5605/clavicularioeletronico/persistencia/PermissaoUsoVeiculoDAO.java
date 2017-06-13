package br.ufsc.ine5605.clavicularioeletronico.persistencia;

import br.ufsc.ine5605.clavicularioeletronico.entidades.PermissaoUsoVeiculo;

/**
 *
 * @author Flávio
 */
public class PermissaoUsoVeiculoDAO extends BaseDAO<String, PermissaoUsoVeiculo> {
    
    private static PermissaoUsoVeiculoDAO instance;
    
    public static PermissaoUsoVeiculoDAO getInstance() {
        if (instance == null) {
            instance = new PermissaoUsoVeiculoDAO();
        }
        return instance;
    }
    
    private PermissaoUsoVeiculoDAO() {
        super();
    }

    @Override
    protected String getFileName() {
        return "permissoes.cla";
    }
    
}
