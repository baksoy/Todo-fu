package com.beraaksoy.todofu;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by beraaksoy on 6/6/16.
 */
public class ToDo implements Serializable {
    Long id;
    String title;
    String note;
    Calendar date;
    String priority; // today, soon, later
    //String status; // done, pending

    public ToDo(String title, String note, String priority) {
        this(null, title, note, priority);
    }

    public ToDo(Long id, String title, String note, String priority) {
        setId(id);
        setTitle(title);
        setNote(note);
        setPriority(priority);
        setDate(Calendar.getInstance());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public Calendar getDate() {
        return date;
    }

    public String getPriority() {
        return priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
