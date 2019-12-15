package br.com.pattern.commons.interfaces;

/**
 * This interface is needed to provider objects that can be used in the ObjectPool class
 */
public interface PoolObject {

    /**
     * This method must be implemented with the objective of clearing possible states that could interfere with the use
     * of this object by another consumer
     */
    void clear();

}
