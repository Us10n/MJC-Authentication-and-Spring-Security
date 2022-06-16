package com.epam.esm.service.converter;

/**
 * The interface Dto entity converter.
 *
 * @param <T> the type parameter
 * @param <R> the type parameter
 */
public interface DtoEntityConverter<T, R> {
    /**
     * Convert from R type Dto to T type entity.
     *
     * @param object the object
     * @return the T type Dto
     */
    T convertToDto(R object);

    /**
     * Convert from T type entity to R type Dto.
     *
     * @param object the object
     * @return the R type entity
     */
    R convertToEntity(T object);
}
