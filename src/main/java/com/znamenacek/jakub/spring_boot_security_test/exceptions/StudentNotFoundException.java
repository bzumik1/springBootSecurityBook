package com.znamenacek.jakub.spring_boot_security_test.exceptions;

import com.google.common.base.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Integer id){
        super("Student with id "+ id +" not found.");
    }
}
