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

import java.util.List;

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

        btVerTodosCurso = findViewById(R.id.btVerTodosCursos);
        btVerTodosCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verTodosLosCursos();
            }
        });

        btVerCursosProfesor = findViewById(R.id.btVerCursosProfesor);
        btVerCursosProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verCursosPorProfesor();
            }
        });
    }

    private void verCursosPorProfesor(){
        Call<List<Curso>> call = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .getCursosProfesor(profesor);

        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if(response.code()==200){
                    for(int i=0; i<response.body().size(); i++){
                        Log.d("TAG1", "Nombre Curso: " + response.body().get(i).getNombre()
                                + " Codigo Profesor: " + response.body().get(i).getProfesorId());
                    }
                }else if(response.code()==404){
                    Log.d("TAG1", "No hay cursos");
                }
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {

            }
        });
    }

    private void verTodosLosCursos(){
        Call<List<Curso>> call = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .getTodosLosCursos();

        call.enqueue(new Callback<List<Curso>>() {
            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if(response.code()==200){
                    for(int i=0; i<response.body().size(); i++){
                        Log.d("TAG1", "Nombre Curso: " + response.body().get(i).getNombre()
                        + "Codigo Profesor: " + response.body().get(i).getProfesorId());
                    }
                }else if(response.code()==404){
                    Log.d("TAG1", "No hay cursos");
                }
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {

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
                    Log.d("TAG1", "curso creado correctamente");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

}
