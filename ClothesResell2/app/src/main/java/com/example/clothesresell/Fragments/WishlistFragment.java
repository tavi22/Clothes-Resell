package com.example.clothesresell.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.clothesresell.Adapter.MyPhotoAdapter;
import com.example.clothesresell.Model.Post;
import com.example.clothesresell.Model.User;
import com.example.clothesresell.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link MyProfileFragmentnewInstance} factory method to
 * create an instance of this fragment.
 */

public class WishlistFragment extends Fragment {

    ImageView image_profile, options;
    TextView posts, followers, following, fullname, bio, username;
    Button edit_profile;

    private List<String> mySaves;

    RecyclerView recyclerView;
    MyPhotoAdapter myPhotoAdapter;
    List<Post> postList;

    FirebaseUser firebaseUser;
    String profileid;

    private RecyclerView recyclerView_saves;
    private MyPhotoAdapter myFotosAdapter_saves;
    private List<Post> postList_saves;

    ImageButton my_fotos, saved_fotos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        saved_fotos = view.findViewById(R.id.saved_fotos);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        myPhotoAdapter = new MyPhotoAdapter(getContext(), postList);
        recyclerView.setAdapter(myPhotoAdapter);

        recyclerView_saves = view.findViewById(R.id.recycler_view_save);
        recyclerView_saves.setHasFixedSize(true);
        LinearLayoutManager mLayoutManagers = new GridLayoutManager(getContext(), 3);
        recyclerView_saves.setLayoutManager(mLayoutManagers);
        postList_saves = new ArrayList<>();
        myFotosAdapter_saves = new MyPhotoAdapter(getContext(), postList_saves);
        recyclerView_saves.setAdapter(myFotosAdapter_saves);

        recyclerView.setVisibility(View.VISIBLE);
        recyclerView_saves.setVisibility(View.GONE);

        mySaves();


        saved_fotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
                recyclerView_saves.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }




    private void mySaves(){
        mySaves = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Saves").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    mySaves.add(snapshot.getKey());
                }
                readSaves();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void readSaves(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList_saves.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);

                    for (String id : mySaves) {
                        if (post.getPostid().equals(id)) {
                            postList_saves.add(post);
                        }
                    }
                }
                myFotosAdapter_saves.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

/**
 public class MyProfileFragment extends Fragment {

 // TODO: Rename parameter arguments, choose names that match
 // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
 private static final String ARG_PARAM1 = "param1";
 private static final String ARG_PARAM2 = "param2";

 // TODO: Rename and change types of parameters
 private String mParam1;
 private String mParam2;

 public MyProfileFragment() {
 // Required empty public constructor
 }

 *
 * Use this factory method to create a new instance of
 * this fragment using the provided parameters.
 *
 * @param param1 Parameter 1.
 * @param param2 Parameter 2.
 * @return A new instance of fragment MyProfileFragment.

// TODO: Rename and change types and number of parameters
public static MyProfileFragment newInstance(String param1, String param2) {
MyProfileFragment fragment = new MyProfileFragment();
Bundle args = new Bundle();
args.putString(ARG_PARAM1, param1);
args.putString(ARG_PARAM2, param2);
fragment.setArguments(args);
return fragment;
}

 @Override
 public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 if (getArguments() != null) {
 mParam1 = getArguments().getString(ARG_PARAM1);
 mParam2 = getArguments().getString(ARG_PARAM2);
 }
 }

 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState) {
 // Inflate the layout for this fragment
 return inflater.inflate(R.layout.fragment_my_profile, container, false);
 }
 }


 public class MyProfileFragment extends AppCompatActivity {
 @NonNull View view;
 private static final String TAG = "MyProfileFragment";
 private static final int ACTIVITY_NUM = 4;

 private Context mContext = ProfileA;

 ActivityMainBinding biding;


 //    private ProgressBar mProgressBar;

 public MyProfileFragment() {
 // Required empty public constructor
 }

 public static MyProfileFragment newInstance(String param1, String param2) {
 MyProfileFragment fragment = new MyProfileFragment();

 return fragment;
 }

 @Override
 public void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);

 setContentView(R.layout.activity_main);


 tempGridSetup();
 }

 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState) {
 // Inflate the layout for this fragment
 return inflater.inflate(R.layout.fragment_my_profile, container, false);
 }

 @Override
 //    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
 //        super.onViewCreated(view, savedInstanceState);
 //        this.view = view;
 //    }

 private void tempGridSetup(){
 ArrayList<String> imgURLs = new ArrayList<>();
 //        imgURLs.add("https://www.adidas.com/us/men-clothing");
 imgURLs.add("https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/b47d77eba6f945ea8dabac210127b11e_9366/Stan_Smith_Shoes_White_FX5501_01_standard.jpg");
 imgURLs.add("https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/58f1be3ebc904c8a8fb5ae0a01673ae5_9366/Ultraboost_5.0_DNA_Shoes_Black_GX9329_01_standard.jpg");
 imgURLs.add("https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/5a70faa1767645ee85e5adde00766e85_9366/Twill_Pocket_Hoodie_Beige_HE4679_21_model.jpg");
 imgURLs.add("https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/354441e468c144048b7cade5005aa462_9366/Badge_of_Sport_Tee_Grey_HI1147_21_model.jpg");
 imgURLs.add("https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/27d1229f859a490db32dad0b008a4c8f_9366/Blue_Version_Remix_Duffel_Bag_Blue_H25137_01_standard.jpg");
 imgURLs.add("https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/e50704e5be3e409fbe4eadf800d1eb3e_9366/Train_Icons_Training_Shorts_Blue_HC4272_21_model.jpg");

 setupImageGrid(imgURLs);
 }
 private void setupImageGrid(ArrayList<String> imgURLs){
 GridView gridView = (GridView) view.findViewById(R.id.gridPosts);


 GridImageAdaptor adapter = new GridImageAdaptor(mContext, R.layout.grid_imageview, "", imgURLs);
 gridView.setAdapter(adapter);
 }

 public void setmContext(Context mContext) {
 this.mContext = mContext;
 }
 }
 */