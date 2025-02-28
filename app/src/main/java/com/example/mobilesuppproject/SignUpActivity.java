package com.example.mobilesuppproject;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUpActivity extends AppCompatActivity {

    private UserSecurityDao udao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private EditText edFname;
    private EditText edLname;
    private EditText edAge;
    private EditText edPhone;
    private EditText edEmail;
    private EditText edPassword;
    TextView errortv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle("Gains Lab");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the database and DAO
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        udao = db.userSecurityDao();

        Button b = findViewById(R.id.buttonSignUp);
        edFname = findViewById(R.id.editTextFirstName);
        edLname = findViewById(R.id.editTextLastName);
        edAge = findViewById(R.id.editTextAge);
        edPhone = findViewById(R.id.editTextPhone);
        edEmail = findViewById(R.id.editTextEmail);
        edPassword = findViewById(R.id.editTextPassword);
        errortv = findViewById(R.id.errorSignUp);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = edFname.getText().toString().trim();
                String lname = edLname.getText().toString().trim();
                String age = edAge.getText().toString().trim();
                String phone = edPhone.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                if (fname.isEmpty() || lname.isEmpty() || age.isEmpty()
                        || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    errortv.setText("Please fill in all fields");
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    errortv.setText("Invalid email");
                    return;
                }

                if (password.length() < 8) {
                    errortv.setText("Password at least 8 char");
                    return;
                }

                executorService.execute(() -> {
                    udao.addUser(new UserSecurity(email, password, fname, lname, parseInt(age), phone));
                });

                Intent i = new Intent(SignUpActivity.this, HomeActivity.class);
                i.putExtra("userEmail", email);
                startActivity(i);
            }
        });
    }
}
