package com.sms.student_management.service;

import com.sms.student_management.model.Teacher;
import com.sms.student_management.repository.TeacherRepository;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    private final TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public Teacher registerTeacher(Teacher teacher) {
        return repository.save(teacher);
    }

    public Teacher login(String email, String password) {

        Teacher teacher = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if(!teacher.getPassword().equals(password)){
            throw new RuntimeException("Invalid password");
        }

        return teacher;
    }
}