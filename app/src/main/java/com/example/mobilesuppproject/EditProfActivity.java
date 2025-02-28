package com.example.mobilesuppproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditProfActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etAge, etPhone, etEmail;
    private EditText etCurrentPassword, etNewPassword, etConfirmPassword;
    private Button btnSaveChanges;
    private TextView errortv;

    private UserSecurityDao udao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_prof);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editp), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent() != null && getIntent().hasExtra("userEmail")) {
            userEmail = getIntent().getStringExtra("userEmail");
        }

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        udao = db.userSecurityDao();

        etFirstName       = findViewById(R.id.etFirstName);
        etLastName        = findViewById(R.id.etLastName);
        etAge             = findViewById(R.id.etAge);
        etPhone           = findViewById(R.id.etPhone);
        etEmail           = findViewById(R.id.etEmail);
        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword     = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmNewPassword);
        errortv           = findViewById(R.id.errorTextView);

        btnSaveChanges    = findViewById(R.id.btnSaveChanges);

        if (!TextUtils.isEmpty(userEmail)) {
            executor.execute(() -> {
                UserSecurity user = udao.getUserByEmail(userEmail);

                if (user != null) {
                    runOnUiThread(() -> {
                        etFirstName.setText(user.getFName());
                        etLastName.setText(user.getLName());
                        etAge.setText(String.valueOf(user.getAge()));
                        etPhone.setText(user.getPhoneNb());
                        etEmail.setText(user.getEmail());
                    });
                }
            });
        }

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName   = etFirstName.getText().toString().trim();
                String lastName    = etLastName.getText().toString().trim();
                String ageStr      = etAge.getText().toString().trim();
                String phone       = etPhone.getText().toString().trim();
                String currentPass = etCurrentPassword.getText().toString().trim();
                String newPass     = etNewPassword.getText().toString().trim();
                String confirmPass = etConfirmPassword.getText().toString().trim();
                String finalEmail  = etEmail.getText().toString().trim();

                if (TextUtils.isEmpty(firstName) ||
                        TextUtils.isEmpty(lastName)  ||
                        TextUtils.isEmpty(ageStr)    ||
                        TextUtils.isEmpty(phone)     ||
                        TextUtils.isEmpty(finalEmail)) {

                    Toast.makeText(EditProfActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(finalEmail).matches()) {
                    errortv.setText("Invalid email");
                    return;
                }

                if (!TextUtils.isEmpty(newPass) || !TextUtils.isEmpty(confirmPass)) {
                    if (!newPass.equals(confirmPass)) {
                        Toast.makeText(EditProfActivity.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(currentPass)) {
                        Toast.makeText(EditProfActivity.this, "Please enter your current password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (newPass.length() < 8) {
                        errortv.setText("Password at least 8 char");
                        return;
                    }
                }

                executor.execute(() -> {
                    UserSecurity user = udao.getUserByEmail(userEmail);
                    if (user == null) {
                        runOnUiThread(() -> {
                            Toast.makeText(EditProfActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                        });
                        return;
                    }
                    if (!TextUtils.isEmpty(newPass)) {
                        if (!user.getPassword().equals(currentPass)) {
                            runOnUiThread(() -> {
                                Toast.makeText(EditProfActivity.this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                            });
                            return;
                        }
                        user.setPassword(newPass);
                    }

                    user.setFName(firstName);
                    user.setLName(lastName);
                    try {
                        user.setAge(Integer.parseInt(ageStr));
                    } catch (NumberFormatException e) {
                        user.setAge(0);
                    }
                    user.setPhoneNb(phone);
                    user.setEmail(finalEmail);
                    udao.updateUser(user);
                    runOnUiThread(() -> {
                        Toast.makeText(EditProfActivity.this, "Profile updated!", Toast.LENGTH_SHORT).show();
                    });
                    Intent r = new Intent();
                    r.putExtra("newEmail", finalEmail);
                    setResult(RESULT_OK, r);
                    finish();
                });
            }
        });
    }
}
