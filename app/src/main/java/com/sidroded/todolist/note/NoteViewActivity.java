package com.sidroded.todolist.note;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.sidroded.todolist.R;

public class NoteViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        toolbar = findViewById(R.id.toolbar_note_view);
        /*setSupportActionBar(toolbar);*/

        toolbar.setTitle(R.string.note_view_toolbar_title_text);
    }
}