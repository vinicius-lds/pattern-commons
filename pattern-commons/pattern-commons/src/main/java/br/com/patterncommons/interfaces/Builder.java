package br.com.patterncommons.interfaces;

/**
 * Help to instatiate complex objects
 *
 * @param <T> The complex object that it's going to be created
 */
public interface Builder<T> {

    /**
     * Build a complex object given its attributes
     *
     * @return the builded object
     */
    T build();

}
