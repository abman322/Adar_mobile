package com.example.adar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {

    private EditText mSignupEmail, mSignupPassword;
    private Button mSignupButton;
    private TextView mSignupToLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide(); // Hides the action bar

        mSignupButton = findViewById(R.id.signupButton);
        mSignupEmail = findViewById(R.id.signupEmail);
        mSignupPassword = findViewById(R.id.signupPassword);
        mSignupToLogin = findViewById(R.id.signupToLogin);

        mAuth = FirebaseAuth.getInstance();

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
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                        sendEmailVerification();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "Verification Email Sent.", Toast.LENGTH_SHORT).show();
                    mAuth.signOut();
                    finish();
                    startActivity(new Intent(Signup.this, MainActivity.class));
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), "Unable to send verification email.", Toast.LENGTH_SHORT).show();
        }
    }
}