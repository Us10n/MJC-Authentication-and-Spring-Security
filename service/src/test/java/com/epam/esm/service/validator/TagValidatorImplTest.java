package com.epam.esm.service.validator;

import com.epam.esm.domain.dto.TagDto;
import com.epam.esm.service.exception.ExceptionHolder;
import com.epam.esm.service.util.validator.TagValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TagValidatorImplTest {

    @Test
    void isNameValidPositive() {
        String name = "Test string";
        boolean status = TagValidator.isNameValid(name);
        Assertions.assertTrue(status);
    }

    @Test
    void isNameValidNegative() {
        String name = "";
        boolean status = TagValidator.isNameValid(name);
        Assertions.assertFalse(status);
    }

    @Test
    void isTagDtoPositive() {
        TagDto tagDto = new TagDto(1, "name");
        ExceptionHolder exceptionHolder = new ExceptionHolder();
        TagValidator.isTagDtoValid(tagDto,exceptionHolder);

        boolean status=exceptionHolder.getExceptionMessages().isEmpty();
        Assertions.assertTrue(status);
    }

    @Test
    void isTagDtoNegative() {
        TagDto tagDto = new TagDto(1, "");
        ExceptionHolder exceptionHolder = new ExceptionHolder();
        TagValidator.isTagDtoValid(tagDto,exceptionHolder);

        boolean status=exceptionHolder.getExceptionMessages().isEmpty();
        Assertions.assertFalse(status);
    }
}