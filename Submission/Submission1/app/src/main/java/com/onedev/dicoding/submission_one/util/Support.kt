package com.onedev.dicoding.submission_one.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.onedev.dicoding.submission_one.R
import java.text.DecimalFormat

object Support {
    fun convertToDec(value: Double): String {
        val dec = DecimalFormat("#,###")
        return dec.format(value)
    }

    fun ImageView.loadImage(url: Int) {
        Glide.with(this)
            .load(url)
            .circleCrop()
            .placeholder(R.drawable.ic_baseline_person)
            .into(this)
    }
}