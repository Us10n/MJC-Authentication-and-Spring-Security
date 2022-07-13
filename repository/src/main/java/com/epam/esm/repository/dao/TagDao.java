package com.epam.esm.repository.dao;

import com.epam.esm.domain.entity.Tag;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Data access object that perform operations with {@link Tag} model in database
 */
public interface TagDao extends Dao<Tag> {
    /**
     * Find object instance in database by name.
     *
     * @param name object name
     * @return {@link Optional Optional.ofNullable()} of found object.
     */
    Optional<Tag> findByName(String name);

    /**
     * Find widely used tag of user with highest cost of all orders optional.
     *
     * @return found tag.
     */
    List<Tag> findWidelyUsedTagsOfUserWithHighestCostOfAllOrders(Integer page, Integer limit);

    long countAllWidelyUsedTagsOfUserWithHighestCostOfAllOrders();
}
