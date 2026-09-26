package com.example.nexoinvaulit;


import android.content.Context;
import android.util.Base64;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordUtils {
    private static final String PREFS = "security";
    private static final int ITERATIONS = 120000;
    private PasswordUtils() {}
    public static boolean isSet(Context c) { return c.getSharedPreferences(PREFS, 0).contains("hash"); }
    public static void setPassword(Context c, String password) throws Exception {
        byte[] salt = new byte[16]; new SecureRandom().nextBytes(salt);
        String hash = derive(password, salt);
        c.getSharedPreferences(PREFS, 0).edit().putString("salt", b64(salt)).putString("hash", hash).apply();
    }
    public static boolean verify(Context c, String password) throws Exception {
        android.content.SharedPreferences p = c.getSharedPreferences(PREFS, 0);
        if (!p.contains("hash")) return false;
        return derive(password, Base64.decode(p.getString("salt", ""), Base64.NO_WRAP)).equals(p.getString("hash", ""));
    }
    private static String derive(String password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, 256);
        byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        return b64(result);
    }
    private static String b64(byte[] v) { return Base64.encodeToString(v, Base64.NO_WRAP); }
}
