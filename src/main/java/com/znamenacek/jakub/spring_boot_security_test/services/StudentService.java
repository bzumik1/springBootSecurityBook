package com.znamenacek.jakub.spring_boot_security_test.services;

import com.znamenacek.jakub.spring_boot_security_test.models.Student;
import com.znamenacek.jakub.spring_boot_security_test.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentById(Integer id){
        return studentRepository.findById(id);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }
}
