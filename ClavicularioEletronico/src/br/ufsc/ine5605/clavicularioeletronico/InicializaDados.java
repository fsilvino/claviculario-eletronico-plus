package br.ufsc.ine5605.clavicularioeletronico;

import br.ufsc.ine5605.clavicularioeletronico.controladores.*;
import br.ufsc.ine5605.clavicularioeletronico.enums.Cargo;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosFuncionario;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosVeiculo;
import java.util.Calendar;
import java.util.Date;

/**
 * Inicializa dados de teste
 * @author Flávio
 */
public class InicializaDados {
    
    private InicializaDados() {
        
    }
    
    public static void insere() {
        try {
            Calendar c = Calendar.getInstance();
            c.set(1980, 1, 1);
            Date nascimentoJean = c.getTime();
            
            c.set(1975, 5, 20);
            Date nascimentoPaulo = c.getTime();
            
            c.set(1985, 3, 15);
            Date nascimentoJoao = c.getTime();
            
            c.set(1970, 7, 9);
            Date nascimentoMaria = c.getTime();
            
            ControladorFuncionario.getInstance().inclui(new DadosFuncionario(1, "Jean", nascimentoJean, "12323423", Cargo.DIRETORIA, false));
            ControladorFuncionario.getInstance().inclui(new DadosFuncionario(2, "Paulo", nascimentoPaulo, "49679479", Cargo.MOTORISTA, false));
            ControladorFuncionario.getInstance().inclui(new DadosFuncionario(3, "Joao", nascimentoJoao, "27957303", Cargo.MOTORISTA, false));
            ControladorFuncionario.getInstance().inclui(new DadosFuncionario(4, "Maria", nascimentoMaria, "98623734", Cargo.MOTORISTA, false));
            
            ControladorVeiculo.getInstance().inclui(new DadosVeiculo("AAA-1234", "Fusca", "VW", 1973, 120000));
            ControladorVeiculo.getInstance().inclui(new DadosVeiculo("BBB-4321", "320i", "BMW", 2014, 50000));
            ControladorVeiculo.getInstance().inclui(new DadosVeiculo("CCC-5678", "Uno", "Fiat", 2015, 35000));
            ControladorVeiculo.getInstance().inclui(new DadosVeiculo("DDD-0987", "Uno", "Palio", 2003, 99333));
            
            ControladorPermissaoUsoVeiculo.getInstance().inclui(2, "AAA-1234");
            ControladorPermissaoUsoVeiculo.getInstance().inclui(2, "CCC-5678");
            ControladorPermissaoUsoVeiculo.getInstance().inclui(3, "AAA-1234");
            ControladorPermissaoUsoVeiculo.getInstance().inclui(4, "AAA-1234");
            ControladorPermissaoUsoVeiculo.getInstance().inclui(3, "BBB-4321");
            ControladorPermissaoUsoVeiculo.getInstance().inclui(4, "DDD-0987");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
