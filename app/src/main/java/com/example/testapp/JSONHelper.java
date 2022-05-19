package com.example.testapp;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class JSONHelper {

    private static final String FILE_NAME = "data.json";

    static boolean exportToJSON(Context context, List<ContentValues> list) {

        Gson gson = new Gson();
        String jsonString = gson.toJson(list);
        try(FileOutputStream fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    static List<ContentValues> importFromJSON(Context context) {

        try(FileInputStream fileInputStream = context.openFileInput(FILE_NAME);
            InputStreamReader streamReader = new InputStreamReader(fileInputStream)){

            Gson gson = new Gson();
            return gson.fromJson(streamReader, (Type) ContentValues.class);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        return null;
    }

}
