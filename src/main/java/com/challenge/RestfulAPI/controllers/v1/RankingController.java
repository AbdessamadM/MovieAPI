package com.challenge.RestfulAPI.controllers.v1;

import com.challenge.RestfulAPI.api.v1.models.RankingDTO;
import com.challenge.RestfulAPI.services.RankingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author abdessamadM on 25/06/2020
 */

@Api(tags = "Ranking", value = "Operation pertaining to Ranking")
@Slf4j
@RestController
@RequestMapping("/api/top")
public class RankingController {

    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService){ this.rankingService = rankingService;}

    /**
     * GET  /top : get top movies
     *
     * @param startDate start date in range
     * @param endDate end date in range
     * @return ResponseEntity with status 200 (OK) and the list of ranking in the body
     */
    @ApiOperation(value = "Get top ranking movies based on a number of comments", response = RankingDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", dataType = "LocalDate",
                    value = "Start date in range in this format : yyyy-MM-dd"),
            @ApiImplicitParam(name = "endDate", dataType = "LocalDate",
                    value = "End date in range in this format : yyyy-MM-dd"),
    })
    @GetMapping({""})
    public ResponseEntity<List<RankingDTO>> getTop(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                   @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        rankingService.createListOfRanking(startDate, endDate);
        List<RankingDTO> rankingDTOList = rankingService.getTopRanking();
        if(rankingDTOList == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok().body(rankingDTOList);
    }
}
