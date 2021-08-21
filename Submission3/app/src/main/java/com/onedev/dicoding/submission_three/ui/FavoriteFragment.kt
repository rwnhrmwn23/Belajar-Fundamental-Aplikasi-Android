package com.onedev.dicoding.submission_three.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.onedev.dicoding.submission_three.R
import com.onedev.dicoding.submission_three.adapter.FavoriteAdapter
import com.onedev.dicoding.submission_three.databinding.FragmentFavoriteBinding
import com.onedev.dicoding.submission_three.model.ItemUser
import com.onedev.dicoding.submission_three.util.IFavorite
import com.onedev.dicoding.submission_three.util.Support
import com.onedev.dicoding.submission_three.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment(), IFavorite {

    private lateinit var viewModel: FavoriteViewModel
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter = FavoriteAdapter(this)
        adapter.notifyDataSetChanged()
        binding?.rvFavorite?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvFavorite?.adapter = adapter

        showLoading(true)
        viewModel.selectAllFavorite.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                adapter.setListUser(it as ArrayList<ItemUser>)
                binding?.rvFavorite?.visibility = View.VISIBLE
                binding?.llNoDataAvailable?.visibility = View.GONE
            } else {
                setHasOptionsMenu(false)
                binding?.rvFavorite?.visibility = View.GONE
                binding?.llNoDataAvailable?.visibility = View.VISIBLE
            }
            showLoading(false)
        })
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
                viewModel.deleteAllFavorite()
                Toast.makeText(requireContext(), getString(R.string.success_delete_all_favorite), Toast.LENGTH_SHORT).show()
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

    override fun deleteFavorite(status: Boolean, username: String) {
        if (status) {
            viewModel.selectSpecificFavorite(username)
            viewModel.itemUser.observe(viewLifecycleOwner, { itemUser ->
                if (itemUser != null) {
                    viewModel.deleteFavorite(itemUser)
                    Support.showSnackBar(requireView(), getString(R.string.success_delete_favorite))
                }
            })
        }
    }
}