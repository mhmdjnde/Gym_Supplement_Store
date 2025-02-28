package com.example.mobilesuppproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Register = findViewById(R.id.buttonRegister);
        Intent signUp = new Intent(MainActivity.this, SignUpActivity.class);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signUp);
            }
        });

        Button log = findViewById(R.id.buttonLogin);
        Intent logIn = new Intent(MainActivity.this, LoginActivity.class);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(logIn);
            }
        });
    }
}
