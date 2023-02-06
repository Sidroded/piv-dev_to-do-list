package com.sidroded.todolist.calendar;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sidroded.todolist.R;
import com.sidroded.todolist.note.NoteModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<NoteModel> {

    private final Activity context;
    private  List<NoteModel> dataList;
    private List<NoteModel> filteredData = new ArrayList<>();
    private String filter;
    public ListViewAdapter(Activity context, List<NoteModel>data, String filter) {
        super(context, R.layout.list_tile,data);
        this.filter = filter;
        this.context=context;
        dataList=data;
    }



    public View getView(int position, View view, ViewGroup parent) {
        View listItem = view;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.list_tile,parent,false);

        filterList();
        if(filteredData.size()>position){NoteModel currentNote = filteredData.get(position);


            TextView titleText = (TextView) listItem.findViewById(R.id.title);
            TextView subtitleText = (TextView) listItem.findViewById(R.id.subtitle);

            titleText.setText(currentNote.getTittle());
            subtitleText.setText(currentNote.getDescription());

        }else {
            listItem.setVisibility(View.INVISIBLE);
        }


        return listItem;
    }

    private void filterList() {
        for (NoteModel current : dataList) {
            if (current.getCategory().equals(filter) || filter.equals("Всі")) {
                filteredData.add(current);
            }
        }
    }
}
