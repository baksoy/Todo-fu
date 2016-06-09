package com.beraaksoy.todofu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by beraaksoy on 6/9/16.
 */
public class ToDoDAO {
    private static final String TAG = "ToDoDAO";
    private Context context;

    public ToDoDAO(Context context) {
        this.context = context;
    }

    // Insert
    public void insert(ToDo toDo) {
        TodoDbHelper helper = TodoDbHelper.getsInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TodoDbHelper.TodoTableColumns.TODO_TITLE, toDo.getTitle());
        values.put(TodoDbHelper.TodoTableColumns.TODO_NOTE, toDo.getNote());
        values.put(TodoDbHelper.TodoTableColumns.TODO_DATE, (toDo.getDate().getTimeInMillis() / 1000));
        values.put(TodoDbHelper.TodoTableColumns.TODO_PRIORITY, toDo.getPriority());
        long recordId = db.insert(TodoDbHelper.Tables.TODO_TABLE, null, values);
        Log.d(TAG, "************** RECORD INSERTED ****************");
        Log.d(TAG, "ID: " + recordId);
        Log.d(TAG, "Title: " + values.get(TodoDbHelper.TodoTableColumns.TODO_TITLE));
        Log.d(TAG, "Note: " + values.get(TodoDbHelper.TodoTableColumns.TODO_NOTE));
        Log.d(TAG, "Priority: " + values.get(TodoDbHelper.TodoTableColumns.TODO_PRIORITY));
        Log.d(TAG, "Date: "
                + toDo.getDate().get(Calendar.DAY_OF_MONTH) + "/"
                + toDo.getDate().get(Calendar.MONTH) + "/"
                + toDo.getDate().get(Calendar.YEAR));
    }

    // Update
    public void update(ToDo toDo) {
        TodoDbHelper helper = TodoDbHelper.getsInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TodoDbHelper.TodoTableColumns.TODO_TITLE, toDo.getTitle());
        values.put(TodoDbHelper.TodoTableColumns.TODO_NOTE, toDo.getNote());
        values.put(TodoDbHelper.TodoTableColumns.TODO_PRIORITY, toDo.getPriority());

        String selection = TodoDbHelper.TodoTableColumns.TODO_ID + " =?";
        String[] selectionArgs = {String.valueOf(toDo.get_id())};
        int rowsAffected = db.update(TodoDbHelper.Tables.TODO_TABLE, values, selection, selectionArgs);
        Log.d(TAG, "Updated Rowcount: " + rowsAffected);
    }

    // Delete
    public void delete(ToDo toDo) {
        String selection = TodoDbHelper.TodoTableColumns.TODO_ID + " =?";
        String[] selectionArgs = {String.valueOf(toDo.get_id())};
        TodoDbHelper helper = TodoDbHelper.getsInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowsAffected = db.delete(TodoDbHelper.Tables.TODO_TABLE, selection, selectionArgs);
        Log.d(TAG, "Deleted Rowcount: " + rowsAffected);
    }

    // Read All
    public List<ToDo> list() {
        TodoDbHelper helper = TodoDbHelper.getsInstance(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Build an array to specify the columns of the table you want your query to return
        String[] projection = {
                TodoDbHelper.TodoTableColumns.TODO_ID,
                TodoDbHelper.TodoTableColumns.TODO_TITLE,
                TodoDbHelper.TodoTableColumns.TODO_NOTE,
                TodoDbHelper.TodoTableColumns.TODO_DATE,
                TodoDbHelper.TodoTableColumns.TODO_PRIORITY
        };

        String sortOrder = TodoDbHelper.TodoTableColumns.TODO_DATE + " DESC";

        // Query the table and return a cursor
        Cursor c = db.query(
                TodoDbHelper.Tables.TODO_TABLE,        // The table to query
                projection,                             // The columns to return
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                sortOrder                               // The sort order
        );

        List<ToDo> todoList = new ArrayList<>();

        while (c.moveToNext()) {
            String title = c.getString(c.getColumnIndex(TodoDbHelper.TodoTableColumns.TODO_TITLE));
            String note = c.getString(c.getColumnIndex(TodoDbHelper.TodoTableColumns.TODO_NOTE));
            Calendar date = new GregorianCalendar();
            date.setTimeInMillis(c.getLong(c.getColumnIndex(TodoDbHelper.TodoTableColumns.TODO_DATE)) * 1000);
            String priority = c.getString(c.getColumnIndex(TodoDbHelper.TodoTableColumns.TODO_PRIORITY));

            todoList.add(new ToDo(title, note, priority));
        }
        Log.d(TAG, "TodoItems Listed: " + todoList.size());
        c.close();
        return todoList;
    }

}
