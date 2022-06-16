package com.epam.esm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data transfer object for GiftCertificate
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "giftCertificates")
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    private long giftCertificateId;
    private String name;
    private String description;
    private Double price;
    private Integer duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<TagDto> tags;
}
