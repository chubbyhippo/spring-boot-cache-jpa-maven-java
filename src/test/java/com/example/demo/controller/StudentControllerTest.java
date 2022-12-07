package com.example.demo.controller;

import com.example.demo.dto.StudentDto;
import com.example.demo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private StudentService studentService;

    @Test
    void getGetStudents() {
        when(studentService.getStudents()).thenReturn(List.of(new StudentDto(1L, "Abby"),
                new StudentDto(2L, "Bill"),
                new StudentDto(3L, "Chars"),
                new StudentDto(4L, "Dicky")));

        webTestClient.get()
                .uri("/students")
                .exchange()
                .expectBodyList(StudentDto.class)
                .hasSize(4);

        verify(studentService, times(1)).getStudents();

    }

    @Test
    void addAddStudent() {
        when(studentService.addStudent(anyString())).thenReturn(List.of(new StudentDto(1L, "Abby"),
                new StudentDto(2L, "Bill"),
                new StudentDto(3L, "Chars"),
                new StudentDto(4L, "Dicky"),
                new StudentDto(5L, "Elena")));

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/students")
                        .queryParam("name", "Elena")
                        .build())
                .exchange()
                .expectBodyList(StudentDto.class)
                .hasSize(5);

        verify(studentService, times(1)).addStudent(anyString());
    }
}