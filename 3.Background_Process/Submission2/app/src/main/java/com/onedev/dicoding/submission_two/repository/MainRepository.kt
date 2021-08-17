package com.onedev.dicoding.submission_two.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.onedev.dicoding.submission_two.MyApplication
import com.onedev.dicoding.submission_two.di.RetroService
import com.onedev.dicoding.submission_two.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainRepository(application: Application) {

    @Inject
    lateinit var mService: RetroService

    init {
        (application as MyApplication).getRetroComponent().inject(this)
    }

    val showProgress = MutableLiveData<Boolean>()
    val usersData = MutableLiveData<List<ItemSearchUser>>()
    val usersDetail = MutableLiveData<ItemDetailUser>()
    val followerAndFollowing = MutableLiveData<FollowersAndFollowing>()

    fun searchUserByUsername(username: String) {
        usersData.value = null
        showProgress.value = true
        mService.searchUserByUsername(username)
            .enqueue(object : Callback<SearchUserResponse> {
                override fun onResponse(call: Call<SearchUserResponse>, response: Response<SearchUserResponse>) {
                    if (response.isSuccessful) {
                        if (response.body()!!.total_count > 0)
                            usersData.value = response.body()!!.items
                        else
                            usersData.value = null
                    }

                    showProgress.value = false
                    Log.d(TAG, "onResponse searchUserByUsername: ${Gson().toJson(response.body())}")
                }

                override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                    showProgress.value = false
                    Log.d(TAG, "onFailure searchUserByUsername: ${t.localizedMessage}")
                }
            })
    }

    fun getUserDetail(username: String) {
        mService.getDetailUser(username)
            .enqueue(object : Callback<ItemDetailUser> {
                override fun onResponse(call: Call<ItemDetailUser>, response: Response<ItemDetailUser>) {
                    if (response.isSuccessful)
                        usersDetail.value = response.body()!!
                    else
                        usersData.value = null

                    Log.d(TAG, "onResponse getUserDetail: ${Gson().toJson(response.body())}")
                }

                override fun onFailure(call: Call<ItemDetailUser>, t: Throwable) {
                    Log.d(TAG, "onFailure getUserDetail: ${t.localizedMessage}")
                }
            })
    }

    fun getFollowers(username: String) {
        mService.getFollowers(username)
            .enqueue(object : Callback<FollowersAndFollowing> {
                override fun onResponse(call: Call<FollowersAndFollowing>, response: Response<FollowersAndFollowing>) {
                    if (response.isSuccessful)
                        followerAndFollowing.value = response.body()!!
                    else
                        followerAndFollowing.value = null

                    Log.d(TAG, "onResponse getFollowers: ${Gson().toJson(response.body())}")
                }

                override fun onFailure(call: Call<FollowersAndFollowing>, t: Throwable) {
                    Log.d(TAG, "onFailure getFollowers: ${t.localizedMessage}")
                }
            })
    }

    fun getFollowing(username: String) {
        mService.getFollowing(username)
            .enqueue(object : Callback<FollowersAndFollowing> {
                override fun onResponse(call: Call<FollowersAndFollowing>, response: Response<FollowersAndFollowing>) {
                    if (response.isSuccessful)
                        followerAndFollowing.value = response.body()!!
                    else
                        followerAndFollowing.value = null

                    Log.d(TAG, "onResponse getFollowing: ${Gson().toJson(response.body())}")
                }

                override fun onFailure(call: Call<FollowersAndFollowing>, t: Throwable) {
                    Log.d(TAG, "onFailure getFollowing: ${t.localizedMessage}")
                }
            })
    }

    companion object {
        private const val TAG = "MainRepository"
    }
}