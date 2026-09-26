package com.example.nexoinvaulit;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SetupActivity extends AppCompatActivity {

    private EditText password;
    private EditText confirmPassword;
    private Button actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_password);

        TextView title = findViewById(R.id.title);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm);
        actionButton = findViewById(R.id.action);

        title.setText("Create app password");
        actionButton.setText("Create password");

        actionButton.setOnClickListener(view -> createPassword());
    }

    private void createPassword() {
        String passwordText = password.getText().toString();
        String confirmText = confirmPassword.getText().toString();

        if (passwordText.length() < 6) {
            password.setError("Use at least 6 characters");
            return;
        }

        if (!passwordText.equals(confirmText)) {
            confirmPassword.setError("Passwords do not match");
            return;
        }

        try {
            PasswordUtils.setPassword(this, passwordText);

            Toast.makeText(
                    this,
                    "Password created successfully",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        } catch (Exception exception) {
            Toast.makeText(
                    this,
                    "Could not save password",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}

