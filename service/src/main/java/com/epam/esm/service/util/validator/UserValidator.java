package com.epam.esm.service.util.validator;

import com.epam.esm.domain.dto.TagDto;
import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.service.exception.ExceptionHolder;
import lombok.experimental.UtilityClass;

import static com.epam.esm.service.exception.ExceptionMessageKey.*;

@UtilityClass
public class UserValidator {
    private static final String VALID_EMAIL_REGEX = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String VALID_NAME_REGEX = "^[A-Za-z][A-Za-z]{0,127}$";
    //Minimum eight characters, at least one letter and one number:
    private static final String VALID_PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    public boolean isEmailValid(String email) {
        return email != null && email.matches(VALID_EMAIL_REGEX);
    }

    public boolean isNameValid(String name) {
        return name != null && name.matches(VALID_NAME_REGEX);
    }

    public boolean isPasswordValid(String password) {
        return password != null && password.matches(VALID_PASSWORD_REGEX);
    }

    public void isUserRegisterDtoValid(UserDto userDto, ExceptionHolder exceptionHolder) {
        if (userDto == null) {
            exceptionHolder.addException(NULL_PASSED, TagDto.class);
            return;
        }

        if(!isEmailValid(userDto.getEmail())){
            exceptionHolder.addException(BAD_USER_EMAIL, userDto.getEmail());
        }
        if(!isPasswordValid(userDto.getPassword())){
            exceptionHolder.addException(BAD_USER_PASSWORD, userDto.getPassword());
        }
        if(!isNameValid(userDto.getName())){
            exceptionHolder.addException(BAD_USER_NAME, userDto.getName());
        }
    }

    public void isUserAuthenticationDtoValid(UserDto userDto, ExceptionHolder exceptionHolder) {
        if (userDto == null) {
            exceptionHolder.addException(NULL_PASSED, TagDto.class);
            return;
        }

        if(!isEmailValid(userDto.getEmail())){
            exceptionHolder.addException(BAD_USER_EMAIL, userDto.getEmail());
        }
        if(!isPasswordValid(userDto.getPassword())){
            exceptionHolder.addException(BAD_USER_PASSWORD, userDto.getPassword());
        }
    }

}
