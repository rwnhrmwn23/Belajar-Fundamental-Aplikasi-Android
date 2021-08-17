package com.onedev.dicoding.submission_two.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.onedev.dicoding.submission_two.R
import com.onedev.dicoding.submission_two.adapter.TabFollowerAndFollowingAdapter
import com.onedev.dicoding.submission_two.databinding.FragmentFollowersFollowingBinding
import com.onedev.dicoding.submission_two.util.Support

class FollowersFollowingFragment : Fragment() {
    private var _binding: FragmentFollowersFollowingBinding? = null
    private val binding get() = _binding!!
    private val args: FollowersFollowingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Support.hideActionBar(requireActivity())

        binding.tvToolbarTitle.text = args.username
        binding.toolbar.setNavigationOnClickListener {
            it.findNavController().navigate(R.id.action_followersFollowingFragment_to_detailHomeFragment)
        }

        val followerAndFollowingAdapter = TabFollowerAndFollowingAdapter(requireActivity() as AppCompatActivity)
        binding.viewPager.adapter = followerAndFollowingAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following,
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}