package com.onedev.dicoding.submission_two.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.onedev.dicoding.submission_two.adapter.FollowersAdapter
import com.onedev.dicoding.submission_two.databinding.FragmentFollowersBinding
import com.onedev.dicoding.submission_two.util.Constant
import com.onedev.dicoding.submission_two.util.PreferenceManager
import com.onedev.dicoding.submission_two.viewmodel.MainViewModel

class FollowersFragment : Fragment() {
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: FollowersAdapter
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowersAdapter()
        preferenceManager = PreferenceManager(requireContext())
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getFollowers(preferenceManager.getString(Constant.USERNAME))

        viewModel.showProgress.observe(viewLifecycleOwner, {
            if (it) {
                binding.shimmerViewContainer.startShimmerAnimation()
                binding.shimmerViewContainer.visibility = View.VISIBLE
            } else {
                binding.shimmerViewContainer.stopShimmerAnimation()
                binding.shimmerViewContainer.visibility = View.GONE
            }
        })

        viewModel.followerData.observe(viewLifecycleOwner, {
            if (it.size > 0) {
                adapter.setListUser(it)
                binding.rvFollowers.layoutManager = LinearLayoutManager(requireContext())
                binding.rvFollowers.setHasFixedSize(true)
                binding.rvFollowers.adapter = adapter

                binding.rvFollowers.visibility = View.VISIBLE
                binding.llNoDataAvailable.visibility = View.GONE
            } else {
                binding.rvFollowers.visibility = View.GONE
                binding.llNoDataAvailable.visibility = View.VISIBLE
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}