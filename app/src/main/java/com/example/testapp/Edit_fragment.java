package com.example.testapp;

import android.os.Bundle;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Edit_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Edit_fragment extends Fragment {


    public Edit_fragment() {
        // Required empty public constructor
    }

    public static Edit_fragment newInstance(String param1, String param2) {
        Edit_fragment fragment = new Edit_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_, container, false);
        Button save = view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
            }
        });
        return view;
    }
}