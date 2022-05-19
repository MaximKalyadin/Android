package com.example.testapp;

import androidx.fragment.app.FragmentActivity;


import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends FragmentActivity {

    Main main_frag;
    FragmentTransaction fTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_frag = new Main();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCont, main_frag);
        fTrans.commit();
    }
}