package com.funguscow.splat.functions;

/**
 * void -> T functional interface
 * @param <T> Type of returned object(s)
 */
public interface Generator<T> {

    /**
     * Run this function
     * @return This function's return value
     */
    T get();

}
