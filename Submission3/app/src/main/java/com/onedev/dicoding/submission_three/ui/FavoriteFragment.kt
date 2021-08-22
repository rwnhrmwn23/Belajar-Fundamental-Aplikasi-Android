package com.onedev.dicoding.submission_three.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.onedev.dicoding.submission_three.R
import com.onedev.dicoding.submission_three.adapter.FavoriteAdapter
import com.onedev.dicoding.submission_three.databinding.FragmentFavoriteBinding
import com.onedev.dicoding.submission_three.provider.UserProvider
import com.onedev.dicoding.submission_three.provider.UserProvider.Companion.CONTENT_URI
import com.onedev.dicoding.submission_three.util.IFavorite
import com.onedev.dicoding.submission_three.util.MappingHelper
import com.onedev.dicoding.submission_three.util.Support
import com.onedev.dicoding.submission_three.viewmodel.FavoriteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment(), IFavorite {

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var urlWithId: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter = FavoriteAdapter(this)
        binding?.rvFavorite?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvFavorite?.setHasFixedSize(true)
        binding?.rvFavorite?.adapter = adapter

        val handleThread = HandlerThread("DataObserver")
        handleThread.start()
        val handler = Handler(handleThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadFavoriteAsync()
            }
        }
        activity?.contentResolver?.registerContentObserver(CONTENT_URI, true, myObserver)

        loadFavoriteAsync()

//        viewModel.selectAllFavorite.observe(viewLifecycleOwner, {
//            if (it.isNotEmpty()) {
//                adapter.setListUser(it as ArrayList<ItemUser>)
//                binding?.rvFavorite?.visibility = View.VISIBLE
//                binding?.llNoDataAvailable?.visibility = View.GONE
//            } else {
//                setHasOptionsMenu(false)
//                binding?.rvFavorite?.visibility = View.GONE
//                binding?.llNoDataAvailable?.visibility = View.VISIBLE
//            }
//            showLoading(false)
//        })
    }

    private fun loadFavoriteAsync() {
        lifecycleScope.launch(Dispatchers.Main) {
            showLoading(true)
            val deferredFavorite = async(Dispatchers.IO) {
                // CONTENT_URI = content://com.onedev.dicoding.submission_three/favorite_database
                val cursor = activity?.contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            showLoading(false)
            val favoriteList = deferredFavorite.await()

            if (favoriteList.size > 0) {
                adapter.setListUser(favoriteList)
                binding?.rvFavorite?.visibility = View.VISIBLE
                binding?.llNoDataAvailable?.visibility = View.GONE
            } else {
                setHasOptionsMenu(false)
                binding?.rvFavorite?.visibility = View.GONE
                binding?.llNoDataAvailable?.visibility = View.VISIBLE
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                lifecycleScope.launch(Dispatchers.IO) {
                    activity?.contentResolver?.delete(CONTENT_URI, null, null)
                }
                Support.showSnackBar(requireView(), getString(R.string.success_delete_all_favorite))
            }
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setTitle(getString(R.string.delete_all_favorite))
            .setMessage(getString(R.string.confirmation_delete_all_favorite))
            .create()
        builder.show()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.shimmerViewContainer?.startShimmer()
            binding?.shimmerViewContainer?.visibility = View.VISIBLE
        } else {
            binding?.shimmerViewContainer?.stopShimmer()
            binding?.shimmerViewContainer?.visibility = View.GONE
        }
    }

    override fun deleteFavorite(status: Boolean, userId: Int) {
        if (status) {
            val urlWithId = Uri.parse("$CONTENT_URI/${userId}")
            lifecycleScope.launch(Dispatchers.IO) {
                activity?.contentResolver?.delete(urlWithId, null, null)
            }
            Support.showSnackBar(requireView(), getString(R.string.success_delete_favorite))
        }
    }
}