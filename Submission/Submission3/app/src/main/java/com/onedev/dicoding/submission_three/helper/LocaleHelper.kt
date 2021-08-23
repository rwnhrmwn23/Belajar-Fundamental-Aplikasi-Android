package com.onedev.dicoding.submission_three.helper

import android.content.Context
import androidx.preference.PreferenceManager
import java.util.*

object LocaleHelper {
    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

    fun getLanguage(context: Context): String? {
        return getPersistedLanguage(context, Locale.getDefault().language)
    }

    fun setLocale(context: Context, language: String?): Context? {
        persist(context, language)
        return language?.let {
            updateResourcesLegacy(context, it)
        }
    }

    fun getPersistedLanguage(context: Context, defaultLanguage: String): String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
    }

    private fun persist(context: Context, language: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}