package com.onedev.dicoding.submission_three.util

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.snackbar.Snackbar
import com.onedev.dicoding.submission_three.R
import com.onedev.dicoding.submission_three.model.ItemUser
import com.onedev.dicoding.submission_three.provider.UserProvider
import com.onedev.dicoding.submission_three.widget.FavoriteStackWidget
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

    fun ContentValues.toItemUser(): ItemUser =
        ItemUser(
            id = getAsInteger(USER_ID),
            avatar_url = getAsString(USER_AVATAR_URL),
            login = getAsString(USER_USERNAME),
        )

    fun ImageView.loadImage(url: String) {
        Glide.with(context)
            .load(url)
            .circleCrop()
            .placeholder(R.drawable.ic_baseline_person)
            .into(this)
    }

    fun String.toBitmap(context: Context): Bitmap {
        var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.img_picture)

        val option = RequestOptions()
            .error(R.drawable.img_picture)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        try {
            Glide.with(context)
                .setDefaultRequestOptions(option)
                .asBitmap()
                .load(this)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onLoadCleared(placeholder: Drawable?) {}
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        bitmap = resource
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }

    fun refreshWidget(context: Context) {
        FavoriteStackWidget.sendRefreshBroadcast(context)
    }

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


    fun showSnackBar(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
    }
}