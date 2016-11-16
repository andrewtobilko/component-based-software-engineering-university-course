package com.tobilko.entity;

/**
 *
 * Created by Andrew Tobilko on 11/16/2016.
 *
 */
public class Task {

    private String title;
    private String description;
    private TaskType type;

    public Task(String title, String description, TaskType type) {
        this.title = title;
        this.description = description;
        this.type = type;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TaskType getType() { return type; }
    public void setType(TaskType type) { this.type = type; }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }
}