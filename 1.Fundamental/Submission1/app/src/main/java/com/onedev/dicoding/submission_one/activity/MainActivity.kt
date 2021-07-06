package com.onedev.dicoding.submission_one.activity

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.onedev.dicoding.submission_one.R
import com.onedev.dicoding.submission_one.adapter.UserAdapter
import com.onedev.dicoding.submission_one.databinding.ActivityMainBinding
import com.onedev.dicoding.submission_one.model.Users

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var dataAvatar: TypedArray
    private lateinit var dataCompany: Array<String>
    private lateinit var dataFollower: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataName: Array<String>
    private lateinit var dataRepository: Array<String>
    private lateinit var dataUsername: Array<String>
    private var users = arrayListOf<Users>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataAvatar = resources.obtainTypedArray(R.array.avatar)
        dataCompany = resources.getStringArray(R.array.company)
        dataFollower = resources.getStringArray(R.array.followers)
        dataFollowing = resources.getStringArray(R.array.following)
        dataLocation = resources.getStringArray(R.array.location)
        dataName = resources.getStringArray(R.array.name)
        dataRepository = resources.getStringArray(R.array.repository)
        dataUsername = resources.getStringArray(R.array.username)

        binding.imgLinearLayout.setOnClickListener(this)
        binding.imgGridLayout.setOnClickListener(this)

        loadData("view_linear")
    }

    private fun loadData(viewType: String) {
        if (viewType == "view_linear") {
            adapter = UserAdapter(this, viewType)
            binding.rvUser.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            binding.rvUser.adapter = adapter
        } else {
            adapter = UserAdapter(this, viewType)
            binding.rvUser.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
            binding.rvUser.adapter = adapter
        }

        addItem()
    }

    private fun addItem() {
        users.clear()
        for (item in dataUsername.indices) {
            val user = Users(
                dataAvatar.getResourceId(item,-1),
                dataCompany[item],
                dataFollower[item],
                dataFollowing[item],
                dataLocation[item],
                dataName[item],
                dataRepository[item],
                dataUsername[item]
            )
            users.add(user)
        }
        adapter.listUsers = users
    }

    override fun onClick(v: View?) {
        when(v) {
            binding.imgLinearLayout -> loadData("view_linear")
            binding.imgGridLayout -> loadData("view_grid")
        }
    }
}