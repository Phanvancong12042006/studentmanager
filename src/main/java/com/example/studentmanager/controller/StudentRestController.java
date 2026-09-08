package com.example.studentmanager.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.studentmanager.Student;
import com.example.studentmanager.repository.StudentRepository;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    @Autowired
    private StudentRepository studentRepository;

    // 1. GET /api/students - Lấy tất cả sinh viên
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    // 2. GET /api/students/{id} - Lấy sinh viên theo ID (UUID)
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") UUID id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 3. GET /api/students/code/{code} - Tìm theo mã sinh viên
    @GetMapping("/code/{code}")
    public ResponseEntity<Student> getStudentByCode(@PathVariable("code") String code) {
        Optional<Student> student = studentRepository.findByStudentCode(code);
        return student.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 4. GET /api/students/search?keyword=... - Tìm kiếm sinh viên
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(@RequestParam("keyword") String keyword) {
        List<Student> result = studentRepository
                .findByFullNameContainingIgnoreCaseOrStudentCodeContainingIgnoreCase(keyword, keyword);
        return ResponseEntity.ok(result);
    }

    // 5. POST /api/students - Thêm sinh viên
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student savedStudent = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }

    // 6. PUT /api/students/{id} - Cập nhật sinh viên
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") UUID id, @RequestBody Student studentDetails) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student existingStudent = optionalStudent.get();
            existingStudent.setStudentCode(studentDetails.getStudentCode());
            existingStudent.setFullName(studentDetails.getFullName());
            existingStudent.setEmail(studentDetails.getEmail());
            existingStudent.setPhone(studentDetails.getPhone());
            existingStudent.setClassName(studentDetails.getClassName());
            
            Student updatedStudent = studentRepository.save(existingStudent);
            return ResponseEntity.ok(updatedStudent);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 7. DELETE /api/students/{id} - Xóa 1 sinh viên theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") UUID id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 8. DELETE /api/students - Xóa tất cả sinh viên
    @DeleteMapping
    public ResponseEntity<Void> deleteAllStudents() {
        studentRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}