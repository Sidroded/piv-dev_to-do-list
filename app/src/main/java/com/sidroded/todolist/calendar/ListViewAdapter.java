package com.sidroded.todolist.calendar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sidroded.todolist.R;
import com.sidroded.todolist.note.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private  List<String> maintitle=new ArrayList<>();
    private  List<String> subtitle=new ArrayList<>();
    List<NoteModel> dataList;
    public ListViewAdapter(Activity context, List<NoteModel>dataList) {
        super(context, R.layout.list_tile);

        this.context=context;
        this.dataList=dataList;
        lol(dataList);

    }

void lol(List<NoteModel> dataList){
        if (dataList.size()!=0){
            for(int i=0;i<dataList.size();i++)
            {
                maintitle.add(dataList.get(i).getTittle());
                subtitle.add(dataList.get(i).getDescription());
            }
        }

}
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_tile, null,true);
        View currentItemView = view;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.
                    list_tile, parent, false);
        }
        String currentNumberPosition = getItem(position);
        TextView titleText =  rowView.findViewById(R.id.title);
        TextView subtitleText =  rowView.findViewById(R.id.subtitle);

        titleText.setText(maintitle.get(position));
        subtitleText.setText(subtitle.get(position));

        return rowView;

    }
}
