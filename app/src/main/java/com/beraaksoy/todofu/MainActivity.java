package com.beraaksoy.todofu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Adding Menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setLogo(R.drawable.app_logo);
        }

        RecyclerView toDoItems = (RecyclerView) findViewById(R.id.todoList);

        // Get a handle on our database
        TodoDbHelper dbHelper = TodoDbHelper.getsInstance(this);

        List<ToDo> toDoList = new ArrayList<>();

        // Grab all our Todoo items and add append it to our list
        List<ToDo> result = dbHelper.getAllPosts();
        for (ToDo toDo : result) {
            toDoList.add(toDo);
        }

        // Set our adapter to display to list of Todos
        ToDoAdapter adapter = new ToDoAdapter(toDoList);
        if (toDoItems != null) {
            toDoItems.setAdapter(adapter);
        }
        if (toDoItems != null) {
            toDoItems.setLayoutManager(new LinearLayoutManager(this));
        }


        // ADD A TODOO ITEM
        FloatingActionButton add_place_fab = (FloatingActionButton) findViewById(R.id.fab_save_todo);
        assert add_place_fab != null;
        add_place_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }

}
