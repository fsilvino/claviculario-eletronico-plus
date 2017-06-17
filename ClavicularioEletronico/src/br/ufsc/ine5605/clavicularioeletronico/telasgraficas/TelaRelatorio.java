package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

import br.ufsc.ine5605.clavicularioeletronico.transferencias.DadosEventoClaviculario;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.JFrame;
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
    
    public TelaRelatorio() {
        super("Relatório");
        init();
    }
    
    private void init() {
        Container container = getContentPane();
        container.setLayout(new GridBagLayout());
        
        GridBagConstraints constraint = new GridBagConstraints();
        
        Dimension tamanhoTela = new Dimension(500, 600);
        
        this.table = new JTable();
        this.table.setPreferredScrollableViewportSize(new Dimension(tamanhoTela.width - 50, tamanhoTela.height - 50));
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
        tbModel.addColumn("Placa");
        
        for (DadosEventoClaviculario evento : eventos) {
            tbModel.addRow(new Object[] { evento.dataHora.getTime().toString(), evento.evento, evento.matricula, evento.placa });
        }
        
        this.table.setModel(tbModel);
        this.repaint();
    }
    
}
