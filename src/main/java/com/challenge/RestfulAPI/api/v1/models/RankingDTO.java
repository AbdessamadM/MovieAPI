package com.challenge.RestfulAPI.api.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author abdessamadM on 25/06/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingDTO implements Serializable {

    @JsonIgnore // to ignore returning id within the response of top Ranking
    @ApiModelProperty(notes = "Ranking id automatically generated by database", example = "1")
    private Long id;

    @ApiModelProperty(notes = "The movie id ranked", example="1")
    private Integer movieId;

    @ApiModelProperty(notes = "The total number of comments added to the movie", example = "2")
    private Integer totalComments;

    @ApiModelProperty(notes = "The ranking position of the movie", example = "1")
    private Integer rank;

    public RankingDTO(Integer movieId, Integer totalComments, Integer rank) {
        this.movieId = movieId;
        this.totalComments = totalComments;
        this.rank = rank;
    }
}
