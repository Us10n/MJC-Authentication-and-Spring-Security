package com.epam.esm.service.mapper;

import com.epam.esm.domain.dto.TagDto;
import com.epam.esm.domain.entity.Tag;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    @Mapping(source = "id", target = "tagId")
    TagDto mapToDto(Tag tag);

    @InheritInverseConfiguration
    Tag mapToEntity(TagDto tagDto);
}
