package br.ufsc.ine5605.clavicularioeletronico.persistencia;

import br.ufsc.ine5605.clavicularioeletronico.entidades.PermissaoUsoVeiculo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Flávio
 */
public class PermissaoUsoVeiculoDAO {
    
    private List<PermissaoUsoVeiculo> cache;
    private final String fileName;
    
    private static PermissaoUsoVeiculoDAO instance;
    
    public static PermissaoUsoVeiculoDAO getInstance() {
        if (instance == null) {
            instance = new PermissaoUsoVeiculoDAO();
        }
        return instance;
    }
    
    private PermissaoUsoVeiculoDAO() {
        this.cache = new ArrayList<>();
        this.fileName = "permissoes.cla";
        load();
    }
    
    public PermissaoUsoVeiculo get(int index) {
        return this.cache.get(index);
    }
    
    public void add(PermissaoUsoVeiculo permissaoUsoVeiculo) {
        if (permissaoUsoVeiculo != null) {
            this.cache.add(permissaoUsoVeiculo);
            persist();
        } else {
            throw new RuntimeException("O valor deve ser diferente de null!");
        }
    }
    
    public void load() {
        try {
            FileInputStream fi = new FileInputStream(fileName);
            ObjectInputStream oi = new ObjectInputStream(fi);
            
            this.cache = (ArrayList<PermissaoUsoVeiculo>) oi.readObject();
            
            oi.close();
            fi.close();
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public void remove(int index) {
        this.cache.remove(index);
        persist();
    }
    
    public void remove(PermissaoUsoVeiculo permissao) {
        this.cache.remove(permissao);
        persist();
    }
    
    public void persist() {
        
        try {
            FileOutputStream fo = new FileOutputStream(this.fileName);
            ObjectOutputStream oo = new ObjectOutputStream(fo);
            
            oo.writeObject(this.cache);

            oo.flush();
            fo.flush();

            oo.close();
            fo.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public List<PermissaoUsoVeiculo> getList() {
        return this.cache;
    }
    
}