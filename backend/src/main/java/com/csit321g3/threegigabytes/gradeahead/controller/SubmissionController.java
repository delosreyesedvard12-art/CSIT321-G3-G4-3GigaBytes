package com.csit321g3.threegigabytes.gradeahead.controller;

import com.csit321g3.threegigabytes.gradeahead.model.Submission;
import com.csit321g3.threegigabytes.gradeahead.repository.SubmissionRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController extends CrudController<Submission> {

    private final SubmissionRepository submissions;

    public SubmissionController(SubmissionRepository repo) {
        super(repo);
        this.submissions = repo;
    }

    @GetMapping("/by-task/{taskId}")
    public List<Submission> byTask(@PathVariable Long taskId) {
        return submissions.findByTaskId(taskId);
    }

    @GetMapping("/by-student/{studentId}")
    public List<Submission> byStudent(@PathVariable Long studentId) {
        return submissions.findByStudentId(studentId);
    }

    @Override
    protected void beforeUpdate(Submission existing, Submission body) {
        body.setSubmittedAt(existing.getSubmittedAt());
    }
}
