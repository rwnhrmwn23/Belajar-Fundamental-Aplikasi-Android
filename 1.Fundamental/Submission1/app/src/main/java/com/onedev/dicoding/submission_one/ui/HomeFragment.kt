package com.onedev.dicoding.submission_one.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.onedev.dicoding.submission_one.adapter.UserAdapter
import com.onedev.dicoding.submission_one.databinding.FragmentHomeBinding
import com.onedev.dicoding.submission_one.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserAdapter()
        binding.rvUser.setHasFixedSize(true)

        loadData()
    }

    private fun loadData() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.loadUsersData()

        viewModel.showProgress.observe(viewLifecycleOwner, {
            if (it == true)
                binding.shimmerViewContainer.startShimmerAnimation()
            else
                binding.shimmerViewContainer.stopShimmerAnimation()
        })

        viewModel.usersData.observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setListUser(it)
                binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
                binding.rvUser.adapter = adapter
                binding.rvUser.visibility = View.VISIBLE
                binding.shimmerViewContainer.visibility = View.GONE
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