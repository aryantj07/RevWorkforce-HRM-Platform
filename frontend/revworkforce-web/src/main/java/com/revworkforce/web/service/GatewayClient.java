package com.revworkforce.web.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GatewayClient {

    private final RestClient restClient;

    public GatewayClient(
            RestClient.Builder restClientBuilder,
            @Value("${gateway.base-url}") String gatewayUrl) {

        this.restClient = restClientBuilder
                .baseUrl(gatewayUrl)
                .build();
    }

    public RestClient.RequestHeadersSpec<?> authenticatedGet(
            String uri,
            HttpSession session) {

        String token = (String) session.getAttribute("token");

        return restClient.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    public RestClient.RequestBodySpec authenticatedPost(
            String uri,
            HttpSession session) {

        String token = (String) session.getAttribute("token");

        return restClient.post()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    public RestClient.RequestBodySpec authenticatedPut(
            String uri,
            HttpSession session) {

        String token = (String) session.getAttribute("token");

        return restClient.put()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    public RestClient.RequestHeadersSpec<?> authenticatedDelete(
            String uri,
            HttpSession session) {

        String token = (String) session.getAttribute("token");

        return restClient.delete()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    public RestClient.RequestBodySpec authenticatedPatch(
            String uri,
            HttpSession session) {

        String token = (String) session.getAttribute("token");

        return restClient.patch()
                .uri(uri)
                .header(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + token
                );
    }
}