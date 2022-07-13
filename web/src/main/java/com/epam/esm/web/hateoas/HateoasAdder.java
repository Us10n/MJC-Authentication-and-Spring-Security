package com.epam.esm.web.hateoas;

import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;

public interface HateoasAdder<T extends RepresentationModel<T>> {
    void addLinksToEntity(T entity);
    void addLinksToCollection(PagedModel<T> model);
}
