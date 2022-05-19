package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Report extends AppCompatActivity {

    DBHelper dbHelper;
    ArrayList<String> titles = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        titles.clear();
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("film", null, null, null, null, null, "mark");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int title_i = cursor.getColumnIndex("title");
                do {
                    titles.add(cursor.getString(title_i));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        ListView lvMain = findViewById(R.id.listItem);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);

        lvMain.setAdapter(adapter);
        Context context = this;
        Button b_json = findViewById(R.id.button6);
        b_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ContentValues> list = new ArrayList<ContentValues>();
                Cursor cursor = db.query("film", null, null, null, null, null, "mark");
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int title_i = cursor.getColumnIndex("title");
                        int mark_i = cursor.getColumnIndex("mark");
                        int desc_i = cursor.getColumnIndex("description");
                        int look_i = cursor.getColumnIndex("is_look");
                        do {
                            ContentValues cv = new ContentValues();
                            cv.put("title", cursor.getString(title_i));
                            cv.put("mark", cursor.getInt(mark_i));
                            cv.put("description", cursor.getString(desc_i));
                            cv.put("look", cursor.getInt(look_i) == 1);
                            list.add(cv);
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                JSONHelper.exportToJSON(context, list);
            }
        });

        Button im_json = findViewById(R.id.import_j);
        im_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ContentValues> cv = JSONHelper.importFromJSON(context);
            }
        });
    }
}