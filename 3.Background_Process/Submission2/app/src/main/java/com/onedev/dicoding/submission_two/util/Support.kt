package com.onedev.dicoding.submission_two.util

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

object Support {
    fun replaceRepo(text: String): String {
        return text
            .replace("Repository", "")
            .replace("Gudang", "")
            .trim()
    }

    fun replaceSymbol(text: String): String {
        return text
            .substring(5)
            .trim()
    }

    fun convertToDec(value: Double): String {
        val dec = DecimalFormat("#,###")
        return dec.format(value)
    }

    fun showActionBar(activity: Activity) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    fun hideActionBar(activity: Activity) {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }
}