package com.sidroded.todolist.note;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.sidroded.todolist.R;

public class NoteViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);


        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle(R.string.note_view_toolbar_title_text);
    }
}