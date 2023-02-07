package com.sidroded.todolist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sidroded.todolist.calendar.CalendarFragment;
import com.sidroded.todolist.friends.FriendsFragment;
import com.sidroded.todolist.note.AddNoteActivity;
import com.sidroded.todolist.note.FriendsSpinnerAdapter;
import com.sidroded.todolist.note.NoteModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {
    private static final int FILE_SELECT_CODE = 0;
    int position;
    String[] categories = new String[]{"Інше", "Активність", "Відпочинок", "Зустріч"};
    Uri file;
    Uri uri;
    String path;
    StorageReference storageRef;
    FirebaseFirestore firestore;
    NoteModel item;
    TextView title;
    String category = "";
    TextView description;
    String filename = "";
    TextView time;
    TextView date;
    Button addFileButton;
    Calendar dateAndTime = Calendar.getInstance();
    FirebaseStorage storage;
    FriendsSpinnerAdapter friendsSpinnerAdapter;
    int titleColor;
    long millis;
    Spinner friendsSpinner;
    Button save;
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            date.setText(DateUtils.formatDateTime(EditNoteActivity.this,
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
        setContentView(R.layout.activity_edit_note);
        firestore = CalendarFragment.getDb();
        position = getIntent().getIntExtra("data", 0);
        item = CalendarFragment.getNote(position);
        title = findViewById(R.id.add_note_name_action_edit_text_view_id);
        description = findViewById(R.id.add_note_description_edit_text_view_id);
        time = findViewById(R.id.add_node_time_view_id);
        date = findViewById(R.id.add_node_date_view_id);
        friendsSpinner = findViewById(R.id.add_node_set_friend_spinner_id);
        addFileButton = findViewById(R.id.add_note_add_file_button_id);
        storage = FirebaseStorage.getInstance();
        title.setText(item.getTittle());
        description.setText(item.getDescription());
        date.setText(item.getDate());
        time.setText(item.getTime());
        millis = item.getMillis();
        friendsSpinner=findViewById(R.id.add_node_set_friend_spinner_id);
        friendsSpinnerAdapter = new FriendsSpinnerAdapter(this, R.layout.friends_spinner_tile, FriendsFragment.getFrindsListData());
        friendsSpinner.setAdapter(friendsSpinnerAdapter);
        Spinner spinner = findViewById(R.id.add_node_set_category_spinner_id);
        ArrayAdapter<String> adapter = new ArrayAdapter(EditNoteActivity.this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        titleColor = typedValue.data;
        toolbar.setTitle(Html.fromHtml("<b><font face = '' color='" + titleColor + "'>Редагування події</font></b>"));

        storageRef = storage.getReference();

    }

    public void setDateEdit(View v) {

        new DatePickerDialog(EditNoteActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTimeEdit(View v) {

        new TimePickerDialog(EditNoteActivity.this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    public void setFileEdit(View v) {

    }

    public void addEdit(View v) {
        String temp="";
        if(friendsSpinnerAdapter.getCheckedFriends().size()!=0)
        {
            for(int i=0;i<friendsSpinnerAdapter.getCheckedFriends().size();i++){
                temp+=friendsSpinnerAdapter.getCheckedFriends().get(i)+",";
            }
        }
        NoteModel addingElement = new NoteModel(title.getText().toString(), description.getText().toString(), date.getText().toString(), time.getText().toString(), temp,category, filename,new Date().getTime());
        firestore.collection(MainActivity.getUser().getUser().getUid()).add(addingElement);
        firebaseSave();
        firestore.collection(MainActivity.getUser().getUser().getUid())
                .whereEqualTo("millis", millis)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        if (!querySnapshot.isEmpty()) {
                            // Get the document ID
                            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                            String documentId = document.getId();

                            // Delete the document
                            firestore.collection(MainActivity.getUser().getUser().getUid()).document(documentId)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("Firestore", "Document deleted successfully");
                                            CalendarFragment.updateDataList(position);
                                            Log.d("kek", "deleted");
                                            Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.e("Firestore", "Error deleting document", e);
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Error retrieving document", e);
                    }
                });


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

        public void cancelEdit(View v) {
            Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }