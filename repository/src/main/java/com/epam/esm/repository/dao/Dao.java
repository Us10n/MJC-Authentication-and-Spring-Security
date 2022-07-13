package com.epam.esm.repository.dao;

import java.util.List;
import java.util.Optional;

/**
 * Data access object that performs operations with databases
 *
 * @param <T> define model DAO operates with
 * @see , GiftCertificateDaoTagDao
 */
public interface Dao<T> {
    /**
     * Create {@link T} object instance in database.
     *
     * @param object object to create in database
     * @return {@link Optional Optional.ofNullable()} of created object
     */
    T create(T object);

    /**
     * Find object instance in database by id.
     *
     * @param id object id
     * @return {@link Optional Optional.ofNullable()} of found object.
     */
    Optional<T> findById(long id);

    /**
     * Find limit entities from page.
     *
     * @param page  the page number
     * @param limit the page size
     * @return found objects
     */
    List<T> findAll(int page, int limit);

    /**
     * Count amount of all objects.
     *
     * @return amount
     */
    long countAll();

    /**
     * Delete object instance in database by id.
     *
     * @param id objects id
     */
    void deleteById(long id);
}
