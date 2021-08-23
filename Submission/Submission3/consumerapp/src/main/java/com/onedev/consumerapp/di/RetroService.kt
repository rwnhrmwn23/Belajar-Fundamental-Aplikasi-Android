package com.onedev.consumerapp.di

import com.onedev.consumerapp.model.FollowersAndFollowing
import com.onedev.consumerapp.model.ItemDetailUser
import com.onedev.consumerapp.model.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroService {
    @GET("search/users")
    fun searchUserByUsername(
        @Query("q") username: String
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<ItemDetailUser>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<FollowersAndFollowing>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<FollowersAndFollowing>
}