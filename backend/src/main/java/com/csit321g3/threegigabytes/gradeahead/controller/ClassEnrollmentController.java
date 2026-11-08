package com.csit321g3.threegigabytes.gradeahead.controller;

import com.csit321g3.threegigabytes.gradeahead.model.ClassEnrollment;
import com.csit321g3.threegigabytes.gradeahead.model.Classroom;
import com.csit321g3.threegigabytes.gradeahead.repository.ClassEnrollmentRepository;
import com.csit321g3.threegigabytes.gradeahead.repository.ClassroomRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/enrollments")
public class ClassEnrollmentController extends CrudController<ClassEnrollment> {

    public record JoinRequest(String joinCode, Long studentId) { }

    private final ClassEnrollmentRepository enrollments;
    private final ClassroomRepository classrooms;

    public ClassEnrollmentController(ClassEnrollmentRepository repo, ClassroomRepository classrooms) {
        super(repo);
        this.enrollments = repo;
        this.classrooms = classrooms;
    }

    @GetMapping("/by-classroom/{classroomId}")
    public List<ClassEnrollment> byClassroom(@PathVariable Long classroomId) {
        return enrollments.findByClassroomId(classroomId);
    }

    @GetMapping("/by-student/{studentId}")
    public List<ClassEnrollment> byStudent(@PathVariable Long studentId) {
        return enrollments.findByStudentId(studentId);
    }

    /** Must-have 5: a student joins a classroom with the invitation code. */
    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public ClassEnrollment join(@RequestBody JoinRequest req) {
        if (req.joinCode() == null || req.studentId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "joinCode and studentId are required");
        }
        Classroom classroom = classrooms.findByJoinCode(req.joinCode().trim().toUpperCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid invitation code"));
        if (enrollments.existsByClassroomIdAndStudentId(classroom.getId(), req.studentId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Student is already enrolled in this class");
        }
        ClassEnrollment enrollment = new ClassEnrollment();
        enrollment.setClassroomId(classroom.getId());
        enrollment.setStudentId(req.studentId());
        enrollment.setStatus("ACTIVE");
        return enrollments.save(enrollment);
    }
}
