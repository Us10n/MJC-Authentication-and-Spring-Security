package com.epam.esm.service.service;

import com.epam.esm.domain.criteria.GiftCertificateCriteria;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.domain.entity.GiftCertificate;
import org.springframework.hateoas.PagedModel;

/**
 * Interface that provides CRUD operations for GiftCertificates
 */
public interface GiftCertificateService extends CRUD<GiftCertificateDto, GiftCertificate> {
    /**
     * Read by criteria specified in criteria object.
     *
     * @param criteria object that contains read criterias
     * @return the list of {@link GiftCertificateDto} objects
     */
    PagedModel<GiftCertificateDto> readByCriteria(GiftCertificateCriteria criteria, Integer page, Integer limit);
}
