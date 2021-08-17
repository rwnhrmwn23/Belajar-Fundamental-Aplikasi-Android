package com.onedev.dicoding.submission_one.repository

import android.app.Application
import android.content.res.TypedArray
import androidx.lifecycle.MutableLiveData
import com.onedev.dicoding.submission_one.R
import com.onedev.dicoding.submission_one.model.Users

class MainRepository(private val application: Application) {
    private lateinit var dataAvatar: TypedArray
    private lateinit var dataCompany: Array<String>
    private lateinit var dataFollower: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataName: Array<String>
    private lateinit var dataRepository: Array<String>
    private lateinit var dataUsername: Array<String>
    private var users = arrayListOf<Users>()

    val showProgress = MutableLiveData<Boolean>()
    val usersData = MutableLiveData<List<Users>>()

    fun loadUsersData() {
        showProgress.value = true

        dataAvatar = application.resources.obtainTypedArray(R.array.avatar)
        dataCompany = application.resources.getStringArray(R.array.company)
        dataFollower = application.resources.getStringArray(R.array.followers)
        dataFollowing = application.resources.getStringArray(R.array.following)
        dataLocation = application.resources.getStringArray(R.array.location)
        dataName = application.resources.getStringArray(R.array.name)
        dataRepository = application.resources.getStringArray(R.array.repository)
        dataUsername = application.resources.getStringArray(R.array.username)

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
            showProgress.value = false
        }
        usersData.value = users
    }
}