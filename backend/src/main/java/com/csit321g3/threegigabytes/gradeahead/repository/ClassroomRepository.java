    package com.csit321g3.threegigabytes.gradeahead.repository;

    import com.csit321g3.threegigabytes.gradeahead.model.Classroom;
    import java.util.List;
    import java.util.Optional;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
        Optional<Classroom> findByJoinCode(String joinCode);
boolean existsByJoinCode(String joinCode);
List<Classroom> findByTeacherId(Long teacherId);
    }
