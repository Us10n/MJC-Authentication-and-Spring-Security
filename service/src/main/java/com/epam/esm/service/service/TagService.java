package com.epam.esm.service.service;

import com.epam.esm.domain.dto.TagDto;
import com.epam.esm.domain.entity.Tag;
import org.springframework.hateoas.PagedModel;

/**
 * Interface that provides CRUD operations for Tags
 */
public interface TagService extends CRD<TagDto, Tag> {
    /**
     * Find widely used tag of user with highest cost of all orders tag dto.
     *
     * @return the tag dto
     */
    PagedModel<TagDto> findWidelyUsedTagOfUserWithHighestCostOfAllOrders(Integer page, Integer limit);
}
