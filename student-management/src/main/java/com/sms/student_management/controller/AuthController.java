package com.sms.student_management.controller;

import com.sms.student_management.model.Teacher;
import com.sms.student_management.security.JwtUtil;
import com.sms.student_management.service.TeacherService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final TeacherService service;
    private final JwtUtil jwtUtil;

    public AuthController(TeacherService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody Teacher teacher) {

        Teacher logged = service.login(teacher.getEmail(),
                teacher.getPassword());

        return jwtUtil.generateToken(logged.getEmail());
    }
}