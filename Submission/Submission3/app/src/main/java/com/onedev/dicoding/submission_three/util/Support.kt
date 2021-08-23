package com.onedev.dicoding.submission_three.util

import android.app.Activity
import android.content.ContentValues
import android.net.Uri
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
    const val USER_ID = "id"
    const val USER_USERNAME = "login"
    const val USER_AVATAR_URL = "avatar_url"

    const val AUTHORITY = "com.onedev.dicoding.submission_three"
    const val TABLE_NAME = "tb_favorite"
    private const val SCHEME = "content"

    val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TABLE_NAME)
        .build()

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
            id = getAsInteger(USER_ID),
            avatar_url = getAsString(USER_AVATAR_URL),
            login = getAsString(USER_USERNAME),
        )
}