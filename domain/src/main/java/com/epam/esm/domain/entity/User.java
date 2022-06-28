package com.epam.esm.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

/**
 * The type User.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Audited
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user")
    private List<OrderDetail> orders;
}
