package com.epam.esm.service.service.impl;

import com.epam.esm.domain.criteria.GiftCertificateCriteria;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.domain.entity.GiftCertificate;
import com.epam.esm.domain.entity.Tag;
import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.service.converter.impl.GiftCertificateConverter;
import com.epam.esm.service.converter.impl.TagConverter;
import com.epam.esm.service.exception.DuplicateEntityException;
import com.epam.esm.service.exception.ExceptionHolder;
import com.epam.esm.service.exception.IncorrectParameterException;
import com.epam.esm.service.exception.NoSuchElementException;
import com.epam.esm.service.service.GiftCertificateService;
import com.epam.esm.service.util.handler.DateHandler;
import com.epam.esm.service.util.validator.GiftCertificateCriteriaValidator;
import com.epam.esm.service.util.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.service.exception.ExceptionMessageKey.GIFT_CERTIFICATE_EXIST;
import static com.epam.esm.service.exception.ExceptionMessageKey.GIFT_CERTIFICATE_NOT_FOUND;


/**
 * The type Gift certificate service.
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {


    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateConverter giftCertificateConverter;
    private final TagConverter tagConverter;

    /**
     * Instantiates a new Gift certificate service.
     *
     * @param giftCertificateDao       the gift certificate dao
     * @param tagDao                   the tag dao
     * @param giftCertificateConverter the gift certificate converter
     * @param tagConverter             the tag converter
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao,
                                      GiftCertificateConverter giftCertificateConverter, TagConverter tagConverter) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftCertificateConverter = giftCertificateConverter;
        this.tagConverter = tagConverter;
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto object) {
        ExceptionHolder exceptionHolder = new ExceptionHolder();

        LocalDateTime currentDate = DateHandler.getCurrentDate();
        object.setCreateDate(currentDate);
        object.setLastUpdateDate(currentDate);

        GiftCertificateValidator.isGiftCertificateDtoValid(object, exceptionHolder);
        if (!exceptionHolder.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionHolder);
        }
        if (giftCertificateDao.findByName(object.getName()).isPresent()) {
            throw new DuplicateEntityException(GIFT_CERTIFICATE_EXIST);
        }

        GiftCertificate certificateModel = giftCertificateConverter.convertToEntity(object);

        List<Tag> newTags = new ArrayList<>(certificateModel.getTags());
        List<Tag> tagsToPersist = createTagListToPersist(newTags);
        certificateModel.setTags(new HashSet<>(tagsToPersist));
        GiftCertificate createdCertificate = giftCertificateDao.create(certificateModel);

        return giftCertificateConverter.convertToDto(createdCertificate);
    }

    @Override
    public PagedModel<GiftCertificateDto> readAll(Integer page, Integer limit) {
        List<GiftCertificateDto> giftCertificateDtos = giftCertificateDao.findAll(page, limit)
                .stream()
                .map(giftCertificateConverter::convertToDto)
                .collect(Collectors.toList());
        long totalNumberOfEntities = giftCertificateDao.countAll();
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(limit, page, totalNumberOfEntities);
        return PagedModel.of(giftCertificateDtos, metadata);
    }

    @Override
    public GiftCertificateDto readById(long id) {
        Optional<GiftCertificate> optionalGitCertificate = giftCertificateDao.findById(id);
        if (!optionalGitCertificate.isPresent()) {
            throw new NoSuchElementException(GIFT_CERTIFICATE_NOT_FOUND);
        }

        return giftCertificateConverter.convertToDto(optionalGitCertificate.get());
    }

    @Override
    public PagedModel<GiftCertificateDto> readByCriteria(GiftCertificateCriteria criteria, Integer page, Integer limit) {
        ExceptionHolder exceptionHolder = new ExceptionHolder();

        GiftCertificateCriteriaValidator.isCriteriaValid(criteria, exceptionHolder);
        if (!exceptionHolder.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionHolder);
        }

        List<GiftCertificate> foundCertificates = giftCertificateDao.findByCriteria(criteria, page, limit);
        List<GiftCertificateDto> certificateDtos = foundCertificates.stream()
                .map(giftCertificateConverter::convertToDto)
                .collect(Collectors.toList());
        long totalNumberOfEntities = giftCertificateDao.countAllByCriteria(criteria);
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(limit, page, totalNumberOfEntities);
        return PagedModel.of(certificateDtos, metadata);
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto object) {
        ExceptionHolder exceptionHolder = new ExceptionHolder();

        //copy values from existing certificate
        GiftCertificateDto currentCertificate = readById(object.getGiftCertificateId());
        if (object.getName() == null) object.setName(currentCertificate.getName());
        if (object.getDescription() == null) object.setDescription(currentCertificate.getDescription());
        if (object.getDuration() == null) object.setDuration(currentCertificate.getDuration());
        if (object.getPrice() == null) object.setPrice(currentCertificate.getPrice());
        if (object.getCreateDate() == null) object.setCreateDate(currentCertificate.getCreateDate());
        if (object.getTags() == null) object.setTags(currentCertificate.getTags());
        object.setLastUpdateDate(DateHandler.getCurrentDate());

        GiftCertificateValidator.isGiftCertificateDtoValid(object, exceptionHolder);
        if (!exceptionHolder.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionHolder);
        }

        GiftCertificate certificateModel = giftCertificateConverter.convertToEntity(object);
        List<Tag> newTags = object.getTags().stream()
                .map(tagConverter::convertToEntity)
                .collect(Collectors.toList());
        List<Tag> tagsToPersist = createTagListToPersist(newTags);
        certificateModel.setTags(new HashSet<>(tagsToPersist));
        GiftCertificate updatedCertificate = giftCertificateDao.update(certificateModel);

        return giftCertificateConverter.convertToDto(updatedCertificate);
    }

    @Override
    public void delete(long id) {
        if (!giftCertificateDao.findById(id).isPresent()) {
            throw new NoSuchElementException(GIFT_CERTIFICATE_NOT_FOUND);
        }

        giftCertificateDao.deleteById(id);
    }

    private List<Tag> createTagListToPersist(List<Tag> tags) {
        List<Tag> uniqueTags = tags.stream()
                .distinct()
                .collect(Collectors.toList());
        List<Tag> tagsToPersist = new ArrayList<>();
        uniqueTags.forEach(newTag -> {
            Optional<Tag> foundTag = tagDao.findByName(newTag.getName());
            tagsToPersist.add(foundTag.orElse(newTag));
        });
        return tagsToPersist;
    }
}
