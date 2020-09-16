package com.challenge.RestfulAPI.controllers.v1;

import com.challenge.RestfulAPI.api.v1.models.CommentDTO;
import com.challenge.RestfulAPI.api.v1.models.MovieDTO;
import com.challenge.RestfulAPI.controllers.v1.util.HeaderUtil;
import com.challenge.RestfulAPI.controllers.v1.vm.CommentObj;
import com.challenge.RestfulAPI.helpers.Constants;
import com.challenge.RestfulAPI.services.MovieService;
import com.challenge.RestfulAPI.services.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

/**
 * @Author abdessamadM on 23/06/2020
 */
@Api(tags = "Comment", value = "Operations pertaining to comments")
@Slf4j
@RestController
@RequestMapping(Constants.COMMENT) // COMMENT is a constant variable exist in 'helpers.Constants'
public class CommentController {

    private final CommentService commentService;
    private final MovieService movieService;

    @Autowired
    public CommentController(CommentService commentService, MovieService movieService) {
        this.commentService = commentService;
        this.movieService = movieService;
    }

    /**
     * POST  /comments : Create a new comment.
     *
     * @param commentObj the commentObj is the request body that contains {idMovie and comment}
     * @return the ResponseEntity with status 201 (Created) and with body the new commentDTO, or with status 400 (Bad Request) if the comment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(value = "Create a new comment", response = CommentDTO.class)
    @PostMapping({""})
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentObj commentObj) throws URISyntaxException{
        log.debug("REST-POST request to create comment : {}", commentObj);
        CommentDTO commentDTO =new CommentDTO();
        MovieDTO movieDTO = new MovieDTO();
        // Get movie entity
        if(commentObj.getMovieId() != null){
            movieDTO = movieService.getById(commentObj.getMovieId());
        }
        if(movieDTO == null || commentObj.getMovieId() == null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("Comment", "movieIdNull",
                    "The movie you are trying to comment doesn't exist or movieId is Null")).body(null);
        }
        if(commentObj.getComment() == null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("Comment", "commentNull",
                    "The comment value is Null")).body(null);
        }
        LocalDate dateNow = LocalDate.now();
        commentDTO.setComment(commentObj.getComment());
        commentDTO.setMovie(movieDTO);
        commentDTO.setDate(dateNow);

        CommentDTO result = commentService.create(commentDTO);
        return ResponseEntity.created(new URI(Constants.COMMENT + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("Comment", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /comments/:id : Updates an existing comment.
     *
     * @param commentDTO the comment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commentDTO,
     *      * or with status 400 (Bad Request) if the commentDTO is not valid,
     *      * or with status 500 (Internal Server Error) if the commentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(value = "Update an existing comment", response = CommentDTO.class)
    @PutMapping({""})
    public ResponseEntity<CommentDTO> updateComment(@Valid @RequestBody CommentDTO commentDTO) throws URISyntaxException {
        log.debug("REST request to update Comment : {}", commentDTO);
        // If the comment id doesn't exist, go create it
        if(commentDTO.getId() == null){
            return createComment(new CommentObj(commentDTO.getMovie().getId(), commentDTO.getComment()));
        }
        CommentDTO result = commentService.update(commentDTO, Long.valueOf(commentDTO.getId().toString()));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("Comment", commentDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /comments : get all the comments
     *
     * @param movieId used for filtering the result, to get comments related to this movieId
     * @return ResponseEntity with status 200 (OK) and the list of comments in the body
     */
    @ApiOperation(value = "Get all available comments from database", response = CommentDTO.class)
    @GetMapping({""})
    public ResponseEntity<List<CommentDTO>> getAllComments(@RequestParam(required = false) Long movieId){
        log.debug("REST request to get all comments");
        List<CommentDTO> commentDTOList;
        if(movieId == null){
            commentDTOList = commentService.getAll();
        } else {
            commentDTOList = commentService.getByMovieId(movieId);
        }
        if(commentDTOList == null){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok().body(commentDTOList);
    }

}
