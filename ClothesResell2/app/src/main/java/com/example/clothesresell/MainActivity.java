package com.example.clothesresell;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.clothesresell.Fragments.ExploreFragment;
import com.example.clothesresell.Fragments.HelpFragment;
import com.example.clothesresell.Fragments.HomeFragment;
import com.example.clothesresell.Fragments.MyProfileFragment;
import com.example.clothesresell.Fragments.NotificationsFragment;
import com.example.clothesresell.Fragments.ProfileFragment;
import com.example.clothesresell.Fragments.WishlistFragment;
import com.example.clothesresell.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private MaterialToolbar topAppBar;
    FirebaseAuth firebaseAuth;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fab = findViewById(R.id.fab);
        firebaseAuth = FirebaseAuth.getInstance();

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            String publisher = intent.getString("publisherid");

            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
            editor.putString("profileid", publisher);
            editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ProfileFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomeFragment()).commit();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PostActivity.class));
            }
        });



        replaceFragment(new HomeFragment());

        binding.bottomNavView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;

                case R.id.explore:
                    replaceFragment(new ExploreFragment());
                    break;

                case R.id.profile:
                    replaceFragment(new MyProfileFragment());

                    break;

                case R.id.notification:
                    replaceFragment(new NotificationsFragment());
                    break;
            }

            return true;
        });

        topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.wishlist:
                replaceFragment(new WishlistFragment());
                break;

            case R.id.help:
                replaceFragment(new HelpFragment());
                break;

            case R.id.logout:
                firebaseAuth.signOut();
                signOutUser();
                break;

//            default:
//                replaceFragment(new HomeFragment());
//                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void signOutUser() {
        Intent intent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(intent);
        finish();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

}