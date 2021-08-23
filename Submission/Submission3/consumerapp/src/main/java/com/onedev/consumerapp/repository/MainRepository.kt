package com.onedev.consumerapp.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.onedev.consumerapp.MyApplication
import com.onedev.consumerapp.di.RetroService
import com.onedev.consumerapp.model.*
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
    val usersData = MutableLiveData<ArrayList<ItemUser>>()
    val usersDetail = MutableLiveData<ItemDetailUser>()
    val followerData = MutableLiveData<FollowersAndFollowing>()
    val followingData = MutableLiveData<FollowersAndFollowing>()

    fun searchUserByUsername(username: String) {
        mService.searchUserByUsername(username)
            .enqueue(object : Callback<SearchUserResponse> {
                override fun onResponse(call: Call<SearchUserResponse>, response: Response<SearchUserResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.total_count?.let {
                            if (it > 0)
                                 usersData.postValue(response.body()?.items)
                            else
                                usersData.postValue(null)
                        }
                    }
                    Log.d(TAG, "onResponse searchUserByUsername: ${Gson().toJson(response.body())}")
                }

                override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure searchUserByUsername: ${t.localizedMessage}")
                }
            })
    }

    fun getUserDetail(username: String) {
        showProgress.value = true
        mService.getDetailUser(username)
            .enqueue(object : Callback<ItemDetailUser> {
                override fun onResponse(call: Call<ItemDetailUser>, response: Response<ItemDetailUser>) {
                    if (response.isSuccessful)
                        usersDetail.postValue(response.body())
                    else
                        usersData.postValue(null)

                    showProgress.value = false
                    Log.d(TAG, "onResponse getUserDetail: ${Gson().toJson(response.body())}")
                }

                override fun onFailure(call: Call<ItemDetailUser>, t: Throwable) {
                    showProgress.value = false
                    Log.d(TAG, "onFailure getUserDetail: ${t.localizedMessage}")
                }
            })
    }

    fun getFollowers(username: String) {
        showProgress.value = true
        mService.getFollowers(username)
            .enqueue(object : Callback<FollowersAndFollowing> {
                override fun onResponse(call: Call<FollowersAndFollowing>, response: Response<FollowersAndFollowing>) {
                    if (response.isSuccessful)
                        followerData.postValue(response.body())
                    else
                        followerData.postValue(null)

                    showProgress.value = false
                    Log.d(TAG, "onResponse getFollowers: ${Gson().toJson(response.body())}")
                }

                override fun onFailure(call: Call<FollowersAndFollowing>, t: Throwable) {
                    showProgress.value = false
                    Log.d(TAG, "onFailure getFollowers: ${t.localizedMessage}")
                }
            })
    }

    fun getFollowing(username: String) {
        showProgress.value = true
        mService.getFollowing(username)
            .enqueue(object : Callback<FollowersAndFollowing> {
                override fun onResponse(call: Call<FollowersAndFollowing>, response: Response<FollowersAndFollowing>) {
                    if (response.isSuccessful)
                        followingData.postValue(response.body())
                    else
                        followingData.postValue(null)

                    showProgress.value = false
                    Log.d(TAG, "onResponse getFollowing: ${Gson().toJson(response.body())}")
                }

                override fun onFailure(call: Call<FollowersAndFollowing>, t: Throwable) {
                    showProgress.value = false
                    Log.d(TAG, "onFailure getFollowing: ${t.localizedMessage}")
                }
            })
    }

    companion object {
        private const val TAG = "MainRepository"
    }
}