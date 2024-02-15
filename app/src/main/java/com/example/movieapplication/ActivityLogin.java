package com.example.movieapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityLogin extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, createAcount;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);



        firebaseAuth = FirebaseAuth.getInstance();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }

        emailEditText = findViewById(R.id.inputUsername);
        passwordEditText = findViewById(R.id.inputPassword);
        loginButton = findViewById(R.id.buttonlogin);
        createAcount = findViewById(R.id.newacount);

        createAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityLogin.this, RegisterActivity.class));
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.equals("") || password.equals("")) {
                    Toast.makeText(ActivityLogin.this, "Password or Email is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(ActivityLogin.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Login success, go to the main activity or display a success message
                                        Toast.makeText(ActivityLogin.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ActivityLogin.this, MainActivity.class));
                                        finish();
                                    } else {
                                        // If login fails, display a message to the user.
                                        Toast.makeText(ActivityLogin.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }


            }
        });
    }
}
