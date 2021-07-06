package com.onedev.dicoding.submission_one.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
    val avatar: Int,
    val company: String,
    val follower: String,
    val following: String,
    val location: String,
    val name: String,
    val repository: String,
    val username: String
): Parcelable