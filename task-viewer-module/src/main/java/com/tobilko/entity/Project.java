package com.tobilko.entity;

import com.tobilko.Viewable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project implements Viewable {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private @OneToMany List<Task> tasks;

    public Project() {
        tasks = new ArrayList<>();
    }

    public Project(String title, List<Task> tasks) {
        this.title = title;
        this.tasks = tasks;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}