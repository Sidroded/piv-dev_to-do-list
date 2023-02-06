package com.sidroded.todolist.friends;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sidroded.todolist.R;
import com.sidroded.todolist.note.NoteViewActivity;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {
ListView frindsList;
static List<String> frindsListData=new ArrayList<>();

    public static List<String> getFrindsListData() {
        return frindsListData;
    }

    public static void addFriend(String friend){frindsListData.add(friend);}
    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        frindsList=rootView.findViewById(R.id.friends_list_page);
        FriendsViewAdapter adapter= new FriendsViewAdapter(getActivity(),frindsListData);
        frindsList.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        FloatingActionButton calendarFOB = view.findViewById(R.id.friendsFloatingActionButton);
        calendarFOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        AddFriendDialog addFriendDialog = new AddFriendDialog();
        addFriendDialog.show(getFragmentManager(), "Додати учасника");
    }
@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        menu.clear();
    }
}