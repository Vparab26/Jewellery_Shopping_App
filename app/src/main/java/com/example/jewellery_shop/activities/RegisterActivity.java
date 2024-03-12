package com.example.jewellery_shop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jewellery_shop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN=
            Pattern.compile("^"+
                    "(?=.*[0-9])"+
                    "(?=.*[a-z])"+
                    "(?=.*[A-Z])"+
                    "(?=.*[!@#$%^&+=])"+
                    "(?=\\s+$])"+
                    ".{6,}"+
                    "$");
    EditText name,email,password;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth =FirebaseAuth.getInstance();
        if (auth.getCurrentUser() !=null){
            startActivity(new Intent(RegisterActivity.this, MainActivity2.class));
        }
        name =findViewById(R.id.name);
        email =findViewById(R.id.email);
        password =findViewById(R.id.password);

        sharedPreferences=getSharedPreferences("onBoardingScreen",MODE_PRIVATE);

        boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);
        if (isFirstTime){
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();

            Intent intent = new Intent(RegisterActivity.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }
        getSupportActionBar().hide();
    }
    public void signup(View view) {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Name is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Email is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Password is Empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!PASSWORD_PATTERN.matcher(userPassword).matches()) {
            Toast.makeText(this, "Password is to weak", Toast.LENGTH_SHORT).show();

        }
        if (userPassword.length() < 6) {
            Toast.makeText(this, "Password length must be greater than 6 letter", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity2.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
        }


    public void signin(View view){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}
