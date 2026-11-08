package com.csit321g3.threegigabytes.gradeahead.controller;

import com.csit321g3.threegigabytes.gradeahead.model.User;
import com.csit321g3.threegigabytes.gradeahead.repository.UserRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/users")
public class UserController extends CrudController<User> {

    public UserController(UserRepository repo) {
        super(repo);
    }

    @Override
    protected void beforeCreate(User body) {
        if (body.getPasswordHash() == null || body.getPasswordHash().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password is required");
        }
        body.setPasswordHash(hash(body.getPasswordHash()));
    }

    @Override
    protected void beforeUpdate(User existing, User body) {
        body.setCreatedAt(existing.getCreatedAt());
        if (body.getPasswordHash() == null || body.getPasswordHash().isBlank()) {
            body.setPasswordHash(existing.getPasswordHash());   // keep the old password
        } else {
            body.setPasswordHash(hash(body.getPasswordHash()));
        }
    }

    // TODO (Increment 2): replace SHA-256 with BCrypt from Spring Security.
    private static String hash(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(raw.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
