package com.epam.esm.web.hateoas.impl;

import com.epam.esm.domain.criteria.GiftCertificateCriteria;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.web.controller.GiftCertificateController;
import com.epam.esm.web.controller.TagController;
import com.epam.esm.web.hateoas.HateoasAdder;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateHateoasAdder implements HateoasAdder<GiftCertificateDto> {

    private static final Class<GiftCertificateController> GIFT_CERTIFICATE_CONTROLLER = GiftCertificateController.class;
    private static final Class<TagController> TAG_CONTROLLER = TagController.class;

    private static final String REDUNDANT_INFO = "\\{.*?}";
    private static final String EMPTY = "";

    @Override
    public void addLinksToEntity(GiftCertificateDto entity) {
        entity.add(linkTo(methodOn(GIFT_CERTIFICATE_CONTROLLER).readGiftCertificateById(entity.getGiftCertificateId())).withSelfRel().withType("GET"));
        entity.add(linkTo(methodOn(GIFT_CERTIFICATE_CONTROLLER).createGiftCertificate(entity)).withRel("create").withType("POST"));
        entity.add(linkTo(methodOn(GIFT_CERTIFICATE_CONTROLLER).updateGiftCertificate(entity.getGiftCertificateId(), entity)).withRel("update").withType("PATCH"));
        entity.add(linkTo(methodOn(GIFT_CERTIFICATE_CONTROLLER).deleteGiftCertificateById(entity.getGiftCertificateId())).withRel("delete").withType("POST"));
        entity.getTags().forEach(
                tagDto -> tagDto.add(linkTo(methodOn(TAG_CONTROLLER).readTadById(tagDto.getTagId())).withSelfRel().withType("GET")));
    }

    @Override
    public void addLinksToCollection(PagedModel<GiftCertificateDto> model) {
        throw new UnsupportedOperationException();
    }

    public void addLinksToCollection(PagedModel<GiftCertificateDto> model, GiftCertificateCriteria criteria) {
        PagedModel.PageMetadata metadata = model.getMetadata();
        int page = (int) metadata.getNumber();
        int limit = (int) metadata.getSize();
        int totalPages = (int) metadata.getTotalPages();

        String selfLinkString = linkTo(methodOn(GIFT_CERTIFICATE_CONTROLLER)
                .readAllGiftCertificates(criteria.getTagNames(),
                        criteria.getPartName(), criteria.getPartDesc(),
                        criteria.getSortBy(), criteria.getSortOrder(), page, limit))
                .toUriComponentsBuilder()
                .toUriString()
                .replaceAll(REDUNDANT_INFO, EMPTY);
        model.add(Link.of(selfLinkString, "self").withType("GET"));
        if (page < totalPages) {
            String nextLinkString = linkTo(methodOn(GIFT_CERTIFICATE_CONTROLLER)
                    .readAllGiftCertificates(criteria.getTagNames(),
                            criteria.getPartName(), criteria.getPartDesc(),
                            criteria.getSortBy(), criteria.getSortOrder(), page + 1, limit))
                    .toUriComponentsBuilder()
                    .toUriString()
                    .replaceAll(REDUNDANT_INFO, EMPTY);
            model.add(Link.of(nextLinkString, "next").withType("GET"));
        }
        if (page > 1) {
            String prevLinkString = linkTo(methodOn(GIFT_CERTIFICATE_CONTROLLER)
                    .readAllGiftCertificates(criteria.getTagNames(),
                            criteria.getPartName(), criteria.getPartDesc(),
                            criteria.getSortBy(), criteria.getSortOrder(), page - 1, limit))
                    .toUriComponentsBuilder()
                    .toUriString()
                    .replaceAll(REDUNDANT_INFO, EMPTY);
            model.add(Link.of(prevLinkString, "prev").withType("GET"));
        }
        model.getContent().forEach(this::addLinksToEntity);
    }


}
