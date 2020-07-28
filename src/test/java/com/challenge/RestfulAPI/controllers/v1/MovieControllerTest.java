package com.challenge.RestfulAPI.controllers.v1;

import com.challenge.RestfulAPI.domains.Movie;
import com.challenge.RestfulAPI.repositories.MovieRepository;
import com.challenge.RestfulAPI.services.MovieService;
import com.challenge.RestfulAPI.api.v1.mappers.MovieMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author abdessamadM on 24/06/2020
 */
public class MovieControllerTest {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private MovieService movieService;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;


    private MockMvc restMovieMockMvc;
    private Movie movie;

    @BeforeEach
    public void setup() {
        /*MockitoAnnotations.initMocks(this);
        final MovieController movieController = new MovieController(movieService, restTemplate);
        this.restMovieMockMvc = MockMvcBuilders.standaloneSetup(movieController).build();*/
    }

    @Before("")
    public void initTest() {
        //movieRepository.deleteAll();
    }
    @Test
    public void createMoviesTest() throws Exception {
        //int databaseSizeBeforeCreate = movieRepository.findAll().size();
        /* TODO*/
        // Create the movie
        // Validate the Movie in the database
    }

    @Test
    public void getAllMoviesTest() throws Exception {
        // Initialize the database
        //movieRepository.save(movie);

        // Get all the movieList
        /*restMovieMockMvc.perform(get("/api/movies?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))*/
                /*add test to check json content */;
    }

    @Test
    public void getMovieTest() throws Exception {
        // Initialize the database
        //movieRepository.save(movie);

        // Get the movie
        /*restMovieMockMvc.perform(get("/api/movies/{id}", movie.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))*/
                /*add  test body Json */;
    }

    @Test
    public void deleteCommentTest() throws Exception {
        // Initialize the database
        //movieRepository.save(movie);
        //int databaseSizeBeforeDelete = movieRepository.findAll().size();

        // Get the movie
        // restMovieMockMvc.perform(delete("/api/movies/{id}", movie.getId())
                // add test

        // Validate the database is empty
        //List<Movie> movieList = movieRepository.findAll();
        //assertThat(movieList).hasSize(databaseSizeBeforeDelete - 1);
    }

}
