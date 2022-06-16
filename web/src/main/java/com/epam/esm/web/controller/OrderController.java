package com.epam.esm.web.controller;

import com.epam.esm.domain.dto.OrderDetailDto;
import com.epam.esm.domain.entity.Order;
import com.epam.esm.service.service.OrderService;
import com.epam.esm.web.hateoas.impl.OrderHateoasAdder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@Validated
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderHateoasAdder orderHateoasAdder;

    @Autowired
    public OrderController(OrderService orderService, OrderHateoasAdder hateoasAdder) {
        this.orderService = orderService;
        this.orderHateoasAdder = hateoasAdder;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<OrderDetailDto> readAllOrders(@RequestParam(name = "page", defaultValue = "1") @Positive Integer page,
                                                    @RequestParam(name = "limit", defaultValue = "10") @Positive Integer limit) {
        PagedModel<OrderDetailDto> orderDetailDtos = orderService.readAll(page, limit);
        orderHateoasAdder.addLinksToCollection(orderDetailDtos);

        return orderDetailDtos;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDetailDto readOrderById(@PathVariable long id) {
        OrderDetailDto orderDetailDto = orderService.readById(id);
        orderHateoasAdder.addLinksToEntity(orderDetailDto);

        return orderDetailDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDetailDto createOrder(@RequestBody Order order) {
        OrderDetailDto orderDetailDto = orderService.create(order);
        orderHateoasAdder.addLinksToEntity(orderDetailDto);

        return orderDetailDto;
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<OrderDetailDto> readOrdersByUserId(@PathVariable long id,
                                                         @RequestParam(name = "page", defaultValue = "1") @Positive Integer page,
                                                         @RequestParam(name = "limit", defaultValue = "10") @Positive Integer limit) {
        PagedModel<OrderDetailDto> orderDetailDtos = orderService.readOrdersByUserId(id, page, limit);
        orderHateoasAdder.addLinksToCollection(orderDetailDtos);

        return orderDetailDtos;
    }
}
