package com.onedev.dicoding.submission_two.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.onedev.dicoding.submission_two.R
import com.onedev.dicoding.submission_two.ui.FollowersFragment
import com.onedev.dicoding.submission_two.ui.FollowingFragment

class TabFollowerAndFollowingAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val pages = listOf(
        FollowersFragment(),
        FollowingFragment()
    )

    override fun getCount(): Int = pages.size

    override fun getItem(position: Int): Fragment = pages[position]

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> context.resources.getString(R.string.followers)
            else -> context.resources.getString(R.string.following)
        }
    }

}