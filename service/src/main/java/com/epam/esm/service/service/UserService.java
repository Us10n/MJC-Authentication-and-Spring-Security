package com.epam.esm.service.service;

import com.epam.esm.domain.dto.UserDto;
import org.springframework.hateoas.PagedModel;

/**
 * The interface User service.
 */
public interface UserService {

    /**
     * Create T type object.
     *
     * @param object object to create instance of
     * @return the t
     */
    UserDto create(UserDto object);

    /**
     * Read all paged model.
     *
     * @param page  the page
     * @param limit the limit
     * @return the paged model
     */
    PagedModel<UserDto> readAll(Integer page, Integer limit);

    /**
     * Read by id user dto.
     *
     * @param id the id
     * @return the user dto
     */
    UserDto readById(long id);

    UserDto readByEmail(String email);
}
