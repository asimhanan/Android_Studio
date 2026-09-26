package com.example.nexoinvaulit;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.io.*;

public class folder extends AppCompatActivity {
    private String folderName;
    private LinearLayout list;
    private final int PICK = 10;
    private boolean videoMode;
    private VaultStorageManager vaultStorageManager;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_folder);

        vaultStorageManager = new VaultStorageManager(this);
        folderName = getIntent().getStringExtra("folder");

        ((TextView) findViewById(R.id.title)).setText(folderName);
        list = findViewById(R.id.list);
        videoMode = "Videos".equalsIgnoreCase(folderName);

        findViewById(R.id.importButton).setOnClickListener(v -> pick(false));
        findViewById(R.id.pasteButton).setOnClickListener(v -> paste());
        load();
    }

    private File dir() {
        // Uses the unified storage path: internal_storage/SecureVault/[folderName]
        return vaultStorageManager.getFolder(folderName);
    }

    private void pick(boolean unused) {
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        i.setType(videoMode ? "video/*" : "image/*");
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(i, PICK);
    }

    private void paste() {
        ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (!cm.hasPrimaryClip()) {
            Toast.makeText(this, "Clipboard is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        ClipData c = cm.getPrimaryClip();
        for (int x = 0; x < c.getItemCount(); x++) {
            Uri u = c.getItemAt(x).getUri();
            if (u != null) copyUri(u);
        }
        load();
    }

    @Override
    protected void onActivityResult(int r, int code, Intent data) {
        super.onActivityResult(r, code, data);
        if (r == PICK && code == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                for (int x = 0; x < data.getClipData().getItemCount(); x++) {
                    copyUri(data.getClipData().getItemAt(x).getUri());
                }
            } else if (data.getData() != null) {
                copyUri(data.getData());
            }
            load();
        }
    }

    private void copyUri(Uri uri) {
        String name = "file_" + System.currentTimeMillis();
        Cursor cur = getContentResolver().query(uri, null, null, null, null);
        if (cur != null) {
            int n = cur.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (cur.moveToFirst() && n >= 0) name = cur.getString(n);
            cur.close();
        }

        boolean success = vaultStorageManager.importUriToFolder(uri, dir(), name);
        if (!success) {
            Toast.makeText(this, "Could not copy file", Toast.LENGTH_SHORT).show();
        }
    }

    private void load() {
        list.removeAllViews();
        File[] fs = dir().listFiles();
        if (fs == null || fs.length == 0) {
            TextView t = new TextView(this);
            t.setText("No files yet. Import from Gallery or paste a copied file.");
            t.setPadding(16, 32, 16, 16);
            list.addView(t);
            return;
        }
        for (File f : fs) {
            Button row = new Button(this);
            row.setText(f.getName());
            row.setAllCaps(false);
            row.setOnClickListener(v -> {
                Intent i = new Intent(this, ViewerActivity.class);
                i.putExtra("path", f.getAbsolutePath());
                i.putExtra("video", videoMode);
                startActivity(i);
            });
            list.addView(row);
        }
    }
}