package com.beraaksoy.todofu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    EditText mTodoTitle;
    EditText mTodoNote;
    EditText mTodoDate;
    RadioButton mPriorityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Adding Toolbar to Main screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowHomeEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(true);
        }

        mTodoTitle = (EditText) findViewById(R.id.todoItemTitle);
        mTodoNote = (EditText) findViewById(R.id.todoItemNote);
        mTodoDate = (EditText) findViewById(R.id.todoItemDate);

        // SAVE OUR TODOOO ITEM
        FloatingActionButton save_todo_fab = (FloatingActionButton) findViewById(R.id.fab_save_todo);
        assert save_todo_fab != null;
        save_todo_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                String title = mTodoTitle.getText().toString();
                String priority = mPriorityButton.getText().toString();

                ToDo todo = new ToDo(title, priority);
                TodoDbHelper dbhelper = TodoDbHelper.getsInstance(v.getContext());
                dbhelper.insertTodo(todo);
                startActivity(intent);
            }
        });

    }

    // Set TodoTask Priority
    public void onTodoPriorityButtonClicked(View view) {
        // Check if a radio button is clicked to set todotask priority
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioToday:
                if (checked)
                    mPriorityButton = (RadioButton) findViewById(R.id.radioToday);
                break;
            case R.id.radioTomorrow:
                if (checked)
                    Toast.makeText(DetailActivity.this, "TOMORROW", Toast.LENGTH_SHORT).show();
                mPriorityButton = (RadioButton) findViewById(R.id.radioTomorrow);
                break;
            case R.id.radioLater:
                if (checked) {
                    Toast.makeText(DetailActivity.this, "LATER", Toast.LENGTH_SHORT).show();
                    mPriorityButton = (RadioButton) findViewById(R.id.radioLater);
                    break;
                }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_todo:
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Confirm Delete");
                alertDialog.setMessage("Are you sure you want to delete this TodoFu?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                );
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Todofu Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
