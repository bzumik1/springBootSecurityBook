package com.znamenacek.jakub.spring_boot_security_test.repositories;

import com.znamenacek.jakub.spring_boot_security_test.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
