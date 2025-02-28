package com.example.mobilesuppproject;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText edEmail;
    private EditText edPassword;
    private Button b;
    private UserSecurityDao udao;

    // Single-thread executor for background operations
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the database and DAO
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        udao = db.userSecurityDao();

        b = findViewById(R.id.loginButton);
        edEmail = findViewById(R.id.username);
        edPassword = findViewById(R.id.password);

        TextView tvErrorMessage = findViewById(R.id.tvErrorMessage);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lemail = edEmail.getText().toString().trim();
                String lpassword = edPassword.getText().toString().trim();

                // Basic input check
                if (lemail.isEmpty() || lpassword.isEmpty()) {
                    tvErrorMessage.setText("Please fill in all fields");
                    tvErrorMessage.setVisibility(View.VISIBLE);
                    return;
                }

                // Run the query in background
                executorService.execute(() -> {
                    // This now works because checkAccountEmail() returns an int
                    int count = udao.checkAccountEmail(lemail, lpassword);

                    // Switch back to UI thread to update the UI
                    runOnUiThread(() -> {
                        if (count > 0) {
                            // For simplicity, we're only checking if the email exists
                            // (You probably also want to confirm the password here!)
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            i.putExtra("userEmail", lemail);
                            startActivity(i);
                        } else {
                            tvErrorMessage.setText("Invalid email or password");
                            tvErrorMessage.setVisibility(View.VISIBLE);
                        }
                    });
                });
            }
        });
    }
}

