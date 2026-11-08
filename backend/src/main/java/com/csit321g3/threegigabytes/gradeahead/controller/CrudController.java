package com.csit321g3.threegigabytes.gradeahead.controller;

import com.csit321g3.threegigabytes.gradeahead.model.BaseEntity;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/** Shared Create / Read / Update / Delete endpoints for every core entity. */
public abstract class CrudController<T extends BaseEntity> {

    protected final JpaRepository<T, Long> repo;

    protected CrudController(JpaRepository<T, Long> repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<T> findAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public T findOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Record " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public T create(@Valid @RequestBody T body) {
        body.setId(null);
        beforeCreate(body);
        return repo.save(body);
    }

    @PutMapping("/{id}")
    public T update(@PathVariable Long id, @Valid @RequestBody T body) {
        T existing = findOne(id);
        body.setId(id);
        beforeUpdate(existing, body);
        return repo.save(body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record " + id + " not found");
        }
        repo.deleteById(id);
    }

    /** Hook: runs before a new record is saved. */
    protected void beforeCreate(T body) { }

    /** Hook: runs before an existing record is replaced (copy fields the client may not send). */
    protected void beforeUpdate(T existing, T body) { }
}
