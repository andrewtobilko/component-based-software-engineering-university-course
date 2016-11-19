package com.tobilko.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 *
 * Created by Andrew Tobilko on 11/16/2016.
 *
 */
public class Project {

    private String title;
    private List<Task> tasks;

    public Project(String title, List<Task> tasks) {
        this.title = title;
        this.tasks = tasks;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }

}