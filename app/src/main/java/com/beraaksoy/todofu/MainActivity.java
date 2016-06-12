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
    public static final String ACTION_EDIT = "edit_todoitem";

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

        RecyclerView todoRecyclerView = (RecyclerView) findViewById(R.id.todoList);

        // Initialize our TodoList to be displayed on the Main Screen
        final List<Todo> todoList = new ArrayList<>();

        // Grab all our TodoItems from the db and append it to our display list
        TodoDAO dao = new TodoDAO(this);
        List<Todo> result = dao.list();
        for (Todo todoItem : result) {
            todoList.add(todoItem);
        }

        // Set our adapter to display our TodoList
        TodoAdapter adapter = new TodoAdapter(todoList, this);

        if (todoRecyclerView != null) {
            todoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            Drawable divider = getResources().getDrawable(R.drawable.item_divider);
            todoRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration(divider));
        }

        if (todoRecyclerView != null) {
            todoRecyclerView.setAdapter(adapter);
        }


        // ADD A TODOITEM
        FloatingActionButton add_place_fab = (FloatingActionButton) findViewById(R.id.fab_add_todo);
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
