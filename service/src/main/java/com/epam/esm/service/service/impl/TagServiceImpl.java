package com.epam.esm.service.service.impl;

import com.epam.esm.domain.dto.TagDto;
import com.epam.esm.domain.entity.Tag;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.service.exception.*;
import com.epam.esm.service.mapper.TagMapper;
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

    /**
     * Instantiates a new Tag service.
     *
     * @param tagDao       the tag dao
     */
    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
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

        Tag tagModel = TagMapper.INSTANCE.mapToEntity(object);
        Tag createdTag = tagDao.create(tagModel);

        return TagMapper.INSTANCE.mapToDto(createdTag);
    }

    @Override
    public PagedModel<TagDto> readAll(Integer page, Integer limit) {
        List<TagDto> tagDtos = tagDao.findAll(page, limit)
                .stream()
                .map(TagMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());

        if(tagDtos.isEmpty()){
            throw new EmptyListRequestedException();
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

        return TagMapper.INSTANCE.mapToDto(foundTag);
    }

    @Override
    public PagedModel<TagDto> findWidelyUsedTagOfUserWithHighestCostOfAllOrders(Integer page, Integer limit) {
        List<Tag> tags = tagDao.findWidelyUsedTagsOfUserWithHighestCostOfAllOrders(page, limit);
        List<TagDto> tagDtos = tags.stream()
                .map(TagMapper.INSTANCE::mapToDto)
                .collect(Collectors.toList());

        if(tagDtos.isEmpty()){
            throw new EmptyListRequestedException();
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
