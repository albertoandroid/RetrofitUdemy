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
import com.androiddesdecero.retrofitudemy.model.ProfesorLenguaje;
import com.androiddesdecero.retrofitudemy.shared_pref.SharedPrefManager;

import java.util.List;

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

        btVerTodosLenguajes = findViewById(R.id.btVerTodosLenguajes);
        btVerTodosLenguajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verLenguajes();
            }
        });

        btAsignarLenguajeProfesor = findViewById(R.id.btAsignarLenguajeProfesor);
        btAsignarLenguajeProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearLenguajeProfesor();
            }
        });

        btVerLenguajesProfesor = findViewById(R.id.btVerLenguajesProfesor);
        btVerLenguajesProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verLenguajesProfesor();
            }
        });
    }

    private void verLenguajesProfesor(){
        Call<List<Lenguaje>> call = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .verLenguajesProfesor(profesor);

        call.enqueue(new Callback<List<Lenguaje>>() {
            @Override
            public void onResponse(Call<List<Lenguaje>> call, Response<List<Lenguaje>> response) {
                if(response.code()==200){
                    for(int i = 0; i<response.body().size(); i++){
                        Log.d("TAG1", response.body().get(i).getNombre());
                    }
                }else if(response.code()==404){
                    Log.d("TAG1", "No hay lenguajes");
                }
            }

            @Override
            public void onFailure(Call<List<Lenguaje>> call, Throwable t) {

            }
        });
    }

    private void crearLenguajeProfesor(){
        Lenguaje lenguaje = new Lenguaje();
        lenguaje.setId(Long.parseLong(etLenguaje.getText().toString()));
        ProfesorLenguaje profesorLenguaje = new ProfesorLenguaje();
        profesorLenguaje.setLenguaje(lenguaje);
        profesorLenguaje.setProfesor(profesor);
        Call<Void> call = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .saveLenguajeProfesor(profesorLenguaje);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==201){
                    Log.d("TAG1", "Hemos asociado un lenguaje a un profesor");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void verLenguajes(){
        Call<List<Lenguaje>> call = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .getLenguajes();

        call.enqueue(new Callback<List<Lenguaje>>() {
            @Override
            public void onResponse(Call<List<Lenguaje>> call, Response<List<Lenguaje>> response) {
                if(response.code()==200){
                    for(int i = 0; i<response.body().size(); i++){
                        Log.d("TAG1", response.body().get(i).getNombre());
                    }
                }else if(response.code()==404){
                    Log.d("TAG1", "No hay lenguajes");
                }
            }

            @Override
            public void onFailure(Call<List<Lenguaje>> call, Throwable t) {

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
