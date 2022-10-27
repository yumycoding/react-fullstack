package com.unity.reactfullstack.controller;

import com.unity.reactfullstack.dto.Users;
import com.unity.reactfullstack.entities.Student;
import com.unity.reactfullstack.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/students")
public class StudentController {

    private final StudentService service;


    @GetMapping(path = "/name")
    ResponseEntity<Student> findStudentByName(@RequestParam("name") String name) {
        return new ResponseEntity<>(service.findByName(name), HttpStatus.FOUND);
    }

    @GetMapping(path = "/id")
    ResponseEntity<Student> findStudentByName(@RequestParam("id") Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.FOUND);
    }

    @GetMapping(path = "/all")
    ResponseEntity<List<Student>> findAllStudent() {
        return new ResponseEntity<>(service.allStudent(), HttpStatus.FOUND);
    }

    @PostMapping(path = "/save")
    ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student) {
        Student saveStudent = service.saveStudent(student);
        return new ResponseEntity<>(saveStudent, HttpStatus.CREATED);
    }

    @GetMapping(path = "user/{id}")
    ResponseEntity<Users> findUsers(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.fetchUsersById(id));
    }


}
