package com.tobilko.entity;

/**
 *
 * Created by Andrew Tobilko on 11/16/2016.
 *
 */
public enum TaskType {

    BUG("BUG"), FEATURE("FEATURE"), IMPROVEMENT("IMPROVEMENT");

    private String title;

    TaskType(String title) {
        this.title = title;
    }

    public static TaskType getByTitle(String title) {
        TaskType[] values = TaskType.values();
        for (TaskType task : values) {
            if (task.title.equals(title)) return task;
        }
        return BUG;
    }

    @Override
    public String toString() {
        return title;
    }
}
