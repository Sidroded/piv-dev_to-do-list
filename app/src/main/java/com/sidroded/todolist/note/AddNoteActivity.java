package com.sidroded.todolist.note;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.sidroded.todolist.R;

import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {
    TextView time;
    TextView date;
    Calendar dateAndTime=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        time= findViewById(R.id.add_node_time_view_id);
        date=findViewById(R.id.add_node_date_view_id);
        time.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
        date.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));

    }
    public void setTime(View v){
        new TimePickerDialog(AddNoteActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
        time.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }
    public void setDate(View v){
        new DatePickerDialog(AddNoteActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
        date.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }
    };

    TimePickerDialog.OnTimeSetListener t= (view, hourOfDay, minute) -> {
        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateAndTime.set(Calendar.MINUTE, minute);
    };

}