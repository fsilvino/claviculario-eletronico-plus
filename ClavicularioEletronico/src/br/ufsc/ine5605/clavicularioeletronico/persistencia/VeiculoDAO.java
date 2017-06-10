/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.ine5605.clavicularioeletronico.persistencia;

import br.ufsc.ine5605.clavicularioeletronico.entidades.Veiculo;

/**
 *
 * @author Flávio
 */
public class VeiculoDAO extends BaseDAO<String, Veiculo> {

    public VeiculoDAO() {
        super();
    }

    @Override
    protected String getFileName() {
        return "veiculos.cla";
    }
    
    
    
}
