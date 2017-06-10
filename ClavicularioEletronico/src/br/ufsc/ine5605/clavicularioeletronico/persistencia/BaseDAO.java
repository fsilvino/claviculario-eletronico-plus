package br.ufsc.ine5605.clavicularioeletronico.persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Flávio
 * @param <K>
 * @param <V>
 */
public abstract class BaseDAO<K, V> {
    
    private HashMap<K, V> cache;
    private final String fileName;
    
    protected BaseDAO() {
        this.cache = new HashMap<>();
        this.fileName = this.getFileName();
    }
    
    protected abstract String getFileName();
    
    public V get(K key) {
        return this.cache.get(key);
    }
    
    public void put(K key, V value) {
        if (key != null && value != null) {
            this.cache.put(key, value);
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
        } catch (IOException iOException) {
            
        } catch (ClassNotFoundException classNotFoundException) {
            
        }
            
        
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
            
        } catch (IOException iOException) {
            
        }
    }
    
    public Collection<V> getList() {
        return this.cache.values();
    }
    
}
