package com.onedev.dicoding.submission_one.util

import java.text.DecimalFormat

object Support {
    fun convertToDec(value: Double): String {
        val dec = DecimalFormat("#,###")
        return dec.format(value)
    }
}