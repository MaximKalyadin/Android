package com.example.testapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Add_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_Fragment extends Fragment {

    DBHelper dbHelper;

    public Add_Fragment() {
        // Required empty public constructor
    }

    public static Add_Fragment newInstance(String param1, String param2) {
        Add_Fragment fragment = new Add_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dbHelper = new DBHelper(this.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Bundle bundle = this.getArguments();
        boolean isAdd = false;
        int item_id = 0;
        if (bundle != null) {
            isAdd = bundle.getBoolean("is_add");
            item_id = bundle.getInt("item_id");
        }
        View view = inflater.inflate(R.layout.fragment_add_, container, false);
        EditText title = view.findViewById(R.id.title);
        CheckBox look = view.findViewById(R.id.checkBox2);
        EditText mark = view.findViewById(R.id.mark);
        EditText desc = view.findViewById(R.id.desc);
        if (isAdd) {
            String idSTR = String.valueOf(item_id);
            String[] selectionArgs = new String[] { idSTR };
            Cursor cursor = db.query("film", null, "id == ?", selectionArgs, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int title_i = cursor.getColumnIndex("title");
                    int mark_i = cursor.getColumnIndex("mark");
                    int desc_i = cursor.getColumnIndex("description");
                    int look_i = cursor.getColumnIndex("is_look");
                    do {
                        title.setText(cursor.getString(title_i));
                        mark.setText(cursor.getString(mark_i));
                        desc.setText(cursor.getString(desc_i));
                        look.setChecked(cursor.getInt(look_i) == 1);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        }

        Button save = view.findViewById(R.id.save);
        boolean finalIsAdd = isAdd;
        int finalItem_id = item_id;
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save
                ContentValues contentValues = new ContentValues();
                contentValues.put("title", title.getText().toString());
                contentValues.put("is_look", look.isChecked());
                contentValues.put("mark", mark.getText().toString());
                contentValues.put("description", desc.getText().toString());
                try {
                    if (!finalIsAdd) {
                        long id = db.insert("film", null, contentValues);
                    } else {
                        String idSTR = String.valueOf(finalItem_id);
                        String[] selectionArgs = new String[] { idSTR };
                        long id = db.update("film", contentValues, "id == ?", selectionArgs);
                    }
                } catch (Exception e) {
                    Log.d("exception", e.getMessage());
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.popBackStack();
                dbHelper.close();
            }
        });
        return view;
    }
}