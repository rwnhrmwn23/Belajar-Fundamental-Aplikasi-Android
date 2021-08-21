package com.onedev.dicoding.submission_three.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tb_favorite")
data class ItemUser(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val avatar_url: String,
    val login: String
): Parcelable