package com.androiddesdecero.retrofitudemy.api;

import com.androiddesdecero.retrofitudemy.model.Profesor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by albertopalomarrobledo on 1/12/18.
 */

public interface WebServiceApi {

    @POST("/api/sign_up")
    Call<Void> registrarProfesor(@Body Profesor profesor);

    @POST("/api/login")
    Call<List<Profesor>> login(@Body Profesor profesor);
}
