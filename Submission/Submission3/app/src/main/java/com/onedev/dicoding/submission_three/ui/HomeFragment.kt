package com.onedev.dicoding.submission_three.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onedev.dicoding.submission_three.R
import com.onedev.dicoding.submission_three.adapter.UserAdapter
import com.onedev.dicoding.submission_three.databinding.FragmentHomeBinding
import com.onedev.dicoding.submission_three.helper.SupportHelper
import com.onedev.dicoding.submission_three.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SupportHelper.showActionBar(requireActivity())

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setHasOptionsMenu(true)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding?.rvUser?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvUser?.adapter = adapter

        viewModel.usersData.observe(viewLifecycleOwner, { data ->
            if (data == null) {
                binding?.rvUser?.visibility = View.GONE
                binding?.llNotSearching?.visibility = View.GONE
                binding?.llNoDataAvailable?.visibility = View.VISIBLE
            } else {
                adapter.setListUser(data)
                binding?.rvUser?.visibility = View.VISIBLE
                binding?.llNotSearching?.visibility = View.GONE
                binding?.llNoDataAvailable?.visibility = View.GONE
            }
            showLoading(false)
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.rvUser?.visibility = View.GONE
            binding?.llNotSearching?.visibility = View.GONE
            binding?.shimmerViewContainer?.startShimmer()
            binding?.shimmerViewContainer?.visibility = View.VISIBLE
        } else {
            binding?.shimmerViewContainer?.stopShimmer()
            binding?.shimmerViewContainer?.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                viewModel.searchUserByUsername(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                requireView().findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
                true
            }
            R.id.menu_setting -> {
                requireView().findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.shimmerViewContainer?.startShimmer()
    }

    override fun onPause() {
        binding?.shimmerViewContainer?.stopShimmer()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}