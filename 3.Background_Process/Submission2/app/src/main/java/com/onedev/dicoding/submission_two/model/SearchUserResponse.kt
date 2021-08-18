package com.onedev.dicoding.submission_two.model

data class SearchUserResponse(
    val incomplete_results: Boolean,
    val items: ArrayList<ItemSearchUser>,
    val total_count: Int
)