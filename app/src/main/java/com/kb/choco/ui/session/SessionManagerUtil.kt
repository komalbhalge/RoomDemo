package com.kb.choco.ui.session

import android.content.Context
import javax.inject.Singleton

@Singleton
object SessionManagerUtil {

    private const val SESSION_PREFERENCES = "com.kb.choco.ui.session_manager.SESSION_PREFERENCES"
    private const val SESSION_TOKEN = "com.kb.choco.ui.session_manager.SESSION_TOKEN"
    private const val IS_LOGGED_IN = "is_user_loggedin"

    fun startUserSession(context: Context, token: String) {
        val editor = context.getSharedPreferences(SESSION_PREFERENCES, 0).edit()
        editor.putString(SESSION_TOKEN, token)
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.apply()
    }

    fun endUserSession(context: Context) {
        val editor = context.getSharedPreferences(SESSION_PREFERENCES, 0).edit()
        editor.clear()
        editor.apply()
    }

    fun getToken(context: Context): String? {
        return context.getSharedPreferences(SESSION_PREFERENCES, 0).getString(SESSION_TOKEN, null)
    }
    fun isSessionValid(context: Context): Boolean {
        return context.getSharedPreferences(SESSION_PREFERENCES, 0).getBoolean(IS_LOGGED_IN, false)
    }
}