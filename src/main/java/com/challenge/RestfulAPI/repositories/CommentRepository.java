package com.challenge.RestfulAPI.repositories;

import com.challenge.RestfulAPI.domains.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author abdessamadM on 23/06/2020
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value="SELECT c.* FROM comment c WHERE c.movie_id = :movieId", nativeQuery = true)
    List<Comment> findByMovieId(@Param("movieId") Long movieId);


    // query that retrieve number of comments of the movie in the specified date range
    @Query(value = "SELECT m.id, count(c.id) as totalComments " +
            "FROM comment as c right join movie as m on m.id=c.movie_id " +
            "WHERE m.year between :startDate and :endDate " +
            "GROUP BY m.id", nativeQuery = true)
    List<Object[]> findTotalCommentsByEachMovie(@Param("startDate") Integer startDate, @Param("endDate") Integer endDate);

}
