package com.onedev.dicoding.submission_three.model

data class SearchUserResponse(
    val incomplete_results: Boolean,
    val items: ArrayList<ItemSearchUser>,
    val total_count: Int
)