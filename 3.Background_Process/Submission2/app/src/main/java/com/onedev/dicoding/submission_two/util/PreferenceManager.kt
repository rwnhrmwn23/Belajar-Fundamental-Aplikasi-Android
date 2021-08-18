package com.onedev.dicoding.submission_two.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(Constant.KEY_PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String): String {
        return sharedPreferences.getString(key, null)!!
    }
}