package com.sidroded.todolist.calendar;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sidroded.todolist.MainActivity;
import com.sidroded.todolist.R;
import com.sidroded.todolist.note.NoteModel;
import com.sidroded.todolist.note.NoteViewActivity;

import java.util.ArrayList;
import java.util.List;

public class CalendarCellsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    CalendarView calendarView;
    TextView dateDisplay;
    ListView task_list;
    static FirebaseFirestore db;
    List<NoteModel> filteredData;

    public static FirebaseFirestore getDb() {
        return db;
    }

    static List<NoteModel> dataList=new ArrayList<>();

    private String mParam1;
    private String mParam2;
    private String date;

    public CalendarCellsFragment() {
        // Required empty public constructor
    }

    public static CalendarCellsFragment newInstance(String param1, String param2) {
        CalendarCellsFragment fragment = new CalendarCellsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar_cells, container, false);



        calendarView = rootView.findViewById(R.id.calendarView);
        dateDisplay = rootView.findViewById(R.id.date_display);
        dateDisplay.setText("Date: ");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                int month = i1 + 1;
                String day = String.valueOf(i2);
                if (day.length() == 1) {
                    day = "0" + day;
                }
                String monthStr = String.valueOf(month);
                if (monthStr.length() == 1) {
                    monthStr = "0" + monthStr;
                }
                date = day + "." + monthStr + "." + i;
                dateDisplay.setText("Date: " + date);

            }
        });

        task_list= rootView.findViewById(R.id.task_list_cells);
        db = FirebaseFirestore.getInstance();

        filteredData = new ArrayList<>();

        if(MainActivity.getUser()!=null){
            db.collection(MainActivity.getUser().getUser().getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                if(dataList.size()!=task.getResult().size())
                                {
                                    dataList.add(document.toObject(NoteModel.class));
                                    Log.d("HUI", document.toObject(NoteModel.class).getTittle());
                                }
                            }

                            for (NoteModel current : dataList) {
                                if ((current.getCategory().equals(MainActivity.getFilter()) || MainActivity.getFilter().equals("Всі")) && current.getDate().equals(date)) {
                                    filteredData.add(current);
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
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.calendar_item_ic).setVisible(false);
        menu.findItem(R.id.calendar_main_ic).setVisible(true);
    }
}