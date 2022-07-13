package com.epam.esm.service.validator;

import com.epam.esm.domain.criteria.GiftCertificateCriteria;
import com.epam.esm.service.exception.ExceptionHolder;
import com.epam.esm.service.util.validator.GiftCertificateCriteriaValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class GiftCertificateCriteriaValidatorTest {

    @Test
    void isNameValidPositive() {
        String name = "Test string";
        boolean status = GiftCertificateCriteriaValidator.isPartNameValid(name);
        Assertions.assertTrue(status);
    }

    @Test
    void isNameValidNegative() {
        String name = "";
        boolean status = GiftCertificateCriteriaValidator.isPartNameValid(name);
        Assertions.assertFalse(status);
    }

    @Test
    void isDescriptionValidPositive() {
        String description = "Test string";
        boolean status = GiftCertificateCriteriaValidator.isPartDescriptionValid(description);
        Assertions.assertTrue(status);
    }

    @Test
    void isDescriptionValidNegative() {
        String description = "";
        boolean status = GiftCertificateCriteriaValidator.isPartDescriptionValid(description);
        Assertions.assertFalse(status);
    }

    @Test
    void isSortOrderValidPositive1() {
        String sortOrder = "ASC";
        boolean status = GiftCertificateCriteriaValidator.isSortOrderValid(sortOrder);
        Assertions.assertTrue(status);
    }

    @Test
    void isSortOrderValidPositive2() {
        String sortOrder = "DESC";
        boolean status = GiftCertificateCriteriaValidator.isSortOrderValid(sortOrder);
        Assertions.assertTrue(status);
    }

    @Test
    void isSortOrderValidNegative() {
        String sortOrder = "DESCa";
        boolean status = GiftCertificateCriteriaValidator.isSortOrderValid(sortOrder);
        Assertions.assertFalse(status);
    }

    @Test
    void isCriteriaValidPositive() {
        Set<String> tags = new HashSet<>();
        tags.add("tagName");
        GiftCertificateCriteria criteria = new GiftCertificateCriteria(
                tags,"partName","partDesc","name","ASC"
        );
        ExceptionHolder exceptionHolder=new ExceptionHolder();
        GiftCertificateCriteriaValidator.isCriteriaValid(criteria,exceptionHolder);

        boolean status=exceptionHolder.getExceptionMessages().isEmpty();
        Assertions.assertTrue(status);
    }

    @Test
    void isCriteriaValidNegative() {
        Set<String> tags = new HashSet<>();
        tags.add("tagName");
        GiftCertificateCriteria criteria = new GiftCertificateCriteria(
                tags,"","partDesc","name","ASC"
        );
        ExceptionHolder exceptionHolder=new ExceptionHolder();
        GiftCertificateCriteriaValidator.isCriteriaValid(criteria,exceptionHolder);

        boolean status=exceptionHolder.getExceptionMessages().isEmpty();
        Assertions.assertFalse(status);
    }
}
