package com.example.nexoinvaulit;


import android.content.Intent; import android.os.Bundle; import android.widget.*; import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override public void onCreate(Bundle b){super.onCreate(b);setContentView(R.layout.activity_main);
        findViewById(R.id.imagesFolder).setOnClickListener
                (v->open("Images"));
        findViewById(R.id.videosFolder).setOnClickListener
                (v->open("Videos"));
        findViewById(R.id.changePassword).setOnClickListener
                (v->startActivity(new Intent(this,ChangePasswordActivity.class)));
    }
    private void open(String folder){Intent i=new Intent(this,folder.class);
        i.putExtra("folder",folder);startActivity(i);}
    @Override protected void onResume(){super.onResume();}
}
