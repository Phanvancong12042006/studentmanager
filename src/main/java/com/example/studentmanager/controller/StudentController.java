package com.example.studentmanager.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.studentmanager.entity.Student;
import com.example.studentmanager.repository.StudentRepository;
import com.example.studentmanager.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    // Xem danh sách sinh viên
    @GetMapping
    public String listStudents(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("students", studentService.searchByName(keyword));
        model.addAttribute("keyword", keyword);
        model.addAttribute("newStudent", new Student()); // Dùng cho Modal Thêm mới
        return "students";
    }

    // Xem chi tiết sinh viên
    @GetMapping("/{id}")
    public String studentDetail(@PathVariable("id") UUID id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "student-detail";
    }

    // Thêm mới sinh viên
    @PostMapping("/add")
    public String addStudent(@ModelAttribute("newStudent") Student student) {
        studentRepository.save(student);
        return "redirect:/students";
    }

    // Trang hiển thị Form Cập nhật sinh viên
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") UUID id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "student-edit";
    }

    // Xử lý Cập nhật sinh viên
    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id") UUID id, @ModelAttribute("student") Student updatedStudent) {
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent != null) {
            existingStudent.setStudentCode(updatedStudent.getStudentCode());
            existingStudent.setFullName(updatedStudent.getFullName());
            existingStudent.setEmail(updatedStudent.getEmail());
            existingStudent.setPhone(updatedStudent.getPhone());
            existingStudent.setClassName(updatedStudent.getClassName());
            studentRepository.save(existingStudent);
        }
        return "redirect:/students";
    }

    // Xóa sinh viên
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") UUID id) {
        studentRepository.deleteById(id);
        return "redirect:/students";
    }
}