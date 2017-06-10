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

/**
 *
 * @author Flávio
 */
public abstract class TelaBaseCadastro<DTO> extends JFrame {
    
    protected List<ITelaBaseCadastroObserver> observers;
    protected String acao;
    protected JButton btOk;
    protected JButton btCancela;
    private ActionManager actManager;
    
    public TelaBaseCadastro(String titulo) {
        super(titulo);
        this.observers = new ArrayList<>();
    }
    
    protected void init() {
        
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());
        
        this.actManager = new ActionManager();
        
        this.btOk = new JButton("OK");
        this.btOk.setActionCommand(AcoesCadastro.ACAO_OK);
        this.btOk.addActionListener(actManager);
        GridBagConstraints constraint = new GridBagConstraints();
        
        constraint.gridheight = 1;
        constraint.gridwidth = 1;
        constraint.gridx = this.getColunaBtOk();
        constraint.gridy = this.getLinhaBtOk();
        container.add(this.btOk, constraint);
        
        this.btCancela = new JButton("Cancela");
        this.btCancela.setActionCommand(AcoesCadastro.ACAO_CANCELA);
        this.btCancela.addActionListener(actManager);
        constraint.gridx++;
        container.add(this.btCancela, constraint);
        
        setSize(getTamanhoJanela());
        setLocationRelativeTo(null);
    }
    
    protected abstract int getColunaBtOk();
    protected abstract int getLinhaBtOk();
    protected abstract Dimension getTamanhoJanela();
    
    public void addObserver(ITelaBaseCadastroObserver observer) {
        if (!this.observers.contains(observer)) {
            this.observers.add(observer);
        }
    }
    
    public void removeObserver(ITelaBaseCadastroObserver observer) {
        this.observers.remove(observer);
    }
    
    private void notificaSalvar() {
        for (ITelaBaseCadastroObserver observer : this.observers) {
            observer.salvaCadastro(this.acao);
        }
    }
    
    public void abreInclusao() {
        this.acao = AcoesCadastro.ACAO_INCLUI;
        this.reset();
        setVisible(true);
    }
    
    public void abreAlteracao(DTO item) {
        this.acao = AcoesCadastro.ACAO_ALTERA;
        setDados(item);
        setVisible(true);
    }
    
    public void fechaTela() {
        setVisible(false);
    }
    
    protected abstract void reset();
    protected abstract void setDados(DTO item);
    public abstract DTO getDados();
    
    private class ActionManager implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            if (AcoesCadastro.ACAO_OK.equals(e.getActionCommand())) {
                notificaSalvar();
                fechaTela();
            } else if (AcoesCadastro.ACAO_CANCELA.equals(e.getActionCommand())) {
                fechaTela();
            }
            
        }
        
    }
    
}
