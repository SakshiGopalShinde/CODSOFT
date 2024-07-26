package com.sakshi13.getitdone;

import java.io.Serializable;

public class Task implements Serializable {
    private String title;
    private String description;
    private String deadline;
    private boolean isCompleted;

    public Task(String title, String description, String deadline, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.isCompleted = isCompleted;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
