package com.sidroded.todolist.note;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.sidroded.todolist.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FriendsSpinnerAdapter extends ArrayAdapter<List<String>> {
   List<String> friends= new ArrayList<>();
   List<Boolean> checked;
    CheckBox checkBox;
    private final Activity context;
    public FriendsSpinnerAdapter(Activity context, List<String> friends) {
        super(context, R.layout.friends_list_tile, Collections.singletonList(friends));
        this.context=context;
        this.friends=friends;
        for (int i =0;i<friends.size();i++)
        {
            checked.add(false);
        }
    }
    public View getView(int position, View view, ViewGroup parent) {
        View listItem = view;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.friends_spinner_tile,parent,false);
        String currentFriend=friends.get(position);
         checkBox=(CheckBox) listItem.findViewById(R.id.checkBoxPressed);
        TextView titleText =  (TextView) listItem.findViewById(R.id.title);
        titleText.setText(currentFriend);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                 checked.set(position, true);
                else {
                    checked.set(position, false);
                }
            }
        });


        return listItem;

    }


}
