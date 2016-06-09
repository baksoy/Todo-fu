package com.beraaksoy.todofu;

import android.content.Context;
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

    private static final String TAG = "DetailActivity";
    public static final String ACTION_EDIT = "update_todoitem";
    private EditText mTodoTitle;
    private EditText mTodoNote;
    private RadioButton mPriorityToday;
    private RadioButton mPrioritySoon;
    private RadioButton mPriorityLater;
    private ToDo mToDo; // Our Serializable ToDoItem

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
        mPriorityToday = (RadioButton) findViewById(R.id.radioToday);
        mPrioritySoon = (RadioButton) findViewById(R.id.radioSoon);
        mPriorityLater = (RadioButton) findViewById(R.id.radioLater);

        // EDIT - If we are Editing, get todoitem fields from Main and set the Detail views with it
        Intent intent = getIntent();
        //mToDo = (ToDo) intent.getSerializableExtra(MainActivity.TODOITEM);
        String title = intent.getStringExtra("title");
        String note = intent.getStringExtra("note");
        mTodoTitle.setText(title);
        mTodoNote.setText(note);

        // if (mToDo != null) {
        //     mTodoTitle.setText(mToDo.getTitle()); //Set title in detail view
        //     mTodoNote.setText(mToDo.getNote());   //Set note in detail view
        //     switch (mToDo.getPriority()) {        //Set priority in detail view
        //         case MainActivity.TODAY:
        //             if (mPriorityToday != null) {
        //                 mPriorityToday.setChecked(true);
        //             }
        //             break;
        //         case MainActivity.SOON:
        //             if (mPrioritySoon != null) {
        //                 mPrioritySoon.setChecked(true);
        //             }
        //             break;
        //         case MainActivity.LATER:
        //             if (mPriorityLater != null) {
        //                 mPriorityLater.setChecked(true);
        //             }
        //             break;
        //     }
        // }

        // SAVE our todoitem
        FloatingActionButton save_todo_fab = (FloatingActionButton) findViewById(R.id.fab_save_todo);
        assert save_todo_fab != null;
        save_todo_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                String title = mTodoTitle.getText().toString();
                String note = mTodoNote.getText().toString();
                String priority = null;
                if (mPriorityToday.isChecked()) {
                    priority = mPriorityToday.getText().toString();
                } else if (mPrioritySoon.isChecked()) {
                    priority = mPrioritySoon.getText().toString();
                } else {
                    priority = mPriorityLater.getText().toString();
                }

                ToDoDAO dao = new ToDoDAO(v.getContext());
                ToDo toDo = new ToDo(title, note, priority);
                dao.insert(toDo);
                setResult(RESULT_OK, intent);
                startActivity(intent);
                finish();

                //if (intent.getAction() == ACTION_EDIT) {
                //mToDo.setTitle(title);
                //mToDo.setNote(note);
                //mToDo.setPriority(priority);
                //dao.update(mToDo);
                //intent.putExtra("Todo", mToDo);
                //setResult(RESULT_OK, intent);
                //finish();
                //} else {
                //    ToDo toDo = new ToDo(title, note, priority);
                //    dao.insert(toDo);
                //    finish();
                //}
            }
        });
    }

    // Set todoitem Priority
    public void onTodoPriorityButtonClicked(View view) {
        // Check if a radio button is clicked to set todotask priority
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioToday:
                if (checked)
                    mPriorityToday.setChecked(true);
                break;
            case R.id.radioSoon:
                if (checked)
                    mPrioritySoon.setChecked(true);
                break;
            case R.id.radioLater:
                if (checked) {
                    mPriorityLater.setChecked(true);
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

    public static Intent getActionIntent(Context context, ToDo toDo, String action) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.setAction(action);
        intent.putExtra(MainActivity.TODOITEM, toDo);
        return intent;
    }

}
