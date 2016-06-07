package com.beraaksoy.todo_fu;

import java.util.Date;

/**
 * Created by beraaksoy on 6/6/16.
 */
public class ToDo {
    String task;
    String priority;
    String status;
    String note;
    Date date;

    public ToDo(String task) {
        this.task = task;
        this.date = new Date();
    }

    public ToDo(String task, String priority) {
        this.task = task;
        this.priority = priority;
        this.date = new Date();
    }

    public ToDo(String task, String priority, String status, String note) {
        this.task = task;
        this.priority = priority;
        this.status = status;
        this.note = note;
        this.date = new Date();
    }

    public String getPriority() {
        return priority;
    }

    public String getTask() {
        return task;
    }
}
