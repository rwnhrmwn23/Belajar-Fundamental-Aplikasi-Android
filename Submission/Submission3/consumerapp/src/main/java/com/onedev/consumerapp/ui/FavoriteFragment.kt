package com.onedev.consumerapp.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.database.ContentObserver
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.onedev.consumerapp.R
import com.onedev.consumerapp.adapter.FavoriteAdapter
import com.onedev.consumerapp.databinding.FragmentFavoriteBinding
import com.onedev.consumerapp.interfaces.IFavorite
import com.onedev.consumerapp.helper.MappingHelper
import com.onedev.consumerapp.helper.SupportHelper
import com.onedev.consumerapp.helper.SupportHelper.CONTENT_URI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment(), IFavorite {

    private lateinit var adapter: FavoriteAdapter
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

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
    }

    private fun loadFavoriteAsync() {
        lifecycleScope.launch(Dispatchers.Main) {
            showLoading(true)
            val deferredFavorite = async(Dispatchers.IO) {
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
                SupportHelper.showSnackBar(requireView(), getString(R.string.success_delete_all_favorite))
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
            SupportHelper.showSnackBar(requireView(), getString(R.string.success_delete_favorite))
        }
    }
}