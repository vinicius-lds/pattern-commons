package br.com.pattern.commons.abstracts;

import br.com.pattern.commons.interfaces.PoolObject;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class has the objective to centralize the creation of new object of a certain type, and only instantiate objects
 * that actually need to exist, therefore providing better performance for the software being written
 *
 * @param <T> The object type being managed in the pool
 */
public abstract class ObjectPool<T extends PoolObject> {

    private List<T> pool = Collections.synchronizedList(new LinkedList<>());

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
     * Acquires a object from the pool, that must be returned to it after its use has come to a end
     *
     * @return object from the pool
     */
    private synchronized T acquire() {
        if (pool.isEmpty()) {
            pool.add(newObject());
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


    /**
     * This method must be implemented to feed the pool of objects every time it's required
     *
     * @return must return a valid instance of a object to be put into the pool
     */
    protected abstract T newObject();

}
