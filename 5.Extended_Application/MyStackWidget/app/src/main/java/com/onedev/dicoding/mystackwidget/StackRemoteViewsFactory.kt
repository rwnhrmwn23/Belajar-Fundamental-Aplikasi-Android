package com.onedev.dicoding.mystackwidget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf

internal class StackRemoteViewsFactory(private val context: Context): RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<Bitmap>()

    override fun onDataSetChanged() {
        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.darth_vader))
        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.star_wars_logo))
        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.storm_trooper))
        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.starwars))
        mWidgetItems.add(BitmapFactory.decodeResource(context.resources, R.drawable.falcon))
    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])

        val extras = bundleOf(
            ImageBannerWidget.EXTRA_ITEM to position
        )

        val fillIntent = Intent()
        fillIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false

    override fun onCreate() { }

    override fun onDestroy() { }
}