package com.epam.esm.service.service;

import com.epam.esm.domain.dto.OrderDetailDto;
import com.epam.esm.domain.dto.OrderDto;
import com.epam.esm.domain.dto.OrderInputDto;
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
    OrderDto create(OrderInputDto object);

    /**
     * Read all paged model.
     *
     * @param page  the page
     * @param limit the limit
     * @return the paged model
     */
    PagedModel<OrderDto> readAll(Integer page, Integer limit);

    /**
     * Read by id order detail dto.
     *
     * @param id the id
     * @return the order detail dto
     */
    OrderDto readById(long id);

    /**
     * Read orders by user id paged model.
     *
     * @param id    the id
     * @param page  the page
     * @param limit the limit
     * @return the paged model
     */
    PagedModel<OrderDto> readOrdersByUserId(long id, Integer page, Integer limit);
}
