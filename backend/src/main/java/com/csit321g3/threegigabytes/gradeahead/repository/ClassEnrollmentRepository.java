    package com.csit321g3.threegigabytes.gradeahead.repository;

    import com.csit321g3.threegigabytes.gradeahead.model.ClassEnrollment;
    import java.util.List;
    import java.util.Optional;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface ClassEnrollmentRepository extends JpaRepository<ClassEnrollment, Long> {
        List<ClassEnrollment> findByClassroomId(Long classroomId);
List<ClassEnrollment> findByStudentId(Long studentId);
boolean existsByClassroomIdAndStudentId(Long classroomId, Long studentId);
    }
