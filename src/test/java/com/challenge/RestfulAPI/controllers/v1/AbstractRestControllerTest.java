package com.challenge.RestfulAPI.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * @Author abdessamadM on 24/06/2020
 */

public abstract class AbstractRestControllerTest {

    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
