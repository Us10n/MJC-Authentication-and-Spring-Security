package com.epam.esm.service.service;

/**
 * Interface of service that performs {@link CRD} operations. Plus update operation
 *
 * @param <T> Dto class
 * @param <R> Model class
 */
public interface CRUD<T, R> extends CRD<T, R> {
    /**
     * Update T type object.
     *
     * @param object object with new values
     * @return instance of updated object
     */
    T update(T object);
}
