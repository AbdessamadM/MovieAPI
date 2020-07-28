package com.challenge.RestfulAPI.api.v1.mappers;

/**
 * @Author abdessamadM on 23/06/2020
 */

import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */
public interface EntityMapper<D, E> {

    public E toEntity(D dto);

    public D toDto(E entity);

    public List<E> toEntities(List<D> dtos);

    public List<D> toDtos(List<E> entities);
}