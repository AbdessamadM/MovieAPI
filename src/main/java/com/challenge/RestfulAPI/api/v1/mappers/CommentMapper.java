package com.challenge.RestfulAPI.api.v1.mappers;

import com.challenge.RestfulAPI.api.v1.models.CommentDTO;
import com.challenge.RestfulAPI.domains.Comment;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author abdessamadM on 23/06/2020
 */
@Mapper(uses = {MovieMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper extends EntityMapper<CommentDTO, Comment>{

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);
}
