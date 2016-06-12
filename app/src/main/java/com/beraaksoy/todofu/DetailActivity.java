package com.beraaksoy.todofu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

public class DetailActivity extends AppCompatActivity
        implements CalendarDatePickerDialogFragment.OnDateSetListener {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private EditText mTodoTitle;
    private EditText mTodoNote;
    private RadioButton mPriorityToday;
    private RadioButton mPrioritySoon;
    private RadioButton mPriorityLater;
    private TextView mTodoDate;
    private Todo mTodo; // Our Serializable TodoItem
    private Intent intent;

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
        mTodoDate = (TextView) findViewById(R.id.todoItemDate);
        Button button = (Button) findViewById(R.id.calendarButton);

        assert button != null;
        button.setText(R.string.calendar_date_picker_set);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(DetailActivity.this);
                cdp.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        // Receive our Serializable TodoItem Object
        intent = getIntent();
        mTodo = (Todo) intent.getSerializableExtra(MainActivity.TODOITEM);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(MainActivity.ACTION_EDIT)) {
                setEditMode();
            }
        }

        // SAVE our todoItem
        FloatingActionButton save_todo_fab = (FloatingActionButton) findViewById(R.id.fab_save_todo);
        assert save_todo_fab != null;
        save_todo_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivityIntent = new Intent(v.getContext(), MainActivity.class);
                TodoDAO dao = new TodoDAO(v.getContext());
                String title = getTitleString();
                String note = getNoteString();
                String date = getDateString();
                String priority = getPriorityString();

                // UPDATE if we get an intent to Update through intent.setAction
                if (intent.getAction() != null) {
                    if (intent.getAction().equals(MainActivity.ACTION_EDIT)) {
                        Long id = mTodo.getId();
                        mTodo.setId(id);
                        mTodo.setTitle(title);
                        mTodo.setNote(note);
                        mTodo.setDate(date);
                        mTodo.setPriority(priority);
                        if (isValid()) {
                            dao.update(mTodo);
                            startActivity(mainActivityIntent);
                            finish();
                        } else {
                            Toast.makeText(DetailActivity.this, "Title is required. Please enter valid data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else { // Otherwise, it's a NEW todoitem
                    if (isValid()) {
                        Todo todoItem = new Todo(title, note, date, priority);
                        dao.insert(todoItem);
                        startActivity(mainActivityIntent);
                        finish();
                    } else {
                        Toast.makeText(DetailActivity.this, "Title is required. Please enter valid data.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @NonNull
    private String getPriorityString() {
        String priority;
        if (mPriorityToday.isChecked()) {
            priority = mPriorityToday.getText().toString();
        } else if (mPrioritySoon.isChecked()) {
            priority = mPrioritySoon.getText().toString();
        } else {
            priority = mPriorityLater.getText().toString();
        }
        return priority;
    }

    @NonNull
    private String getTitleString() {
        return mTodoTitle.getText().toString();
    }

    @NonNull
    private String getNoteString() {
        return mTodoNote.getText().toString();
    }

    @NonNull
    private String getDateString() {
        return mTodoDate.getText().toString();
    }

    private boolean isValid() {
        return mTodoTitle.getText().toString().length() != 0;
    }

    // Set Edit mode
    private void setEditMode() {
        if (mTodo != null) {
            mTodoTitle.setText(mTodo.getTitle()); //Set title in detail view
            mTodoNote.setText(mTodo.getNote());   //Set note in detail view
            mTodoDate.setText(mTodo.getDate());   //Set note in detail view
            Log.d(TAG, "Id: " + mTodo.getId());
            Log.d(TAG, "Title: " + mTodo.getTitle());
            Log.d(TAG, "Note: " + mTodo.getNote());
            Log.d(TAG, "Note: " + mTodo.getDate());
            Log.d(TAG, "Priority: " + mTodo.getPriority());
            switch (mTodo.getPriority()) {        //Set priority in detail view
                case MainActivity.TODAY:
                    if (mPriorityToday != null) {
                        mPriorityToday.setChecked(true);
                    }
                    break;
                case MainActivity.SOON:
                    if (mPrioritySoon != null) {
                        mPrioritySoon.setChecked(true);
                    }
                    break;
                case MainActivity.LATER:
                    if (mPriorityLater != null) {
                        mPriorityLater.setChecked(true);
                    }
                    break;
            }
        }
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

                if (mTodo != null) {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Confirm Delete");
                    alertDialog.setMessage("Are you sure you want to delete this Todo Item?");
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
                                    Intent mainActivityIntent = new Intent(DetailActivity.this, MainActivity.class);
                                    TodoDAO dao = new TodoDAO(DetailActivity.this);
                                    dao.delete(mTodo);
                                    Toast.makeText(getApplicationContext(), "Todo item deleted!", Toast.LENGTH_SHORT).show();
                                    startActivity(mainActivityIntent);
                                }
                            }
                    );
                    alertDialog.show();
                    break;
                } else {
                    Toast.makeText(DetailActivity.this, "You haven't saved yet. Nothing to delete.", Toast.LENGTH_SHORT).show();
                }

        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getActionIntent(Context context, String todoKey, Todo todoValue, String todoAction) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.setAction(todoAction);
        intent.putExtra(todoKey, todoValue);
        return intent;
    }

    @Override
    public void onResume() {
        // Reattaching to the fragment
        super.onResume();
        CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = (CalendarDatePickerDialogFragment) getSupportFragmentManager()
                .findFragmentByTag("DATE_PICKER");
        if (calendarDatePickerDialogFragment != null) {
            calendarDatePickerDialogFragment.setOnDateSetListener(this);
        }
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        mTodoDate.setText(getString(R.string.calendar_date_picker_result_values,
                getMonthFromInt(monthOfYear), // Month
                dayOfMonth,                   // Day
                year));                       // Year
    }

    private String getMonthFromInt(int num) {
        String monthAbr;
        switch (num) {
            case 0:
                monthAbr = "JAN";
                break;
            case 1:
                monthAbr = "FEB";
                break;
            case 2:
                monthAbr = "MAR";
                break;
            case 3:
                monthAbr = "APR";
                break;
            case 4:
                monthAbr = "MAY";
                break;
            case 5:
                monthAbr = "JUN";
                break;
            case 6:
                monthAbr = "JUL";
                break;
            case 7:
                monthAbr = "AUG";
                break;
            case 8:
                monthAbr = "SEP";
                break;
            case 9:
                monthAbr = "OCT";
                break;
            case 10:
                monthAbr = "NOV";
                break;
            case 11:
                monthAbr = "DEC";
                break;
            default:
                return "Cannot determine month";
        }
        return monthAbr;
    }

}
