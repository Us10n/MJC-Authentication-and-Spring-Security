package com.epam.esm.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

/**
 * Data transfer object for User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "users")
public class UserDto extends RepresentationModel<UserDto> {
    private long userId;
    private String email;
    private String password;
    private String name;
    private UserRole role;
    private List<OrderDetailDto> orders;
}
