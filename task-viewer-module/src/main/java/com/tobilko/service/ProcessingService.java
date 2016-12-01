package com.tobilko.service;

import com.tobilko.entity.Project;
import com.tobilko.entity.Task;
import com.tobilko.processor.Processor;

public class ProcessingService {

    private Processor<Project> projectProcessor = new Processor<>();
    private Processor<Task> taskProcessor = new Processor<>();

    public Processor<Project> getProjectProcessor() {
        return projectProcessor;
    }

    public Processor<Task> getTaskProcessor() {
        return taskProcessor;
    }

}
