package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.telas.*;
import br.ufsc.ine5605.clavicularioeletronico.controladores.ControladorSistema;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 
 * @author Gabriel
 */
public class TelaSistemaNew extends JFrame {
    
    private MenuBar menuBar;
    private ActionManager actManager;

    public TelaSistemaNew() {
        super("Claviculário Eletrônico - UFSC/2017");
        init();
    }
      
    private void init() {
        actManager = new ActionManager();
        
        menuBar = new MenuBar();
        
        /*
        Menu menu = new Menu("Claviculário");
        MenuItem menuItem = new MenuItem("Retirar Chave...");
        menu.add(menuItem);
        
        menuItem = new MenuItem("Devolver Chave...");
        menu.add(menuItem);
        
        menuItem = new MenuItem("Relatório...");
        menu.add(menuItem);
        menuBar.add(menu);
        */
        
        Menu menu = new Menu("Cadastros");
        MenuItem menuItem = new MenuItem("Funcionários...");
        menuItem.setActionCommand(AcoesTelaPrincipal.COMANDO_CADASTRO_FUNCIONARIO);
        menuItem.addActionListener(actManager);
        menu.add(menuItem);
        
        menuItem = new MenuItem("Veículos...");
        menuItem.setActionCommand(AcoesTelaPrincipal.COMANDO_CADASTRO_VEICULO);
        menuItem.addActionListener(actManager);
        menu.add(menuItem);
        menuBar.add(menu);
        
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMenuBar(menuBar);
    }

    private class ActionManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (AcoesTelaPrincipal.COMANDO_CADASTRO_FUNCIONARIO.equals(e.getActionCommand())) {
                ControladorSistema.getInstance().abreCadastroFuncionario();
            } else if (AcoesTelaPrincipal.COMANDO_CADASTRO_VEICULO.equals(e.getActionCommand())) {
                ControladorSistema.getInstance().abreCadastroVeiculo();
            }
        }
        
    }
    
    private class AcoesTelaPrincipal {
        
        public static final String COMANDO_CADASTRO_FUNCIONARIO = "ABRE_CADASTRO_FUNCIONARIO";
        public static final String COMANDO_CADASTRO_VEICULO = "ABRE_CADASTRO_VEICULO";
        
    }
    
}
