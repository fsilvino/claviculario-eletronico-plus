package br.ufsc.ine5605.clavicularioeletronico.persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 * 
 * @author Flávio
 * @param <K> Tipo da chave
 * @param <V> Tipo da entidade
 */
public abstract class BaseDAO<K, V> {
    
    private HashMap<K, V> cache;
    private final String fileName;
    
    protected BaseDAO() {
        this.cache = new HashMap<>();
        this.fileName = this.getFileName();
        load();
    }
    
    protected abstract String getFileName();
    
    public V get(K key) {
        return this.cache.get(key);
    }
    
    public void put(K key, V value) {
        if (key != null && value != null) {
            this.cache.put(key, value);
            persist();
        } else {
            throw new RuntimeException("A chave e o valor devem ser diferentes de null!");
        }
    }
    
    public void load() {
        
        try {
            FileInputStream fi = new FileInputStream(fileName);
            ObjectInputStream oi = new ObjectInputStream(fi);
            
            this.cache = (HashMap<K, V>) oi.readObject();
            
            oi.close();
            fi.close();
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public void remove(K key) {
        this.cache.remove(key);
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
    
    public Collection<V> getList() {
        return this.cache.values();
    }
    
}
