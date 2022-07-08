package com.epam.esm.service.service.impl;

import com.epam.esm.domain.dto.TagDto;
import com.epam.esm.domain.entity.Tag;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.service.converter.impl.TagConverter;
import com.epam.esm.service.exception.*;
import com.epam.esm.service.service.TagService;
import com.epam.esm.service.util.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.service.exception.ExceptionMessageKey.TAG_EXIST;
import static com.epam.esm.service.exception.ExceptionMessageKey.TAG_NOT_FOUND;

/**
 * The type Tag service.
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final TagConverter tagConverter;

    /**
     * Instantiates a new Tag service.
     *
     * @param tagDao       the tag dao
     * @param tagConverter the tag converter
     */
    @Autowired
    public TagServiceImpl(TagDao tagDao, TagConverter tagConverter) {
        this.tagDao = tagDao;
        this.tagConverter = tagConverter;
    }

    @Override
    public TagDto create(TagDto object) {
        ExceptionHolder exceptionHolder = new ExceptionHolder();
        TagValidator.isTagDtoValid(object, exceptionHolder);
        if (!exceptionHolder.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionHolder);
        }
        if (tagDao.findByName(object.getName()).isPresent()) {
            throw new DuplicateEntityException(TAG_EXIST);
        }

        Tag tagModel = tagConverter.convertToEntity(object);
        Tag createdTag = tagDao.create(tagModel);

        return tagConverter.convertToDto(createdTag);
    }

    @Override
    public PagedModel<TagDto> readAll(Integer page, Integer limit) {
        List<TagDto> tagDtos = tagDao.findAll(page, limit)
                .stream()
                .map(tagConverter::convertToDto)
                .collect(Collectors.toList());

        if(tagDtos.isEmpty()){
            throw new PageNumberOutOfBoundException();
        }
        long totalNumberOfEntities = tagDao.countAll();
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(limit, page, totalNumberOfEntities);
        return PagedModel.of(tagDtos, metadata);
    }

    @Override
    public TagDto readById(long id) {
        Optional<Tag> optionalTag = tagDao.findById(id);
        Tag foundTag = optionalTag
                .orElseThrow(() -> new NoSuchElementException(TAG_NOT_FOUND));

        return tagConverter.convertToDto(foundTag);
    }

    @Override
    public PagedModel<TagDto> findWidelyUsedTagOfUserWithHighestCostOfAllOrders(Integer page, Integer limit) {
        List<Tag> tags = tagDao.findWidelyUsedTagsOfUserWithHighestCostOfAllOrders(page, limit);
        List<TagDto> tagDtos = tags.stream()
                .map(tagConverter::convertToDto)
                .collect(Collectors.toList());

        if(tagDtos.isEmpty()){
            throw new PageNumberOutOfBoundException();
        }
        long totalNumberOfEntities = tagDao.countAllWidelyUsedTagsOfUserWithHighestCostOfAllOrders();
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(limit, page, totalNumberOfEntities);
        return PagedModel.of(tagDtos, metadata);
    }

    @Override
    public void delete(long id) {
        if (!tagDao.findById(id).isPresent()) {
            throw new NoSuchElementException(TAG_NOT_FOUND);
        }
        tagDao.deleteById(id);
    }
}
