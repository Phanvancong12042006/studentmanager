package com.example.studentmanager.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.studentmanager.entity.Student;
import com.example.studentmanager.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> searchByName(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return studentRepository.findByFullNameContainingIgnoreCaseOrStudentCodeContainingIgnoreCase(keyword, keyword);
        }
        return studentRepository.findAll();
    }

    public Student getStudentById(UUID id) {
        return studentRepository.findById(id).orElse(null);
    }
}