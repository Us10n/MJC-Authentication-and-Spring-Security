package com.epam.esm.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * The type Tag.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tags")
@Audited
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;
}
