package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * 
 * @author Flávio
 */
public abstract class TelaBaseTable<E> extends JFrame {

    private JTable table;
    private JScrollPane spTable;
    private JButton btInclui;
    private JButton btAltera;
    private JButton btExclui;
    private ActionManager actManager;
    private final List<ITelaBaseTableObserver> observers;
    protected List<E> lista;
    
    public TelaBaseTable(String title) {
        super(title);
        this.lista = new ArrayList<>();
        this.observers = new ArrayList<>();
        init();
    }
    
    public void addObserver(ITelaBaseTableObserver observer) {
        if (!this.observers.contains(observer)) {
            this.observers.add(observer);
        }
    }
    
    public void removeObserver(ITelaBaseTableObserver observer) {
        this.observers.remove(observer);
    }
    
    public void setLista(List<E> lista) {
        this.lista = lista;
        this.atualizaDados();
    }
    
    protected E getItemSelecionado() {
        int row = this.table.getSelectedRow();
        if (row > -1) {
            return this.lista.get(row);
        }
        return null;
    }
    
    private void notificaInclui() {
        for (ITelaBaseTableObserver observer : this.observers) {
            observer.inclui();
        }
    }
    
    private void notificaAltera() {
        E item = this.getItemSelecionado();
        if (item != null) {
            for (ITelaBaseTableObserver observer : this.observers) {
                observer.altera(item);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado!");
        }
    }
    
    private void notificaExclui() {
        E item = this.getItemSelecionado();
        if (item != null) {
            for (ITelaBaseTableObserver observer : this.observers) {
                observer.exclui(item);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum item selecionado!");
        }
    }
    
    protected abstract Dimension getTamanhoTela();
    protected abstract TableModel getTableModel();

    protected void init() {
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());
        
        this.actManager = new ActionManager();
        
        Dimension tamanhoTela = this.getTamanhoTela();
        
        this.table = new JTable();
        this.table.setPreferredScrollableViewportSize(new Dimension(tamanhoTela.width - 100, tamanhoTela.height - 200));
        //this.table.setPreferredScrollableViewportSize(new Dimension(300, 200));
        this.table.setFillsViewportHeight(true);
        GridBagConstraints constraint = new GridBagConstraints();
        
        constraint.fill = GridBagConstraints.CENTER;
        constraint.gridwidth = 4;
        constraint.gridheight = 3;
        constraint.gridx = 0;
        constraint.gridy = 0;
        
        this.spTable = new JScrollPane(this.table);
        container.add(this.spTable, constraint);
        
        this.btInclui = new JButton("Inclui");
        this.btInclui.setActionCommand(AcoesCadastro.ACAO_INCLUI);
        this.btInclui.addActionListener(actManager);
        constraint.gridwidth = 1;
        constraint.gridheight = 1;
        constraint.gridx = 0;
        constraint.gridy = 4;
        container.add(this.btInclui, constraint);
        
        this.btAltera = new JButton("Altera");
        this.btAltera.setActionCommand(AcoesCadastro.ACAO_ALTERA);
        this.btAltera.addActionListener(actManager);
        constraint.gridwidth = 1;
        constraint.gridheight = 1;
        constraint.gridx = 1;
        constraint.gridy = 4;
        container.add(this.btAltera, constraint);
        
        this.btExclui = new JButton("Exclui");
        this.btExclui.setActionCommand(AcoesCadastro.ACAO_EXCLUI);
        this.btExclui.addActionListener(actManager);
        constraint.gridwidth = 1;
        constraint.gridheight = 1;
        constraint.gridx = 2;
        constraint.gridy = 4;
        container.add(this.btExclui, constraint);
        
        this.atualizaDados();
        
        this.setSize(tamanhoTela);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    protected void atualizaDados() {
        this.table.setModel(this.getTableModel());
        this.repaint();
    }
    
    private class ActionManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            String comando = e.getActionCommand();
            if (AcoesCadastro.ACAO_INCLUI.equals(comando)) {
                notificaInclui();
            } else if (AcoesCadastro.ACAO_ALTERA.equals(comando)) {
                notificaAltera();
            } else if (AcoesCadastro.ACAO_EXCLUI.equals(comando)) {
                notificaExclui();
            }
        }
        
    }
    
}