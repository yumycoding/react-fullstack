package com.unity.reactfullstack.service;

import com.unity.reactfullstack.dto.Users;
import com.unity.reactfullstack.entities.Student;
import com.unity.reactfullstack.exception.RestTemplateResponseErrorHandler;
import com.unity.reactfullstack.repository.StudentRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.util.List;

@Service

public class StudentService {

    private final StudentRepository studentRepository;

    private RestTemplate restTemplate;

    public StudentService(StudentRepository studentRepository, RestTemplateBuilder restTemplateBuilder) {
        this.studentRepository = studentRepository;
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler())
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
    }


    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> allStudent() {
        return studentRepository.findAll();
    }

    public Student findByName(String name) {
        return studentRepository.findByName(name);
    }

    public Student findById(Integer id) {
        return studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Users fetchUsersById(Long userId) {
        return restTemplate.getForObject("https://jsonplaceholder.typicode.com/users/" + userId, Users.class);
    }
}

