package com.epam.esm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity that contains Users id that purchased Gift Certificate with giftCertificateId
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private long userId;
    private long giftCertificateId;
}
