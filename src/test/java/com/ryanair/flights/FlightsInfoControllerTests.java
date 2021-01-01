package com.ryanair.flights;

import com.ryanair.flights.api.impl.ApiServiceImpl;
import com.ryanair.flights.controller.FlightsInfoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.web.reactive.server.WebTestClient;


@ContextHierarchy({
        @ContextConfiguration(classes = {FlightsInfoController.class}),
        @ContextConfiguration(classes = {ApiServiceImpl.class})
})
@WebFluxTest(FlightsInfoController.class)
public class FlightsInfoControllerTests {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    ApiServiceImpl providerService;

    @Test
    public void testFightsInfoController() {
        //test with all the params
        webTestClient.get().uri(
                uriBuilder ->
                        uriBuilder
                                .path("/interconnections")
                                .queryParam("departure", "DUB")
                                .queryParam("arrival", "MAD")
                                .queryParam("departureDateTime", "2018-03-03T21:00")
                                .queryParam("arrivalDateTime", "2018-03-03T21:00")
                                .build())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testFightsInfoControllerWithBadParams() {
//        String expectedBody = "{\"errorCode\":\"007\",\"message\":\"Bad parameters.\"}";
//        //test with all the params
//        webTestClient.get().uri(
//                uriBuilder ->
//                        uriBuilder
//                                .path("/interconnections")
//                                .queryParam("departure", "D")
//                                .queryParam("arrival", "MAD")
//                                .queryParam("departureDateTime", "2018-03-03T21:00")
//                                .queryParam("arrivalDateTime", "2018-03-03T21:00")
//                                .build())
//                .exchange().expectBody()
//                .jsonPath("$.errorCode").exists();
    }
}
