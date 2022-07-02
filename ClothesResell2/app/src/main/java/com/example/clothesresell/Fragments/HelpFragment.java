package com.example.clothesresell.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.clothesresell.R;


public class HelpFragment extends Fragment {

    private Button back, find_more;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        find_more = view.findViewById(R.id.btn_find);

        find_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://github.com/tavi22/Reseller");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        return view;
    }

}