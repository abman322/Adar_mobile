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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText mRecoveryEmail;
    private Button mRecoveryEmailButton;
    private TextView mGotoLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().hide(); // Hides the action bar

        mRecoveryEmail = findViewById(R.id.recoveryEmail);
        mRecoveryEmailButton = findViewById(R.id.recoveryEmailButton);
        mGotoLogin = findViewById(R.id.gotoLogin);

        mAuth = FirebaseAuth.getInstance();

        mGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mRecoveryEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mRecoveryEmail.getText().toString().trim();
                if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Check your email to recover password", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                            }else{
                                Toast.makeText(getApplicationContext(), "Unable to send recovery email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}