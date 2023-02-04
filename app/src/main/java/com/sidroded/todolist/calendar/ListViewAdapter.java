package com.sidroded.todolist.calendar;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sidroded.todolist.R;
import com.sidroded.todolist.note.NoteModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<NoteModel> {

    private final Activity context;
    private  List<NoteModel> dataList;
    public ListViewAdapter(Activity context, List<NoteModel>data) {
        super(context, R.layout.list_tile,data);

        this.context=context;
        dataList=data;
    }



    public View getView(int position, View view, ViewGroup parent) {
        View listItem = view;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.list_tile,parent,false);
        NoteModel currentNote=dataList.get(position);

        TextView titleText =  (TextView) listItem.findViewById(R.id.title);
        TextView subtitleText =  (TextView) listItem.findViewById(R.id.subtitle);

        titleText.setText(currentNote.getTittle());
        subtitleText.setText(currentNote.getDescription());

        return listItem;

    }
}
