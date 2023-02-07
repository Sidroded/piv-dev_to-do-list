package com.sidroded.todolist.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sidroded.todolist.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FriendsSpinnerAdapter extends ArrayAdapter<String> {
    LayoutInflater mInflater;
    Context mContext;
    List<String> friends;
    List<String> checkedFriends = new ArrayList<>();
    HashMap<Integer, Boolean> checked = new HashMap<>();
    int mResource;

    public List<String> getCheckedFriends() {
        return checkedFriends;
    }

    public FriendsSpinnerAdapter(Context context, int resource, List<String> friends) {
        super(context, resource, friends);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        this.friends = friends;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.friends_spinner_tile, parent, false);

        TextView spinnerItemTitle = view.findViewById(R.id.title_lol);

        CheckBox spinnerItemCheckbox = view.findViewById(R.id.checkBoxPressed);

        String item = friends.get(position);
        spinnerItemTitle.setText(item);

        if (Boolean.TRUE.equals(checked.get(position))) {
           spinnerItemCheckbox.setChecked(Boolean.TRUE.equals(checked.get(position)));
        }

        spinnerItemCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (spinnerItemCheckbox.isChecked()) {
                    checked.put(position, true);
                    checkedFriends.add(spinnerItemTitle.getText().toString());
                } else {
                    checked.put(position, false);
                    checkedFriends.remove(spinnerItemTitle.getText().toString());
                }
            }
        });

        return view;

    }
}