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
import com.androiddesdecero.retrofitudemy.model.Curso;
import com.androiddesdecero.retrofitudemy.model.Profesor;
import com.androiddesdecero.retrofitudemy.shared_pref.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CursoActivity extends AppCompatActivity {

    private EditText etCurso;
    private Button btCrearCurso;
    private Button btVerCursosProfesor;
    private Button btVerTodosCurso;

    private Profesor profesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);
        setUpView();
    }

    private void setUpView(){
        profesor = SharedPrefManager.getInstance(getApplicationContext()).getProfesor();
        etCurso = findViewById(R.id.etCurso);
        btCrearCurso = findViewById(R.id.btCrearCurso);
        btCrearCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearCurso();
            }
        });
    }

    private void crearCurso(){
        Curso curso = new Curso();
        curso.setNombre(etCurso.getText().toString());
        curso.setProfesorId(profesor.getId());
        Call<Void> call = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .crearCurso(curso);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code()==201){
                    Log.d("TAG!", "curso creado correctamente");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
