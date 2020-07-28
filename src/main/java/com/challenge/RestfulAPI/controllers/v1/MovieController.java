package com.challenge.RestfulAPI.controllers.v1;

import com.challenge.RestfulAPI.api.v1.models.MovieDTO;
import com.challenge.RestfulAPI.helpers.Constants;
import com.challenge.RestfulAPI.services.MovieService;
import com.challenge.RestfulAPI.controllers.v1.util.HeaderUtil;
import com.challenge.RestfulAPI.helpers.exceptions.NullValueException;
import com.challenge.RestfulAPI.controllers.v1.vm.Search;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author abdessamadM on 23/06/2020
 */
@Api(tags = "Movie", value = "Operations pertaining to movies")
@Slf4j
@RestController
@RequestMapping(Constants.MOVIE) // MOVIE is a constant variable exist in 'helpers.Constants'
public class MovieController {

    private final MovieService movieService;
    private final RestTemplate restTemplate;

    // Api key value extracted from application.properties file
    @Value("${api.key}")
    private String apiKey;

    @Autowired
    public MovieController(MovieService movieService, RestTemplate restTemplate) {
        this.movieService = movieService;
        this.restTemplate = restTemplate;
    }

    /**
     * POST  /movies : Create movies
     *
     * @param movieTitle movie title that you are looking for
     * @return ResponseEntity with status 201 (Created) and with body that include a list of movie object, along with all data fetched from external API.
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @ApiOperation(value = "Create movies fetched from external API", response = MovieDTO.class)
    @PostMapping({""})
    public ResponseEntity<List<MovieDTO>> createMovies(@RequestBody @NotBlank @NotNull String movieTitle) throws URISyntaxException {
        log.debug("REST-POST request to create movies : {}", movieTitle);
        // return bad request response if movieTitle is not valid
        if(Strings.isNullOrEmpty(movieTitle)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                    "Movie", "titleEmpty", "The movie title should not be empty")).body(null);
        }
        // Url de l'api externe filtré par le titre du film et le type initialisé par "movie"
        String url = "http://www.omdbapi.com/?s=" + movieTitle + "&apikey=" + apiKey + "&type=movie";

        // Utilisation de ParameterizedTypeReference pour convertir la réponse JSON à l'objet Search
        ParameterizedTypeReference<Search> parameterizedTypeReference = new ParameterizedTypeReference<Search>() {};
        // Utilisation de la méthode exchange pour consommer le service web avec la méthode POST
        ResponseEntity<Search> response = restTemplate.exchange(url, HttpMethod.POST,
                null, parameterizedTypeReference);

        // return bad request response if response body is null
        if(response.getBody() == null || response.getBody().getSearchResponse().equals("False") || response.getBody().getTotalResults() ==null ){
            log.error("The movie you are looking for is not found");
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                    "Movie", "noResults", "The movie you are looking for is not found!")).body(null);
        }

        // initialisation de la liste a retourner dans la réponse de la requête
        List<MovieDTO> movieDTOList = new ArrayList<MovieDTO>();
        int total = Integer.parseInt(response.getBody().getTotalResults());
        Search searchResult = response.getBody();
        // La condition ci-dessous sert pour le traitement de pagination, il sera executé si le résultat total est supérieur à 10
        // l'api externe utilise un système de pagination et elle fournit 10 résultat par appelle
        if(total>10){
            double numberOfPage = Math.ceil((double)total/10);
            for(int i=2; i<=(int)numberOfPage; i++){
                ResponseEntity<Search> resp = restTemplate.exchange("http://www.omdbapi.com/?s=" + movieTitle + "&apikey=" + apiKey + "&type=movie&page="+i
                        , HttpMethod.POST, null, parameterizedTypeReference);
                if(resp.getBody()!=null){
                    searchResult.getMovieDTOS().addAll(resp.getBody().getMovieDTOS());
                }
            }
        }
        for(MovieDTO movie: searchResult.getMovieDTOS()){
            //call method create in movieService to complete process of storing the object in the database
            MovieDTO movieDTO = movieService.create(movie);
            // fill movieDTOList for the request response purpose
            if(movieDTO != null) {
                movieDTOList.add(movieDTO);
            }
        }
        return ResponseEntity.created(new URI(Constants.MOVIE))
                .headers(HeaderUtil.createEntityCreationAlert("Movie", movieDTOList.size() +" of movie created"))
                .body(movieDTOList);
    }

    /**
     * PUT  /movies/:id : Updates an existing movie.
     *
     * @param movieDTO the movie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated movieDTO,
     *      * or with status 400 (Bad Request) if the movieDTO is not valid,
     *      * or with status 500 (Internal Server Error) if the movieDTO couldn't be updated
     */
    @ApiOperation(value = "Update an existing movie", response = MovieDTO.class)
    @PutMapping({""})
    public ResponseEntity<MovieDTO> updateMovie(@Valid @RequestBody MovieDTO movieDTO) {
        log.debug("REST request to update Movie : {}", movieDTO);
        if(movieDTO == null) {
            throw new NullValueException("The given object is null");
        }
        MovieDTO result = movieService.update(movieDTO, Long.valueOf(movieDTO.getId().toString()));
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("Movie", movieDTO.getId().toString()))
                .body(result);
    }

    /**
     * GET  /movies : get all the movies
     *
     * @param year used for filtering the result
     * @param sort used for sorting the result with a specific property
     * @return ResponseEntity with status 200 (OK) and the list of movies in the body
     */
    @ApiOperation(value = "Get all available movies from database", response = MovieDTO.class)
    @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
          value = "Sorting criteria in the format: property(,asc|desc). " +
                    "Default sort order is ascending. " +
                    "Multiple sort criteria are supported.")
    @GetMapping({""})
    public ResponseEntity<List<MovieDTO>> getAllMovies(@RequestParam(required = false) String year, Sort sort){
        log.debug("REST request to get all movies");
        List<MovieDTO> movieDTOList = new ArrayList<MovieDTO>();
        if(year == null && sort == null){
            movieDTOList = movieService.getAll();
        } else if(year !=null){
            movieDTOList = movieService.getMoviesByYear(year, sort);
        } else {
            movieDTOList = movieService.getMoviesSortedByProperty(sort);
        }
        if(movieDTOList ==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok().body(movieDTOList);
    }

    /**
     * * GET  /movies/:id : get the "id" movie
     *
     * @param id of the movieDTO to retrieve
     * @return ResponseEntity with status 200 (OK) and with body "movieDTO", or with status 404 (Not Found)
     */
    @ApiOperation(value = "Get movie by id", response = MovieDTO.class)
    @GetMapping({"/{id}"})
    public ResponseEntity<MovieDTO> getMovie(@PathVariable Long id){
        log.debug("REST request to get all movie : {}", id);
        MovieDTO movieDTO = movieService.getById(id);
        if (movieDTO == null ){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("Movie", "idNotExists",
                    "The Id you are looking for doesn't exist")).body(null);
        }
        return ResponseEntity.ok().body(movieDTO);
    }

    /**
     * DELETE  /movies/:id : delete the "id" movie.
     *
     * @param id the id of the movieDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @ApiOperation(value = "Delete movie")
    @DeleteMapping({"/{id}"})
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        log.debug("REST request to delete Movie : {}", id);
        if (id == null) {
            throw new NullValueException("The given id is null");
        }
        MovieDTO movieDTO = movieService.getById(id);
        if (movieDTO == null ){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("Movie", "idNotExists",
                    "The Id you are trying to delete doesn't exist")).body(null);
        }

        movieService.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("Movie", id.toString())).build();
    }

}
