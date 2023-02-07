package com.sidroded.todolist.friends;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.errorprone.annotations.Keep;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sidroded.todolist.MainActivity;
import com.sidroded.todolist.R;
import com.sidroded.todolist.calendar.ListViewAdapter;
import com.sidroded.todolist.note.NoteModel;
import com.sidroded.todolist.note.NoteViewActivity;

import java.util.ArrayList;
import java.util.List;


public class FriendsFragment extends Fragment {
    static FirebaseFirestore db;

    public static FirebaseFirestore getDb() {
        return db;
    }

    static List<String> frindsListData = new ArrayList<>();
    ListView frindsList;

    TextView infoNoFriends;
    public FriendsFragment() {
        // Required empty public constructor
    }

    public static List<String> getFrindsListData() {
        return frindsListData;
    }

    public static void addFriend(String friend) {
        frindsListData.add(friend);
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
        frindsList = rootView.findViewById(R.id.friends_list_page);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        FloatingActionButton calendarFOB = view.findViewById(R.id.friendsFloatingActionButton);
        infoNoFriends = view.findViewById(R.id.friend_text_view_id);
        calendarFOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        db = FirebaseFirestore.getInstance();
        frindsListData.clear();
        if (MainActivity.getUser() != null) {
            db.collection("friends"+MainActivity.getUser().getUser().getUid().toString())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(frindsListData.size()!=task.getResult().size())
                                {
                                    Lol lol= document.toObject(Lol.class);
                                    frindsListData.add(lol.getFriend());
                                }

                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }

                            if (frindsListData.isEmpty()) {
                                infoNoFriends.setText("Наразі у вас немає доданих учасників :(");
                            }

                            FriendsViewAdapter adapter = new FriendsViewAdapter(getActivity(), frindsListData);
                            frindsList.setAdapter(adapter);

                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });

        }
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