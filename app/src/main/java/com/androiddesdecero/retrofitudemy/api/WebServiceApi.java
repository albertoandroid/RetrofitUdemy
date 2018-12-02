package com.androiddesdecero.retrofitudemy.api;

import com.androiddesdecero.retrofitudemy.model.Curso;
import com.androiddesdecero.retrofitudemy.model.Profesor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by albertopalomarrobledo on 1/12/18.
 */

public interface WebServiceApi {

    /*
    PROFESORES
     */
    @POST("/api/sign_up")
    Call<Void> registrarProfesor(@Body Profesor profesor);

    @POST("/api/login")
    Call<List<Profesor>> login(@Body Profesor profesor);

    @DELETE("api/delete/{id}")
    Call<Void> deleteById(@Path("id") Long id);

    @PUT("api/update_sql")
    Call<Profesor> update(@Body Profesor profesor);

    @GET("api/profesores")
    Call<List<Profesor>> getProfesores();

    /*
    CURSOS
     */

    @POST("api/crear_curso")
    Call<Void> crearCurso(@Body Curso curso);

    @GET("api/cursos")
    Call<List<Curso>> getTodosLosCursos();
}
