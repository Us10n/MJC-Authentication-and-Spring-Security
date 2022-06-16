package com.epam.esm.service.converter.impl;

import com.epam.esm.domain.dto.TagDto;
import com.epam.esm.domain.entity.Tag;
import com.epam.esm.service.converter.DtoEntityConverter;
import org.springframework.stereotype.Component;

/**
 * The type Tag converter.
 */
@Component
public class TagConverter implements DtoEntityConverter<TagDto, Tag> {
    @Override
    public TagDto convertToDto(Tag object) {
        return new TagDto(object.getId(), object.getName());
    }

    @Override
    public Tag convertToEntity(TagDto object) {
        return new Tag(object.getTagId(), object.getName());
    }
}
