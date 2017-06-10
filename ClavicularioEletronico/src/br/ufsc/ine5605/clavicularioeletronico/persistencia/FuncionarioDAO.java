/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.ine5605.clavicularioeletronico.persistencia;

import br.ufsc.ine5605.clavicularioeletronico.entidades.Funcionario;

/**
 *
 * @author Flávio
 */
public class FuncionarioDAO extends BaseDAO<Integer, Funcionario> {
    
    private static FuncionarioDAO instance;
    
    public static FuncionarioDAO getInstance() {
        if (instance == null) {
            instance = new FuncionarioDAO();
        }
        return instance;
    }

    private FuncionarioDAO() {
        super();
    }

    @Override
    protected String getFileName() {
        return "funcionarios.cla";
    }
    
}
