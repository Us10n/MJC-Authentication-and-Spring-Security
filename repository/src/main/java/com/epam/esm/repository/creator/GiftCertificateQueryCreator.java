package com.epam.esm.repository.creator;

import com.epam.esm.domain.criteria.GiftCertificateCriteria;
import com.epam.esm.domain.entity.GiftCertificate;
import lombok.experimental.UtilityClass;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Gift certificate query creator utility class.
 */
@UtilityClass
public class GiftCertificateQueryCreator {
    private static final String PERCENT = "%";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";
    private static final String DESCRIPTION = "description";
    private static final String CREATE_DATE = "createDate";
    private static final String DATE = "date";
    private static final String TAGS_TABLE = "tags";

    /**
     * Build get query based on criteria.
     *
     * @param criteria        the criteria
     * @param criteriaBuilder the criteria builder
     * @return the criteria query
     */
    public CriteriaQuery<GiftCertificate> buildGetQueryByCriteria(GiftCertificateCriteria criteria, CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);
        List<Predicate> restrictions = new ArrayList<>();

        restrictions.addAll(addPartOfName(criteria, criteriaBuilder, giftCertificateRoot));
        restrictions.addAll(addPartOfDescription(criteria, criteriaBuilder, giftCertificateRoot));
        restrictions.addAll(addTagNames(criteria, criteriaBuilder, giftCertificateRoot));
        criteriaQuery.select(giftCertificateRoot).where(restrictions.toArray(new Predicate[]{}));

        addSortByName(criteria, criteriaBuilder, criteriaQuery, giftCertificateRoot);
        addSortByCreateDate(criteria, criteriaBuilder, criteriaQuery, giftCertificateRoot);
        addSortById(criteriaBuilder, criteriaQuery, giftCertificateRoot);

        return criteriaQuery;
    }

    private List<Predicate> addPartOfName(GiftCertificateCriteria criteria, CriteriaBuilder criteriaBuilder,
                                          Root<GiftCertificate> root) {
        List<Predicate> restrictions = new ArrayList<>();
        String partName = criteria.getPartName();

        if (partName != null) {
            restrictions.add(criteriaBuilder.like(root.get(NAME), PERCENT + partName + PERCENT));
        }
        return restrictions;
    }

    private List<Predicate> addPartOfDescription(GiftCertificateCriteria criteria, CriteriaBuilder criteriaBuilder,
                                                 Root<GiftCertificate> root) {
        List<Predicate> restrictions = new ArrayList<>();
        String partDescription = criteria.getPartDesc();

        if (partDescription != null) {
            restrictions.add(criteriaBuilder.like(root.get(DESCRIPTION), PERCENT + partDescription + PERCENT));
        }
        return restrictions;
    }

    private void addSortByName(GiftCertificateCriteria criteria, CriteriaBuilder criteriaBuilder,
                               CriteriaQuery<GiftCertificate> criteriaQuery, Root<GiftCertificate> root) {
        String sortOrder = criteria.getSortOrder();
        String sortBy = criteria.getSortBy();

        if (sortOrder != null && sortBy != null && sortBy.equalsIgnoreCase(NAME)) {
            Path<Set<String>> column = root.get(NAME);
            Order order = sortOrder.equalsIgnoreCase(ASC)
                    ? criteriaBuilder.asc(column)
                    : criteriaBuilder.desc(column);
            criteriaQuery.orderBy(order);
        }
    }

    private void addSortByCreateDate(GiftCertificateCriteria criteria, CriteriaBuilder criteriaBuilder,
                                     CriteriaQuery<GiftCertificate> criteriaQuery, Root<GiftCertificate> root) {
        String sortOrder = criteria.getSortOrder();
        String sortBy = criteria.getSortBy();

        if (sortOrder != null && sortBy != null && sortBy.equalsIgnoreCase(DATE)) {
            Path<Set<String>> column = root.get(CREATE_DATE);
            Order order = sortOrder.equalsIgnoreCase(ASC)
                    ? criteriaBuilder.asc(column)
                    : criteriaBuilder.desc(column);
            criteriaQuery.orderBy(order);
        }
    }

    private static void addSortById(CriteriaBuilder criteriaBuilder, CriteriaQuery<GiftCertificate> criteriaQuery,
                                    Root<GiftCertificate> root) {
        if (criteriaQuery.getOrderList().isEmpty()) {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(ID)));
        }
    }

    private List<Predicate> addTagNames(GiftCertificateCriteria criteria, CriteriaBuilder criteriaBuilder,
                                        Root<GiftCertificate> root) {
        List<Predicate> restrictions = new ArrayList<>();

        Set<String> tagNames = criteria.getTagNames();
        if (tagNames != null) {
            restrictions = tagNames.stream()
                    .map(tagName ->
                            criteriaBuilder.equal(root.join(TAGS_TABLE).get(NAME), tagName))
                    .collect(Collectors.toList());
        }
        return restrictions;
    }


}
