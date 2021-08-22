package com.onedev.dicoding.submission_three.util

import android.app.Activity
import android.content.ContentValues
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.onedev.dicoding.submission_three.R
import com.onedev.dicoding.submission_three.model.ItemUser
import com.onedev.dicoding.submission_three.provider.UserProvider
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
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }

    fun hideActionBar(activity: Activity) {
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }

    fun ImageView.loadImage(url: String) {
        Glide.with(context)
            .load(url)
            .circleCrop()
            .placeholder(R.drawable.ic_baseline_person)
            .into(this)
    }

    fun showSnackBar(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
    }

    fun ContentValues.toItemUser(): ItemUser =
        ItemUser(
            id = getAsInteger(UserProvider.USER_ID),
            avatar_url = getAsString(UserProvider.USER_AVATAR_URL),
            login = getAsString(UserProvider.USER_USERNAME),
        )
}