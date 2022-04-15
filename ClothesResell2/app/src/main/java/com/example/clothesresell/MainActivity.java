package com.example.clothesresell;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.clothesresell.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


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

            case R.id.home:
                finish();
                break;

//            default:
//                replaceFragment(new HomeFragment());
//                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

}