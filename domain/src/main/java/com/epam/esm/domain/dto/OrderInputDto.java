package com.epam.esm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entity that contains Users id that purchased Gift Certificate with giftCertificateId
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInputDto {
    private Long userId;
    private List<Long> giftCertificateIds;
}
