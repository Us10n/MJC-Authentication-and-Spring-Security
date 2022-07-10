package com.epam.esm.service.service.impl;

import com.epam.esm.domain.criteria.GiftCertificateCriteria;
import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.domain.entity.GiftCertificate;
import com.epam.esm.domain.entity.Tag;
import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.service.exception.*;
import com.epam.esm.service.mapper.GiftCertificateMapper;
import com.epam.esm.service.mapper.TagMapper;
import com.epam.esm.service.service.GiftCertificateService;
import com.epam.esm.service.util.handler.DateHandler;
import com.epam.esm.service.util.validator.GiftCertificateCriteriaValidator;
import com.epam.esm.service.util.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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

    /**
     * Instantiates a new Gift certificate service.
     *
     * @param giftCertificateDao       the gift certificate dao
     * @param tagDao                   the tag dao
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
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

        GiftCertificate certificateModel = GiftCertificateMapper.INSTANCE.mapToEntity(object);

        List<Tag> newTags = new ArrayList<>(certificateModel.getTags());
        List<Tag> tagsToPersist = createTagListToPersist(newTags);
        certificateModel.setTags(new LinkedHashSet<>(tagsToPersist));
        GiftCertificate createdCertificate = giftCertificateDao.create(certificateModel);

        return GiftCertificateMapper.INSTANCE.mapToDto(createdCertificate);
    }

    @Override
    public PagedModel<GiftCertificateDto> readAll(Integer page, Integer limit) {
        List<GiftCertificateDto> giftCertificateDtos = giftCertificateDao.findAll(page, limit)
                .stream()
                .map(GiftCertificateMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());

        if(giftCertificateDtos.isEmpty()){
            throw new EmptyListRequestedException();
        }
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

        return GiftCertificateMapper.INSTANCE.mapToDto(optionalGitCertificate.get());
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
                .map(GiftCertificateMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());

        if(certificateDtos.isEmpty()){
            throw new EmptyListRequestedException();
        }
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

        GiftCertificate certificateModel = GiftCertificateMapper.INSTANCE.mapToEntity(object);
        List<Tag> newTags = object.getTags().stream()
                .map(TagMapper.INSTANCE::mapToEntity)
                .collect(Collectors.toList());
        List<Tag> tagsToPersist = createTagListToPersist(newTags);
        certificateModel.setTags(new LinkedHashSet<>(tagsToPersist));
        GiftCertificate updatedCertificate = giftCertificateDao.update(certificateModel);

        return GiftCertificateMapper.INSTANCE.mapToDto(updatedCertificate);
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
