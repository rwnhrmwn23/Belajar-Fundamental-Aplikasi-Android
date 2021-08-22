package com.onedev.dicoding.submission_three.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.onedev.dicoding.submission_three.dao.FavoriteDao
import com.onedev.dicoding.submission_three.database.FavoriteDatabase
import com.onedev.dicoding.submission_three.util.Support.toItemUser

class UserProvider : ContentProvider() {

    companion object {
        private const val FAVORITE = 1
        private const val FAVORITE_USER_ID = 2

        private const val AUTHORITY = "com.onedev.dicoding.submission_three"
        private const val SCHEME = "content"
        private const val TABLE_NAME = "tb_favorite"

        const val USER_ID = "id"
        const val USER_USERNAME = "login"
        const val USER_AVATAR_URL = "avatar_url"

        private lateinit var favoriteDao: FavoriteDao

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            // content://com.onedev.dicoding.submission_three/tb_favorite
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)

            // content://com.onedev.dicoding.submission_three/tb_favorite/username
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", FAVORITE_USER_ID)
        }
    }

    override fun onCreate(): Boolean {
        favoriteDao = FavoriteDatabase.getDatabase(context as Context).favoriteDao()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAVORITE -> favoriteDao.selectAllFavorite()
            FAVORITE_USER_ID -> uri.lastPathSegment?.toInt()?.let { favoriteDao.selectSpecificFavorite(it) }
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (FAVORITE) {
            sUriMatcher.match(uri) -> favoriteDao.addFavorite(values?.toItemUser())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (sUriMatcher.match(uri)) {
            FAVORITE -> favoriteDao.deleteAllFavorite()
            FAVORITE_USER_ID -> uri.lastPathSegment?.toInt().let { favoriteDao.deleteFavorite(it) }
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}