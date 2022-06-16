package com.epam.esm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

/**
 * Data transfer object for OrderDetail
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "orders")
public class OrderDetailDto  extends RepresentationModel<OrderDetailDto> {
    private long orderId;
    private Double price;
    private LocalDateTime purchaseTime;
    private long userId;
    private long giftCertificateId;
}
