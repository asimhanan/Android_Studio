package com.example.nexoinvaulit;

import android.content.Context;
import android.net.Uri;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class VaultStorageManager {

    private final Context context;

    public VaultStorageManager(Context context) {
        this.context = context;
    }

    public File getVaultRoot() {
        File vaultDir = new File(context.getFilesDir(), "SecureVault");
        if (!vaultDir.exists()) {
            vaultDir.mkdirs();
        }
        return vaultDir;
    }

    // Unifies directory retrieval for subfolders (e.g., "Images", "Videos")
    public File getFolder(String folderName) {
        File targetFolder = new File(getVaultRoot(), folderName);
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }
        return targetFolder;
    }

    public boolean createFolder(String folderName) {
        File newFolder = new File(getVaultRoot(), folderName);
        return !newFolder.exists() && newFolder.mkdirs();
    }

    public List<File> getFolders() {
        List<File> folderList = new ArrayList<>();
        File[] files = getVaultRoot().listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    folderList.add(file);
                }
            }
        }
        return folderList;
    }

    public boolean importUriToFolder(Uri uri, File targetFolder, String filename) {
        File destinationFile = new File(targetFolder, filename);
        if (destinationFile.exists()) {
            destinationFile = new File(targetFolder, System.currentTimeMillis() + "_" + filename);
        }

        try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
             OutputStream outputStream = new FileOutputStream(destinationFile)) {

            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}