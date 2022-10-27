package com.unity.reactfullstack.repository;

import com.unity.reactfullstack.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByName(String name);
}