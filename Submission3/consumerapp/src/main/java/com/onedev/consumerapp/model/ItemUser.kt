package com.onedev.consumerapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tb_favorite")
data class ItemUser(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val avatar_url: String? = null,
    val login: String? = null
): Parcelable