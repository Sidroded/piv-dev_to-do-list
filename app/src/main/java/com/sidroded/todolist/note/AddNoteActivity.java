package com.sidroded.todolist.note;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sidroded.todolist.MainActivity;
import com.sidroded.todolist.R;
import com.sidroded.todolist.friends.FriendsFragment;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {
    private static final int FILE_SELECT_CODE = 0;

    TextView time;
    TextView date;
    TextView cancel;
    Button addNote;
    FirebaseFirestore db;
    EditText title;
    String path;
    EditText description;
    String[] categories = new String[]{"Інше", "Активність", "Відпочинок", "Зустріч"};
    String category = "";
    FirebaseStorage storage;
    StorageReference storageRef;
    String filename = "";
    Button addFileButton;
    Uri file;
    FriendsSpinnerAdapter friendsSpinnerAdapter;
    Spinner friendsSpinner;
    Uri uri;

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

    public static String getPath(Context context, Uri uri) {
        String filePath = "";
        String wholeID = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            wholeID = DocumentsContract.getDocumentId(uri);
        }
        // Split at colon, use second item in the array
        String id = null;
        if (wholeID != null) {
            id = wholeID.split(":")[1];
        }
        String[] column = {MediaStore.Images.Media.DATA};
        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);
        int columnIndex = cursor.getColumnIndex(column[0]);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();

        return filePath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_note);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        title = findViewById(R.id.add_note_name_action_edit_text_view_id);
        description = findViewById(R.id.add_note_description_edit_text_view_id);
        db = FirebaseFirestore.getInstance();
        addFileButton = findViewById(R.id.add_note_add_file_button_id);
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

        friendsSpinner = findViewById(R.id.add_node_set_friend_spinner_id);
        friendsSpinnerAdapter = new FriendsSpinnerAdapter(this, R.layout.friends_spinner_tile, FriendsFragment.getFrindsListData());
        Spinner spinner = findViewById(R.id.add_node_set_category_spinner_id);
        ArrayAdapter<String> adapter = new ArrayAdapter(AddNoteActivity.this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //friendsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        friendsSpinner.setAdapter(friendsSpinnerAdapter);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = (String) parent.getItemAtPosition(position);
                category = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);


        ActionBar toolbar = getSupportActionBar();

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(androidx.appcompat.R.attr.colorAccent, typedValue, true);
        int titleColor = typedValue.data;

        toolbar.setTitle(Html.fromHtml("<b><font face = '' color='" + titleColor + "'>Нова подія</font></b>"));

    }

    public void add(View v) {
        String temp="";
        if(friendsSpinnerAdapter.getCheckedFriends().size()!=0)
        {
            for(int i=0;i<friendsSpinnerAdapter.getCheckedFriends().size();i++){
                temp+=friendsSpinnerAdapter.getCheckedFriends().get(i)+",";
            }
        }
        NoteModel addingElement = new NoteModel(title.getText().toString(), description.getText().toString(), date.getText().toString(), time.getText().toString(), temp, category, filename, new Date().getTime());
        db.collection(MainActivity.getUser().getUser().getUid()).add(addingElement);
        firebaseSave();
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

    public void addFile(View v) {
        if (addFileButton.getText() == filename) {
            addFileButton.setText(R.string.add_node_add_file_button_text);
        } else {
            showFileSelector();
        }

    }

    private void showFileSelector() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    uri = data.getData();
                    Log.d("MainActivity", "File Uri: " + uri.toString());
                    // Get the path
                    path = getPath(this, uri);
                    //File temp=get
                    System.out.println("filename:" + new File(uri.toString()).getName());
                    filename = new File(uri.toString()).getName();
                    addFileButton.setText(filename.toString());
                    System.out.println("filename:" + filename);
                    //saving in the firebase
                    //firebaseSave();
                    Log.d("MainActivity", "File Path: " + path);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseSave() {

        if (uri != null) {
            StorageReference riversRef = storageRef.child((MainActivity.getUser().getUser().getUid()));
            file = uri;

            UploadTask uploadTask = riversRef.putFile(file);


            uploadTask.addOnProgressListener(taskSnapshot -> {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            });
        }


    }

}