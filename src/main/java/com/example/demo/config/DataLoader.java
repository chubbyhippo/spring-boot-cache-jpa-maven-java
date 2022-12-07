package com.example.demo.config;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final StudentRepository studentRepository;

    @Override
    public void run(ApplicationArguments args) {
        studentRepository.saveAll(Stream.of("Abby",
                        "Bill",
                        "Chars",
                        "Dicky")
                .map(s -> new Student(null, s))
                .toList()
        );
    }
}
