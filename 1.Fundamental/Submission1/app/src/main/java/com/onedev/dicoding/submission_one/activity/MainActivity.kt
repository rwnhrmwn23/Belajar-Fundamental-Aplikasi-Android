package com.onedev.dicoding.submission_one.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.onedev.dicoding.submission_one.adapter.UserAdapter
import com.onedev.dicoding.submission_one.databinding.ActivityMainBinding
import com.onedev.dicoding.submission_one.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        binding.rvUser.setHasFixedSize(true)

        loadData()
    }

    private fun loadData() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.loadUsersData()

        viewModel.showProgress.observe(this, {
            if (it == true)
                binding.shimmerViewContainer.startShimmerAnimation()
            else
                binding.shimmerViewContainer.stopShimmerAnimation()
        })

        viewModel.usersData.observe(this, {
            if (it != null) {
                adapter.setListUser(it)
                binding.rvUser.layoutManager = LinearLayoutManager(this)
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

    override fun onDestroy() {
        binding.shimmerViewContainer.stopShimmerAnimation()
        super.onDestroy()
    }
}