package com.onedev.dicoding.submission_two.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.onedev.dicoding.submission_two.adapter.TabFollowerAndFollowingAdapter
import com.onedev.dicoding.submission_two.databinding.FragmentFollowersFollowingBinding
import com.onedev.dicoding.submission_two.util.Support

class FollowersFollowingFragment : Fragment() {
    private var _binding: FragmentFollowersFollowingBinding? = null
    private val binding get() = _binding
    private val args: FollowersFollowingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersFollowingBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Support.hideActionBar(requireActivity())

        binding?.tvToolbarTitle?.text = args.username
        binding?.toolbar?.setNavigationOnClickListener {
            val toDetailHomeFragment = FollowersFollowingFragmentDirections.actionFollowersFollowingFragmentToDetailHomeFragment()
            toDetailHomeFragment.username = args.username
            it.findNavController().navigate(toDetailHomeFragment)
        }

        selectPage(args.pageIndex)
    }

    private fun selectPage(pageIndex: Int) {
        binding?.viewPager?.adapter = TabFollowerAndFollowingAdapter(requireContext(), childFragmentManager)
        binding?.tabs?.setupWithViewPager(binding?.viewPager)
        binding?.tabs?.setScrollPosition(pageIndex, 0F, true)
        binding?.viewPager?.currentItem = pageIndex
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}