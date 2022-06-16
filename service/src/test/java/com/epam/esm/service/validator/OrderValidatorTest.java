package com.epam.esm.service.validator;

import com.epam.esm.domain.entity.Order;
import com.epam.esm.service.exception.ExceptionHolder;
import com.epam.esm.service.util.validator.OrderValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OrderValidatorTest {

    @Test
    void isUserIdValidPositive() {
        long id = 1;
        boolean status = OrderValidator.isUserIdValid(id);
        Assertions.assertTrue(status);
    }

    @Test
    void isUserIdValidNegative() {
        long id = 0;
        boolean status = OrderValidator.isUserIdValid(id);
        Assertions.assertFalse(status);
    }

    @Test
    void isCertificateIdValidPositive() {
        long id = 1;
        boolean status = OrderValidator.isGiftCertificateIdValid(id);
        Assertions.assertTrue(status);
    }

    @Test
    void isCertificateIdValidNegative() {
        long id = 0;
        boolean status = OrderValidator.isGiftCertificateIdValid(id);
        Assertions.assertFalse(status);
    }

    @Test
    void isOrderValidPositive() {
        Order order=new Order(1,1);
        ExceptionHolder exceptionHolder=new ExceptionHolder();
        OrderValidator.isOrderValid(order,exceptionHolder);

        boolean status=exceptionHolder.getExceptionMessages().isEmpty();
        Assertions.assertTrue(status);
    }

    @Test
    void isOrderValidNegative() {
        Order order=new Order(0,1);
        ExceptionHolder exceptionHolder=new ExceptionHolder();
        OrderValidator.isOrderValid(order,exceptionHolder);

        boolean status=exceptionHolder.getExceptionMessages().isEmpty();
        Assertions.assertFalse(status);
    }

}
