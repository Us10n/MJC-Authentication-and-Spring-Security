package com.epam.esm.repository.dao;

import com.epam.esm.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    /**
     * Find object instance in database by id.
     *
     * @param id object id
     * @return {@link Optional Optional.ofNullable()} of found object.
     */
    Optional<User> findById(long id);

    /**
     * Find limit entities from page.
     *
     * @param page  the page number
     * @param limit the page size
     * @return found objects
     */
    List<User> findAll(int page, int limit);

    /**
     * Count amount of all objects.
     *
     * @return amount
     */
    long countAll();
}
