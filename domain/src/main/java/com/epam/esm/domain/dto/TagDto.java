package com.epam.esm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/**
 * Data transfer object for Tag
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "tags")
public class TagDto extends RepresentationModel<TagDto> {
    private long tagId;
    private String name;

    public TagDto(String name) {
        this.name = name;
    }
}
