package com.onedev.dicoding.submission_three.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.onedev.dicoding.submission_three.model.FollowersAndFollowing
import com.onedev.dicoding.submission_three.model.ItemSearchUser
import com.onedev.dicoding.submission_three.model.ItemDetailUser
import com.onedev.dicoding.submission_three.repository.MainRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MainRepository(application)
    val showProgress: LiveData<Boolean>
    val usersData: LiveData<ArrayList<ItemSearchUser>>
    val userDetail: LiveData<ItemDetailUser>
    val followerData: LiveData<FollowersAndFollowing>
    val followingData: LiveData<FollowersAndFollowing>

    init {
        this.showProgress = repository.showProgress
        this.usersData = repository.usersData
        this.userDetail = repository.usersDetail
        this.followerData = repository.followerData
        this.followingData = repository.followingData
    }

    fun searchUserByUsername(username: String) {
        repository.searchUserByUsername(username)
    }

    fun getUserDetail(username: String) {
        repository.getUserDetail(username)
    }

    fun getFollowers(username: String) {
        repository.getFollowers(username)
    }

    fun getFollowing(username: String) {
        repository.getFollowing(username)
    }
}