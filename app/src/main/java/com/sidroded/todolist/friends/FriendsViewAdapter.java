package com.sidroded.todolist.friends;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sidroded.todolist.MainActivity;
import com.sidroded.todolist.R;
import com.sidroded.todolist.calendar.CalendarFragment;
import com.sidroded.todolist.note.NoteViewActivity;

import java.util.List;

public class FriendsViewAdapter extends ArrayAdapter<String> {
List<String> friends;
FirebaseFirestore firestore;
    private final Activity context;
    public FriendsViewAdapter(Activity context, List<String> friends) {
        super(context, R.layout.friends_list_tile,friends);
        this.context=context;
        this.friends=friends;
    }



    public View getView(int position, View view, ViewGroup parent) {
        firestore=FirebaseFirestore.getInstance();
        View listItem = view;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.friends_list_tile,parent,false);
        String currentFriend=friends.get(position);
        TextView titleText =  (TextView) listItem.findViewById(R.id.title);
        ImageView delete=(ImageView)listItem.findViewById(R.id.deleteFriend);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("friends"+MainActivity.getUser().getUser().getUid().toString())
                        .whereEqualTo("friend", currentFriend)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot querySnapshot) {
                                if (!querySnapshot.isEmpty()) {
                                    // Get the document ID
                                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                    String documentId = document.getId();

                                    // Delete the document
                                    firestore.collection("friends"+MainActivity.getUser().getUser().getUid().toString()).document(documentId)
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("Firestore", "Document deleted successfully");
                                                    MainActivity.navCo.navigate(R.id.friends);
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
        });

        titleText.setText(currentFriend);

        return listItem;

    }
}
