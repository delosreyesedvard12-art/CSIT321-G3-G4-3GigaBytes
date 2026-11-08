package com.csit321g3.threegigabytes.gradeahead.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "submissions")
public class Submission extends BaseEntity {

    @NotNull
    @Column(nullable = false)
    private Long taskId;

    @NotNull
    @Column(nullable = false)
    private Long studentId;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Double score;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer prepMinutes;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer confidenceLevel;

    @Column(updatable = false)
    private LocalDateTime submittedAt;

    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        submittedAt = LocalDateTime.now();
        updatedAt = submittedAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    public Integer getPrepMinutes() { return prepMinutes; }
    public void setPrepMinutes(Integer prepMinutes) { this.prepMinutes = prepMinutes; }
    public Integer getConfidenceLevel() { return confidenceLevel; }
    public void setConfidenceLevel(Integer confidenceLevel) { this.confidenceLevel = confidenceLevel; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
