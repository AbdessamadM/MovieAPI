package com.challenge.RestfulAPI.api.v1.mappers;

import com.challenge.RestfulAPI.api.v1.models.MovieDTO;
import com.challenge.RestfulAPI.domains.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author abdessamadM on 23/06/2020
 */
@Mapper()
public interface MovieMapper extends EntityMapper<MovieDTO, Movie>{

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);
}
