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
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPrefManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(instance == null){
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public void saveProfesor(Profesor profesor){
        editor.putLong(SHARED_PREFERENCES_ID, profesor.getId());
        editor.putString(SHARED_PREFERENCES_NAME, profesor.getNombre());
        editor.putString(SHARED_PREFERENCES_EMAIL, profesor.getEmail());
        editor.putString(SHARED_PREFERENCES_FOTO, profesor.getFoto());
        editor.apply();
    }

    public boolean isLoggedIn(){
        if(sharedPreferences.getLong(SHARED_PREFERENCES_ID, -1) != -1){
            return true;
        }
        return false;
    }

    public Profesor getProfesor(){
        Profesor profesor = new Profesor(
                sharedPreferences.getLong(SHARED_PREFERENCES_ID, -1),
                sharedPreferences.getString(SHARED_PREFERENCES_NAME, null),
                sharedPreferences.getString(SHARED_PREFERENCES_EMAIL, null),
                sharedPreferences.getString(SHARED_PREFERENCES_FOTO, null)
        );
        return profesor;
    }

    public void logOut(){
        editor.clear();
        editor.apply();
    }
}
