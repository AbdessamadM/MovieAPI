package com.challenge.RestfulAPI.controllers.v1.vm;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author abdessamadM on 25/06/2020
 *
 * This class will serve to create a comment
 */
@Data
@AllArgsConstructor
public class CommentObj {

    @ApiModelProperty(notes = "The movie commented", example = "1")
    private Long movieId;

    @NotBlank
    @ApiModelProperty(notes = "The comment of the movie", example = "Wonderful movie")
    private String comment;

}
