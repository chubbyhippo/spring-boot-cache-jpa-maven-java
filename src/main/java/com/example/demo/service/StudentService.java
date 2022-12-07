package com.example.demo.service;

import com.example.demo.dto.StudentDto;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    @Cacheable(value = "students", key = "'key'", sync = true)
    public List<StudentDto> getStudents() {
        simulateSlowService();
        return studentRepository.findAll().stream().map(student -> new StudentDto(student.getId(), student.getName()))
                .toList();
    }

    private void simulateSlowService() {
        try {
            Thread.sleep(Duration.of(5, ChronoUnit.SECONDS));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @CachePut(value = "students", key = "'key'")
    public List<StudentDto> addStudent(String name) {
        studentRepository.save(new Student(null, name));
        return studentRepository.findAll().stream().map(student -> new StudentDto(student.getId(), student.getName()))
                .toList();
    }


}
