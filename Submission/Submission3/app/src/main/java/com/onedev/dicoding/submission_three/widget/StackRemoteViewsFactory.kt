package com.onedev.dicoding.submission_three.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.onedev.dicoding.submission_three.R
import com.onedev.dicoding.submission_three.model.ItemUser
import com.onedev.dicoding.submission_three.util.MappingHelper
import com.onedev.dicoding.submission_three.util.Support
import com.onedev.dicoding.submission_three.util.Support.toBitmap

internal class StackRemoteViewsFactory(private val context: Context): RemoteViewsService.RemoteViewsFactory {

    private var mWidgetItems = ArrayList<ItemUser>()
    private var cursor: Cursor? = null

    override fun onDataSetChanged() {
        cursor?.close()

        val identityToken = Binder.clearCallingIdentity()

        cursor = context.contentResolver?.query(Support.CONTENT_URI, null, null, null, null)
        val listFavorite = MappingHelper.mapCursorToArrayList(cursor)
        mWidgetItems.clear()
        mWidgetItems.addAll(listFavorite)

        Binder.restoreCallingIdentity(identityToken)
    }
    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.list_widget_favorite)
        if (mWidgetItems.isNotEmpty()) {

            mWidgetItems[position].apply {
                rv.setImageViewBitmap(R.id.img_widget_avatar, avatar_url?.toBitmap(context))
                rv.setTextViewText(R.id.tv_widget_username, login)
            }
        }

        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    override fun onDestroy() {
        cursor?.close()
        mWidgetItems.clear()
    }

    override fun onCreate() { }
}