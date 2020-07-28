package com.challenge.RestfulAPI.services;

import com.challenge.RestfulAPI.helpers.exceptions.CreatingObjectException;
import com.challenge.RestfulAPI.api.v1.mappers.RankingMapper;
import com.challenge.RestfulAPI.api.v1.models.RankingDTO;
import com.challenge.RestfulAPI.domains.Ranking;
import com.challenge.RestfulAPI.repositories.CommentRepository;
import com.challenge.RestfulAPI.repositories.RankingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author abdessamadM on 25/06/2020
 */

@Slf4j
@Service
public class RankingService {

    private final RankingMapper rankingMapper;
    private final RankingRepository rankingRepository;
    private final CommentRepository commentRepository;


    @Autowired
    public RankingService(RankingMapper rankingMapper, RankingRepository rankingRepository, CommentRepository commentRepository){
        this.rankingMapper = rankingMapper;
        this.rankingRepository = rankingRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * This method is going to create a ranking list of all movies within a specified date range
     *
     * @param startDate start date
     * @param endDate end date
     */
    public void createListOfRanking(LocalDate startDate, LocalDate endDate){

        // get Total comments by movie if movie released (Year) is between year of startDate and year of endDate
        List<Object[]> totalComments = commentRepository.findTotalCommentsByEachMovie(startDate.getYear(), endDate.getYear());
        if(!totalComments.isEmpty()){
            rankingRepository.deleteAll();
            for(int i=0; i<totalComments.size(); i++){
                try {
                    Object[] obj = totalComments.get(i);
                    RankingDTO rankingDTO = new RankingDTO(
                            Integer.valueOf(String.valueOf(obj[0])),
                            Integer.valueOf(String.valueOf(obj[1])),
                            0);
                    rankingRepository.save(rankingMapper.toEntity(rankingDTO));
                } catch (Exception e) {
                    throw new CreatingObjectException("Error occurred in creating Movie");
                }
            }
        } else {
            log.error("Error occurred in creating Ranking list, There's no movies in this date range ");
        }
    }

    /**
     * This method is going to determine rank of each object in ranking list
     * and save the result in ranking table
     *
     * @return The list of top ranking
     */
    public List<RankingDTO> getTopRanking(){
        // sort list ranking by totalComments {descendants}
        List<Ranking> rankingList = rankingRepository.findAll(Sort.by(Sort.Direction.DESC, "totalComments"));
        if(rankingList.isEmpty()){
            log.error("The ranking table is empty ");
            return null;
        }
        int rankNumber=0; int lastTotalComment = -1;
        List<RankingDTO> rankingDTOList = rankingMapper.toDtos(rankingList);
        for (int i=0; i<rankingDTOList.size(); i++) {
            if(lastTotalComment != rankingDTOList.get(i).getTotalComments()){
                rankingDTOList.get(i).setRank(++rankNumber);
            } else {
                rankingDTOList.get(i).setRank(rankNumber);
            }
            lastTotalComment = rankingDTOList.get(i).getTotalComments();
        }
        rankingRepository.saveAll(rankingMapper.toEntities(rankingDTOList));

        return  rankingDTOList;
    }

}
