package com.sms.student_management.controller;

import com.sms.student_management.model.Attendance;
import com.sms.student_management.service.AttendanceService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceService service;

    public AttendanceController(AttendanceService service) {
        this.service = service;
    }

    @PostMapping
    public Attendance markAttendance(@RequestBody Attendance attendance) {
        return service.markAttendance(attendance);
    }

    @GetMapping("/student/{id}")
    public List<Attendance> getStudentAttendance(@PathVariable Long id) {
        return service.getStudentAttendance(id);
    }
}