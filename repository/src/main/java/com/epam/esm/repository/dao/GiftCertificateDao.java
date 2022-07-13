package com.epam.esm.repository.dao;

import com.epam.esm.domain.criteria.GiftCertificateCriteria;
import com.epam.esm.domain.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

/**
 * Data access object that perform operations with {@link GiftCertificate} model in database
 */
public interface GiftCertificateDao extends Dao<GiftCertificate> {

    /**
     * Update {@link GiftCertificate} object instance with values specified in {@code giftCertificate}.
     *
     * @param giftCertificate contains new values
     * @return {@link Optional} instance of updated object
     */
    GiftCertificate update(GiftCertificate giftCertificate);

    /**
     * Find objects based on criteria.
     *
     * @param criteria the criteria
     * @param page     the page number
     * @param limit    page size
     * @return limit objects from page.
     */
    List<GiftCertificate> findByCriteria(GiftCertificateCriteria criteria, int page, int limit);

    /**
     * Find object instance in database by name.
     *
     * @param name object name
     * @return {@link Optional Optional.ofNullable()} of found object.
     */
    Optional<GiftCertificate> findByName(String name);

    /**
     * Count amount of objects based on criteria.
     *
     * @param criteria the criteria
     * @return amount
     */
    long countAllByCriteria(GiftCertificateCriteria criteria);
}
