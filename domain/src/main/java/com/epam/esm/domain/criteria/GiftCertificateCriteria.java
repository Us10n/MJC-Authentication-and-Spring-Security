package com.epam.esm.domain.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Class that used to hold find criteria.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateCriteria {
    private Set<String> tagNames;
    private String partName;
    private String partDesc;
    private String sortBy;
    private String sortOrder;
}
