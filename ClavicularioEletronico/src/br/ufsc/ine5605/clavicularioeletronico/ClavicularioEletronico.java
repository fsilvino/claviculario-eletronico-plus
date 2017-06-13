package br.ufsc.ine5605.clavicularioeletronico;

import br.ufsc.ine5605.clavicularioeletronico.controladores.ControladorSistema;

/**
 * Classe main do sistema
 * @author Flávio
 */
public class ClavicularioEletronico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ControladorSistema.getInstance().inicia();
    }
    
}
