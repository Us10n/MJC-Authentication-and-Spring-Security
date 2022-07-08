package com.epam.esm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "orders")
public class OrderDto extends RepresentationModel<OrderDto> {
    private long orderId;
    private long userId;
    private LocalDateTime purchaseTime;
    private List<OrderDetailDto> orderDetailDtos;
}
