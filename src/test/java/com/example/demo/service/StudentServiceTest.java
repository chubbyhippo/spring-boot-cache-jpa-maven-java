package com.example.demo.service;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void shouldGetStudents() {
        when(studentRepository.findAll())
                .thenReturn(List.of(new Student(1L, "Abby"),
                        new Student(1L, "Brad"),
                        new Student(1L, "Char"),
                        new Student(1L, "Dicky")));

        var studentDtos = studentService.getStudents();
        assertThat(studentDtos.size()).isGreaterThan(0);
        verify(studentRepository, times(1)).findAll();

    }

    @Test
    void shouldAddStudent() {

        when(studentRepository.save(any(Student.class))).thenReturn(new Student(5L, "Elena"));
        when(studentRepository.findAll())
                .thenReturn(List.of(new Student(1L, "Abby"),
                        new Student(1L, "Brad"),
                        new Student(1L, "Char"),
                        new Student(1L, "Dicky"),
                        new Student(1L, "Elena")));

        List<StudentDto> studentDtos = studentService.addStudent("Elena");
        assertThat(studentDtos.size()).isGreaterThan(0);
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(studentRepository, times(1)).findAll();
    }
}