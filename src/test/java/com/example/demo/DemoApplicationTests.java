package com.example.demo;

import com.example.demo.dto.StudentDto;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "10000")
class DemoApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldGetStudents() {
        webTestClient.get()
                .uri("/students")
                .exchange()
                .expectBodyList(StudentDto.class)
                .hasSize(4);
    }

    @Test
    @DirtiesContext
    void shouldAddStudent() {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/students")
                        .queryParam("name", "Elena")
                        .build())
                .exchange()
                .expectBodyList(StudentDto.class)
                .hasSize(5);
    }

    @Test
    void shouldNotGetStudentsMoreThanASecondAfterBeingCalled() {
        webTestClient.get()
                .uri("/students")
                .exchange()
                .expectBodyList(StudentDto.class)
                .hasSize(4);

        await().atMost(1L, TimeUnit.SECONDS)
                .untilAsserted(() -> webTestClient.get()
                        .uri("/students")
                        .exchange()
                        .expectBodyList(StudentDto.class)
                        .hasSize(4));
    }

    @Test
    @DirtiesContext
    void shouldRefreshCacheAfterUpdating() {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/students")
                        .queryParam("name", "Elena")
                        .build())
                .exchange()
                .expectBodyList(StudentDto.class)
                .hasSize(5);

        webTestClient.get()
                .uri("/students")
                .exchange()
                .expectBodyList(StudentDto.class)
                .hasSize(5);
    }

}
