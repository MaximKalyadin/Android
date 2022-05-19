package com.example.testapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.app.FragmentTransaction;
import android.widget.ListView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Main#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Main extends Fragment {

    String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };
    ArrayList<String> titles  = new ArrayList<String>();

    DBHelper dbHelper;

    public Main() {
        // Required empty public constructor
    }

    public static Main newInstance(String param1, String param2) {
        Main fragment = new Main();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        dbHelper = new DBHelper(this.getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("film", null, null, null, null, null, null, null);
        titles.clear();
        if (cursor.moveToFirst()) {
            int title = cursor.getColumnIndex("title");
            do {
                titles.add(cursor.getString(title));
            } while (cursor.moveToNext());
        }
        cursor.close();

        ListView lvMain = view.findViewById(R.id.list_info);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, titles);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Add_Fragment add_fragment = new Add_Fragment();

                Bundle bundle = new Bundle();
                add_fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                bundle.putBoolean("is_add", true);
                bundle.putInt("item_id", position + 1);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frgmCont, add_fragment);
                fragmentTransaction.addToBackStack("Main");
                fragmentTransaction.commit();
            }
        });

        Button add =view.findViewById(R.id.button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add_Fragment add_fragment = new Add_Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frgmCont, add_fragment);
                fragmentTransaction.addToBackStack("Main");
                fragmentTransaction.commit();
            }
        });

        Button report = view.findViewById(R.id.button2);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Report.class);
                startActivity(i);
            }
        });
        dbHelper.close();
        return view;
    }
}