package com.beraaksoy.todofu;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by beraaksoy on 6/6/16.
 */
public class ToDo implements Serializable {
    int _id;
    String title;
    String note;
    Calendar date;
    String priority; // today, soon, later
    //String status; // done, pending

    public ToDo(String title, String priority) {
        this.title = title;
        this.priority = priority;
        this.date = Calendar.getInstance();
    }

    public ToDo(String title, String note, String priority) {
        this.title = title;
        this.note = note;
        this.date = Calendar.getInstance();
        this.priority = priority;
    }

    public ToDo(int _id, String title, String note, Calendar date, String priority) {
        this._id = _id;
        this.title = title;
        this.note = note;
        this.date = date;
        this.priority = priority;
    }


    public int get_id() {
        return _id;
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

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
