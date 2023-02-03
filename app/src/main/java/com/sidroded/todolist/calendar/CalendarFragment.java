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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sidroded.todolist.R;
import com.sidroded.todolist.note.AddNoteActivity;
import com.sidroded.todolist.user.OpenDataClass;

public class CalendarFragment extends Fragment {
   ListView task_list;


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
        ListViewAdapter adapter=new ListViewAdapter(getActivity(), OpenDataClass.getMaintitle(), OpenDataClass.getTitle_context());
        task_list.setAdapter(adapter);
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        FloatingActionButton calendarFOB = view.findViewById(R.id.calendarFloatingActionButton);
        calendarFOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddNoteActivity.class);
                startActivity(intent);
            }
        });
    }
}