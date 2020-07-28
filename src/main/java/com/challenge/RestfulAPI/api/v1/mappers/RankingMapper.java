package com.challenge.RestfulAPI.api.v1.mappers;

import com.challenge.RestfulAPI.api.v1.models.RankingDTO;
import com.challenge.RestfulAPI.domains.Ranking;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author abdessamadM on 25/06/2020
 */
@Mapper()
public interface RankingMapper extends EntityMapper<RankingDTO, Ranking>{

    RankingMapper INSTANCE = Mappers.getMapper(RankingMapper.class);
}
