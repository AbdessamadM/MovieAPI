package com.challenge.RestfulAPI.controllers.v1;

/**
 * @Author abdessamadM on 24/06/2020
 */

import com.challenge.RestfulAPI.services.CommentService;
import com.challenge.RestfulAPI.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {
    @Mock
    CommentService commentService;
    @Mock
    MovieService movieService;

    CommentController commentController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        /*MockitoAnnotations.initMocks(this);

        commentController = new CommentController(commentService,movieService);

        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();*/
    }

    @Test
    void createCommentTest() throws Exception {
        // Given
            //  commentDTO build one with => Comment.Builder()
        // When
          // do test
    }

    @Test
    void getAllCommentsTest() throws Exception {
        // Given
            //  List<CommentDTO> build  {Comment.Builder(), Comment.Builder() ..}

        // When
            // when match
        // Then
            // verify endpoint
    }
}
