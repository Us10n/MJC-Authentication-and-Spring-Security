package com.epam.esm.service.converter;

import com.epam.esm.domain.dto.TagDto;
import com.epam.esm.domain.entity.Tag;
import com.epam.esm.service.converter.impl.TagConverter;
import com.epam.esm.service.mapper.TagMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagConverterTest {

    private Tag tag;
    private TagDto tagDto;
    private TagConverter tagConverter = new TagConverter();

    @BeforeAll
    public void setup() {
        tag = new Tag(1, "food");
        tagDto=new TagDto(1,"food");
    }

    @Test
    void convertToDto() {
       TagDto actual =tagConverter.convertToDto(tag);
       TagDto expected = tagDto;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void test(){
        TagDto actual = TagMapper.INSTANCE.mapToDto(tag);
        TagDto expected = tagDto;

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void test2(){
        Tag actual = TagMapper.INSTANCE.mapToEntity(tagDto);
        Tag expected = tag;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void convertToEntity() {
        Tag actual = tagConverter.convertToEntity(tagDto);
        Tag expected = tag;

        Assertions.assertEquals(expected, actual);
    }
}