package com.epam.esm.service.service;

import com.epam.esm.domain.dto.OrderDetailDto;
import com.epam.esm.domain.dto.Order;
import org.springframework.hateoas.PagedModel;

import java.util.List;

/**
 * The interface Order service.
 */
public interface OrderService {
    /**
     * Create order.
     *
     * @param object the object
     * @return the order detail dto
     */
    OrderDetailDto create(Order object);

    /**
     * Create orders.
     *
     * @param object the object
     * @return the order detail dto
     */
    List<OrderDetailDto> create(List<Order> object);

    /**
     * Read all paged model.
     *
     * @param page  the page
     * @param limit the limit
     * @return the paged model
     */
    PagedModel<OrderDetailDto> readAll(Integer page, Integer limit);

    /**
     * Read by id order detail dto.
     *
     * @param id the id
     * @return the order detail dto
     */
    OrderDetailDto readById(long id);

    /**
     * Read orders by user id paged model.
     *
     * @param id    the id
     * @param page  the page
     * @param limit the limit
     * @return the paged model
     */
    PagedModel<OrderDetailDto> readOrdersByUserId(long id, Integer page, Integer limit);
}
