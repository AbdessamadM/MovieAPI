package com.challenge.RestfulAPI.repositories;

import com.challenge.RestfulAPI.domains.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author abdessamadM on 25/06/2020
 */
@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {

}
