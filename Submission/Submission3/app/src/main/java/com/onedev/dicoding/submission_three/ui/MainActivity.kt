package com.onedev.dicoding.submission_three.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.onedev.dicoding.submission_three.R
import com.onedev.dicoding.submission_three.databinding.ActivityMainBinding
import com.onedev.dicoding.submission_three.helper.LocaleHelper.getPersistedLanguage
import com.onedev.dicoding.submission_three.helper.LocaleHelper.setLocale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val language = getPersistedLanguage(this@MainActivity, "in")
        setLocale(this@MainActivity, language)

        val navController = findNavController(R.id.fragmentContainerView)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        supportFragmentManager.apply {
            if ((findFragmentById(R.id.main_navigation)?.childFragmentManager?.backStackEntryCount) ?: 0 > 1)
                super.onBackPressed()
            else
                finish()
        }
    }
}