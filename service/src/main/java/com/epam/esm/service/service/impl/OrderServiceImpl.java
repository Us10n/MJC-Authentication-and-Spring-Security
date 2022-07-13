package com.epam.esm.service.service.impl;

import com.epam.esm.domain.dto.OrderDto;
import com.epam.esm.domain.dto.OrderInputDto;
import com.epam.esm.domain.entity.GiftCertificate;
import com.epam.esm.domain.entity.Order;
import com.epam.esm.domain.entity.OrderDetail;
import com.epam.esm.domain.entity.User;
import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.OrderDao;
import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.service.exception.EmptyListRequestedException;
import com.epam.esm.service.exception.ExceptionHolder;
import com.epam.esm.service.exception.IncorrectParameterException;
import com.epam.esm.service.exception.NoSuchElementException;
import com.epam.esm.service.mapper.OrderMapper;
import com.epam.esm.service.service.OrderService;
import com.epam.esm.service.util.handler.DateHandler;
import com.epam.esm.service.util.validator.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.service.exception.ExceptionMessageKey.*;

/**
 * The type Order service.
 */
@Component
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;

    /**
     * Instantiates a new Order service.
     *
     * @param orderDao           the order dao
     * @param userDao            the user dao
     * @param giftCertificateDao the gift certificate dao
     */
    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, GiftCertificateDao giftCertificateDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public OrderDto create(OrderInputDto orderInputDto) {
        ExceptionHolder exceptionHolder = new ExceptionHolder();
        OrderValidator.isOrderInputDtoValid(orderInputDto, exceptionHolder);
        if (!exceptionHolder.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionHolder);
        }
        Optional<User> customerOptional = userDao.findById(orderInputDto.getUserId());
        User customer = customerOptional
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Long giftCertificateId : orderInputDto.getGiftCertificateIds()) {
            Optional<GiftCertificate> requestedCertificateOptional = giftCertificateDao.findById(giftCertificateId);
            if (!requestedCertificateOptional.isPresent()) {
                exceptionHolder.addException(GIFT_CERTIFICATE_NOT_FOUND_BY_ID, giftCertificateId);
            }
            GiftCertificate requestedCertificate = requestedCertificateOptional
                    .orElseThrow(() -> {
                        exceptionHolder.addException(GIFT_CERTIFICATE_NOT_FOUND_BY_ID, giftCertificateId);
                        return new IncorrectParameterException(exceptionHolder);
                    });

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setGiftCertificate(requestedCertificate);
            orderDetail.setPrice(requestedCertificate.getPrice());
            orderDetails.add(orderDetail);
        }
        Order order = new Order();
        order.setPurchaseTime(DateHandler.getCurrentDate());
        order.setUser(customer);
        order.setOrderDetails(orderDetails);
        Order createdOrder = orderDao.create(order);

        return OrderMapper.INSTANCE.mapToDto(createdOrder);
    }

    @Override
    public PagedModel<OrderDto> readAll(Integer page, Integer limit) {
        List<OrderDto> orderDtos = orderDao.findAll(page, limit)
                .stream()
                .map(OrderMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());

        if (orderDtos.isEmpty()) {
            throw new EmptyListRequestedException();
        }
        long totalNumberOfEntities = orderDao.countAll();
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(limit, page, totalNumberOfEntities);
        return PagedModel.of(orderDtos, metadata);
    }

    @Override
    public OrderDto readById(long id) {
        Optional<Order> optionalOrder = orderDao.findById(id);
        Order order = optionalOrder
                .orElseThrow(() -> new NoSuchElementException(ORDER_NOT_FOUND));

        return OrderMapper.INSTANCE.mapToDto(order);
    }

    @Override
    public PagedModel<OrderDto> readOrdersByUserId(long id, Integer page, Integer limit) {
        Optional<User> optionalUser = userDao.findById(id);
        User foundUser = optionalUser
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND));

        List<OrderDto> orderDtos = orderDao.findOrdersByUserId(id, page, limit)
                .stream()
                .map(OrderMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());

        if (orderDtos.isEmpty()) {
            throw new EmptyListRequestedException();
        }
        long totalNumberOfEntities = foundUser.getOrders().size();
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(limit, page, totalNumberOfEntities);
        return PagedModel.of(orderDtos, metadata);
    }
}
