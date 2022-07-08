package com.epam.esm.web.controller;

import com.epam.esm.domain.dto.OrderDetailDto;
import com.epam.esm.domain.dto.OrderDto;
import com.epam.esm.domain.dto.OrderInputDto;
import com.epam.esm.service.service.OrderService;
import com.epam.esm.web.hateoas.impl.OrderHateoasAdder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

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
    public PagedModel<OrderDto> readAllOrders(@RequestParam(name = "page", defaultValue = "1") @Positive Integer page,
                                                    @RequestParam(name = "limit", defaultValue = "10") @Positive Integer limit) {
        PagedModel<OrderDto> orderDtos = orderService.readAll(page, limit);
        orderHateoasAdder.addLinksToCollection(orderDtos);

        return orderDtos;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto readOrderById(@PathVariable long id) {
        OrderDto orderDto = orderService.readById(id);
        orderHateoasAdder.addLinksToEntity(orderDto);

        return orderDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestBody OrderInputDto order) {
        OrderDto orderDto = orderService.create(order);
        orderHateoasAdder.addLinksToEntity(orderDto);

        return orderDto;
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<OrderDto> readOrdersByUserId(@PathVariable long id,
                                                         @RequestParam(name = "page", defaultValue = "1") @Positive Integer page,
                                                         @RequestParam(name = "limit", defaultValue = "10") @Positive Integer limit) {
        PagedModel<OrderDto> orderDtos = orderService.readOrdersByUserId(id, page, limit);
        orderHateoasAdder.addLinksToCollection(orderDtos);

        return orderDtos;
    }
}
