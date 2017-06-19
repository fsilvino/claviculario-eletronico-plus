package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.controladores.ControladorClaviculario;
import br.ufsc.ine5605.clavicularioeletronico.controladores.ControladorFuncionario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Funcionario;
import br.ufsc.ine5605.clavicularioeletronico.entidades.Veiculo;
import br.ufsc.ine5605.clavicularioeletronico.enums.Evento;
import br.ufsc.ine5605.clavicularioeletronico.persistencia.FuncionarioDAO;
import br.ufsc.ine5605.clavicularioeletronico.persistencia.VeiculoDAO;
import br.ufsc.ine5605.clavicularioeletronico.telasgraficas.TelaRelatorio.AcoesRelatorio;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosEventoClaviculario;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosFuncionario;
import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosVeiculo;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Flávio
 */
public class TelaRelatorio extends JFrame {
    
    private JTable table;
    protected JScrollPane spTable;
    private ActionManager actManager;
    private JComboBox cbOpcoes;
    private JLabel lbTipoRelatorio;
    
    public TelaRelatorio() {
        super("Relatório");       
        init();
    }
    
    private void init() {
        actManager = new ActionManager();
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());
        
        GridBagConstraints constraint = new GridBagConstraints();
        
        Dimension tamanhoTela = new Dimension(900, 700);
        
        this.table = new JTable();
        this.table.setPreferredScrollableViewportSize(new Dimension(tamanhoTela.width - 350, tamanhoTela.height - 100));
        this.table.setDefaultEditor(Object.class, null);
        this.table.setFillsViewportHeight(true);
        
        constraint.fill = GridBagConstraints.CENTER;
        constraint.gridwidth = 4;
        constraint.gridheight = 3;
        constraint.gridx = 0;
        constraint.gridy = 0;
        
        this.spTable = new JScrollPane(this.table);
        container.add(this.spTable, constraint);
        
