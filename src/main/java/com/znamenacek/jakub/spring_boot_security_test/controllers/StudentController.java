package com.znamenacek.jakub.spring_boot_security_test.controllers;

import com.znamenacek.jakub.spring_boot_security_test.exceptions.StudentNotFoundException;
import com.znamenacek.jakub.spring_boot_security_test.models.Student;
import com.znamenacek.jakub.spring_boot_security_test.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id){
        return studentService.getStudentById(id)
                .map(student -> new ResponseEntity<>(student,HttpStatus.OK))
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        return new ResponseEntity<>(studentService.createStudent(student), HttpStatus.OK);
    }

}
