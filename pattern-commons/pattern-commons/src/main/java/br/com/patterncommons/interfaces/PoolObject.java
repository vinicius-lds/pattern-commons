package br.com.patterncommons.interfaces;

/**
 * This interface is needed to provide objects that can be reused in the ObjectPool class
 */
public interface PoolObject {

    /**
     * Clear's the properties from the object with the objective of turning the object back to the state when it was
     * first constructed
     */
    void clear();

}
