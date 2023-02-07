package com.sidroded.todolist.calendar;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.sidroded.todolist.note.NoteViewActivity;

import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment  {
   ListView task_list;
    static FirebaseFirestore db;
    List<NoteModel> filteredData;
    String title1 = "";
    String title2 = "";

    public static FirebaseFirestore getDb() {
        return db;
    }

    static List<NoteModel> dataList=new ArrayList<>();

    public static void updateDataList(int i) {
        dataList.remove(i);
    }

    public CalendarFragment() {
    }

    public static NoteModel getNote(int position) {
        return dataList.get(position);
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
         db = FirebaseFirestore.getInstance();

         filteredData = new ArrayList<>();

         dataList.clear();

        if(MainActivity.getUser()!=null){
            db.collection(MainActivity.getUser().getUser().getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                if(dataList.size() != task.getResult().size())
                                {
                                    dataList.add(document.toObject(NoteModel.class));
                                    Log.d("HUI", document.toObject(NoteModel.class).getTittle());
                                    String title1 = document.toObject(NoteModel.class).getTittle().toString();
                                }
                            }

                            for (NoteModel current : dataList) {
                                if (current.getCategory().equals(MainActivity.getFilter()) || MainActivity.getFilter().equals("Всі")) {
                                    filteredData.add(current);
                                     title2 = current.getTittle().toString();
                                }
                            }

                            ListViewAdapter adapter = new  ListViewAdapter(getActivity(), filteredData, MainActivity.getFilter());
                            task_list.setAdapter(adapter);
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });

        }
        task_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), NoteViewActivity.class);
                intent.putExtra("data",i);
                startActivity(intent);
            }
        });
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