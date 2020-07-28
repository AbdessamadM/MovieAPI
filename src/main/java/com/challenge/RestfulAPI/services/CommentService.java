package com.challenge.RestfulAPI.services;

import com.challenge.RestfulAPI.api.v1.mappers.CommentMapper;
import com.challenge.RestfulAPI.api.v1.models.CommentDTO;
import com.challenge.RestfulAPI.domains.Comment;
import com.challenge.RestfulAPI.helpers.exceptions.CreatingObjectException;
import com.challenge.RestfulAPI.repositories.CommentRepository;
import com.challenge.RestfulAPI.api.v1.mappers.MovieMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author abdessamadM on 23/06/2020
 */
@Slf4j
@Service
public class CommentService implements ServiceShared<CommentDTO, Long> {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final MovieMapper movieMapper;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper, MovieMapper movieMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.movieMapper = movieMapper;
    }

    /**
     * Create a comment
     *
     * @param commentDTO the entity to create
     * @return the record who has been created
     */
    @Override
    public CommentDTO create(CommentDTO commentDTO) {
        log.debug("Request to create Comment : {}", commentDTO);
        Comment commentToCreate = commentMapper.toEntity(commentDTO);
        CommentDTO commentCreated = null;
        try {
            Comment comment = commentRepository.save(commentToCreate);
            commentCreated = commentMapper.toDto(comment);
        } catch (Exception e) {
            log.error("Error occurred in creating Comment");
            throw new CreatingObjectException("Error occurred in creating Comment");
        }
        return commentCreated;
    }

    /**
     * Update a comment
     *
     * @param commentDTO to use to update an existing record
     * @param id  of the record to update
     * @return the record who has been updated
     */
    @Override
    public CommentDTO update(CommentDTO commentDTO, Long id) {
        log.debug("Request to update Comment : {}, {}", commentDTO , id);
        Comment comment = commentRepository.getOne(id);
        if(commentDTO.getComment()!=null){
            comment.setComment(commentDTO.getComment());
        }
        if(commentDTO.getDate()!=null){
            comment.setDate(commentDTO.getDate());
        }
        if(commentDTO.getMovie()!=null){
            comment.setMovie(movieMapper.toEntity(commentDTO.getMovie()));
        }
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    /**
     * Get all the comments
     *
     * @return the list of entities
     */
    @Override
    public List<CommentDTO> getAll() {
        log.debug("Request to get all Comments");
        List<Comment> comments = commentRepository.findAll();
        if(comments.isEmpty()){
            log.error("The Comment table is empty");
            return null;
        }
        return commentMapper.toDtos(comments);
    }

    /**
     * Get comments by movieId (Filter)
     *
     * @param movieId  to filter with
     * @return list of entities
     */
    public List<CommentDTO> getByMovieId(Long movieId){
        log.debug("Request to get Comments by movieId : {}", movieId);
        List<Comment> comments = commentRepository.findByMovieId(movieId);
        if(comments.isEmpty()){
            log.error("The Comments with movieId ({}) are not found in database", movieId);
            return null;
        }
        return commentMapper.toDtos(comments);
    }

    /**
     * Get one comment by id
     *
     * @param id the id to use to get the record
     * @return the entity
     */
    @Override
    public CommentDTO getById(Long id) {
        log.debug("Request to get Comment : {}", id);
        Comment comment = commentRepository.getOne(id);
        return commentMapper.toDto(comment);
    }

    /**
     * Delete the comment by id
     *
     * @param id of the record to delete
     */
    @Override
    public void deleteById(Long id) {
        log.debug("Request to delete Comment : {}", id);
        commentRepository.deleteById(id);
    }
}
