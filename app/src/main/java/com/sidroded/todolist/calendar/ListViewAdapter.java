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
    private final List<NoteModel> dataList;
    public ListViewAdapter(Activity context, List<NoteModel>dataList) {
        super(context, R.layout.list_tile,dataList);

        this.context=context;
        this.dataList=dataList;
    }



    public View getView(int position, View view, ViewGroup parent) {

        Log.d("LOL","$position");
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_tile, null,true);
        NoteModel currentNumberPosition = dataList.get(position);
        TextView titleText =  (TextView) rowView.findViewById(R.id.title);
        TextView subtitleText =  (TextView) rowView.findViewById(R.id.subtitle);

        titleText.setText(currentNumberPosition.getTittle());
        subtitleText.setText(currentNumberPosition.getDescription());

        return rowView;

    }
}
