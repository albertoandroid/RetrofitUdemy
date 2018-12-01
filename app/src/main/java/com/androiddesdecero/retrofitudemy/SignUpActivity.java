package com.androiddesdecero.retrofitudemy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androiddesdecero.retrofitudemy.model.Profesor;

public class SignUpActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etPassword;
    private EditText etEmail;

    private Button btSignUp;
    private TextView tvLogin;

    private Profesor profesor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setUpView();
    }

    private void setUpView(){
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btSignUp = findViewById(R.id.btSignUp);
        tvLogin = findViewById(R.id.tvLogin);

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignUp();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void userSignUp(){
        String email = etEmail.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

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

        if(password.isEmpty()){
            etPassword.setError(getResources().getString(R.string.password_error));
            etPassword.requestFocus();
            return;
        }

        if(password.length()<4){
            etPassword.setError(getResources().getString(R.string.password_error_less_than));
            etPassword.requestFocus();
            return;
        }

        profesor = new Profesor();
        profesor.setNombre(name);
        profesor.setEmail(email);
        profesor.setPassword(password);
        crearProfesor();
    }

    private void crearProfesor(){

    }
}
