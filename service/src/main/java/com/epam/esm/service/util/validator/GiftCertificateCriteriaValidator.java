package com.epam.esm.service.util.validator;

import com.epam.esm.domain.criteria.GiftCertificateCriteria;
import com.epam.esm.service.exception.ExceptionHolder;
import lombok.experimental.UtilityClass;

import static com.epam.esm.service.exception.ExceptionMessageKey.*;

/**
 * The type Gift certificate criteria validator.
 */
@UtilityClass
public class GiftCertificateCriteriaValidator {
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private static final String NAME = "name";
    private static final String DATE = "date";
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 128;

    /**
     * Is sort order valid boolean.
     *
     * @param order the order
     * @return the boolean
     */
    public boolean isSortOrderValid(String order) {
        return order.equalsIgnoreCase(ASC) || order.equalsIgnoreCase(DESC);
    }

    /**
     * Is sort column valid boolean.
     *
     * @param column the column
     * @return the boolean
     */
    public boolean isSortColumnValid(String column) {
        return column.equalsIgnoreCase(NAME) || column.equalsIgnoreCase(DATE);
    }

    /**
     * Is part name valid boolean.
     *
     * @param partName the part name
     * @return the boolean
     */
    public boolean isPartNameValid(String partName) {
        return partName.length() >= MIN_LENGTH && partName.length() <= MAX_LENGTH;
    }

    /**
     * Is part description valid boolean.
     *
     * @param partDescription the part description
     * @return the boolean
     */
    public boolean isPartDescriptionValid(String partDescription) {
        return partDescription.length() >= MIN_LENGTH;
    }

    /**
     * Is criteria valid.
     *
     * @param criteria the criteria
     * @param holder   the holder
     */
    public void isCriteriaValid(GiftCertificateCriteria criteria, ExceptionHolder holder) {
        if (criteria == null) {
            holder.addException(NULL_PASSED, GiftCertificateCriteria.class);
            return;
        }
        if (criteria.getSortBy() != null && !isSortColumnValid(criteria.getSortBy())) {
            holder.addException(BAD_SORT_COLUMN, criteria.getSortBy(), NAME, DATE);
        }
        if (criteria.getSortOrder() != null && !isSortOrderValid(criteria.getSortOrder())) {
            holder.addException(BAD_SORT_ORDER, criteria.getSortOrder(), ASC, DESC);
        }
        if (criteria.getPartName() != null && !isPartNameValid(criteria.getPartName())) {
            holder.addException(BAD_PART_NAME, criteria.getPartName());
        }
        if (criteria.getPartDesc() != null && !isPartDescriptionValid(criteria.getPartDesc())) {
            holder.addException(BAD_PART_DESCRIPTION, criteria.getPartDesc());
        }
    }
}
