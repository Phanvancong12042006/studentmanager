package com.example.studentmanager.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studentmanager.entity.Student;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    
    // Tìm sinh viên theo mã sinh viên
    Optional<Student> findByStudentCode(String studentCode);

    // Tìm kiếm sinh viên theo tên hoặc mã sinh viên (chứa từ khóa, không phân biệt hoa thường)
    List<Student> findByFullNameContainingIgnoreCaseOrStudentCodeContainingIgnoreCase(String fullName, String studentCode);
}