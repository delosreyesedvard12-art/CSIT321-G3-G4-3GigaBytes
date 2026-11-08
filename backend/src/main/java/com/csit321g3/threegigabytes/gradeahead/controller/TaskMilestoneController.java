package com.csit321g3.threegigabytes.gradeahead.controller;

import com.csit321g3.threegigabytes.gradeahead.model.TaskMilestone;
import com.csit321g3.threegigabytes.gradeahead.repository.TaskMilestoneRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskMilestoneController extends CrudController<TaskMilestone> {

    private final TaskMilestoneRepository tasks;

    public TaskMilestoneController(TaskMilestoneRepository repo) {
        super(repo);
        this.tasks = repo;
    }

    @GetMapping("/by-classroom/{classroomId}")
    public List<TaskMilestone> byClassroom(@PathVariable Long classroomId) {
        return tasks.findByClassroomId(classroomId);
    }

    @Override
    protected void beforeUpdate(TaskMilestone existing, TaskMilestone body) {
        body.setCreatedAt(existing.getCreatedAt());
    }
}
