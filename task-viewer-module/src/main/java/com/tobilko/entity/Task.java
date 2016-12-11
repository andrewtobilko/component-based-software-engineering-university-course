package com.tobilko.entity;

import com.tobilko.Viewable;

import javax.persistence.*;

/**
 * Created by Andrew Tobilko on 11/16/2016.
 */
@Entity
public class Task implements Viewable {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    public Task() {
        type = TaskType.BUG;
    }

    public Task(String title, String description, TaskType type) {
        this.title = title;
        this.description = description;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

}