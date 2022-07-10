package com.epam.esm.service.service;

import com.epam.esm.domain.dto.TagDto;
import com.epam.esm.domain.entity.Tag;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.dao.impl.TagDaoImpl;
import com.epam.esm.service.exception.DuplicateEntityException;
import com.epam.esm.service.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.hateoas.PagedModel;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagServiceImplTest {

    @Mock
    private TagDao tagDao = Mockito.mock(TagDaoImpl.class);

    private TagService tagService;
    List<TagDto> tagDtos = new ArrayList<>();

    @BeforeAll
    public void setup() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "funny"));
        tags.add(new Tag(2, "useful"));
        tags.add(new Tag(3, "great"));
        tags.add(new Tag(4, "money"));
        tags.add(new Tag(5, "holiday"));
        tags.add(new Tag(6, "sun"));
        tags.add(new Tag(7, "drinks"));
        tags.add(new Tag(8, "food"));
        tagDtos = tags.stream().map(
                tag -> new TagDto(tag.getId(), tag.getName())
        ).collect(Collectors.toList());

        Mockito.when(tagDao.findByName("funny")).thenReturn(Optional.empty());
        Mockito.when(tagDao.findByName("useful")).thenReturn(Optional.of(tags.get(1)));
        Mockito.when(tagDao.findAll(1, 3)).thenReturn(tags.subList(0, 3));
        Mockito.when(tagDao.findById(3)).thenReturn(Optional.of(tags.get(2)));
        Mockito.when(tagDao.findById(1)).thenReturn(Optional.of(tags.get(1)));
        Mockito.when(tagDao.findWidelyUsedTagsOfUserWithHighestCostOfAllOrders(1, 10)).thenReturn(tags.subList(0, 1));
        Mockito.when(tagDao.create(tags.get(0))).thenReturn(tags.get(0));
        Mockito.when(tagDao.countAll()).thenReturn(8L);

        tagService = new TagServiceImpl(tagDao);
    }


    @Test
    void createPositive() {
        TagDto expected = new TagDto(1, "funny");
        TagDto actual = tagService.create(expected);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createNegative() {
        TagDto expected = new TagDto(2, "useful");
        Assertions.assertThrows(DuplicateEntityException.class, () -> tagService.create(expected));
    }

    @Test
    void readAll() {
        PagedModel<TagDto> readTags = tagService.readAll(1, 3);
        List<TagDto> expected = tagDtos.subList(0, 3);
        List<TagDto> actual = new ArrayList<>(readTags.getContent());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readById() {
        TagDto actual = tagService.readById(1);
        TagDto expected = tagDtos.get(1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deletePositive() {
        Assertions.assertDoesNotThrow(() -> tagService.delete(1));
    }

    @Test
    void findWidelyUsedTagOfUserWithHighestCostOfAllOrders() {
        TagDto actual = (TagDto) tagService.findWidelyUsedTagOfUserWithHighestCostOfAllOrders(1, 10).getContent().toArray()[0];
        TagDto expected = tagDtos.get(0);
        Assertions.assertEquals(expected, actual);
    }
}