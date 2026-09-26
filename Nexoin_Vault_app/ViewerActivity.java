package com.example.nexoinvaulit;


import android.net.Uri; import android.os.Bundle; import android.view.View; import android.widget.*; import androidx.appcompat.app.AppCompatActivity; import androidx.core.content.FileProvider; import java.io.File;
public class ViewerActivity extends AppCompatActivity {
    @Override public void onCreate(Bundle b){super.onCreate(b);
        setContentView(R.layout.activity_viewer);
        String path=getIntent().getStringExtra("path");
        boolean video=getIntent().getBooleanExtra("video",false);
        Uri uri=FileProvider.getUriForFile(this,getPackageName()+".fileprovider",
                new File(path));
        if(video){VideoView v=findViewById(R.id.video);
            v.setVisibility(View.VISIBLE);v.setVideoURI(uri);
            v.setMediaController(new MediaController(this));
            v.start();}else{ImageView im=findViewById(R.id.image);
            im.setVisibility(View.VISIBLE);im.setImageURI(uri);}}
}
