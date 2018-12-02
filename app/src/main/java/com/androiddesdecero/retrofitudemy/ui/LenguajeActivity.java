package com.androiddesdecero.retrofitudemy.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androiddesdecero.retrofitudemy.R;
import com.androiddesdecero.retrofitudemy.api.WebService;
import com.androiddesdecero.retrofitudemy.api.WebServiceApi;
import com.androiddesdecero.retrofitudemy.model.Lenguaje;
import com.androiddesdecero.retrofitudemy.model.Profesor;
import com.androiddesdecero.retrofitudemy.shared_pref.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LenguajeActivity extends AppCompatActivity {

    private EditText etLenguaje;
    private Button btCrearLenguaje;
    private Button btVerLenguajesProfesor;
    private Button btVerTodosLenguajes;
    private Button btAsignarLenguajeProfesor;
    private Profesor profesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lenguaje);

        setUpView();
    }

    private void setUpView(){
        profesor = SharedPrefManager.getInstance(getApplicationContext()).getProfesor();
        etLenguaje = findViewById(R.id.etLenguaje);
        btCrearLenguaje = findViewById(R.id.btCrearLenguaje);
        btCrearLenguaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearLenguaje();
            }
        });
    }
    private void crearLenguaje(){
        Lenguaje lenguaje = new Lenguaje();
        lenguaje.setNombre(etLenguaje.getText().toString());
        Call<Void> call = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .crearLenguaje(lenguaje);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201){
                    Log.d("TAG1", "Lenguaje creado correctamente");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
