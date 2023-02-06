package com.sidroded.todolist.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sidroded.todolist.EditNoteActivity;
import com.sidroded.todolist.MainActivity;
import com.sidroded.todolist.R;
import com.sidroded.todolist.calendar.CalendarFragment;

public class NoteViewActivity extends AppCompatActivity {
    TextView title;
    TextView description;
    TextView time;
    TextView date;
    TextView backToCalendar;
    TextView category;
    Button delete;
    Button editNote;
    FirebaseFirestore firestore;
    int position;
NoteModel item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);
        title=findViewById(R.id.note_view_title_text_view);
        description=findViewById(R.id.note_view_description_text_view);
        time=  findViewById(R.id.note_view_time_text_view);
        date=findViewById(R.id.note_view_date_text_view);
        backToCalendar=findViewById(R.id.note_view_cancel_text_view);
        category=findViewById(R.id.note_view_category_list_text_view);
        delete=findViewById(R.id.note_view_delete_button);
        editNote=findViewById(R.id.note_view_edit_button);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle(R.string.note_view_toolbar_title_text);
         firestore = CalendarFragment.getDb();
         position = getIntent().getIntExtra("data",0);
         item= CalendarFragment.getNote(position);
         title.setText(item.getTittle());
         description.setText(item.getDescription());
         time.setText(item.getTime());
         date.setText(item.getDate());
         category.setText(item.getCategory());

    }
   public void cancel(View v){
        Intent intent = new Intent(NoteViewActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void deleteFromFirebase(View v){

        firestore.collection(MainActivity.getUser().getUser().getUid())
                .whereEqualTo("time", item.getTime())
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
                                            Log.d("kek","deleted");
                                           Intent intent= new Intent(NoteViewActivity.this,MainActivity.class);
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
    public void editNote(View v){
        Intent intent= new Intent(NoteViewActivity.this, EditNoteActivity.class);
        intent.putExtra("data",position);
        startActivity(intent);

    }







}