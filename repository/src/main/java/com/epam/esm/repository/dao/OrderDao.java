package com.epam.esm.repository.dao;

import com.epam.esm.domain.entity.OrderDetail;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order dao.
 */
public interface OrderDao {
    /**
     * Create {@link OrderDetail} object instance in database.
     *
     * @param object object to create in database
     * @return {@link Optional Optional.ofNullable()} of created object
     */
    OrderDetail create(OrderDetail object);

    /**
     * Find object instance in database by id.
     *
     * @param id object id
     * @return {@link Optional Optional.ofNullable()} of found object.
     */
    Optional<OrderDetail> findById(long id);

    /**
     * Find limit entities from page.
     *
     * @param page  the page number
     * @param limit the page size
     * @return found objects
     */
    List<OrderDetail> findAll(int page, int limit);

    /**
     * Count amount of all objects.
     *
     * @return amount
     */
    long countAll();

    /**
     * Find orders by user id.
     *
     * @param id    the id
     * @param page  the page
     * @param limit the limit
     * @return the list of users from page.
     */
    List<OrderDetail> findOrdersByUserId(long id, Integer page, Integer limit);
}
