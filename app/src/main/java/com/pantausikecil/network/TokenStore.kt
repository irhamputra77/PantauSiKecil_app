package com.pantausikecil.network

import android.content.Context

class TokenStore(context: Context) {
    private val prefs = context.getSharedPreferences("pantausikecil_session", Context.MODE_PRIVATE)

    var token: String?
        get() = prefs.getString(KEY_TOKEN, null)
        set(value) {
            prefs.edit().putString(KEY_TOKEN, value).apply()
        }

    var userId: String?
        get() = prefs.getString(KEY_USER_ID, null)
        set(value) {
            prefs.edit().putString(KEY_USER_ID, value).apply()
        }

    var userName: String?
        get() = prefs.getString(KEY_USER_NAME, null)
        set(value) {
            prefs.edit().putString(KEY_USER_NAME, value).apply()
        }

    var userEmail: String?
        get() = prefs.getString(KEY_USER_EMAIL, null)
        set(value) {
            prefs.edit().putString(KEY_USER_EMAIL, value).apply()
        }

    var role: String?
        get() = prefs.getString(KEY_ROLE, null)
        set(value) {
            prefs.edit().putString(KEY_ROLE, value).apply()
        }

    var posyanduId: String?
        get() = prefs.getString(KEY_POSYANDU_ID, null)
        set(value) {
            prefs.edit().putString(KEY_POSYANDU_ID, value).apply()
        }

    fun saveLogin(token: String, user: UserDto) {
        prefs.edit()
            .putString(KEY_TOKEN, token)
            .putString(KEY_USER_ID, user.userId)
            .putString(KEY_USER_NAME, user.nama)
            .putString(KEY_USER_EMAIL, user.email)
            .putString(KEY_ROLE, user.role)
            .putString(KEY_POSYANDU_ID, user.posyanduId)
            .apply()
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val KEY_TOKEN = "token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_ROLE = "role"
        private const val KEY_POSYANDU_ID = "posyandu_id"
    }
}
