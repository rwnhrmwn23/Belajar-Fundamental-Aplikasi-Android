package com.onedev.dicoding.submission_three.provider

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.onedev.dicoding.submission_three.dao.FavoriteDao
import com.onedev.dicoding.submission_three.database.FavoriteDatabase
import com.onedev.dicoding.submission_three.util.Support.AUTHORITY
import com.onedev.dicoding.submission_three.util.Support.CONTENT_URI
import com.onedev.dicoding.submission_three.util.Support.TABLE_NAME
import com.onedev.dicoding.submission_three.util.Support.toItemUser
import com.onedev.dicoding.submission_three.widget.FavoriteStackWidget

class UserProvider : ContentProvider() {

//    val application: Application

    companion object {
        private const val FAVORITE = 1
        private const val FAVORITE_USER_ID = 2
        private lateinit var favoriteDao: FavoriteDao
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVORITE)
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