        this.setSize(tamanhoTela);
        setLocationRelativeTo(null);
    }
    
    public void setRelatorio(String titulo, List<DadosEventoClaviculario> eventos) {
        setTitle(titulo);
        
        DefaultTableModel tbModel = new DefaultTableModel();
        
        tbModel.addColumn("Data/Hora");
        tbModel.addColumn("Evento");
        tbModel.addColumn("Matrícula");
        tbModel.addColumn("Nome");
        tbModel.addColumn("Placa");
        
        for (DadosEventoClaviculario evento : eventos) {
            tbModel.addRow(new Object[] { evento.dataHora.getTime().toString(), evento.evento, evento.matricula, ControladorFuncionario.getInstance().getFuncionarioPelaMatricula(evento.matricula).getNome(), evento.placa });
        }
        
        this.table.setModel(tbModel);
        this.repaint();
    }    

    private void removeComponentes() {
        Container container = getContentPane();
        if (cbOpcoes != null){
            container.remove(cbOpcoes);
        }
        if (lbTipoRelatorio != null) {
            container.remove(lbTipoRelatorio);
        }
    }
    public void relatorioEvento() {
        setRelatorio("Relatorio por Eventos", ControladorClaviculario.getInstance().geraRelatorioPorEvento(Evento.ACESSO_PERMITIDO));
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        removeComponentes();
        
        lbTipoRelatorio = new JLabel("Tipo de evento:");
        lbTipoRelatorio.setSize(120, 20);
        constraint.gridx = 1;
        constraint.gridy = 1;
        container.add(lbTipoRelatorio);
        
        cbOpcoes = new JComboBox();
        cbOpcoes.setSize(120, 20);
        constraint.gridx = 2;
        constraint.gridy = 2;
        cbOpcoes.addItem("ACESSO_PERMITIDO");
        cbOpcoes.addItem("MATRICULA_INVALIDA");
        cbOpcoes.addItem("PERMISSAO_INSUFICIENTE");
        cbOpcoes.addItem("VEICULO_INDISPONIVEL");
        cbOpcoes.addItem("ACESSO_BLOQUEADO");
        cbOpcoes.addItem("USUARIO_BLOQUEADO");
        cbOpcoes.addItem("VEICULO_DEVOLVIDO");
        this.cbOpcoes.addActionListener(actManager);
        container.add(cbOpcoes);    
        setVisible(true);
    }
    
    private String getEventoSelected() {
        return cbOpcoes.getSelectedItem().toString();
    }

    public void relatorioFuncionario() {
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());   
        GridBagConstraints constraint = new GridBagConstraints();
        removeComponentes();
        lbTipoRelatorio = new JLabel("Matricula:");
        lbTipoRelatorio.setSize(120, 20);
        constraint.gridx = 1;
        constraint.gridy = 1;
        container.add(lbTipoRelatorio);
        
        cbOpcoes = new JComboBox();
        cbOpcoes.setSize(120, 20);
        constraint.gridx = 2;
        constraint.gridy = 2;
        setNomes();
        this.cbOpcoes.setActionCommand(AcoesRelatorio.ACAO_FUNCIONARIO);
        this.cbOpcoes.addActionListener(actManager);
        container.add(cbOpcoes);
        setVisible(true);
    }
    
    private void setNomes() {
        ArrayList<DadosFuncionario> lista = new ArrayList<>();
        for (Funcionario funcionario : FuncionarioDAO.getInstance().getList()) {
           lista.add(funcionario.getDTO());
        }
        if (this.cbOpcoes != null) {
            this.cbOpcoes.removeAllItems();
            if (lista != null) {
                for (DadosFuncionario dto : lista) {
                    this.cbOpcoes.addItem(dto.matricula);
                }
            }
            this.cbOpcoes.setSelectedIndex(-1);
            repaint();
        }
    }
    

    private void setPlacas() {
        ArrayList<DadosVeiculo> lista = new ArrayList<>();
        for (Veiculo veiculo : VeiculoDAO.getInstance().getList()) {
           lista.add(veiculo.getDTO());
        }
        if (this.cbOpcoes != null) {
            this.cbOpcoes.removeAllItems();
            if (lista != null) {
                for (DadosVeiculo dto : lista) {
                    this.cbOpcoes.addItem(dto.placa);
                }
            }
            this.cbOpcoes.setSelectedIndex(-1);
            repaint();
        }
    }


    public void relatorioCompleto() {
        removeComponentes();
        setRelatorio("Relatorio Completo", ControladorClaviculario.getInstance().geraRelatorioCompleto());
        setVisible(true);
    }

    public void relatorioVeiculo() {
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());   
        GridBagConstraints constraint = new GridBagConstraints();
        removeComponentes();
        lbTipoRelatorio = new JLabel("Placa:");
        lbTipoRelatorio.setSize(120, 20);
        constraint.gridx = 1;
        constraint.gridy = 1;
        container.add(lbTipoRelatorio);
        
        cbOpcoes = new JComboBox();
        cbOpcoes.setSize(120, 20);
        constraint.gridx = 2;
        constraint.gridy = 2;
        setPlacas();
        this.cbOpcoes.setActionCommand(AcoesRelatorio.ACAO_VEICULO);
        this.cbOpcoes.addActionListener(actManager);
        container.add(cbOpcoes);
        setVisible(true);
    }

    private class ActionManager implements ActionListener {

        public ActionManager() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (getEventoSelected().equals(Evento.ACESSO_PERMITIDO.name())) {
                setRelatorio("Relatorio por Eventos", ControladorClaviculario.getInstance().geraRelatorioPorEvento(Evento.ACESSO_PERMITIDO));
            }
            if (getEventoSelected().equals(Evento.ACESSO_BLOQUEADO.name())) {
                setRelatorio("Relatorio por Eventos", ControladorClaviculario.getInstance().geraRelatorioPorEvento(Evento.ACESSO_BLOQUEADO));
            }
            if (getEventoSelected().equals(Evento.MATRICULA_INVALIDA.name())) {
                setRelatorio("Relatorio por Eventos", ControladorClaviculario.getInstance().geraRelatorioPorEvento(Evento.MATRICULA_INVALIDA));
            }
            if (getEventoSelected().equals(Evento.PERMISSAO_INSUFICIENTE.name())) {
                setRelatorio("Relatorio por Eventos", ControladorClaviculario.getInstance().geraRelatorioPorEvento(Evento.PERMISSAO_INSUFICIENTE));
            }
            if (getEventoSelected().equals(Evento.USUARIO_BLOQUEADO.name())) {
                setRelatorio("Relatorio por Eventos", ControladorClaviculario.getInstance().geraRelatorioPorEvento(Evento.USUARIO_BLOQUEADO));
            }
            if (getEventoSelected().equals(Evento.VEICULO_DEVOLVIDO.name())) {
                setRelatorio("Relatorio por Eventos", ControladorClaviculario.getInstance().geraRelatorioPorEvento(Evento.VEICULO_DEVOLVIDO));
            }
            if (getEventoSelected().equals(Evento.VEICULO_INDISPONIVEL.name())) {
                setRelatorio("Relatorio por Eventos", ControladorClaviculario.getInstance().geraRelatorioPorEvento(Evento.VEICULO_INDISPONIVEL));
            }
            if (e.getActionCommand().equals(AcoesRelatorio.ACAO_FUNCIONARIO)) {
                setRelatorio("Relatorio por Funcionario", ControladorClaviculario.getInstance().geraRelatorioPorMatricula(Integer.parseInt(getEventoSelected())));
            }            
            if (e.getActionCommand().equals(AcoesRelatorio.ACAO_VEICULO)) {
                setRelatorio("Relatorio por Veiculo", ControladorClaviculario.getInstance().geraRelatorioPorVeiculo(getEventoSelected()));
            }
            
            
            
        }
        
    }
    
    public abstract class AcoesRelatorio {

        public static final String ACAO_FUNCIONARIO = "FUNCIONARIO";
        public static final String ACAO_VEICULO = "ACAO_VEICULO";
    }
    
    
}
