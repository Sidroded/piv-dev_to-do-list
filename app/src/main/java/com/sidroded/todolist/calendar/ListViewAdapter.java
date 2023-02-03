package com.sidroded.todolist.calendar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sidroded.todolist.R;

public class ListViewAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;

    public ListViewAdapter(Activity context, String[] maintitle,String[] subtitled) {
        super(context, R.layout.list_tile, maintitle);

        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitled;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_tile, null,true);

        TextView titleText =  rowView.findViewById(R.id.title);
        TextView subtitleText =  rowView.findViewById(R.id.subtitle);

        titleText.setText(maintitle[position]);
        subtitleText.setText(subtitle[position]);

        return rowView;

    }
}
