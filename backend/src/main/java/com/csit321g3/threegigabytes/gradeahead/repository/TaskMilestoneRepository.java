package com.csit321g3.threegigabytes.gradeahead.repository;

import com.csit321g3.threegigabytes.gradeahead.model.TaskMilestone;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskMilestoneRepository extends JpaRepository<TaskMilestone, Long> {
    List<TaskMilestone> findByClassroomId(Long classroomId);
}
