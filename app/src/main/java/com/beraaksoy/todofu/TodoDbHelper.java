package com.beraaksoy.todofu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by beraaksoy on 6/7/16.
 */
public class TodoDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todofu.db";
    private static final int DATABASE_VERSION = 1;
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
        String TODO_ID = "_id";
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
                + TodoTableColumns.TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
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
}
