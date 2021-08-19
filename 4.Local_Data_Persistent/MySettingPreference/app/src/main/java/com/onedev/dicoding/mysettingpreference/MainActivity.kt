package com.onedev.dicoding.mysettingpreference

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.setting_holder, MyPreferenceFragment())
            .commit()

//        Get Value From Preference Setting
//        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
//        val name = prefs.getString(getString(R.string.key_name), getString(R.string.nothing))
//        Toast.makeText(this, "$name", Toast.LENGTH_SHORT).show()
    }
}