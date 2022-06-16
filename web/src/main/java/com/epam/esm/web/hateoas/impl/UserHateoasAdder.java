package com.epam.esm.web.hateoas.impl;

import com.epam.esm.domain.dto.UserDto;
import com.epam.esm.web.controller.UserController;
import com.epam.esm.web.hateoas.HateoasAdder;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserHateoasAdder implements HateoasAdder<UserDto> {

    private static final Class<UserController> USER_CONTROLLER = UserController.class;

    @Override
    public void addLinksToEntity(UserDto entity) {
        entity.add(linkTo(methodOn(USER_CONTROLLER).readUserById(entity.getUserId())).withSelfRel().withType("GET"));
    }

    @Override
    public void addLinksToCollection(PagedModel<UserDto> model) {
        PagedModel.PageMetadata metadata = model.getMetadata();
        int page = (int) metadata.getNumber();
        int limit = (int) metadata.getSize();
        int totalPages = (int) metadata.getTotalPages();

        model.add(linkTo(methodOn(USER_CONTROLLER).readAllUsers(page, limit)).withSelfRel().withType("GET"));
        if (page < totalPages) {
            model.add(linkTo(methodOn(USER_CONTROLLER).readAllUsers(page + 1, limit)).withRel("next").withType("GET"));
        }
        if (page > 1) {
            model.add(linkTo(methodOn(USER_CONTROLLER).readAllUsers(page - 1, limit)).withRel("prev").withType("GET"));
        }
        model.getContent().forEach(this::addLinksToEntity);
    }
}
