package com.epam.esm.service.validator;

import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.domain.dto.TagDto;
import com.epam.esm.service.exception.ExceptionHolder;
import com.epam.esm.service.util.validator.GiftCertificateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class GiftCertificateValidatorImplTest {
    @Test
    void isNameValidPositive() {
        String name = "Test string";
        boolean status = GiftCertificateValidator.isNameValid(name);
        Assertions.assertTrue(status);
    }

    @Test
    void isNameValidNegative() {
        String name = "";
        boolean status = GiftCertificateValidator.isNameValid(name);
        Assertions.assertFalse(status);
    }

    @Test
    void isDescriptionValidPositive() {
        String description = "Test string";
        boolean status = GiftCertificateValidator.isDescriptionValid(description);
        Assertions.assertTrue(status);
    }

    @Test
    void isDescriptionValidNegative() {
        String description = "";
        boolean status = GiftCertificateValidator.isDescriptionValid(description);
        Assertions.assertFalse(status);
    }

    @Test
    void isPriceValidPositive() {
        Double price = 1.0;
        boolean status = GiftCertificateValidator.isPriceValid(price);
        Assertions.assertTrue(status);
    }

    @Test
    void isPriceValidNegative() {
        Double price = 0.0;
        boolean status = GiftCertificateValidator.isPriceValid(price);
        Assertions.assertFalse(status);
    }

    @Test
    void isDurationValidPositive() {
        Integer duration = 1;
        boolean status = GiftCertificateValidator.isDurationValid(duration);
        Assertions.assertTrue(status);
    }

    @Test
    void isDurationValidNegative() {
        Integer duration = -1;
        boolean status = GiftCertificateValidator.isDurationValid(duration);
        Assertions.assertFalse(status);
    }

    @Test
    void isDateValidPositive() {
        String date = "2022-04-11T10:00:11.156";
        boolean status = GiftCertificateValidator.isDateValid(date);
        Assertions.assertTrue(status);
    }

    @Test
    void isDateValidNegative() {
        String date = "2022-04-11 10.00.11.156";
        boolean status = GiftCertificateValidator.isDateValid(date);
        Assertions.assertFalse(status);
    }

    @Test
    void isGiftCertificateValidPositive() {
        List<TagDto> tags = new ArrayList<>();
        tags.add(new TagDto(1, "testTag"));
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(
                1, "testName", "testDesctiption",
                1.1, 10,
                LocalDateTime.parse("2022-04-11T10:00:11.156"),
                LocalDateTime.parse("2022-04-11T10:00:11.156"),
                tags
        );

        ExceptionHolder exceptionHolder = new ExceptionHolder();
        GiftCertificateValidator.isGiftCertificateDtoValid(giftCertificateDto, exceptionHolder);
        boolean status = exceptionHolder.getExceptionMessages().isEmpty();

        Assertions.assertTrue(status);
    }

    @Test
    void isGiftCertificateValidNegative() {
        List<TagDto> tags = new ArrayList<>();
        tags.add(new TagDto(1, ""));
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto(
                1, "", "testDesctiption",
                -1.1, -10,
                LocalDateTime.parse("2022-04-11T10:00:11.156"),
                LocalDateTime.parse("2022-04-11T10:00:11.156"),
                tags
        );

        ExceptionHolder exceptionHolder = new ExceptionHolder();
        GiftCertificateValidator.isGiftCertificateDtoValid(giftCertificateDto, exceptionHolder);
        boolean status = exceptionHolder.getExceptionMessages().isEmpty();

        Assertions.assertFalse(status);
    }
}