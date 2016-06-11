package com.beraaksoy.todofu;

import android.os.Build;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by beraaksoy on 6/11/16.
 */
public class DatePickerUtil implements OnDateChangedListener {

    static DatePicker picker;
    static GregorianCalendar now = new GregorianCalendar();
    static String month;
    static String day;
    static String year;
    static Date date;

    public Date getDateOfTodoItem() {
        date = new Date();
        initializePicker();
        return date;
    }

    public void initializePicker() {
        picker.init(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH),DatePickerUtil.this);
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            picker.setCalendarViewShown(isChecked);
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar then = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        Log.d("DATEPICKED", "Month: " + getMonthForInt(then.get(Calendar.MONTH)));
        Log.d("DATEPICKED", "Day: " + then.get(Calendar.DAY_OF_MONTH));
        Log.d("DATEPICKED", "Year: " + then.get(Calendar.YEAR));
    }

    public String getMonthForInt(int num) {
        String monthAbr;
        switch (num) {
            case 0:
                monthAbr = "JAN";
                break;
            case 1:
                monthAbr = "FEB";
                break;
            case 2:
                monthAbr = "MARCH";
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
