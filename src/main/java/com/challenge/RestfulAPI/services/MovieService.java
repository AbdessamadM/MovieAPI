package com.challenge.RestfulAPI.services;

import com.challenge.RestfulAPI.api.v1.models.MovieDTO;
import com.challenge.RestfulAPI.api.v1.mappers.MovieMapper;
import com.challenge.RestfulAPI.domains.Movie;
import com.challenge.RestfulAPI.helpers.exceptions.CreatingObjectException;
import com.challenge.RestfulAPI.repositories.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author abdessamadM on 23/06/2020
 */

@Slf4j
@Service
public class MovieService implements ServiceShared<MovieDTO, Long> {

    private final MovieMapper movieMapper;
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieMapper movieMapper, MovieRepository movieRepository) {
        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
    }

    /**
     * Create a movie
     *
     * @param movieDTO the entity to create
     * @return the record who has been created
     */
    @Override
    public MovieDTO create(MovieDTO movieDTO) {
        log.debug("Request to create Movie : {}", movieDTO);
        Movie movieToCreate = movieMapper.toEntity(movieDTO);
        MovieDTO movieCreated = null;
        try {
            Movie movie = movieRepository.save(movieToCreate);
            movieCreated = movieMapper.toDto(movie);
        } catch (Exception e) {
            log.error("Error occurred in creating Movie");
            throw new CreatingObjectException("Error occurred in creating Movie");
        }
        return movieCreated;
    }

    /**
     * Update a movie
     *
     * @param movieDTO to use to update an existing record
     * @param id  of the record to update
     * @return the record who has been updated
     */
    @Override
    public MovieDTO update(MovieDTO movieDTO, Long id) {
        log.debug("Request to update Movie : {}, {}", movieDTO , id);
        Movie movie = movieRepository.getOne(id);
        if(movieDTO.getTitle() != null){
            movie.setTitle(movieDTO.getTitle());
        }
        if(movieDTO.getYear() != null){
            movie.setYear(movieDTO.getYear());
        }
        if(movieDTO.getImdbId() != null){
            movie.setImdbId(movieDTO.getImdbId());
        }
        if(movieDTO.getType() != null){
            movie.setType(movieDTO.getType());
        }
        if(movieDTO.getPosterUrl() != null){
            movie.setPosterUrl(movieDTO.getPosterUrl());
        }
        movie = movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }

    /**
     * Get all the movies
     *
     * @return the list of entities
     */
    @Override
    public List<MovieDTO> getAll() {
        log.debug("Request to get all Movies");
        List<Movie> movies = movieRepository.findAll();
        if(movies.isEmpty()){
            log.error("The Movie table is empty");
            return null;
        }
        return movieMapper.toDtos(movies);
    }

    /**
     * Get Movie by Year (Filter)
     *
     * @param year to filter with
     * @param sort optional sorting by property and order "&sort=title,asc"
     * @return list of entities
     */
    public List<MovieDTO> getMoviesByYear(String year, Sort sort) {
        log.debug("Request to get Movies by year '{}' and sort '{}'", year, sort.toString());
        List<Movie> movies = movieRepository.findByYear(year, sort);
        if(movies.isEmpty()){
            log.error("The year filter you are looking for doesn't exist {}", year);
            return null;
        }
        return movieMapper.toDtos(movies);
    }

    /**
     * Sort movie by the property plus either 'asc' or 'desc' specified in parameter
     *
     * @param sort example sort parameter "sort=title,asc"
     * @return list of entities
     */
    public List<MovieDTO> getMoviesSortedByProperty(Sort sort){
        log.debug("Request to get Movies sorted by: {}", sort.toString());
        System.out.println(sort.toString());
        List<Movie> movies = movieRepository.findAll(sort);
        if(movies.isEmpty())
        {
            log.error("The movie table is empty or the property ({}) you trying to sort with doesn't exist ", sort.toString());
            return null;
        }
        return movieMapper.toDtos(movies);
    }

    /**
     * Get one movie by id
     *
     * @param id the id to use to get the record
     * @return the entity
     */
    @Override
    public MovieDTO getById(Long id) {
        log.debug("Request to get Movie : {}", id);
        if(!movieRepository.existsById(id)){
            log.error("The Id you are looking for doesn't exist {}", id);
            return null;
        }
        Movie movie = movieRepository.getOne(id);
        return movieMapper.toDto(movie);
    }

    /**
     * Delete the movie by id
     *
     * @param id of the record to delete
     */
    @Override
    public void deleteById(Long id) {
        log.debug("Request to delete Movie : {}", id);
        movieRepository.deleteById(id);
    }

}
