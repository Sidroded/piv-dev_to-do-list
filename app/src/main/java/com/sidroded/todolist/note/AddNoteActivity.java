package com.sidroded.todolist.note;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.firestore.FirebaseFirestore;
import com.sidroded.todolist.MainActivity;
import com.sidroded.todolist.R;

import java.util.Calendar;

public class AddNoteActivity extends AppCompatActivity {
    TextView time;
    TextView date;
    TextView cancel;
    Button addNote;
    FirebaseFirestore db;
    EditText title;
    EditText description;
    String[] categories = new String[] {"Завдання", "Активність", "Відпочинок", "Зустріч"};
    String category = "";


    Calendar dateAndTime = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            date.setText(DateUtils.formatDateTime(AddNoteActivity.this,
                    dateAndTime.getTimeInMillis(),
                    DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }
    };
    TimePickerDialog.OnTimeSetListener t = (view, hourOfDay, minute) -> {
        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateAndTime.set(Calendar.MINUTE, minute);
        time.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_note);
        title = findViewById(R.id.add_note_name_action_edit_text_view_id);
        description = findViewById(R.id.add_note_description_edit_text_view_id);
        db = FirebaseFirestore.getInstance();
        addNote = findViewById(R.id.add_note_save_note_button);
        cancel = findViewById(R.id.add_note_cancel_text_view);
        time = findViewById(R.id.add_node_time_view_id);
        date = findViewById(R.id.add_node_date_view_id);
        time.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
        date.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));



        Spinner spinner = findViewById(R.id.add_node_set_category_spinner_id);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = (String)parent.getItemAtPosition(position);
                category = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);


        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle(R.string.add_note_toolbar_title);

    }

    public void add(View v) {
        NoteModel addingElement = new NoteModel(title.getText().toString(), description.getText().toString(), date.getText().toString(), time.getText().toString(),"Hui",category);
        db.collection( MainActivity.getUser().getUser().getUid()).add(addingElement);
        Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void cancel(View v) {
        Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void setTime(View v) {
        new TimePickerDialog(AddNoteActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    public void setDate(View v) {
        new DatePickerDialog(AddNoteActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();

    }

}