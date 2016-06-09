package com.beraaksoy.todofu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

    public static final String TODOITEM = "todoitem";
    public static final String TODAY = "Today";
    public static final String SOON = "Soon";
    public static final String LATER = "Later";

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

        // Our Todoo list to be displayed on the Main Screen
        final List<ToDo> toDoList = new ArrayList<>();

        // Grab all our Todoo items from the db and append it to our display list
        List<ToDo> result = dbHelper.getAllPosts();
        for (ToDo toDo : result) {
            toDoList.add(toDo);
        }

        // Set our adapter to display our Todoo list
        ToDoAdapter adapter = new ToDoAdapter(toDoList, this);

        if (toDoItems != null) {
            toDoItems.setLayoutManager(new LinearLayoutManager(this));
            Drawable divider = getResources().getDrawable(R.drawable.item_divider);
            toDoItems.addItemDecoration(new HorizontalDividerItemDecoration(divider));
        }

        if (toDoItems != null) {
            toDoItems.setAdapter(adapter);
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
