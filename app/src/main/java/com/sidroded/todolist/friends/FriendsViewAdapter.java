package com.sidroded.todolist.friends;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sidroded.todolist.R;

import java.util.List;

public class FriendsViewAdapter extends ArrayAdapter<String> {
List<String> friends;

    private final Activity context;
    public FriendsViewAdapter(Activity context, List<String> friends) {
        super(context, R.layout.friends_list_tile,friends);
        this.context=context;
        this.friends=friends;
    }



    public View getView(int position, View view, ViewGroup parent) {
        View listItem = view;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.friends_list_tile,parent,false);
        String currentFriend=friends.get(position);
        TextView titleText =  (TextView) listItem.findViewById(R.id.title);

        titleText.setText(currentFriend);

        return listItem;

    }
}
