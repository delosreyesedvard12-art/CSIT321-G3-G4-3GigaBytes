package com.csit321g3.threegigabytes.gradeahead.controller;

import com.csit321g3.threegigabytes.gradeahead.model.Classroom;
import com.csit321g3.threegigabytes.gradeahead.repository.ClassroomRepository;
import java.security.SecureRandom;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController extends CrudController<Classroom> {

    private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final ClassroomRepository classrooms;

    public ClassroomController(ClassroomRepository repo) {
        super(repo);
        this.classrooms = repo;
    }

    @GetMapping("/by-teacher/{teacherId}")
    public List<Classroom> byTeacher(@PathVariable Long teacherId) {
        return classrooms.findByTeacherId(teacherId);
    }

    @Override
    protected void beforeCreate(Classroom body) {
        // Must-have 2: every classroom gets a unique 6-character invitation code.
        if (body.getJoinCode() == null || body.getJoinCode().isBlank()) {
            body.setJoinCode(newUniqueCode());
        } else {
            body.setJoinCode(body.getJoinCode().trim().toUpperCase());
        }
    }

    @Override
    protected void beforeUpdate(Classroom existing, Classroom body) {
        body.setCreatedAt(existing.getCreatedAt());
        if (body.getJoinCode() == null || body.getJoinCode().isBlank()) {
            body.setJoinCode(existing.getJoinCode());
        }
    }

    private String newUniqueCode() {
        String code;
        do {
            StringBuilder sb = new StringBuilder(6);
            for (int i = 0; i < 6; i++) {
                sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
            }
            code = sb.toString();
        } while (classrooms.existsByJoinCode(code));
        return code;
    }
}
