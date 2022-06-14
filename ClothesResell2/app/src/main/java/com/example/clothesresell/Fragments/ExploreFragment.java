package com.example.clothesresell.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clothesresell.Adapter.PostAdapter;
import com.example.clothesresell.Adapter.UserAdapter;
import com.example.clothesresell.Model.Post;
import com.example.clothesresell.Model.User;
import com.example.clothesresell.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ExploreFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;
    private PostAdapter postAdapter;
    private List<Post> mPosts;

    Button users;
    Button posts;
    EditText search_bar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        users = view.findViewById(R.id.btn_users);
        posts = view.findViewById(R.id.btn_posts);

        search_bar = view.findViewById(R.id.search_bar);

        mUsers = new ArrayList<>();
        userAdapter = new UserAdapter(getContext(), mUsers);
        mPosts = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), mPosts);

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readUsers();
                users.setClickable(false);
                posts.setClickable(true);
                posts.setBackgroundColor(Color.RED);
                users.setBackgroundColor(Color.GRAY);
                recyclerView.setAdapter(userAdapter);


            }
        });

        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readPosts();
                posts.setClickable(false);
                users.setClickable(true);
                users.setBackgroundColor(Color.RED);
                posts.setBackgroundColor(Color.GRAY);
                recyclerView.setAdapter(postAdapter);


            }
        });

        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (users.isClickable()) {
                    searchPosts(charSequence.toString().toLowerCase());

                } else {
                    searchUsers(charSequence.toString().toLowerCase());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void searchUsers (String s) {
        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("username")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    User user = snap.getValue(User.class);
                    mUsers.add(user);
                }

                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (search_bar.getText().toString().equals("")) {
                    mUsers.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        User user = snap.getValue(User.class);
                        mUsers.add(user);
                    }

                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void searchPosts(String s) {
        Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("description")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mPosts.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Post post = snap.getValue(Post.class);
                    mPosts.add(post);
                }

                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readPosts() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (search_bar.getText().toString().equals("")) {
                    mPosts.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        Post post = snap.getValue(Post.class);
                        mPosts.add(post);
                    }

                    postAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}