package com.tobilko.entity;

/**
 *
 * Created by Andrew Tobilko on 11/16/2016.
 *
 */
public enum TaskType {

    BUG("BUG"), FEATURE("FEATURE"), IMPROVEMENT("IMPROVEMENT");

    private String type;

    TaskType(String type) {
        this.type = type;
    }
    public static TaskType getByTitle(String title) {
        TaskType[] values = TaskType.values();
        for (TaskType task : values) {
            if (task.type.equals(title)) return task;
        }
        return BUG;
    }

    public String getType() { return type; }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() { return type; }

}