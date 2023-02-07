package com.example.adar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    private EditText mSignupEmail, mSignupPassword;
    private Button mSignupButton;
    private TextView mSignupToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide(); // Hides the action bar

        mSignupButton = findViewById(R.id.signupButton);
        mSignupEmail = findViewById(R.id.signupEmail);
        mSignupPassword = findViewById(R.id.signupPassword);
        mSignupToLogin = findViewById(R.id.signupToLogin);

        mSignupToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mSignupEmail.getText().toString().trim();
                String password = mSignupPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please input all required fields", Toast.LENGTH_SHORT).show();
                }else if(password.length() < 8){
                    Toast.makeText(getApplicationContext(), "Password must be at least eight digits long", Toast.LENGTH_SHORT).show();
                }else{
                    // Register to firebase
                }
            }
        });
    }
}