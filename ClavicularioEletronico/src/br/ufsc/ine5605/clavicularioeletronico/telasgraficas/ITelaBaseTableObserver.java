package br.ufsc.ine5605.clavicularioeletronico.telasgraficas;

/**
 * 
 * @author Flávio
 * @param <E>
 */
public interface ITelaBaseTableObserver<E> {
    
    public void inclui();
    public void altera(E item);
    public void exclui(E item);
    
}
