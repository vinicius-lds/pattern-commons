package br.com.patterncommons.concretes;

import br.com.patterncommons.interfaces.PoolObject;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class has the objective to centralize the creation of new object of a certain type, and only instantiate objects
 * that actually need to exist, therefore providing better performance for the software being written
 *
 * @param <T> The object type being managed in the pool
 */
public final class ObjectPool<T extends PoolObject> {

    private List<T> pool = Collections.synchronizedList(new LinkedList<>());
    private Supplier<T> poolObjectSupplier;

    /**
     * Instantiates a new ObjectPool given a valid PoolObjectPupplier
     *
     * @param poolObjectSupplier supplier that returns a new object to be put into the pool
     */
    public ObjectPool(Supplier<T> poolObjectSupplier) {
        setPoolObjectSupplier(poolObjectSupplier);
    }

    /**
     * Sets the PoolObject Supplier needed to populate the pool
     *
     * @param poolObjectSupplier supplier that returns a new object to be put into the pool
     */
    private void setPoolObjectSupplier(Supplier<T> poolObjectSupplier) {
        if (poolObjectSupplier == null) {
            throw new NullPointerException("Object supplier cannot be null");
        } else {
            this.poolObjectSupplier = poolObjectSupplier;
        }
    }

    /**
     * Given a consumer, acquires a object from the pool, and does a certain action to it
     *
     * @param consumer the action you want to do with the instance of the given object
     */
    public void with(Consumer<T> consumer) {
        var object = acquire();
        consumer.accept(object);
        object.clear();
        release(object);
    }

    /**
     * Given a function, acquires a object from the pool, and does a certain action to it, and return the result
     *
     * @param function Function provided that can use the object from the pool
     * @param <E>      Type of the return value of the function provided
     * @return return value from the function provided
     */
    public <E> E with(Function<T, E> function) {
        var object = acquire();
        var result = function.apply(object);
        object.clear();
        release(object);
        return result;
    }

    /**
     * Acquires a object from the pool, that must be returned to it after its use has come to a end
     *
     * @return object from the pool
     */
    private synchronized T acquire() {
        if (pool.isEmpty()) {
            pool.add(poolObjectSupplier.get());
        }
        return pool.get(0);
    }

    /**
     * Releases the lock from the object by putting him back in the pool, where it can again be taken to be used by another consumer
     *
     * @param object object beeing put into the pool
     */
    private void release(T object) {
        pool.add(object);
    }

}
