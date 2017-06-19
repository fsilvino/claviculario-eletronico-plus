package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.telas.*;
import br.ufsc.ine5605.clavicularioeletronico.controladores.ControladorSistema;
import br.ufsc.ine5605.clavicularioeletronico.enums.Cargo;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 
 * @author Gabriel
 */
public class TelaSistemaNew extends JFrame {
    
    private MenuBar menuBar;
    private ActionManager actManager;
    private JButton btRetirarChave;
    private JButton btDevolverChave;

    public TelaSistemaNew() {
        super("Claviculário Eletrônico - UFSC/2017");
        init();
    }
      
    private void init() {
        actManager = new ActionManager();
        
        menuBar = new MenuBar();
        
        Menu menu = new Menu("Cadastros");
        MenuItem menuItem = new MenuItem("Funcionários");
        menuItem.setActionCommand(AcoesTelaPrincipal.COMANDO_CADASTRO_FUNCIONARIO);
        menuItem.addActionListener(actManager);
        menu.add(menuItem);
        
        menuItem = new MenuItem("Veículos");
        menuItem.setActionCommand(AcoesTelaPrincipal.COMANDO_CADASTRO_VEICULO);
        menuItem.addActionListener(actManager);
        menu.add(menuItem);
        menuBar.add(menu);
        
        menu = new Menu("Relatórios");
        menuItem = new MenuItem("Relatório Completo");
        menuItem.setActionCommand(AcoesTelaPrincipal.COMANDO_RELATORIO_COMPLETO);
        menuItem.addActionListener(actManager);
        menu.add(menuItem);
        
        menuItem = new MenuItem("Relatório por Funcionário");
        menuItem.setActionCommand(AcoesTelaPrincipal.COMANDO_RELATORIO_POR_FUNCIONARIO);
        menuItem.addActionListener(actManager);
        menu.add(menuItem);
        
        menuItem = new MenuItem("Relatório por Veículo");
        menuItem.setActionCommand(AcoesTelaPrincipal.COMANDO_RELATORIO_POR_VEICULO);
        menuItem.addActionListener(actManager);
        menu.add(menuItem);
        
        menuItem = new MenuItem("Relatório por Evento");
        menuItem.setActionCommand(AcoesTelaPrincipal.COMANDO_RELATORIO_POR_EVENTO);
        menuItem.addActionListener(actManager);
        menu.add(menuItem);
        
        menuBar.add(menu);
        
        Container container = getContentPane();
        GridBagLayout l = new GridBagLayout();
        l.rowHeights = new int[] { 70, 70 };
        container.setLayout(l);
        
        GridBagConstraints constraints = new GridBagConstraints();
        
        this.btRetirarChave = new JButton("Retirar Chave");
        this.btRetirarChave.setPreferredSize(new Dimension(250, 60));
        this.btRetirarChave.setActionCommand(AcoesTelaPrincipal.COMANDO_RETIRAR_CHAVE);
        this.btRetirarChave.addActionListener(actManager);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        container.add(this.btRetirarChave, constraints);
        
        this.btDevolverChave = new JButton("Devolver Chave");
        this.btDevolverChave.setPreferredSize(new Dimension(250, 60));
        this.btDevolverChave.setActionCommand(AcoesTelaPrincipal.COMANDO_DEVOLVER_CHAVE);
        this.btDevolverChave.addActionListener(actManager);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        container.add(this.btDevolverChave, constraints);
        
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
            } else if (AcoesTelaPrincipal.COMANDO_RETIRAR_CHAVE.equals(e.getActionCommand())) {
                ControladorSistema.getInstance().abreRetirarChaveClaviculario();
            } else if (AcoesTelaPrincipal.COMANDO_DEVOLVER_CHAVE.equals(e.getActionCommand())) {
                ControladorSistema.getInstance().abreDevolverChaveClaviculario();
            }
        }     
    }
    
    private class AcoesTelaPrincipal {
        
        public static final String COMANDO_CADASTRO_FUNCIONARIO = "ABRE_CADASTRO_FUNCIONARIO";
        public static final String COMANDO_CADASTRO_VEICULO = "ABRE_CADASTRO_VEICULO";
        public static final String COMANDO_RETIRAR_CHAVE = "RETIRAR_CHAVE";
        public static final String COMANDO_DEVOLVER_CHAVE = "DEVOLVER_CHAVE";
        public static final String COMANDO_RELATORIO_COMPLETO = "RELATORIO_COMPLETO";
        public static final String COMANDO_RELATORIO_POR_FUNCIONARIO = "RELATORIO_POR_FUNCIONARIO";
        public static final String COMANDO_RELATORIO_POR_VEICULO = "RELATORIO_POR_VEICULO";
        public static final String COMANDO_RELATORIO_POR_EVENTO = "RELATORIO_POR_EVENTO";
        
       
    }
    
    
    
}
