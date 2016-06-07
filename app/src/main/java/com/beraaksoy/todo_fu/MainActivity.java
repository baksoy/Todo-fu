package com.beraaksoy.todo_fu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

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

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setLogo(R.drawable.app_logo);
        }

        RecyclerView toDoItems = (RecyclerView) findViewById(R.id.todoList);

        List<ToDo> toDoList = new ArrayList<>();
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));
        toDoList.add(new ToDo("Buy Milk", "Today"));
        toDoList.add(new ToDo("Buy Chocolate", "Tomorrow"));
        toDoList.add(new ToDo("Buy Bread", "Soon"));

        ToDoAdapter adapter = new ToDoAdapter(toDoList);
        if (toDoItems != null) {
            toDoItems.setAdapter(adapter);
        }
        if (toDoItems != null) {
            toDoItems.setLayoutManager(new LinearLayoutManager(this));
        }


        // Fab to search a nearby Place and add one
        FloatingActionButton add_place_fab = (FloatingActionButton) findViewById(R.id.fab_add_todo);
        assert add_place_fab != null;
        add_place_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetchPlace();
                Toast.makeText(MainActivity.this, "Fetching list of nearby places", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
