package com.goodsy.goodsyadmin.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.goodsy.goodsyadmin.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btnLogin;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    TextView forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.pass);
        btnLogin = findViewById(R.id.btn_login);
        forgotPass = findViewById(R.id.forget_pass);

        firebaseAuth = FirebaseAuth.getInstance();

        forgotPass.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class)));

        btnLogin.setOnClickListener(view -> {
            String email = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                username.setError("Email is Required");
            } else if (TextUtils.isEmpty(pass)) {
                password.setError("Password is Required");
            } else {
                showProgress();
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid().equals("08dYbJiHWBWLG3wUdswjNVQKAls1")) {
                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Please login with admin user", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String errorMessage = Objects.requireNonNull(task.getException()).getMessage();
                        Toast.makeText(LoginActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                });
            }
        });
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.process_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(
                android.R.color.transparent
        );
    }
}