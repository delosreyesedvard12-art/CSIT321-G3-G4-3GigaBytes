package com.csit321g3.threegigabytes.gradeahead.repository;

import com.csit321g3.threegigabytes.gradeahead.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
