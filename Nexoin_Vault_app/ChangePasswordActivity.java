package com.example.nexoinvaulit;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private Button actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_password);

        TextView title = findViewById(R.id.title);
        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm);
        actionButton = findViewById(R.id.action);

        title.setText("Change password");
        actionButton.setText("Save new password");

        oldPassword.setVisibility(android.view.View.VISIBLE);

        actionButton.setOnClickListener(view -> changePassword());
    }

    private void changePassword() {
        String currentPassword = oldPassword.getText().toString();
        String newPasswordText = newPassword.getText().toString();
        String confirmPasswordText = confirmPassword.getText().toString();

        if (currentPassword.isEmpty()) {
            oldPassword.setError("Enter your current password");
            return;
        }

        if (newPasswordText.length() < 6) {
            newPassword.setError("Use at least 6 characters");
            return;
        }

        if (!newPasswordText.equals(confirmPasswordText)) {
            confirmPassword.setError("Passwords do not match");
            return;
        }

        try {
            if (!PasswordUtils.verify(this, currentPassword)) {
                oldPassword.setError("Current password is incorrect");
                return;
            }

            PasswordUtils.setPassword(this, newPasswordText);

            Toast.makeText(
                    this,
                    "Password changed successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } catch (Exception exception) {
            Toast.makeText(
                    this,
                    "Could not change password",
                    Toast.LENGTH_LONG
            ).show();
        }
    }
}

