package com.example.krishivarsa.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "KrishiVarsaSession";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_ROLE = "role";
    private static final String KEY_LOGIN = "isLoggedIn";

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Save login session
    public void saveLogin(String token, String role) {
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_ROLE, role);
        editor.putBoolean(KEY_LOGIN, true);
        editor.apply();
    }

    // Logout
    public void logout() {
        editor.clear();
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_LOGIN, false);
    }

    public String getRole() {
        return pref.getString(KEY_ROLE, "");
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, "");
    }
}
