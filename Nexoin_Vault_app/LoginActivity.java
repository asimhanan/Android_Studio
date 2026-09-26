package com.example.nexoinvaulit;

import android.content.Intent; import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle b) { super.onCreate(b);
        if(!PasswordUtils.isSet(this)){startActivity(new Intent(this,
                SetupActivity.class));finish();return;}
        setContentView(R.layout.activity_login);
        EditText password=findViewById(R.id.password);
        findViewById(R.id.login).setOnClickListener(v ->
        { try { if(PasswordUtils.verify(this,password.getText().toString()))
        {startActivity(new Intent(this,MainActivity.class));finish();}
        else password.setError("Wrong password"); }
        catch(Exception e){password.setError("Unable to verify password");} });
    }
}
