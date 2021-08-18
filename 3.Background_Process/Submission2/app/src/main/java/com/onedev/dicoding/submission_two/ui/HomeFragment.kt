package com.onedev.dicoding.submission_two.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onedev.dicoding.submission_two.R
import com.onedev.dicoding.submission_two.adapter.UserAdapter
import com.onedev.dicoding.submission_two.databinding.FragmentHomeBinding
import com.onedev.dicoding.submission_two.model.ItemSearchUser
import com.onedev.dicoding.submission_two.util.Constant
import com.onedev.dicoding.submission_two.util.PreferenceManager
import com.onedev.dicoding.submission_two.util.Support
import com.onedev.dicoding.submission_two.viewmodel.MainViewModel
import java.lang.Exception

class HomeFragment : Fragment() {

    companion object {
        private const val STATE_RESULT = "state_result"
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var preferenceManager: PreferenceManager
    private var usernameSearch: String? = null
    private var searchView: SearchView? = null
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
        Support.showActionBar(requireActivity())

        preferenceManager = PreferenceManager(requireContext())

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT)
            if (result != null) {
                searchUserByUsername(result)
            }
        }

        adapter = UserAdapter()

        setHasOptionsMenu(true)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        try {
            usernameSearch = preferenceManager.getString(Constant.USERNAME_SEARCH)
            if (usernameSearch != null) {
                outState.putString(STATE_RESULT, usernameSearch)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView?.queryHint = resources.getString(R.string.search_hint)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                preferenceManager.putString(Constant.USERNAME_SEARCH, query)
                searchUserByUsername(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menu_setting) {
            requireView().findNavController().navigate(R.id.action_homeFragment_to_settingFragment)
            true
        } else {
            false
        }
    }

    private fun searchUserByUsername(username: String) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.searchUserByUsername(username)

        viewModel.showProgress.observe(viewLifecycleOwner, { showProgress ->

            viewModel.usersData.observe(viewLifecycleOwner, { data ->
                if (showProgress == true && data == null) {
                    showLoadingProgress()
                } else if (showProgress == false && data == null) {
                    showDataNotFound()
                } else if (showProgress == false && data != null) {
                    showDataFound(data)
                }
            })
        })
    }

    private fun showDataFound(data: List<ItemSearchUser>) {
        adapter.setListUser(data)
        binding.rvUser.adapter = adapter
        binding.rvUser.visibility = View.VISIBLE
        binding.llNoDataAvailable.visibility = View.GONE
        binding.shimmerViewContainer.visibility = View.GONE
        binding.shimmerViewContainer.stopShimmerAnimation()
    }

    private fun showDataNotFound() {
        binding.rvUser.visibility = View.GONE
        binding.shimmerViewContainer.visibility = View.GONE
        binding.shimmerViewContainer.stopShimmerAnimation()
        binding.llNoDataAvailable.visibility = View.VISIBLE
    }

    private fun showLoadingProgress() {
        binding.rvUser.visibility = View.GONE
        binding.llNotSearching.visibility = View.GONE
        binding.llNoDataAvailable.visibility = View.GONE
        binding.shimmerViewContainer.visibility = View.VISIBLE
        binding.shimmerViewContainer.startShimmerAnimation()
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