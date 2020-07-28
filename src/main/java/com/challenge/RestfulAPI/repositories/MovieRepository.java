package com.challenge.RestfulAPI.repositories;

import com.challenge.RestfulAPI.domains.Movie;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author abdessamadM on 23/06/2020
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByYear(String year, Sort sort);
}
