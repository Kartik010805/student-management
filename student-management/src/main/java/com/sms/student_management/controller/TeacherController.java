package com.sms.student_management.controller;

import com.sms.student_management.model.Teacher;
import com.sms.student_management.service.TeacherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
@CrossOrigin(origins = "*")
public class TeacherController {

    private final TeacherService service;

    public TeacherController(TeacherService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public Teacher register(@RequestBody Teacher teacher) {
        return service.registerTeacher(teacher);
    }

    @PostMapping("/login")
    public Teacher login(@RequestBody Teacher teacher) {
        return service.login(teacher.getEmail(), teacher.getPassword());
    }
}