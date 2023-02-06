package com.sidroded.todolist.calendar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sidroded.todolist.R;
import com.sidroded.todolist.note.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<NoteModel> implements Filterable{

    private final Activity context;
    private List<NoteModel> dataList;
    private String filterInput;
    public ListViewAdapter(Activity context, List<NoteModel>data, String filter) {
        super(context, R.layout.list_tile,data);
        this.filterInput = filter;
        this.context=context;
        dataList=data;
    }



    public View getView(int position, View view, ViewGroup parent) {
        View listItem = view;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.list_tile,parent,false);
            NoteModel currentNote = dataList.get(position);


            TextView titleText = (TextView) listItem.findViewById(R.id.title);
            TextView subtitleText = (TextView) listItem.findViewById(R.id.subtitle);

            titleText.setText(currentNote.getTittle());
            subtitleText.setText(currentNote.getDescription());


        return listItem;
    }


}
