package com.example.mytodobackend.task;

import com.example.mytodobackend.user.User;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name= "tasks")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    // columnDefinition = "TEXT" - неограниченная длинна
    @Column(name = "description", length = 1000)
    private String description;

    private String status;
    private String priority;

    @Column(name = "dead_line")
    private LocalDate deadLine;

    @ElementCollection
    @CollectionTable(
            name = "task_tags",
            joinColumns = @JoinColumn(name = "task_id")
    )
    @Column(name = "tag")
    private List<String> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public LocalDate getDeadLine() { return deadLine; }
    public void setDeadLine(LocalDate deadLine) { this.deadLine = deadLine; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
