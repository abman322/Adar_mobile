package com.example.adar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText mLoginEmail, mLoginPassword;
    private Button mSignupButton, mLoginButton;
    private TextView mForgotPasswordButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide(); // Hides the action bar

        mLoginEmail = findViewById(R.id.loginEmail);
        mLoginPassword = findViewById(R.id.loginPassword);
        mSignupButton = findViewById(R.id.signupButton);
        mLoginButton = findViewById(R.id.loginButton);
        mForgotPasswordButton = findViewById(R.id.forgotPasswordButton);

        mAuth = FirebaseAuth.getInstance();

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
            }
        });

        mForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mLoginEmail.getText().toString().trim();
                String password = mLoginPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please input all required fields", Toast.LENGTH_SHORT).show();
                }else{
                    // Login user
                }
            }
        });
    }
}