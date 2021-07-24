package id.ac.polman.astra.kelompok2.financialrecords.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import id.ac.polman.astra.kelompok2.financialrecords.Entity.UserEntity;

public class Preference {
    private final static String TAG = Preference.class.getSimpleName();
    private final static String KEY = "data_key";
    private final SharedPreferences mSharedPreferences;
    private final SharedPreferences.Editor mEditor;
    private final Gson mGson;

    private final static String USER_KEY = "user_key";

    @SuppressLint("CommitPrefEdits")
    public Preference(Context context){
        mSharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mGson = new Gson();
    }

    public void setUser(UserEntity user){
        String jsonUser = "";
        if (user != null)
            jsonUser = mGson.toJson(user);

        mEditor.putString(USER_KEY, jsonUser);
        mEditor.commit();
    }
}
