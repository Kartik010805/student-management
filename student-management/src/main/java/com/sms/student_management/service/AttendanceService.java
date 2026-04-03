package com.sms.student_management.service;

import com.sms.student_management.model.Attendance;
import com.sms.student_management.repository.AttendanceRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository repository;

    public AttendanceService(AttendanceRepository repository) {
        this.repository = repository;
    }

    public Attendance markAttendance(Attendance attendance) {
        return repository.save(attendance);
    }

    public List<Attendance> getStudentAttendance(Long studentId) {
        return repository.findByStudentId(studentId);
    }
}