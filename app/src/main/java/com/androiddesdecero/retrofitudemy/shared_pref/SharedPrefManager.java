package com.androiddesdecero.retrofitudemy.shared_pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.androiddesdecero.retrofitudemy.model.Profesor;

/**
 * Created by albertopalomarrobledo on 1/12/18.
 */

public class SharedPrefManager {
    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    private static final String SHARED_PREFERENCES_ID = "SHARED_PREFERENCES_ID";
    private static final String SHARED_PREFERENCES_EMAIL = "SHARED_PREFERENCES_EMAIL";
    private static final String SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME";
    private static final String SHARED_PREFERENCES_FOTO = "SHARED_PREFERENCES_FOTO";

    private static SharedPrefManager instance;
    private Context context;

    private SharedPrefManager(Context context){
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(instance == null){
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public void saveProfesor(Profesor profesor){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SHARED_PREFERENCES_ID, profesor.getId());
        editor.putString(SHARED_PREFERENCES_NAME, profesor.getNombre());
        editor.putString(SHARED_PREFERENCES_EMAIL, profesor.getEmail());
        editor.putString(SHARED_PREFERENCES_FOTO, profesor.getFoto());
        editor.apply();
    }
}
