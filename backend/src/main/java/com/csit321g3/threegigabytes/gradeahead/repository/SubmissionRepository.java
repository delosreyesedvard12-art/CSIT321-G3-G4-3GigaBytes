    package com.csit321g3.threegigabytes.gradeahead.repository;

    import com.csit321g3.threegigabytes.gradeahead.model.Submission;
    import java.util.List;
    import java.util.Optional;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface SubmissionRepository extends JpaRepository<Submission, Long> {
        List<Submission> findByTaskId(Long taskId);
List<Submission> findByStudentId(Long studentId);
    }
