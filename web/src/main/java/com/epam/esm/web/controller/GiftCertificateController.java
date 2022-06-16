package com.epam.esm.web.controller;

import com.epam.esm.domain.criteria.GiftCertificateCriteria;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.service.service.GiftCertificateService;
import com.epam.esm.web.hateoas.impl.GiftCertificateHateoasAdder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.Set;

/**
 * Gift certificate rest controller.
 */
@RestController
@Validated
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateHateoasAdder giftCertificateHateoasAdder;

    /**
     * Instantiates a new Gift certificate controller.
     *
     * @param giftCertificateService the gift certificate service
     */
    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, GiftCertificateHateoasAdder hateoasAdder) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateHateoasAdder = hateoasAdder;
    }

    /**
     * Read by id gift certificate.
     *
     * @param id the id
     * @return the gift certificate dto
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto readGiftCertificateById(@PathVariable long id) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.readById(id);
        giftCertificateHateoasAdder.addLinksToEntity(giftCertificateDto);

        return giftCertificateDto;
    }

    /**
     * Create gift certificate.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto createGiftCertificate(@RequestBody GiftCertificateDto giftCertificateDto) {
        GiftCertificateDto createdGiftCertificate = giftCertificateService.create(giftCertificateDto);
        giftCertificateHateoasAdder.addLinksToEntity(createdGiftCertificate);

        return createdGiftCertificate;
    }

    /**
     * Update gift certificate.
     *
     * @param id                 the id
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateGiftCertificate(@PathVariable long id,
                                                    @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setGiftCertificateId(id);
        GiftCertificateDto createdGiftCertificate = giftCertificateService.update(giftCertificateDto);
        giftCertificateHateoasAdder.addLinksToEntity(createdGiftCertificate);

        return createdGiftCertificate;
    }

    /**
     * Delete by id.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteGiftCertificateById(@PathVariable long id) {
        giftCertificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Gets all gift certificates.
     *
     * @param tagNames  the tag names
     * @param partName  the part name
     * @param partDesc  the part desc
     * @param sortBy    the sort by
     * @param sortOrder the sort order
     * @return the all gift certificates
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<GiftCertificateDto> readAllGiftCertificates(
            @RequestParam(name = "tag", required = false) Set<String> tagNames,
            @RequestParam(name = "name", required = false) String partName,
            @RequestParam(name = "description", required = false) String partDesc,
            @RequestParam(name = "sort", required = false) String sortBy,
            @RequestParam(name = "order", defaultValue = "ASC") String sortOrder,
            @RequestParam(name = "page", defaultValue = "1") @Positive Integer page,
            @RequestParam(name = "limit", defaultValue = "10") @Positive Integer limit) {

        GiftCertificateCriteria criteria = new GiftCertificateCriteria();
        criteria.setTagNames(tagNames);
        criteria.setPartName(partName);
        criteria.setPartDesc(partDesc);
        criteria.setSortBy(sortBy);
        criteria.setSortOrder(sortOrder);
        PagedModel<GiftCertificateDto> giftCertificateDtos = giftCertificateService.readByCriteria(criteria, page, limit);
        giftCertificateHateoasAdder.addLinksToCollection(giftCertificateDtos, criteria);

        return giftCertificateDtos;
    }
}
