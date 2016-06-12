package com.beraaksoy.todofu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beraaksoy on 6/9/16.
 */
class TodoDAO {
    private static final String TAG = TodoDAO.class.getSimpleName();
    private final Context context;

    public TodoDAO(Context context) {
        this.context = context;
    }

    // Insert
    public void insert(Todo todoItem) {
        TodoDbHelper helper = TodoDbHelper.getsInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TodoDbHelper.TodoTableColumns.TODO_TITLE, todoItem.getTitle());
        values.put(TodoDbHelper.TodoTableColumns.TODO_NOTE, todoItem.getNote());
        values.put(TodoDbHelper.TodoTableColumns.TODO_DATE, (todoItem.getDate()));
        values.put(TodoDbHelper.TodoTableColumns.TODO_PRIORITY, todoItem.getPriority());
        long recordId = db.insert(TodoDbHelper.Tables.TODO_TABLE, null, values);
        Log.d(TAG, "************** RECORD INSERTED ****************");
        Log.d(TAG, "ID: " + recordId);
        Log.d(TAG, "Title: " + values.get(TodoDbHelper.TodoTableColumns.TODO_TITLE));
        Log.d(TAG, "Note: " + values.get(TodoDbHelper.TodoTableColumns.TODO_NOTE));
        Log.d(TAG, "Date: " + values.get(TodoDbHelper.TodoTableColumns.TODO_DATE));
        Log.d(TAG, "Priority: " + values.get(TodoDbHelper.TodoTableColumns.TODO_PRIORITY));
    }

    // Update
    public void update(Todo todoItem) {
        TodoDbHelper helper = TodoDbHelper.getsInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoDbHelper.TodoTableColumns.TODO_TITLE, todoItem.getTitle());
        values.put(TodoDbHelper.TodoTableColumns.TODO_NOTE, todoItem.getNote());
        values.put(TodoDbHelper.TodoTableColumns.TODO_DATE, todoItem.getDate());
        values.put(TodoDbHelper.TodoTableColumns.TODO_PRIORITY, todoItem.getPriority());

        String selection = TodoDbHelper.TodoTableColumns.TODO_ID + " = ?";
        String[] selectionArgs = {String.valueOf(todoItem.getId())};

        int count = db.update(
                TodoDbHelper.Tables.TODO_TABLE,
                values,
                selection,
                selectionArgs);

        Log.d(TAG, "Updated Rowcount: " + TodoDbHelper.TodoTableColumns.TODO_ID);
        Log.d(TAG, "Updated RowId: " + count);

    }


    // Delete
    public void delete(Todo todoItem) {
        String selection = TodoDbHelper.TodoTableColumns.TODO_ID + " =?";
        String[] selectionArgs = {String.valueOf(todoItem.getId())};
        TodoDbHelper helper = TodoDbHelper.getsInstance(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowsAffected = db.delete(TodoDbHelper.Tables.TODO_TABLE, selection, selectionArgs);
        Log.d(TAG, "Deleted Rowcount: " + rowsAffected);
    }

    // Read All
    public List<Todo> list() {
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

        List<Todo> todoList = new ArrayList<>();

        while (c.moveToNext()) {
            String title = c.getString(c.getColumnIndex(TodoDbHelper.TodoTableColumns.TODO_TITLE));
            String note = c.getString(c.getColumnIndex(TodoDbHelper.TodoTableColumns.TODO_NOTE));
            String date = c.getString(c.getColumnIndex(TodoDbHelper.TodoTableColumns.TODO_DATE));
            //Calendar date = new GregorianCalendar();
            //date.setTimeInMillis(c.getLong(c.getColumnIndex(TodoDbHelper.TodoTableColumns.TODO_DATE)) * 1000);
            String priority = c.getString(c.getColumnIndex(TodoDbHelper.TodoTableColumns.TODO_PRIORITY));
            long id = c.getLong(c.getColumnIndex(TodoDbHelper.TodoTableColumns.TODO_ID));
            todoList.add(new Todo(id, title, note, date, priority));
        }
        Log.d(TAG, "TodoItems Listed: " + todoList.size());
        c.close();
        return todoList;
    }

}
