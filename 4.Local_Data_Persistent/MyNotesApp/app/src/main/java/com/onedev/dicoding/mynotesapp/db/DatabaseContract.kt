package com.onedev.dicoding.mynotesapp.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    internal class NoteColumns: BaseColumns {
        companion object {
            const val TABLE_NAME = "note"
            const val _ID = "id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val DATE = "date"

            const val AUTHORITY = "com.onedev.dicoding.mynotesapp"
            const val SCHEME = "content"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

}