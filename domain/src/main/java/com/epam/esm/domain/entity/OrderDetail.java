package com.epam.esm.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The type Order detail.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Audited
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "price")
    private Double price;

    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gift_certificate_id")
    private GiftCertificate giftCertificate;
}
