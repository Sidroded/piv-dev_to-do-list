package com.sidroded.todolist.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sidroded.todolist.MainActivity;
import com.sidroded.todolist.R;
import com.sidroded.todolist.note.AddNoteActivity;
import com.sidroded.todolist.note.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment  {
   ListView task_list;
List<NoteModel> dataList=new ArrayList<>();

    public CalendarFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        task_list= rootView.findViewById(R.id.task_list);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(MainActivity.getUser()!=null){
            db.collection(MainActivity.getUser().getUser().getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                dataList.add(document.toObject(NoteModel.class));
                                Log.d("HUI", document.toObject(NoteModel.class).getTittle());
                            }
                            ListViewAdapter adapter=new  ListViewAdapter(getActivity(), dataList);
                            task_list.setAdapter(adapter);
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });

        }
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //NavController navController = Navigation.findNavController(view);
        FloatingActionButton calendarFOB = view.findViewById(R.id.calendarFloatingActionButton);


        calendarFOB.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddNoteActivity.class);
            startActivity(intent);
        });
    }
}