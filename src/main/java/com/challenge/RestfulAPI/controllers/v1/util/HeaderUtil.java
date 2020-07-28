package com.challenge.RestfulAPI.controllers.v1.util;


/**
 * @Author abdessamadM on 23/06/2020
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;

/**
 * Utility class for HTTP headers creation.
 */
@Slf4j
public final class HeaderUtil {

    @Value("${spring.application.name}")
    private static String APPLICATION_NAME;

    private HeaderUtil() {
    }

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RestfulAPI-alert", message);
        headers.add("X-RestfulAPI-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert(APPLICATION_NAME + "." + entityName + ".deleted", param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        log.error("Entity processing failed, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-RestfulAPI-error", "error." + errorKey);
        headers.add("X-RestfulAPI-params", entityName);
        return headers;
    }
}