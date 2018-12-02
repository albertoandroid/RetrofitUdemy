package com.androiddesdecero.retrofitudemy.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androiddesdecero.retrofitudemy.R;
import com.androiddesdecero.retrofitudemy.api.WebService;
import com.androiddesdecero.retrofitudemy.api.WebServiceApi;
import com.androiddesdecero.retrofitudemy.model.Profesor;
import com.androiddesdecero.retrofitudemy.shared_pref.SharedPrefManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

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
                updateProfesor();
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
        if(profesor.getFoto()!=null){
            ivProfesor.setImageBitmap(stringToImage(profesor.getFoto()));
        }
        ivProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarImagenPerfil();
            }
        });

        tvCurso = findViewById(R.id.tvCursos);
        tvCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CursoActivity.class));
            }
        });
    }

    private void obtenerPresores(){
        Call<List<Profesor>> call = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .getProfesores();

        call.enqueue(new Callback<List<Profesor>>() {
            @Override
            public void onResponse(Call<List<Profesor>> call, Response<List<Profesor>> response) {
                if(response.code()==200){
                    for(int i=0; i<response.body().size(); i++){
                        Log.d("TAG1", "Nombre: " + response.body().get(i).getNombre());
                    }
                }else if(response.code()==404){
                    Log.d("TAG1", "No hay profesores");
                }
            }

            @Override
            public void onFailure(Call<List<Profesor>> call, Throwable t) {

            }
        });
    }

    private void cambiarImagenPerfil(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                if(bitmap != null){
                    ivProfesor.setImageBitmap(bitmap);
                    profesor.setFoto(imageToString());
                }else{
                    profesor.setFoto("");
                }
            }catch (IOException e){

            }
        }
    }

    private Bitmap stringToImage(String encondedString){
        try{
            byte[] encodeByte = Base64.decode(encondedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch (Exception e){
            return null;
        }
    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void updateProfesor(){
        String email = etEmail.getText().toString().trim();
        String name = etName.getText().toString().trim();

        if(name.isEmpty()){
            etName.setError(getResources().getString(R.string.name_error));
            etName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            etEmail.setError(getResources().getString(R.string.email_error));
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError(getResources().getString(R.string.email_doesnt_match));
            etEmail.requestFocus();
            return;
        }

        profesor.setNombre(name);
        profesor.setEmail(email);

        Call<Profesor> call = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .update(profesor);
        call.enqueue(new Callback<Profesor>() {
            @Override
            public void onResponse(Call<Profesor> call, Response<Profesor> response) {
                if(response.code() == 200){
                    Log.d("TAG1", "Usuario actualizado correctamente");
                    SharedPrefManager.getInstance(getApplicationContext())
                            .saveProfesor(response.body());
                }else if(response.code()==400){
                    Log.d("TAG1", "Usuario no existe");
                }else{
                    Log.d("TAG1", "Error indeterminado");
                }
            }

            @Override
            public void onFailure(Call<Profesor> call, Throwable t) {

            }
        });
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
