package com.onedev.dicoding.mynotesapp.helper

import android.view.View

class CustomOnItemClickListener(private val position: Int, private val onItemClickCallback: OnItemClickCallback): View.OnClickListener {

    interface OnItemClickCallback {
        fun onItemClicked(view: View, position: Int)
    }

    override fun onClick(view: View) {
        onItemClickCallback.onItemClicked(view, position)
    }
}