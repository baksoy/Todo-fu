package com.beraaksoy.todofu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beraaksoy on 6/7/16.
 */
public class TodoDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "TodoDbHelper";

    public static final String DATABASE_NAME = "todofu.db";
    public static final int DATABASE_VERSION = 1;
    private static TodoDbHelper sInstance;

    //Set Constructor to private and make calls via Singleton to avoid leaks.
    private TodoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Singleton
    public static synchronized TodoDbHelper getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TodoDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    // Tables
    interface Tables {
        String TODO_TABLE = "todo";
    }

    // Columns
    public interface TodoTableColumns {
        String TODO_TITLE = "todo_title";
        String TODO_NOTE = "todo_note";
        String TODO_DATE = "todo_date";
        String TODO_PRIORITY = "todo_priority";
        String TODO_STATUS = "todo_status";
    }

    // Create Database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.TODO_TABLE + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TodoTableColumns.TODO_TITLE + " TEXT,"
                + TodoTableColumns.TODO_NOTE + " TEXT,"
                + TodoTableColumns.TODO_DATE + " TEXT,"
                + TodoTableColumns.TODO_PRIORITY + " TEXT,"
                + TodoTableColumns.TODO_STATUS + " TEXT)");
    }

    // Upgrade Database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Version check. If user is on older version, add some fields to
        // their DB here (ALTER TABLE) without deleting their existing data
        // and set them to the latest database version.

        if (oldVersion != DATABASE_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS " + Tables.TODO_TABLE);
            onCreate(db);
        }
    }


    /**
     * CRUD METHODS ***********************
     **/

    // CREATE
    public void insertTodo(ToDo toDo) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(TodoTableColumns.TODO_TITLE, toDo.title);
            values.put(TodoTableColumns.TODO_PRIORITY, toDo.priority);
            db.insertOrThrow(Tables.TODO_TABLE, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add a todo item in the database.");
        } finally {
            db.endTransaction();
        }
    }

    // GET ALL
    public List<ToDo> getAllPosts() {
        List<ToDo> toDoList = new ArrayList<>();
        String TODOLIST_SELECT_QUERY = String.format("SELECT * FROM %s", Tables.TODO_TABLE);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TODOLIST_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex(TodoTableColumns.TODO_TITLE));
                    String priority = cursor.getString(cursor.getColumnIndex(TodoTableColumns.TODO_PRIORITY));
                    ToDo toDo = new ToDo(title, priority);
                    toDoList.add(toDo);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get the todo list from the database.");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return toDoList;
    }
}
