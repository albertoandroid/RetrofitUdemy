package com.androiddesdecero.retrofitudemy.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androiddesdecero.retrofitudemy.R;
import com.androiddesdecero.retrofitudemy.api.WebService;
import com.androiddesdecero.retrofitudemy.model.Profesor;
import com.androiddesdecero.retrofitudemy.shared_pref.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private ImageView ivProfesor;

    private Button btUpdate;
    private TextView tvLogOut;
    private TextView tvDelete;
    private TextView tvCurso;
    private TextView tvLenguajes;

    private Profesor profesor;
    private Bitmap bitmap;

    private static final int IMG_REQUEST = 333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpProfesor();
        setUpView();
    }

    private void setUpProfesor(){
        profesor = SharedPrefManager.getInstance(getApplicationContext()).getProfesor();
    }

    private void setUpView(){
        etName = findViewById(R.id.etName);
        etName.setText(profesor.getNombre());
        etEmail = findViewById(R.id.etEmail);
        etEmail.setText(profesor.getEmail());
        btUpdate = findViewById(R.id.btUpdate);
        tvLogOut = findViewById(R.id.tvLogOut);
        tvDelete = findViewById(R.id.tvDelete);

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo update profesor
            }
        });

        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteById();
            }
        });

        ivProfesor = findViewById(R.id.imageView);
    }

    private void deleteById(){
        Call<Void> call = WebService
                .getInstance()
                .createService()
                .deleteById(profesor.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==200){
                    Log.d("TAG1", "Usuario borrado correctamente");
                    logout();
                }else{
                    Log.d("TAG1", "Error no definido");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void logout(){
        SharedPrefManager.getInstance(getApplicationContext()).logOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
